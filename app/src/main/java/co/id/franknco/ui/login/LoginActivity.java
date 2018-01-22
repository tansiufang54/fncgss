package co.id.franknco.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.ui.forgotpass.ForgotPasswordActivity;
import co.id.franknco.ui.signup.SignupActivity;

/**
 * Created by GSS-NB-2016-0012 on 8/25/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    TextView _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    @BindView(R.id.link_signup2)
    TextView _signupLink2;
    @BindView(R.id.link_forgot_pass)
    TextView _forgotpassLink;
   /* @BindView(R.id.txt_login)
    TextView _txt_login;*/
    @BindView(R.id.keep_me_login)
    TextView _txt_keep_me_login;
    @BindView(R.id.cb_keepmelogin)
    CheckBox _cb_keep_me_login;


    ConfigurasiAPI function;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        function = new ConfigurasiAPI(this);
         /** RESPONSIVE FONT TEXT */
        /*_emailText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                16);
        _passwordText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
               16);
        _loginButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                16);
        _signupLink.setTextSize(TypedValue.COMPLEX_UNIT_SP,
               12);
        _forgotpassLink.setTextSize(TypedValue.COMPLEX_UNIT_SP,
              9);
        _txt_login.setTextSize(TypedValue.COMPLEX_UNIT_SP,
              30);
        _txt_keep_me_login.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                9);
*/

        /**  change to masking password */
        _passwordText.setTransformationMethod(new PasswordTransformationMethod());


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        _signupLink2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        _forgotpassLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

       /* final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
       // progressDialog.show();*/

        String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String cb = "2";
        if(_cb_keep_me_login.isChecked()){
            cb = "1";
        }
        function.Login(username, password,cb);
        _loginButton.setEnabled(true);
        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        //onLoginSuccess();
                        // onLoginFailed();
                        //progressDialog.dismiss();

                    }
                }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        super.finish();

    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        /*Intent intent = new Intent(getApplicationContext(), TIDActivity.class);
        finish();
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();*/
    }

    public void onLoginFailed() {
       // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("enter USERNAME");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("enter PASSWORD");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
