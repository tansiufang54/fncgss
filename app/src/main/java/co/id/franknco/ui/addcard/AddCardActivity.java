package co.id.franknco.ui.addcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.ui.main.MainActivity;
import co.id.franknco.ui.settings.SettingsActivity;

public class AddCardActivity extends AppCompatActivity {
    private static final String TAG = "AddCardActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_add_card)
    Button _btn_add_card;
    @BindView(R.id.input_card_number)
    EditText _et_card_number;

    ConfigurasiAPI function;
    String cardnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        function = new ConfigurasiAPI(this);
        setupToolbar();
        _btn_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addcard();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

   private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }

    }


    public void addcard() throws UnsupportedEncodingException {
        Log.d(TAG, "add card");
        _btn_add_card.setEnabled(false);
        if (!validate()) {
            onAddCardFailed();
            return;
        }

        function.AddCardNum(cardnumber);
        _btn_add_card.setEnabled(true);
    }


    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
//        finish();
    }

    public void onAddCardFailed() {
        Toast.makeText(getBaseContext(), "Add Card failed", Toast.LENGTH_LONG).show();

        _btn_add_card.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        cardnumber = _et_card_number.getText().toString().trim();
        if (cardnumber.isEmpty() || cardnumber.length() < 16 || cardnumber.length() > 16) {
            _et_card_number.setError("please insert valid cardnumber ");
            valid = false;
        } else {
            _et_card_number.setError(null);
        }


        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(myintent);
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
