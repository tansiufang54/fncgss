package co.id.franknco.dialog;

/**
 * Created by GSS-NB-2017-0013 on 12/6/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.id.franknco.R;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;

/**
 * Created by GSS-NB-2017-0013 on 12/4/2017.
 */

public class CustomDialogAdd_Card extends Dialog implements android.view.View.OnClickListener {
    private Activity activity;
    private Button btnPositive, btnNegative;
    private String lbl_btnPositive = "Ok", lbl_btnNegative = "Cancel";
    private String lbl_dialogTitle, lbl_dialogMsg, lbl_color = "";
    private TextView dialogTitle, dialogMsg;
    private EditText a, b, c, d;
    private String a1, b1, c1, d1;
    ConfigurasiAPI function;

    public CustomDialogAdd_Card(Activity anActivity) {
        super(anActivity);
        this.activity = anActivity;
        function = new ConfigurasiAPI(activity);
    }

    public CustomDialogAdd_Card(Activity anActivity, String dialogTitle, String aDialogMsg, String color) {
        super(anActivity);
        this.activity = anActivity;
        this.lbl_dialogTitle = dialogTitle;
        this.lbl_dialogMsg = aDialogMsg;
        this.lbl_color = color;

    }

    public CustomDialogAdd_Card(Activity anActivity, String dialogTitle, String aDialogMsg, String aPositiveTxt, String aNegativeTxt) {
        super(anActivity);
        this.activity = anActivity;
        this.lbl_dialogTitle = dialogTitle;
        this.lbl_dialogMsg = aDialogMsg;
        this.lbl_btnPositive = aPositiveTxt;
        this.lbl_btnNegative = aNegativeTxt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_addcard);

        //to change dialog window size
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        a = (EditText) findViewById(R.id.numb14);
        b = (EditText) findViewById(R.id.numb58);
        c = (EditText) findViewById(R.id.numb912);
        d = (EditText) findViewById(R.id.numb1316);
        a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    b.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    c.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    d.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnNegative = (Button) findViewById(R.id.cd_btn_negative2);
        btnNegative.setOnClickListener(this);

        btnPositive = (Button) findViewById(R.id.cd_btn_positive2);
        btnPositive.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cd_btn_positive2:
                btnPositiveClicked();
                break;
            case R.id.cd_btn_negative2:
                btnNegativeClicked();
                break;
            default:
                break;
        }
        dismiss();
    }


    public void btnPositiveClicked() {
        a1 = a.getText().toString();
        b1 = b.getText().toString();
        c1 = c.getText().toString();
        d1 = d.getText().toString();

        function.AddCardNum(a1 + b1 + c1 + d1);
        // Toast.makeText(activity, "Btn Positive Clicked", Toast.LENGTH_LONG).show();
    }

    public void btnNegativeClicked() {
        // Toast.makeText(activity, "Btn Negative Clicked", Toast.LENGTH_LONG).show();
    }
}
