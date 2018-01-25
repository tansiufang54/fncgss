package co.id.franknco.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
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

        String cardNum = "";

        Log.i("MyCustomPagerAdapter2", "instantiateItem: Display color card: " + position);


        try {
//            GradientDrawable drawable = (GradientDrawable) cardImages.getBackground();
//            if(position%3 == 0){
//                drawable.setColor(ContextCompat.getColor(activity, R.color.black_2));
//            }
//            if(position%3 == 1){
//                drawable.setColor(ContextCompat.getColor(activity, R.color.gold));
//            }

            cardImages.setImageBitmap((interfaceHelper.decodeBase64(jobj.getString("front_view"))));

            //cardImages.getBackground().setColorFilter(ContextCompat.getColor(activity, R.color.gold), PorterDuff.Mode.SRC_IN);

            cardNum = temp3DES.decrypt(jobj.getString("card_number"));
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

        container.addView(itemView,position);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
