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
    float mTextPadding = 0f;
    float mTextSize = 15f;
    Typeface mTextTypeFace;
    String mtextViewValue = "";

    //type codes

    WebView mCodeView;
    String mSourceCode = "", mCodeLanguage = "java";
    float mCodeViewPadding = 0f;

    //type equation
    MathView equationView;
    int mEquationTextColor = Color.BLACK;
    String mEquation = "";
    int mEquationViewBackgroundColor = Color.WHITE;
    float mEquationPadding = 0f, mEquationTextSize = 0f;


    int mBackground = Color.WHITE;
    String mViewType = "text";
    int mSelectedBackground;


    // Constants
    public static final int DEFAULT_TEXT_COLOR = Color.TRANSPARENT;



    // Variables
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
                R.styleable.slackWiseRadioButton, 0, 0);
        Resources resources = getContext().getResources();
        try {

            mtextViewValue = a.getString(R.styleable.slackWiseRadioButton_setText);
            mTextSize = a.getDimension(R.styleable.slackWiseRadioButton_setTextSize,15f);
            mTextColor = a.getColor(R.styleable.slackWiseRadioButton_setTextColor,Color.WHITE);
            mTextPadding = a.getDimension(R.styleable.slackWiseRadioButton_setTextPadding,0f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTextTypeFace = a.getFont(R.styleable.slackWiseRadioButton_setTypfeFace);
            }
            mEquation = a.getString(R.styleable.slackWiseRadioButton_setEquation);
            mEquationTextSize = a.getDimension(R.styleable.slackWiseRadioButton_setEquationTextSize,15f);
            mEquationTextColor = a.getColor(R.styleable.slackWiseRadioButton_setEquationColor,Color.BLACK);
            mEquationPadding = a.getDimension(R.styleable.slackWiseRadioButton_setEquationPadding,0f);
            mEquationViewBackgroundColor = a.getColor(R.styleable.slackWiseRadioButton_setEquationViewBackgroundColor,Color.BLACK);

            mCodeViewPadding = a.getDimension(R.styleable.slackWiseRadioButton_setCodePadding,0f);
            mSourceCode = a.getString(R.styleable.slackWiseRadioButton_setSourceCode);
            mCodeLanguage = a.getString(R.styleable.slackWiseRadioButton_setLang);

            mViewType = a.getString(R.styleable.slackWiseRadioButton_setRadioButtonViewType);
            mBackground = a.getResourceId(R.styleable.slackWiseRadioButton_setBackground,R.color.White);
            mSelectedBackground = a.getResourceId(R.styleable.slackWiseRadioButton_setSelectedBackground,R.color.grey);
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

        if(mViewType.equalsIgnoreCase("text")){

           view =  inflater.inflate(R.layout.radio_type_text, this, true);
           textView = view.findViewById(R.id.text_view);
           textView.setTextSize(mTextSize);
           textView.setText(mtextViewValue);
           textView.setTypeface(mTextTypeFace);
           textView.setPadding((int)mTextPadding,(int)mTextPadding,(int)mTextPadding,(int)mTextPadding);
           textView.setTextColor(mTextColor);
        }
        else if(mViewType.equalsIgnoreCase("codes")){

            view =  inflater.inflate(R.layout.radio_type_codes, this, true);
            mCodeView  = view.findViewById(R.id.codes_view);

            Codeview.with(getContext())
                    .withCode(mSourceCode)
                    .setStyle(Settings.WithStyle.XCODE)
                    .setLang(Settings.Lang.JAVA)
                    .into(mCodeView);
        }
        else if(mViewType.equalsIgnoreCase("equations")){

            view =  inflater.inflate(R.layout.radio_type_equations, this, true);
            equationView = view.findViewById(R.id.equations_view);
            equationView.setViewBackgroundColor(mEquationViewBackgroundColor);
            equationView.setTextSize((int)mEquationTextSize);
            equationView.setTextColor(mEquationTextColor);
            equationView.setDisplayText(mEquation);

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