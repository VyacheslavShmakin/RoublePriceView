package ru.shmakinv.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * PriceView
 *
 * @author Vyacheslav Shmakin
 * @version 02.04.2016
 */
public class PriceView extends LinearLayout {

    protected TextView mPriceView, mPriceViewLimiter, mRoubleView;
    protected String mValue;

    protected final int[] mAttrs = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor,
            android.R.attr.layout_gravity,
            android.R.attr.fontFamily};

    public PriceView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PriceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        inflate(context, R.layout.layout_price_view, this);

        mPriceView = findViewById(R.id.price);
        mPriceViewLimiter = findViewById(R.id.price_limiter);
        mRoubleView = findViewById(R.id.currency_sign);
        initResources(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void initResources(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null) {
            return;
        }
        initSystemAttributes(context, attrs, defStyleAttr, defStyleRes);
        initCustomAttributes(context, attrs);
    }

    protected void initSystemAttributes(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Arrays.sort(mAttrs);
        final TypedArray appearance = context.obtainStyledAttributes(attrs, mAttrs, defStyleAttr, defStyleRes);
        if (appearance != null) {
            int count = appearance.getIndexCount();

            for (int i = 0; i < count; i++) {
                int index = appearance.getIndex(i);
                switch (mAttrs[index]) {
                    case android.R.attr.textColor:
                        ColorStateList colorList = appearance.getColorStateList(index);
                        if (colorList != null) {
                            setTextColor(colorList);
                        } else {
                            setTextColor(appearance.getColor(index, 0));
                        }
                        break;
                    case android.R.attr.textSize:
                        setTextSize(appearance.getDimensionPixelSize(index, 0));
                        break;
                    case android.R.attr.fontFamily:
                        Typeface tf = getTypeface(appearance, index);
                        if (tf != null) {
                            setTypeface(tf);
                        }
                        break;
                }
            }
            appearance.recycle();
        }
    }

    protected void initCustomAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PriceView);
        if (attr != null) {
            int roublePadding = (int) attr.getDimension(
                    R.styleable.PriceView_pv_roublePadding,
                    getResources().getDimension(R.dimen.default_padding));

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mPriceView.getLayoutParams();
            params.gravity = attr.getInteger(R.styleable.PriceView_pv_price_gravity, Gravity.CENTER);
            mPriceView.setLayoutParams(params);

            mValue = attr.getString(R.styleable.PriceView_pv_value);
            int maxlength = attr.getInteger(
                    R.styleable.PriceView_pv_regionLength,
                    getResources().getInteger(R.integer.default_max_length));

            //noinspection ConstantConditions
            setValue(mValue);
            setRegionLength(maxlength);
            setRoublePadding(roublePadding);
            attr.recycle();
        }
    }

    @Nullable
    private Typeface getTypeface(@NonNull TypedArray array, int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return array.getFont(index);
        } else {
            return getTypefaceFromAttrs(array, index);
        }
    }

    @Nullable
    private Typeface getTypefaceFromAttrs(@NonNull TypedArray array, int index) {
        int id = array.getResourceId(index, -1);
        return ResourcesCompat.getFont(getContext(), id);
    }

    public void setTextSize(float sizePixels) {
        mPriceView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePixels);
        mPriceViewLimiter.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePixels);
        mRoubleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePixels);
    }

    public void setRegionLength(int signCount) {
        mPriceViewLimiter.setText(updateLimit(signCount));
    }

    @NonNull
    protected String updateLimit(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("8");
        }
        return sb.toString();
    }

    public void setRoublePadding(int padding) {
        mRoubleView.setPadding(padding, 0, 0, 0);
    }

    public void setTypeface(@NonNull Typeface typeface) {
        mPriceView.setTypeface(typeface);
        mRoubleView.setTypeface(typeface);
    }

    public void setTextColor(int textColor) {
        mPriceView.setTextColor(textColor);
        mRoubleView.setTextColor(textColor);
    }

    public void setTextColor(@NonNull ColorStateList stateList) {
        mPriceView.setTextColor(stateList);
        mRoubleView.setTextColor(stateList);
    }

    public void setValue(@NonNull String value) {
        mValue = value;
        mPriceView.setText(value);
    }

    @Nullable
    public String getValue() {
        return mValue;
    }
}
