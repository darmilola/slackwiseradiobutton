package com.slackwise.slackwisebuttongroup;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.protectsoft.webviewcode.Codeview;
import com.protectsoft.webviewcode.Settings;
import com.slackwise.slackwiseradiobutton.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import katex.hourglass.in.mathlib.MathView;


public class SlackWiseButton extends RelativeLayout implements CheckableRadio {


    String[] langArray = {"java","python","javascript","ruby","c#","php","sql","c++"};
    HashMap<String,String> map = new HashMap<String, String>();

    //type text
    TextView textView;
    int mTextColor = Color.BLACK;
    float mTextSize = 10f;
    Typeface mTextTypeFace;
    String mtextViewValue = "";

    //type codes

    WebView mCodeView;
    String mSourceCode = "", mCodeLanguage = "java";


    //type equation
    MathView equationView;
    int mEquationTextColor = Color.BLACK;
    String mEquation = "";
    int mEquationViewBackgroundColor = Color.WHITE;
    float mEquationTextSize = 0f;



    String mViewType = "1";
    int mSelectedBackground;
    float padding;
    int selectedTextColor;






    private Drawable mInitialBackgroundDrawable;
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;
    private boolean mChecked;
    private ArrayList<CheckableRadio.OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();


    //================================================================================
    // Constructors
    //================================================================================

    public SlackWiseButton(Context context) {
        super(context);
        map.put("text","1");
        map.put("code","2");
        map.put("equation","3");
        setupView();
    }

