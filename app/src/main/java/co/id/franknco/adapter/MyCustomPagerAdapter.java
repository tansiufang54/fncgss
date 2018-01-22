package co.id.franknco.adapter;

/**
 * Created by GSS-NB-2017-0013 on 10/1/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import co.id.franknco.R;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.controller.NumberTextWatcher;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.fragment.BottomNav1;
import co.id.franknco.ui.history.HistoryActivity;
import co.id.franknco.ui.main.MainActivity;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    SessionManager sessionManager;
    Temp3DES temp3DES;


    public MyCustomPagerAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        sessionManager = new SessionManager(context);
        temp3DES = new Temp3DES(context);
        data = arraylist;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.card_images);
        ListView _listCollege = (ListView) itemView.findViewById(R.id.listHistory_card);
        TextView txtCardNumber = null;
        TextView txtCardUser = null;
        TextView txtCardSaldo = null;
        TextView txtCardExpired = null;
        String txtFrontView = null;
        String str_CardNumber = null;


        resultp = data.get(position);

        txtCardNumber = (TextView) itemView.findViewById(R.id.card_number);
        //txtCardUser = (TextView) itemView.findViewById(R.id.card_user);
        txtCardSaldo = (TextView) itemView.findViewById(R.id.card_saldo);
        txtCardExpired = (TextView) itemView.findViewById(R.id.card_expired);



       // txtCardUser.setText(resultp.get(BottomNav1.USER));

        /** Numbering Text */
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
        String prezzo = decimalFormat.format(Integer.parseInt(resultp.get(BottomNav1.SALDO)));
        txtCardSaldo.setText(prezzo);

        txtCardExpired.setText(resultp.get(BottomNav1.VALID_UNTIL));
        txtFrontView = (resultp.get(BottomNav1.FRONT_VIEW));
        str_CardNumber = (resultp.get(BottomNav1.CARD_NUMBER));
         /** MAKE SPACE */
        StringBuilder s;
        s = new StringBuilder(str_CardNumber);

        for(int i = 4; i < s.length(); i += 5){
            s.insert(i, " ");
        }
        txtCardNumber.setText(s.toString());

        /*  ListViewCardAdapter cardAdapter = new ListViewCardAdapter(context , data);
        _listCollege.setAdapter(cardAdapter);*/


        byte[] decodedString = decodeImage(txtFrontView);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        imageView.requestLayout();
        imageView.getLayoutParams().height = WRAP_CONTENT;
        imageView.getLayoutParams().width = MATCH_PARENT;

        container.addView(itemView);

        //listening to image click

        final String finalStr_CardNumber1 = str_CardNumber;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HistoryActivity.class);
                i.putExtra("code", finalStr_CardNumber1);
                context.startActivity(i);
               // Toast.makeText(context, "you clicked image " + (position + 1 +" "+ finalStr_CardNumber1.toString()), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public static byte[] decodeImage(String imageDataString) {

        return Base64.decode(imageDataString, Base64.DEFAULT);
    }


}