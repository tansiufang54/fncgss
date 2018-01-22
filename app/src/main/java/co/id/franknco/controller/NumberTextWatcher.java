package co.id.franknco.controller;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by GSS-NB-2017-0013 on 12/15/2017.
 */

public class NumberTextWatcher extends android.support.v7.widget.AppCompatTextView {

    String rawText;

    public NumberTextWatcher(Context context) {
        super(context);

    }

    public NumberTextWatcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberTextWatcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        rawText = text.toString();
        String prezzo = text.toString();
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            prezzo = decimalFormat.format(Integer.parseInt(text.toString()));
        }catch (Exception e){}

        super.setText(prezzo, type);
    }

    @Override
    public CharSequence getText() {

        return rawText;
    }
}