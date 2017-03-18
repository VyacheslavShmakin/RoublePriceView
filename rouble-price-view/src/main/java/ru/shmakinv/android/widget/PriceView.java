package ru.shmakinv.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.devspark.robototextview.RobotoTypefaces;
import com.devspark.robototextview.widget.RobotoTextView;

/**
 * PriceView
 *
 * @author Vyacheslav Shmakin
 * @version 02.04.2016
 */
public class PriceView extends LinearLayout {

    private RobotoTextView mPriceView, mPriceViewLimiter, mRoubleView;
    private String mValue;
    private float mTextSize;
    private int mRoublePadding, mMaxLength, mTextColor;

    public PriceView(Context context) {
        super(context);
        init(context, null);
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PriceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.layout_price_view, this);

        mPriceView = (RobotoTextView) findViewById(R.id.price);
        mPriceViewLimiter = (RobotoTextView) findViewById(R.id.price_limiter);
        mRoubleView = (RobotoTextView) findViewById(R.id.currency_sign);

        if (!isInEditMode()) {
            RobotoTypefaces.setUpTypeface(mPriceView, context, attrs);
            RobotoTypefaces.setUpTypeface(mPriceViewLimiter, context, attrs);
            RobotoTypefaces.setUpTypeface(mRoubleView, context, attrs);
        }
        initResources(context, attrs);
    }

    private void initResources(@NonNull Context context, @NonNull AttributeSet attrs) {
        int[] attributes = new int[]{android.R.attr.layout_gravity};
        final TypedArray sysAttr = context.obtainStyledAttributes(attrs, attributes);
        final TypedArray customAttrs = context.obtainStyledAttributes(attrs, R.styleable.PriceView);

        Resources res = getResources();
        int defPadding = 0;
        int defLength = 0;
        int defColor = 0;
        float defTextSize = 0;

        if (res != null) {
            defPadding = (int) res.getDimension(R.dimen.default_padding);
            defLength = res.getInteger(R.integer.default_max_length);
            defColor = ContextCompat.getColor(context, android.R.color.black);
            defTextSize = res.getDimension(R.dimen.default_font_size);
        }

        if (sysAttr != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mPriceView.getLayoutParams();
            params.gravity = sysAttr.getInteger(0, Gravity.CENTER);
            mPriceView.setLayoutParams(params);
            sysAttr.recycle();
        }

        if (customAttrs != null) {
            mRoublePadding = (int) customAttrs.getDimension(R.styleable.PriceView_roublePadding, defPadding);
            mValue = customAttrs.getString(R.styleable.PriceView_value);
            mTextSize = customAttrs.getDimension(R.styleable.PriceView_textSize, defTextSize);
            mTextColor = customAttrs.getColor(R.styleable.PriceView_textColor, defColor);
            mMaxLength = customAttrs.getInteger(R.styleable.PriceView_regionLength, defLength);

            //noinspection ConstantConditions
            setValue(mValue);
            setTextSize(mTextSize);
            setTextColor(mTextColor);
            setRegionLength(mMaxLength);
            setRoublePadding(mRoublePadding);
            customAttrs.recycle();
        }
    }

    public void setTextSize(float size) {
        mTextSize = size;
        mPriceView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        mPriceViewLimiter.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        mRoubleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setRegionLength(int signCount) {
        mMaxLength = signCount;
        mPriceViewLimiter.setText(updateLimit(signCount));
    }

    @NonNull
    private String updateLimit(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("8");
        }
        return sb.toString();
    }

    public void setRoublePadding(int padding) {
        mRoublePadding = padding;
        mRoubleView.setPadding(padding, 0, 0, 0);
    }

    public void setTypeface(@NonNull Typeface typeface) {
        RobotoTypefaces.setUpTypeface(mPriceView, typeface);
        RobotoTypefaces.setUpTypeface(mRoubleView, typeface);
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        mPriceView.setTextColor(textColor);
        mRoubleView.setTextColor(textColor);
    }

    public void setValue(@NonNull String value) {
        mValue = value;
        mPriceView.setText(value);
    }

    @Nullable
    public String getValue() {
        return mValue;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public int getRoublePadding() {
        return mRoublePadding;
    }

    public int getMaxLength() {
        return mMaxLength;
    }

    public int getTextColor() {
        return mTextColor;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        //end
        ss.value = this.mValue;
        ss.textSize = this.mTextSize;
        ss.roublePadding = this.mRoublePadding;
        ss.maxTextLength = this.mMaxLength;
        ss.color = this.mTextColor;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end
        setValue(ss.value);
        setTextSize(ss.textSize);
        setRoublePadding(ss.roublePadding);
        setRegionLength(ss.maxTextLength);
        setTextColor(ss.color);
    }

    static class SavedState extends BaseSavedState {
        String value;
        float textSize;
        int roublePadding, maxTextLength, color;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.value = in.readString();
            this.textSize = in.readFloat();
            this.roublePadding = in.readInt();
            this.maxTextLength = in.readInt();
            this.color = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.value);
            out.writeFloat(this.textSize);
            out.writeInt(this.roublePadding);
            out.writeInt(this.maxTextLength);
            out.writeInt(this.color);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
