package co.id.franknco.ui.changepass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ChangePasswordActivity";
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.input_old_password)
    EditText _oldP;
    @BindView(R.id.input_new_password)
    EditText _newP;
    @BindView(R.id.input_reenter_new_password)
    EditText _reNewP;
    @BindView(R.id.btn_change_pass)
    CardView _butChangeP;
    ConfigurasiAPI function;
    String oldP;
    String newP;
    String reNewP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        function = new ConfigurasiAPI(this);
        _butChangeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    changepass();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    public void changepass() throws UnsupportedEncodingException {
        Log.d(TAG, "ChangePass");
        _butChangeP.setEnabled(false);
        if (!validate()) {
            onChangePassFailed();
            return;
        }

        function.ChangePass(oldP,reNewP);
        _butChangeP.setEnabled(true);
    }


/*    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
//        finish();
    }*/

    public void onChangePassFailed() {
        //Toast.makeText(getBaseContext(), "Change Password failed", Toast.LENGTH_LONG).show();

        _butChangeP.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        oldP = _oldP.getText().toString().trim();
        newP = _newP.getText().toString().trim();
        reNewP = _reNewP.getText().toString().trim().trim();


        if (oldP.isEmpty() || oldP.length() < 4 || oldP.length() > 10) {
            _oldP.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _oldP.setError(null);
        }

        if (newP.isEmpty() || newP.length() < 4 || newP.length() > 10) {
            _newP.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _newP.setError(null);
        }

        if (reNewP.isEmpty() || reNewP.length() < 4 || reNewP.length() > 10 || !(reNewP.equals(newP))) {
            _reNewP.setError("Password Do not match");
            valid = false;
        } else {
            _reNewP.setError(null);
        }

        return valid;
    }

}
