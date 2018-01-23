package co.id.franknco.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.adapter.ListViewHistoryAdapter2;
import co.id.franknco.adapter.MyCustomPagerAdapter;
import co.id.franknco.adapter.MyCustomPagerAdapter2;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.controller.InterfaceHelper;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.dialog.CustomDialog;
import co.id.franknco.dialog.CustomDialogAdd_Card;
import co.id.franknco.model.DataObject;
import co.id.franknco.ui.addcard.AddCardActivity;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.main.MainActivity;


/**
 * Created by GSS-NB-2016-0012 on 9/4/2017.
 */

public class BottomNav1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ci_saldo)
    TextView cardSaldo;
    @BindView(R.id.ci_issued)
    TextView cardIssued;
    @BindView(R.id.ci_expired)
    TextView cardExpired;
    @BindView(R.id.lv_cardHistory)
    ListView lvhis;
    @BindView(R.id.tv_cardHistory_Empty)
    TextView chEmpty;


    private JSONArray cardDataArray = new JSONArray();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog dialog;
    SessionManager sessionManager;
    Temp3DES temp3DES;
    private InterfaceHelper interfaceHelper;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    ArrayList<HashMap<String, String>> arraylist;
    MyCustomPagerAdapter sliderAdapter;
    public static String CARD_ID = "CARD_ID";
    public static String CARD_KEY = "CARD_KEY";
    public static String CARD_NUMBER = "CARD_NUMBER";
    public static String SALDO = "SALDO";
    public static String VALID_UNTIL = "VALID_UNTIL";
    public static String FRONT_VIEW = "FRONT_VIEW";
    public static String USER = "USER";
    public static String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static String PAYMENT_METHOD = "PAYMENT_METHOD";
    public static String AMOUNT = "AMOUNT";
    public static String DATE = "DATE";

    public BottomNav1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category1, container, false);
        dialog = ProgressDialog.show(getActivity(), "", "Please wait...",
                true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss() ;
            }
        }, 5000);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this);
        sessionManager = new SessionManager(getActivity());
        temp3DES = new Temp3DES(getActivity());
        interfaceHelper = InterfaceHelper.getInstance();
        ButterKnife.bind(this, rootView);
        viewPager.setPadding(20, 0, 20, 0);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(10);
        getCardListUser();
        swipeRefreshLayout.setRefreshing( false );
        swipeRefreshLayout.setEnabled( false );

        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        getCardListUser();

                                    }
                                }
        );*/

        // setupAdapter();

        FloatingActionButton myFab = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialogAdd_Card myCD = new CustomDialogAdd_Card(getActivity()) {

                };

                myCD.show();
              /*  Intent myintent = new Intent(getActivity(), AddCardActivity.class);
                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                startActivity(myintent);*/
            }
        });
        return rootView;
    }

    private void setupAdapter() {

   /* sliderAdapter = new MyCustomPagerAdapter(getActivity(), images);
                                    viewPager.setAdapter(sliderAdapter);*/
    }

    /**
     * GET CARD LIST USER
     */

    private void getCardListUser() {

        swipeRefreshLayout.setRefreshing(true);
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
                            Log.i("TAG", "onResponse: " + data);
                            if (code.equals("0710")) {
                             try {
                                 cardDataArray = new JSONArray(data);
                                 MyCustomPagerAdapter2 adapter2 = new MyCustomPagerAdapter2(getActivity(), cardDataArray);
                                 viewPager.setAdapter(adapter2);
                                 setCardData(0);
                                 viewPager.addOnPageChangeListener(replaceData());
                             }catch (NullPointerException e){

                             }
                            } else if (code.equals("0720")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(getActivity(), 0);
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
                                hasiljalur = new AlertDialog.Builder(getActivity(), 0);
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
                                hasiljalur = new AlertDialog.Builder(getActivity(), 0);
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

                            } else {
                                sessionManager.logoutUser_L();
                                Intent myintent = new Intent(getActivity(), LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                getActivity().finish();
                                getActivity().startActivity(myintent);
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),
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

    private ViewPager.OnPageChangeListener replaceData() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCardData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    private void setCardData(int position) {
        JSONObject jobj = getObjItem(position);
        String cardNum;

        try {
            cardNum = temp3DES.decrypt(jobj.getString("card_number"));
            String csaldo = temp3DES.decrypt_cr(temp3DES.decrypt(jobj.getString("x175")),temp3DES.decrypt_cardkey(jobj.getString("card_key")));
            cardSaldo.setText("Rp. "+ interfaceHelper.numberFormat(Integer.parseInt(csaldo)));

            String activationDate = temp3DES.decrypt(jobj.getString("activation_date"));
            String yyAD = activationDate.substring(2,4);
            String mmmAD = interfaceHelper.monthConvert(activationDate.substring(5,7));
            String ddAD = activationDate.substring(8,10);
            cardIssued.setText(ddAD + " / " + mmmAD + " / " + yyAD);

            String validDate = temp3DES.decrypt(jobj.getString("valid_until"));
            String yy = validDate.substring(2,4);
            String mmm = interfaceHelper.monthConvert(validDate.substring(5,7));
            String dd = validDate.substring(8,10);
            cardExpired.setText(dd + " / " + mmm + " / " + yy);

            getCardHistory(cardNum);

        }catch (JSONException je)
        {
            je.printStackTrace();
        }
    }

    private void getCardHistory(String card_number) {
        String tag_string_req = "req_getpercardtransaction";
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
                                lvhis.setVisibility(View.VISIBLE);
                                chEmpty.setVisibility(View.GONE);

                                ListViewHistoryAdapter2 historyAdapter = new ListViewHistoryAdapter2(getActivity(), new JSONArray(data));
                                lvhis.setAdapter(historyAdapter);
                                //to show all listview content without scrolling
                                interfaceHelper.setListViewHeight(lvhis);

                            } else {
                                lvhis.setVisibility(View.GONE);
                                chEmpty.setVisibility(View.VISIBLE);
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
                    Toast.makeText(getContext(),
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

    private void getCardListUser2() {

        swipeRefreshLayout.setRefreshing(true);
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
                                List<DataObject> getdata = new ArrayList<DataObject>();
                                arraylist = new ArrayList<HashMap<String, String>>();
                                JSONArray cardlist = response.getJSONArray("msg");
                                for (int i = 0; i < cardlist.length(); i++) {
                                    //JSONArray transactionlist = cardlist.getJSONObject(i).getJSONArray("transaction");
                                    HashMap<String, String> map = new HashMap<String, String>();

                                    map.put("CARD_ID", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_id")));
                                    map.put("CARD_NUMBER", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_number")));
                                    map.put("SALDO", temp3DES.decrypt_cr(temp3DES.decrypt(cardlist.getJSONObject(i).getString("x175")), temp3DES.decrypt_cardkey(cardlist.getJSONObject(i).getString("card_key"))));
                                    map.put("CARD_KEY", temp3DES.decrypt(cardlist.getJSONObject(i).getString("card_key")));
                                    map.put("VALID_UNTIL", temp3DES.decrypt(cardlist.getJSONObject(i).getString("valid_until")));
                                    map.put("FRONT_VIEW", cardlist.getJSONObject(i).getString("front_view"));
                                    map.put("USER", sessionManager.getUser());

                                         arraylist.add(map);


                                }

                                sliderAdapter = new MyCustomPagerAdapter(getActivity(), arraylist);
                                viewPager.setAdapter(sliderAdapter);


                            } else if (code.equals("0720")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(getActivity(), 0);
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
                                hasiljalur = new AlertDialog.Builder(getActivity(), 0);
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
                                hasiljalur = new AlertDialog.Builder(getActivity(), 0);
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

                            } else {
                                sessionManager.logoutUser_L();
                                Intent myintent = new Intent(getActivity(), LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                getActivity().finish();
                                getActivity().startActivity(myintent);
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),
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

    @Override
    public void onRefresh() {
        getCardListUser();
    }


    public JSONObject getObjItem(int position) {
        if(cardDataArray == null) return null;
        else return cardDataArray.optJSONObject(position);
    }
}