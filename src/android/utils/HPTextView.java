package com.hpe.hybridsitescope.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shlomi on 27/03/2015.
 */
public class HPTextView extends TextView {

    public HPTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public HPTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public HPTextView(Context context) {
        super(context);
        init();
    }


    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HPSimplified_Lt.ttf");
        setTypeface(tf);
    }
}