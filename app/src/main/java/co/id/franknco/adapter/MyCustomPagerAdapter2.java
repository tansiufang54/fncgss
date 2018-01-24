package co.id.franknco.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

import co.id.franknco.R;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.controller.InterfaceHelper;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;

public class MyCustomPagerAdapter2 extends PagerAdapter {

    private Activity activity;
    private JSONArray cardDataArray = new JSONArray();
    private Temp3DES temp3DES;
    private LayoutInflater layoutInflater;
    private SessionManager sessionManager;
    private InterfaceHelper interfaceHelper;

    public MyCustomPagerAdapter2(Activity anActivity, JSONArray dataArray)
    {
        this.activity = anActivity;
        this.cardDataArray = dataArray;
        this.temp3DES = new Temp3DES(activity.getApplicationContext());
        this.layoutInflater = (LayoutInflater) anActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sessionManager = new SessionManager(activity.getApplicationContext());
        this.interfaceHelper = InterfaceHelper.getInstance();
    }


    public JSONObject getObjItem(int position) {
        if(cardDataArray == null) return null;
        else return cardDataArray.optJSONObject(position);
    }

    @Override
    public int getCount() {
        if(this.cardDataArray == null)
        {
            return 0;
        }
        else return cardDataArray.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = layoutInflater.inflate(R.layout.card_item, container, false);

        JSONObject jobj = getObjItem(position);

        ImageView cardImages = (ImageView) itemView.findViewById(R.id.ci_image);
        TextView cardNumber = (TextView) itemView.findViewById(R.id.ci_number);
        TextView cardSaldo = (TextView) itemView.findViewById(R.id.ci_saldo);
        TextView cardIssued = (TextView) itemView.findViewById(R.id.ci_issued);
        TextView cardExpired = (TextView) itemView.findViewById(R.id.ci_expired);

        String cardNum = "";

        try {
            //cardImages.setImageBitmap((interfaceHelper.decodeBase64(jobj.getString("front_view"))));
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



        }catch (JSONException je)
        {
            je.printStackTrace();
        }

        StringBuilder s;
        s = new StringBuilder(cardNum);

        for(int i = 4; i < s.length(); i += 5){
            s.insert(i, " ");
        }
        cardNumber.setText(s.toString());

        //get history here
        getCardHistory(itemView, cardNum);

        container.addView(itemView,position);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    private void getCardHistory(final View rootview, String card_number) {
        String tag_string_req = "req_getpercardtransaction";
        String DataMSG = "";
        DataMSG = sessionManager.getUsername() + "#" + temp3DES.encrypt(card_number) + "#" + temp3DES.encrypt("15");
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "0800");
        postParam.put("msg", DataMSG);
        postParam.put("token", sessionManager.getTokenId());
        final View thisRootView = rootview;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String code = response.getString("code");
                            String data = response.getString("msg");


                            if (code.equals("0810")) {
                                ListView lvhis = (ListView) rootview.findViewById(R.id.lv_cardHistory);
                                lvhis.setVisibility(View.VISIBLE);
                                rootview.findViewById(R.id.tv_cardHistory_Empty).setVisibility(View.GONE);

                                ListViewHistoryAdapter2 historyAdapter = new ListViewHistoryAdapter2(activity, new JSONArray(data));
                                lvhis.setAdapter(historyAdapter);
                                //to show all listview content without scrolling
                                interfaceHelper.setListViewHeight(lvhis);

                            } else {
                                rootview.findViewById(R.id.lv_cardHistory).setVisibility(View.GONE);
                                rootview.findViewById(R.id.tv_cardHistory_Empty).setVisibility(View.VISIBLE);
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
                    Toast.makeText(activity.getApplicationContext(),
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
