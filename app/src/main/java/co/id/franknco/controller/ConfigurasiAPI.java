package co.id.franknco.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.id.franknco.R;
import co.id.franknco.adapter.ListViewHistoryAdapter;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.crypto.TripleDES;
import co.id.franknco.dialog.CustomDialog;
import co.id.franknco.ui.history.HistoryActivity;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.main.MainActivity;
import co.id.franknco.ui.settings.SettingsActivity;

/**
 * Created by GSS-NB-2016-0012 on 9/27/2017.
 */

public class ConfigurasiAPI {
    private Context context;
    private Activity activity;
    private TripleDES tripleDES;
    private Temp3DES temp3DES;
    private SessionManager sessionManager;
    private ProgressDialog pDialog;
    String green = "green";
    String red = "red";


    public ConfigurasiAPI(Activity mContext) {
        context = mContext;
        activity = mContext;
        sessionManager = new SessionManager(context);
        tripleDES = new TripleDES();
        temp3DES = new Temp3DES(activity);
        pDialog = new ProgressDialog(context);
    }

    /**
     * CHECK TOKEN
     */
    public void CheckToken() {

        String tag_string_req = "req_check_token";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0000");
        postParam.put("msg", sessionManager.getUsername());
        postParam.put("token", sessionManager.getTokenId());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                             if (!code.equals("0009")) {
                                sessionManager.logoutUser_L();
                                Intent myintent = new Intent(activity, LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.finish();
                                activity.startActivity(myintent);
                            } else {
                             }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG);
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

    /**
     * LOGOUT
     */
    public void Logout() {

        String tag_string_req = "req_log_out";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "1800");
        postParam.put("msg", sessionManager.getUsername());
        postParam.put("token", sessionManager.getTokenId());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                            if (code.equals("1810")) {
                                sessionManager.logoutUser_L();
                                Intent myintent = new Intent(activity, LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.finish();
                                activity.startActivity(myintent);
                            } else {
                                Toast.makeText(activity,
                                        "Try to logout again!", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG);
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

    /**
     * LOGIN
     */
    public void Login(final String hp, final String password, final String keepmelogin) {
        // Tag used to cancel the request
        String tag_string_req = "req_log_in";
        pDialog.setMessage("Please Wait");
        pDialog.show();
        pDialog.setCancelable(false);
        String dataMSG = "";
        dataMSG = temp3DES.encrypt(hp) + "#" + temp3DES.encrypt((tripleDES.stringToSHA2(password)))+"#"+temp3DES.encrypt(keepmelogin);

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0100");
        postParam.put("msg", dataMSG);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                             pDialog.dismiss();
                            if (code.equals("0110")) {
                                JSONObject msg = response.getJSONObject("msg");
                                String token = msg.getString("token");
                                String fullname = msg.getString("fullname");
                                Intent myintent = new Intent(activity, MainActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.finish();
                                activity.startActivity(myintent);
                                sessionManager.createLoginSession(temp3DES.encrypt(hp), temp3DES.decrypt(fullname), token);


                            } else if (code.equals("0120")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0130")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else{
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();
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
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG);
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

    /**
     * SIGN UP
     */
    public void SignUp(final String cardnumber, final String fullname, final String dob, final String address,
                       final String email, final String hp, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_sign_up";

        SimpleDateFormat format = new SimpleDateFormat("ddmmyyyy");
        Date newDate = null;
        try {
            newDate = format.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("yyyy-mm-dd");
        String date = format.format(newDate);
        String dataMSG = "";
        dataMSG = temp3DES.encrypt(cardnumber) + "#" + temp3DES.encrypt(fullname) + "#" + temp3DES.encrypt(date) + "#" + temp3DES.encrypt(address) + "#" + temp3DES.encrypt(email) + "#" + temp3DES.encrypt(hp) + "#" + temp3DES.encrypt((tripleDES.stringToSHA2(password)))+ "#"+ temp3DES.encrypt("A02");

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0200");
        postParam.put("msg", dataMSG);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                            if (code.equals("0210")) {
                                CustomDialog myCD = new CustomDialog(activity, "Sign up is success", "Welcome to CMK's Apps. ",green) {
                                    @Override
                                    public void btnPositiveClicked() {
                                        Intent myintent = new Intent(activity, LoginActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        activity.finish();
                                        activity.startActivity(myintent);
                                    }


                                };
                                myCD.show();



                            } else if (code.equals("0220")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0230")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0240")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0250")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                   if (error != null) { //NULL DATA GIVEN
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG).show();
                } else { //DATA GIVEN
                   /* Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();*/
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
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

    /**
     * FORGOT PASSWORD
     */
    public void ForgetPass(final String hp, final String email) {

        String tag_string_req = "req_forgot";
        String dataMSG = "";
        dataMSG = temp3DES.encrypt(hp) + "#" + temp3DES.encrypt(email);
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0300");
        postParam.put("msg", dataMSG);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                            if (code.equals("0310")) {
                                CustomDialog myCD = new CustomDialog(activity, "Check your email!", "We sent an email to " + email + "\n Tap the link in that email to reset your password. ",green) {
                                    @Override
                                    public void btnPositiveClicked() {
                                        Intent myintent = new Intent(activity, LoginActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        activity.finish();
                                        activity.startActivity(myintent);
                                    }


                                };
                                myCD.show();
                            } else if (code.equals("0320")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0330")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }

                                };
                                myCD.show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG);
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

    /**
     * CHANGE PASSWORD
     */
    public void ChangePass(final String o_pass, final String n_pass) {

        String tag_string_req = "req_changepass";
        String dataMSG = sessionManager.getUsername() + "#" + temp3DES.encrypt(tripleDES.stringToSHA2(o_pass)) + "#" + temp3DES.encrypt(tripleDES.stringToSHA2(n_pass));
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0400");
        postParam.put("msg", dataMSG);
        postParam.put("token", sessionManager.getTokenId());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                         try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                            if (code.equals("0410")) {
                                CustomDialog myCD = new CustomDialog(activity, "Success!", "Password successfully changed ",green) {
                                    @Override
                                    public void btnPositiveClicked() {

                                        Intent intent = activity.getIntent();
                                        activity.finish();
                                        activity.startActivity(intent);
                                    }


                                };
                                myCD.show();


                            } else if (code.equals("0420")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0430")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0440")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG);
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


    /**
     * ADD CARD
     */
    public void AddCardNum(final String cardnumb) {
        String tag_string_req = "req_add_card";
        String dataMSG = "";
        dataMSG = sessionManager.getUsername() + "#" + temp3DES.encrypt(cardnumb)+ "#"+ temp3DES.encrypt("A02");

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0900");
        postParam.put("msg", dataMSG);
        postParam.put("token", sessionManager.getTokenId());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");
                            if (code.equals("0910")) {
                                CustomDialog myCD = new CustomDialog(activity, "Success!", data,green) {
                                    @Override
                                    public void btnPositiveClicked() {
                                        Intent myintent = new Intent(activity, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        activity.finish();
                                        activity.startActivity(myintent);
                                    }


                                };
                                myCD.show();



                            } else if (code.equals("0920")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0930")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0940")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            } else if (code.equals("0950")) {
                                CustomDialog myCD = new CustomDialog(activity, "Failed!", data,red) {
                                    @Override
                                    public void btnPositiveClicked() {

                                    }


                                };
                                myCD.show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    Toast.makeText(activity,
                            "Connection Problem!", Toast.LENGTH_LONG).show();
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