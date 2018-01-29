package co.id.franknco.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.id.franknco.R;

/**
 * Created by GSS-NB-2017-0013 on 12/4/2017.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private TextView btnPositive, btnNegative;
    private String lbl_btnPositive = "OK", lbl_btnNegative = "Cancel";
    private String lbl_dialogTitle, lbl_dialogMsg, lbl_color = "";
    private TextView dialogTitle, dialogMsg;

    public CustomDialog(Activity anActivity, String dialogTitle, String aDialogMsg) {
        super(anActivity);
        this.activity = anActivity;
        this.lbl_dialogTitle = dialogTitle;
        this.lbl_dialogMsg = aDialogMsg;

    }

    public CustomDialog(Activity anActivity, String dialogTitle, String aDialogMsg, String color) {
        super(anActivity);
        this.activity = anActivity;
        this.lbl_dialogTitle = dialogTitle;
        this.lbl_dialogMsg = aDialogMsg;
        this.lbl_color = color;

    }

    public CustomDialog(Activity anActivity, String dialogTitle, String aDialogMsg, String aPositiveTxt, String aNegativeTxt) {
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
        setContentView(R.layout.custom_dialog);
        btnPositive = (TextView) findViewById(R.id.cd_btn_positive);
        btnNegative = (TextView) findViewById(R.id.cd_btn_negative);
        dialogMsg = (TextView) findViewById(R.id.cd_message);
        //to change dialog window size
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogTitle = (TextView) findViewById(R.id.cd_title);
        dialogTitle.setText(lbl_dialogTitle);
        if (lbl_color.contains("red")) {
            dialogTitle.setTextColor(ContextCompat.getColor(activity, R.color.red_2));
        } else if (lbl_color.contains("green")) {
            dialogTitle.setTextColor(ContextCompat.getColor(activity, R.color.green));
        }else{
            dialogTitle.setTextColor(ContextCompat.getColor(activity, R.color.grayish_brown));
        }




        dialogMsg.setText(lbl_dialogMsg);


        if(lbl_btnNegative.equals("Cancel")){
            btnNegative.setVisibility(View.GONE);
        }else {
            btnNegative.setVisibility(View.VISIBLE);
        }
        btnNegative.setText(lbl_btnNegative);
        btnNegative.setOnClickListener(this);


        btnPositive.setText(lbl_btnPositive);
        btnPositive.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cd_btn_positive:
                btnPositiveClicked();
                break;
            case R.id.cd_btn_negative:
                btnNegativeClicked();
                break;
            default:
                break;
        }
        dismiss();
    }


    public void btnPositiveClicked() {
        Toast.makeText(activity, "Btn Positive Clicked", Toast.LENGTH_LONG).show();
    }

    public void btnNegativeClicked() {
        Toast.makeText(activity, "Btn Negative Clicked", Toast.LENGTH_LONG).show();
    }
}
