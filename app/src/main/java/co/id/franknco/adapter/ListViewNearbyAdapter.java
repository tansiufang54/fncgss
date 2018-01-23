package co.id.franknco.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import co.id.franknco.R;
import co.id.franknco.ui.history.HistoryActivity;
import co.id.franknco.ui.nearby.NearbyActivity;
import co.id.franknco.ui.nearby.NearbyActivityMap;

/**
 * Created by GSS-NB-2017-0013 on 10/1/2017.
 */

public class ListViewNearbyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewNearbyAdapter(Context context,
                                 ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }


    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        TextView txtTitle = null;
        TextView txtAddress = null;
        TextView txtOpening = null;
        TextView txtKm = null;
        TextView txtTelp1 = null;
        TextView txtTelp2 = null;
        String txtLatitude = null;
        String txtLongitude = null;
        String stringAddress = null;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_nearby, parent, false);
        resultp = data.get(position);

        txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
        txtAddress = (TextView) convertView.findViewById(R.id.txt_address);
        txtOpening = (TextView) convertView.findViewById(R.id.txt_open);
        txtKm = (TextView) convertView.findViewById(R.id.txt_km);
//        txtTelp1 = (TextView) convertView.findViewById(R.id.txt_telp1);
//        txtTelp2 = (TextView) convertView.findViewById(R.id.txt_telp2);


        txtTitle.setText(resultp.get(NearbyActivityMap.TITLE));
        stringAddress = resultp.get(NearbyActivityMap.ADDRESS);
        txtAddress.setText(resultp.get(NearbyActivityMap.ADDRESS));

//        txtTelp1.setText(resultp.get(NearbyActivityMap.TELP1));
//        txtTelp2.setText(resultp.get(NearbyActivityMap.TELP2));
//        txtLatitude = resultp.get(NearbyActivityMap.LATITUDE);
//        txtLongitude = resultp.get(NearbyActivityMap.LONGITUDE);
        final String finaltxtAddress = stringAddress;
        final String finalTxtLatitude = txtLatitude;
        final String finalTxtLongitude = txtLongitude;

        Log.i("ListViewNearbyAdapter", "getView: item count list: " + resultp.size());


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                resultp = data.get(position);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + finaltxtAddress));

                context.startActivity(intent);

            }
        });
        return convertView;
    }
}




