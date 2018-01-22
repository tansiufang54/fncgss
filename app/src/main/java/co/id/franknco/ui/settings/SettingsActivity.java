package co.id.franknco.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.dialog.CustomDialog;
import co.id.franknco.testslider;
import co.id.franknco.ui.changepass.ChangePasswordActivity;
import co.id.franknco.ui.editprofile.EditProfileActivity;
import co.id.franknco.ui.faq.FAQActivity;
import co.id.franknco.ui.history.HistoryActivity;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.policyprivacy.PrivacyPolicyActivity;
import co.id.franknco.ui.termsandconditions.TermsandConditionActivity;

public class SettingsActivity extends Fragment {
    private static final String TAG = "SettingsActivity";
    @BindView(R.id.txt_header_name)
    TextView _txtName;
    ConfigurasiAPI function;
    SessionManager session;
   /* @BindView(R.id.toolbar)Toolbar toolbar;*/

    public SettingsActivity() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.bind(this, rootView);
        function = new ConfigurasiAPI(getActivity());
        session = new SessionManager(getActivity());
        String name = session.getUser();
        String upperString = name.substring(0,1).toUpperCase() + name.substring(1);
        _txtName.setText(upperString);
        //setupToolbar();
        return rootView;
    }

    /*private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }*/


    @OnClick(R.id.txt_settings_editprofile)
    public void toEditProfile(View v){
        startActivity(new Intent(getActivity(), EditProfileActivity.class));
    }

    @OnClick(R.id.txt_change_pass)
    public void toChangePass(View v){
        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
    }
    @OnClick(R.id.txt_faq)
    public void toFaq(View v){
        startActivity(new Intent(getActivity(), FAQActivity.class));
    }

    @OnClick(R.id.txt_privacy_policy)
    public void toPrivacyPolicy(View v){
        startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class));
    }
     @OnClick(R.id.txt_history)
    public void toHistory(View v){
        startActivity(new Intent(getActivity(), HistoryActivity.class));
    }
   @OnClick(R.id.txt_logout)
    public void toLogout(View v){
       CustomDialog myCD = new CustomDialog(getActivity(), "Log out!", "Are you sure you want to log out?","Yes","No") {

           @Override
           public void btnPositiveClicked() {
               function.Logout();
           }

           @Override
           public void btnNegativeClicked() {

           }


       };
       myCD.show();
    }



}
