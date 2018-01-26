package co.id.franknco.ui.splash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.id.franknco.R;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.controller.RootUtil;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.dialog.CustomDialog;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.main.MainActivity;

/**
 * Created by GSS-NB-2017-0013 on 12/19/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private IntentLauncher launcher;
    private boolean isBackPressed;
    private static long SLEEP_TIME = 3;    // Sleep for some time
    private Temp3DES temp3DES;
    private SessionManager sessionManager;
    private String username, token, umid;
    String red = "red";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        temp3DES = new Temp3DES(this);
        sessionManager = new SessionManager(this);
        validationConnectivity();
    }

    private void validationConnectivity() {
        if ( RootUtil.isDeviceRooted(this)) {
            CustomDialog myCD = new CustomDialog(this, "Caution!", "Application Error",red) {
                @Override
                public void btnPositiveClicked() {
                    SplashActivity.super.onBackPressed();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    startActivity(intent);
                }
            };
            myCD.show();
        } else {
            // Start timer and launch main activity
            launcher = new IntentLauncher();
            launcher.start();
        }
    }


    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME * 1000);

            } catch (Exception e) {

            } finally {
                // Start main activity
                try {
                    username = sessionManager.getUsername();

                    token = sessionManager.getTokenId();

                    umid = sessionManager.getumId();

                } catch (NullPointerException e) {

                }

                if (username != null && token != null) {
                    CheckKeepMe(username, token);
                } else {
                    if (!isBackPressed) {

                        Bundle b = new Bundle();
                        b.putString("rememberme", "1");
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtras(b);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        }

    }

    /**
     * Check KeepMeLogin
     */
    public void CheckKeepMe(final String hp, final String token) {
        // Tag used to cancel the request
        String tag_string_req = "req_ckml_in";
        String dataMSG = "";

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "2500");
        postParam.put("msg", hp);
        postParam.put("token", token);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                   try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                            if (code.equals("2510")) {
                                String fullname = sessionManager.getUser();
                                Intent myintent = new Intent(SplashActivity.this, MainActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(myintent);
                                sessionManager.createLoginSession(hp, fullname, token, umid);

                                return;
                            } else {

                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            // String error = "1";
                            // Check for error node in json

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else { //DATA GIVEN
                   /* Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();*/
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        //for timeout and make data do not send twice
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GetUrlName.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }
}
