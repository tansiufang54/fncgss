package co.id.franknco.controller;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by GSS-NB-2017-0013 on 12/19/2017.
 */

public class MyTextView2 extends AppCompatTextView {

    public MyTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView2(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/AvenirNextLTPro-Demi.otf");
        setTypeface(tf ,1);

    }
}