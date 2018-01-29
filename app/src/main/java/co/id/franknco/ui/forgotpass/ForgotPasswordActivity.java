package co.id.franknco.ui.forgotpass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.main.MainActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.input_email)
    EditText _etEmailText;
    @BindView(R.id.input_hp)
    EditText _etHpText;
    @BindView(R.id.btn_forgot_pass)
    ImageButton _butForgotPass;
    ConfigurasiAPI function;
    String email;
    String hp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        function = new ConfigurasiAPI(this);
        setupToolbar();
        _butForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    forgotpass();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


    }



    public void forgotpass() throws UnsupportedEncodingException {
        _butForgotPass.setEnabled(false);
        if (!isValidate()) {
            onForgotPassFailed();
            return;
        }

        Log.d(TAG, "ForgotPass");
        function.ForgetPass(hp,email);
        _butForgotPass.setEnabled(true);
    }


/*    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
//        finish();
    }*/

    public void onForgotPassFailed() {
        //Toast.makeText(getBaseContext(), "Forgot Password failed", Toast.LENGTH_LONG).show();

        _butForgotPass.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        email = _etEmailText.getText().toString().trim();
        hp = _etHpText.getText().toString().trim();



        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _etEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            _etEmailText.setError(null);
        }
        if (hp.isEmpty() || (hp.length() >= 13)|| (hp.length() <= 9)) {
            _etHpText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _etHpText.setError(null);
        }


        return valid;
    }

    public boolean isValidate() {
        boolean valid = true;
        email = _etEmailText.getText().toString().trim();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _etEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            _etEmailText.setError(null);
        }

        return valid;
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    @Override
    public void onBackPressed() {
        Intent myintent = new Intent(getApplicationContext(), LoginActivity.class);
        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(myintent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