    public SlackWiseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        map.put("text","1");
        map.put("code","2");
        map.put("equation","3");
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public SlackWiseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        map.put("text","1");
        map.put("code","2");
        map.put("equation","3");
        setupView();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlackWiseButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        map.put("text","1");
        map.put("code","2");
        map.put("equation","3");
        setupView();

    }

    //================================================================================
    // Init & inflate methods
    //================================================================================

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.slk, 0, 0);
        Resources resources = getContext().getResources();
        try {

            mtextViewValue = a.getString(R.styleable.slk_setText);
            mTextSize = a.getDimension(R.styleable.slk_setTextSize,5f);
            mTextColor = a.getColor(R.styleable.slk_setTextColor,Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTextTypeFace = a.getFont(R.styleable.slk_setTypeFace);
            }
            mEquation = a.getString(R.styleable.slk_setEquation);
            mEquationTextSize = a.getDimension(R.styleable.slk_setEquationSize,15f);
            mEquationTextColor = a.getColor(R.styleable.slk_setEquationColor,Color.BLACK);
            padding = a.getDimension(R.styleable.slk_setPadding,0f);
            selectedTextColor = a.getColor(R.styleable.slk_selectedTextColor,Color.GREEN);
            mEquationViewBackgroundColor = a.getColor(R.styleable.slk_setEquationViewBackgroundColor,Color.BLACK);
            mSourceCode = a.getString(R.styleable.slk_setSourceCode);
            mCodeLanguage = a.getString(R.styleable.slk_setLang);

            mViewType = a.getString(R.styleable.slk_setRadioButtonViewType);
            mSelectedBackground = a.getResourceId(R.styleable.slk_setSelectedBackground,R.color.grey);

                    } finally {
            a.recycle();
        }
    }

    // Template method
    private void setupView() {
        inflateAndBindView();
        setCustomTouchListener();
    }

    protected void inflateAndBindView() {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        float  dpPadding = PixelsToDp(padding,getContext());

        if(mViewType == null){



        }
        else if(mViewType.equalsIgnoreCase("1")){

           view =  inflater.inflate(R.layout.type_text, this, true);

           //float pxTextSize = GetPxEquivalent(mTextSize);
           float dpTextSize = PixelsToDp(mTextSize,getContext());
           textView = view.findViewById(R.id.text_view);
           textView.setTextSize(dpTextSize);
           textView.setText(mtextViewValue);
           textView.setTypeface(mTextTypeFace);
           textView.setTextColor(mTextColor);
           setPadding((int)dpPadding,(int)dpPadding,(int)dpPadding,(int)dpPadding);
        }
        else if(mViewType.equalsIgnoreCase("2")){

            view =  inflater.inflate(R.layout.type_codes, this, true);
            mCodeView  = view.findViewById(R.id.codes_view);
            String langFromArray;
            try {
                 langFromArray = langArray[Integer.parseInt(mCodeLanguage)-1];
            }
            catch (NumberFormatException e){

                langFromArray = mCodeLanguage;
            }


            Codeview.with(getContext())
                    .withCode(mSourceCode)
                    .setStyle(Settings.WithStyle.XCODE)
                    .setLang(langFromArray)
                    .setAutoWrap(true)
                    .into(mCodeView);
            setPadding((int)dpPadding,(int)dpPadding,(int)dpPadding,(int)dpPadding);

        }
        else if(mViewType.equalsIgnoreCase("3")){

            view =  inflater.inflate(R.layout.type_equations, this, true);

            float dpEquationSize = PixelsToDp(mEquationTextSize,getContext());
            equationView = view.findViewById(R.id.equations_view);
            equationView.setViewBackgroundColor(mEquationViewBackgroundColor);
            equationView.setTextSize((int)dpEquationSize);
            equationView.setTextColor(mEquationTextColor);
            equationView.setDisplayText(mEquation);
            setPadding((int)dpPadding,(int)dpPadding,(int)dpPadding,(int)dpPadding);

        }
        else{

            view =  inflater.inflate(R.layout.type_text, this, true);


            //float pxTextSize = GetPxEquivalent(mTextSize);

            float dpTextSize = PixelsToDp(mTextSize,getContext());
            textView = view.findViewById(R.id.text_view);
            textView.setTextSize(dpTextSize);
            textView.setText(mtextViewValue);
            textView.setTypeface(mTextTypeFace);
            textView.setTextColor(mTextColor);
            setPadding((int)dpPadding,(int)dpPadding,(int)dpPadding,(int)dpPadding);

        }

        mInitialBackgroundDrawable = getBackground();
    }




    //================================================================================
    // Overriding default behavior
    //================================================================================


    public  float PixelsToDp(float px, Context context){

        return  px/((float)context.getResources().getDisplayMetrics().densityDpi/ DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
    }

    protected void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public void setSourceCode(String mSourceCode) {
        this.mSourceCode = mSourceCode;

        String langFromArray;
        try {
            langFromArray = langArray[Integer.parseInt(mCodeLanguage)-1];
        }
        catch (NumberFormatException e){

            langFromArray = mCodeLanguage;
        }
        Codeview.with(getContext())
                .withCode(mSourceCode)
                .setStyle(Settings.WithStyle.XCODE)
                .setLang(langFromArray)
                .into(mCodeView);
    }

    public void setCodeLanguage(String mCodeLanguage) {

        String lang = mCodeLanguage;
        Codeview.with(getContext())
                .withCode(mSourceCode)
                .setStyle(Settings.WithStyle.XCODE)
                .setLang(lang)
                .into(mCodeView);
        this.mCodeLanguage = mCodeLanguage;
    }

    public void setViewType(String mViewType) {
        this.mViewType = map.get(mViewType);
        setupView();
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;

        if(textView != null){

            textView.setTextColor(mTextColor);
        }

    }

    public void setTextSize(float mTextSize) {

        this.mTextSize = mTextSize;
        if(textView != null){

            textView.setTextSize(mTextSize);
        }

    }


    public void setTextTypeFace(Typeface mTextTypeFace) {
        this.mTextTypeFace = mTextTypeFace;
        if(textView != null){

            textView.setTypeface(mTextTypeFace);
        }

    }

    public void setPadding(float padding) {
        this.padding = padding;

        setPadding((int)padding,(int)padding,(int)padding,(int)padding);
    }

    public void setTextViewValue(String mtextViewValue) {

        this.mtextViewValue = mtextViewValue;
        if(textView != null){

            textView.setText(mtextViewValue);

        }
    }

    public void setSelectedBackground(int mSelectedBackground) {

        this.mSelectedBackground = mSelectedBackground;

    }

    public void setSelectedTextColor(int selectedTextColor) {

        this.selectedTextColor = selectedTextColor;

    }

    public void setEquation(String mEquation) {

        this.mEquation = mEquation;

        if(equationView != null){


            equationView.setDisplayText(mEquation);

        }
    }

    public void setEquationTextColor(int mEquationTextColor) {

        this.mEquationTextColor = mEquationTextColor;

        if(equationView != null){

            equationView.setTextColor(mEquationTextColor);
        }
    }

    public void setEquationTextSize(float mEquationTextSize) {

       // float pxSize = GetPxEquivalent(mEquationTextSize);
        this.mEquationTextSize = mEquationTextSize;
        equationView.setTextSize((int) mEquationTextSize);
    }

    public void setEquationViewBackgroundColor(int mEquationViewBackgroundColor) {

        this.mEquationViewBackgroundColor = mEquationViewBackgroundColor;

        if(equationView != null){

            equationView.setViewBackgroundColor(mEquationViewBackgroundColor);
        }
    }

    private float GetPxEquivalent(float dp){
        float px;
        float density = getContext().getResources().getDisplayMetrics().density;
        return   px = Math.round((float) dp * density);


    }

    public OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    private void onTouchUp(MotionEvent motionEvent) {
        // Handle user defined click listeners
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
        }
    }
    //================================================================================
    // Public methods
    //================================================================================

    public void setCheckedState() {
        setBackgroundResource(mSelectedBackground);
        if(textView != null){

            textView.setTextColor(selectedTextColor);
        }
        if(equationView != null){

            equationView.setTextColor(selectedTextColor);
        }

    }

    public void setNormalState() {
        setBackground(mInitialBackgroundDrawable);

        if(textView != null){

            textView.setTextColor(mTextColor);
        }
        if(equationView != null){

            equationView.setTextColor(mEquationTextColor);
        }

    }



    //================================================================================
    // Checkable implementation
    //================================================================================

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if (!mOnCheckedChangeListeners.isEmpty()) {
                for (int i = 0; i < mOnCheckedChangeListeners.size(); i++) {
                    mOnCheckedChangeListeners.get(i).onCheckedChanged(this, mChecked);
                }
            }
            if (mChecked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);
    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private final class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(event);
                    break;
            }
            if (mOnTouchListener != null) {
                mOnTouchListener.onTouch(v, event);
            }
            return true;
        }
    }
}