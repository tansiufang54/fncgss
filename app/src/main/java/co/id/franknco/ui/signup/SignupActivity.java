package co.id.franknco.ui.signup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.api.ApiService;
import co.id.franknco.api.RestApi;
import co.id.franknco.controller.Base64Utils;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.Encrypter;
import co.id.franknco.controller.HashPassword;
import co.id.franknco.controller.HexConverter;
import co.id.franknco.model.GeneralResponse;
import co.id.franknco.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.sv_layer1)
    ScrollView _LLnextsignup;
    @BindView(R.id.sv_layer2)
    ScrollView _LLsignup;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_address)
    EditText _addressText;
    @BindView(R.id.input_dateofbirth)
    EditText _etDateOfBirth;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_card_number)
    EditText _etNoCard;
    @BindView(R.id.input_password)
    EditText _pText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPText;
    @BindView(R.id.btn_next_signup)
    ImageButton _signupnextButton;
    @BindView(R.id.btn_signup)
    CardView _signupButton;
    @BindView(R.id.checkbox_termsandconditions)
    CheckBox _checkboxTnC;
   /* @BindView(R.id.link_login)
    TextView _loginLink;*/
    ConfigurasiAPI function;
    String currentDateTimeString;
    String cardnumber;
    String name;
    String address;
    String dob;
    String email;
    String mobile;
    String p;
    String reEnterP;
    String code_page = "0";
    Calendar myCalendar = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        setupToolbar();
        function = new ConfigurasiAPI(this);

        /** DATE PICKER FOR DOB */
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        _etDateOfBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /** code 0 = belum pindah halaman
         * code 1 = sudah validasi halaman 1
         * code 2 = sudah masuk halaman 2
         */
        _signupnextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_page = "1";
                try {
                    signup();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_page = "2";
                try {
                    signup();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

       /* _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });*/
    }

    private void updateLabel() {
        String myFormat = "ddMMyyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        _etDateOfBirth.setText(sdf.format(myCalendar.getTime()));
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

    public void signup() throws UnsupportedEncodingException {
        Log.d(TAG, "Signup");

        if (code_page.contains("2")) {

            if (!validate()) {
                onSignupFailed();
                code_page = "2";
                return;
            }
            code_page = "0";
            _signupButton.setEnabled(false);
            function.SignUp(cardnumber, name, dob, address, email, mobile, p);
            _signupButton.setEnabled(true);
        } else {

            if (!validate_first()) {
                onSignupFailed();
                code_page = "0";
                return;
            }
            code_page = "2";
            _LLnextsignup.setVisibility(View.GONE);
            _LLsignup.setVisibility(View.VISIBLE);
        }


    }


    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
//        finish();
    }

    public void onSignupFailed() {
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        cardnumber = _etNoCard.getText().toString();
        name = _nameText.getText().toString();
        address = _addressText.getText().toString().trim();
        dob = _etDateOfBirth.getText().toString().trim();
        mobile = _mobileText.getText().toString().trim();


        if (cardnumber.isEmpty() || !(cardnumber.length() >= 15 )) {
            _etNoCard.setError("please insert valid cardnumber");
            valid = false;
        } else {
            _etNoCard.setError(null);
        }

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        if (dob.isEmpty()) {
            _etDateOfBirth.setError("Enter Valid Date of Birth");
            valid = false;
        } else {
            _etDateOfBirth.setError(null);
        }

        if (mobile.isEmpty() || !(mobile.length() <= 12)|| !(mobile.length() >= 9)) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (!_checkboxTnC.isChecked()) {
            _checkboxTnC.setError("Please Check CMK's Terms and Services");
            valid = false;
        } else {
            _checkboxTnC.setError(null);
        }
        return valid;
    }

    public boolean validate_first() {
        boolean valid = true;
        email = _emailText.getText().toString().trim();
        p = _pText.getText().toString().trim();
        reEnterP = _reEnterPText.getText().toString().trim();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (p.isEmpty() || p.length() < 4 || p.length() > 10) {
            _pText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _pText.setError(null);
        }

        if (reEnterP.isEmpty() || reEnterP.length() < 4 || reEnterP.length() > 10 || !(reEnterP.equals(p))) {
            _reEnterPText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPText.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        if (code_page.contains("1") || code_page.contains("2")) {
            _LLnextsignup.setVisibility(View.VISIBLE);
            _LLsignup.setVisibility(View.GONE);
            code_page = "0";
        }else {
            Intent myintent = new Intent(getApplicationContext(), LoginActivity.class);
            myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(myintent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
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