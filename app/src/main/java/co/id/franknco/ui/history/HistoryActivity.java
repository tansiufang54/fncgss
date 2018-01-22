package co.id.franknco.ui.history;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.adapter.ListViewHistoryAdapter;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.model.CardModel;
import co.id.franknco.model.DataObject;
import co.id.franknco.ui.main.MainActivity;

/**
 * Created by GSS-NB-2017-0013 on 9/28/2017.
 */

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listHistory)
    ListView _listCollege;
    @BindView(R.id.proListHistory)
    ProgressBar _proCollageList;
    @BindView(R.id.spCardNumber)
    Spinner _spinnerCard;
    @BindView(R.id.rel_HistoryResult)
    RelativeLayout _relHistoryResult;
    @BindView(R.id.txtResult)
    TextView txtResult;

    ConfigurasiAPI function;
    SessionManager sessionManager;
    Temp3DES temp3DES;


    ArrayList<String> arraylist_card;
    ArrayList<HashMap<String, String>> arraylist;


    public static String CARD_ID = "CARD_ID";
    public static String CARD_NUMBER = "CARD_NUMBER";
    public static String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static String PAYMENT_METHOD = "PAYMENT_METHOD";
    public static String AMOUNT = "AMOUNT";
    public static String DATE = "DATE";
    public static String LOCATION = "LOCATION";
    public static String USER_CASHIER = "USER_CASHIER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        function = new ConfigurasiAPI(this);
        sessionManager = new SessionManager(this);
        temp3DES = new Temp3DES(this);
        setupToolbar();
        Intent intent = getIntent();

        if (null != intent) {
            String text = getIntent().getStringExtra("code");
            getPerCardTransaction(text);
        }


        //getCardListUser();

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


    /**
     * Spinner CardNumber
     */
    private void spinnerregular() {

        _spinnerCard.setAdapter(new ArrayAdapter<String>(HistoryActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                arraylist_card));


        _spinnerCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = _spinnerCard.getSelectedItem().toString();
                getPerCardTransaction(text);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * GET USER CARD LIST
     */
    private void getCardListUser() {

        String tag_string_req = "req_getcardlistuser";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0700");
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

                            if (code.equals("0710")) {

                                arraylist_card = new ArrayList<String>();
                                JSONArray cardlist = response.getJSONArray("msg");
                                for (int i = 0; i < cardlist.length(); i++) {
                                    // Populate spinner with country names
                                    arraylist_card.add(temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_number")));
                                }
                                spinnerregular();

                            } else if (code.equals("0720")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(HistoryActivity.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            } else if (code.equals("0730")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(HistoryActivity.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            } else if (code.equals("0740")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(HistoryActivity.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

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
                    Toast.makeText(HistoryActivity.this,
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
     * GET CARD LIST TRANSACTION
     */
    public void getCardListTransaction() {
        String tag_string_req = "req_getcardlisttransaction";

        String DataMSG = "";
        DataMSG = sessionManager.getUsername() + "#" + temp3DES.encrypt("30");
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "1900");
        postParam.put("msg", DataMSG);
        postParam.put("token", sessionManager.getTokenId());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");

                            if (code.equals("1910")) {

                                arraylist = new ArrayList<HashMap<String, String>>();
                                JSONArray cardlist = response.getJSONArray("msg");
                                 for (int i = 0; i < cardlist.length(); i++) {

                                    HashMap<String, String> map = new HashMap<String, String>();

                                    map.put("CARD_ID", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_id")));
                                    map.put("CARD_NUMBER", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_number")));
                                    map.put("TRANSACTION_TYPE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("transaction_type")));
                                    map.put("AMOUNT", temp3DES.decrypt(cardlist.getJSONObject(i).getString("x761")));
                                    map.put("DATE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("trans_date")));
                                    map.put("USER_CASHIER", temp3DES.decrypt(cardlist.getJSONObject(i).getString("user_cashier")));
                                    map.put("PAYMENT_METHOD", temp3DES.decrypt(cardlist.getJSONObject(i).getString("payment_method")));

                                    arraylist.add(map);


                                }

                                if (arraylist != null) {
                                    _proCollageList.setVisibility(View.GONE);
                                    _listCollege.setVisibility(View.VISIBLE);
                                     ListViewHistoryAdapter adapter = new ListViewHistoryAdapter(HistoryActivity.this, arraylist);
                                    _listCollege.setAdapter(adapter);
                                }

                            } else if (code.equals("1920")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(HistoryActivity.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                        Intent myintent = new Intent(HistoryActivity.this, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        HistoryActivity.this.finish();
                                        HistoryActivity.this.startActivity(myintent);
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            } else if (code.equals("1930")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(HistoryActivity.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                        Intent myintent = new Intent(HistoryActivity.this, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        HistoryActivity.this.finish();
                                        HistoryActivity.this.startActivity(myintent);
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            } else if (code.equals("1940")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(HistoryActivity.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                        Intent myintent = new Intent(HistoryActivity.this, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        HistoryActivity.this.finish();
                                        HistoryActivity.this.startActivity(myintent);
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

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
                    Toast.makeText(HistoryActivity.this,
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
     * GET PER CARD LIST TRANSACTION
     */
    public void getPerCardTransaction(String card_number) {
        String tag_string_req = "req_getpercardtransaction";
        _listCollege.setVisibility(View.VISIBLE);
        String DataMSG = "";
        DataMSG = sessionManager.getUsername() + "#" + temp3DES.encrypt(card_number) + "#" + temp3DES.encrypt("15");
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0800");
        postParam.put("msg", DataMSG);
        postParam.put("token", sessionManager.getTokenId());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                         try {
                            String code = response.getString("code");
                            String data = response.getString("msg");

                            if (code.equals("0810")) {

                                arraylist = new ArrayList<HashMap<String, String>>();
                                JSONArray cardlist = response.getJSONArray("msg");
                                for (int i = 0; i < cardlist.length(); i++) {

                                    HashMap<String, String> map = new HashMap<String, String>();

                                    map.put("CARD_ID", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_id")));
                                    map.put("CARD_NUMBER", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_number")));
                                    map.put("TRANSACTION_TYPE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("transaction_type")));
                                    map.put("AMOUNT", temp3DES.decrypt(cardlist.getJSONObject(i).getString("x761")));
                                    map.put("DATE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("trans_date")));
                                    map.put("USER_CASHIER", temp3DES.decrypt(cardlist.getJSONObject(i).getString("user_cashier")));
                                    map.put("PAYMENT_METHOD", temp3DES.decrypt(cardlist.getJSONObject(i).getString("payment_method")));
                                    //map.put("LOCATION", temp3DES.decrypt(cardlist.getJSONObject(i).getString("location")));

                                    arraylist.add(map);

                                }

                                if (arraylist != null) {
                                    txtResult.setVisibility(View.GONE);
                                    _relHistoryResult.setVisibility(View.VISIBLE);
                                    _proCollageList.setVisibility(View.GONE);
                                    _listCollege.setVisibility(View.VISIBLE);
                                    ListViewHistoryAdapter adapter = new ListViewHistoryAdapter(HistoryActivity.this, arraylist);
                                    _listCollege.setAdapter(adapter);
                                }

                            } else {
                                _listCollege.setVisibility(View.GONE);
                                _proCollageList.setVisibility(View.GONE);
                                txtResult.setVisibility(View.VISIBLE);

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
                    Toast.makeText(HistoryActivity.this,
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
