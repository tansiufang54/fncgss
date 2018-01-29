package co.id.franknco.ui.editprofile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.ui.signup.SignupActivity;


public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.input_firstname)
    EditText _inputFirstName;
    @BindView(R.id.input_lastname)
    EditText _inputLastName;
    @BindView(R.id.input_dateofbirth)
    EditText _inputDob;
    @BindView(R.id.input_address)
    EditText _inputAddress;
    @BindView(R.id.input_mobile)
    EditText _inputMobile;
    @BindView(R.id.btn_save_editprofile)
    CardView _butSaveEditProfile;
    ConfigurasiAPI function;
    String inputfirstname, inputlastname, inputdob, inputaddress, inputmobile;
    Calendar myCalendar = Calendar.getInstance();

    private SessionManager sessionManager;
    private Temp3DES temp3DES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        ButterKnife.bind(this);
        function = new ConfigurasiAPI(this);
        sessionManager = new SessionManager(this);
        temp3DES = new Temp3DES(this);



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

        _inputDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        _butSaveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editprofile();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        setupToolbar();
        displayProfile();
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

    private void displayProfile() {
        if (sessionManager.getUser().isEmpty())return;
        String user = sessionManager.getUser();
        String[] name = user.split(" ");
        _inputFirstName.setText(name[0]);
        _inputLastName.setText(name[1]);
        _inputMobile.setText(temp3DES.decrypt(sessionManager.getUsername()));
    }

    public void editprofile() throws UnsupportedEncodingException {
        Log.d(TAG, "ChangePass");
        _butSaveEditProfile.setEnabled(false);
        if (!validate()) {
            onEditProfileFailed();
            return;
        }

        function.EditProfile(inputfirstname + " " + inputlastname, inputdob, inputaddress, inputmobile);
        _butSaveEditProfile.setEnabled(true);
    }

    public void onEditProfileFailed() {
        //Toast.makeText(getBaseContext(), "Change Password failed", Toast.LENGTH_LONG).show();

        _butSaveEditProfile.setEnabled(true);
    }

    private void updateLabel() {
        String myFormat = "ddMMyyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        _inputDob.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean validate() {
        boolean valid = true;

        inputfirstname = _inputFirstName.getText().toString().trim();
        inputlastname = _inputLastName.getText().toString().trim();
        inputdob = _inputDob.getText().toString().trim();
        inputaddress = _inputAddress.getText().toString().trim();
        inputmobile = _inputMobile.getText().toString().trim();


        if (inputfirstname.isEmpty() || inputfirstname.length() < 3) {
            _inputFirstName.setError("at least 3 characters");
            valid = false;
        } else {
            _inputFirstName.setError(null);
        }


        if (inputaddress.isEmpty()) {
            _inputAddress.setError("Enter Valid Address");
            valid = false;
        } else {
            _inputAddress.setError(null);
        }

        if (inputdob.isEmpty()) {
            _inputDob.setError("Enter Valid Date of Birth");
            valid = false;
        } else {
            _inputDob.setError(null);
        }

        if (inputmobile.isEmpty() || !(inputmobile.length() <= 12) || !(inputmobile.length() >= 9)) {
            _inputMobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _inputMobile.setError(null);
        }

        return valid;
    }
}
