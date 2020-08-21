package com.slackwise.slackwiseradiobutton;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.protectsoft.webviewcode.Codeview;
import com.protectsoft.webviewcode.Settings;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import katex.hourglass.in.mathlib.MathView;


public class SlackWiseRadioButton extends RelativeLayout implements CheckableRadio {

    //type text
    TextView textView;
    int mTextColor = Color.BLACK;
    float mTextSize = 15f;
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


    int mBackground = Color.WHITE;
    String mViewType = "text";
    int mSelectedBackground;
    float padding;
    int selectedTextColor;

    // Constants
    public static final int DEFAULT_TEXT_COLOR = Color.TRANSPARENT;




    private Drawable mInitialBackgroundDrawable;
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;
    private boolean mChecked;
    private ArrayList<CheckableRadio.OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();


    //================================================================================
    // Constructors
    //================================================================================

    public SlackWiseRadioButton(Context context) {
        super(context);
        setupView();
    }

    public SlackWiseRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public SlackWiseRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlackWiseRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.srb, 0, 0);
        Resources resources = getContext().getResources();
        try {

            mtextViewValue = a.getString(R.styleable.srb_setText);
            mTextSize = a.getDimension(R.styleable.srb_setTextSize,15f);
            mTextColor = a.getColor(R.styleable.srb_setTextColor,Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTextTypeFace = a.getFont(R.styleable.srb_setTypfeFace);
            }
            mEquation = a.getString(R.styleable.srb_setEquation);
            mEquationTextSize = a.getDimension(R.styleable.srb_setEquationSize,15f);
            mEquationTextColor = a.getColor(R.styleable.srb_setEquationColor,Color.BLACK);
            padding = a.getDimension(R.styleable.srb_setPadding,0f);
            selectedTextColor = a.getColor(R.styleable.srb_selectedTextColor,Color.GREEN);
            mEquationViewBackgroundColor = a.getColor(R.styleable.srb_setEquationViewBackgroundColor,Color.BLACK);
            mSourceCode = a.getString(R.styleable.srb_setSourceCode);
            mCodeLanguage = a.getString(R.styleable.srb_setLang);

            mViewType = a.getString(R.styleable.srb_setRadioButtonViewType);
            mBackground = a.getResourceId(R.styleable.srb_setBackground,R.color.White);
            mSelectedBackground = a.getResourceId(R.styleable.srb_setSelectedBackground,R.color.grey);
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

        if(mViewType.equalsIgnoreCase("1")){

           view =  inflater.inflate(R.layout.radio_type_text, this, true);
           textView = view.findViewById(R.id.text_view);
           textView.setTextSize(mTextSize);
           textView.setText(mtextViewValue);
           textView.setTypeface(mTextTypeFace);
           textView.setTextColor(mTextColor);
           //setPadding(padding,padding,padding,padding);
        }
        else if(mViewType.equalsIgnoreCase("2")){

            view =  inflater.inflate(R.layout.radio_type_codes, this, true);
            mCodeView  = view.findViewById(R.id.codes_view);

            Codeview.with(getContext())
                    .withCode(mSourceCode)
                    .setStyle(Settings.WithStyle.XCODE)
                    .setLang(Settings.Lang.JAVA)
                    .into(mCodeView);
        }
        else if(mViewType.equalsIgnoreCase("3")){

            view =  inflater.inflate(R.layout.radio_type_equations, this, true);
            equationView = view.findViewById(R.id.equations_view);
            equationView.setViewBackgroundColor(mEquationViewBackgroundColor);
            equationView.setTextSize((int)mEquationTextSize);
            equationView.setTextColor(mEquationTextColor);
            equationView.setDisplayText(mEquation);

        }
        else{

            view =  inflater.inflate(R.layout.radio_type_text, this, true);
            textView = view.findViewById(R.id.text_view);
            textView.setTextSize(mTextSize);
            textView.setText(mtextViewValue);
            textView.setTypeface(mTextTypeFace);
            textView.setTextColor(mTextColor);

        }
        mInitialBackgroundDrawable = getBackground();
    }


    //================================================================================
    // Overriding default behavior
    //================================================================================

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

        Codeview.with(getContext())
                .withCode(mSourceCode)
                .setStyle(Settings.WithStyle.XCODE)
                .setLang(Settings.Lang.JAVA)
                .into(mCodeView);
    }

    public void setCodeLanguage(String mCodeLanguage) {
        this.mCodeLanguage = mCodeLanguage;
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