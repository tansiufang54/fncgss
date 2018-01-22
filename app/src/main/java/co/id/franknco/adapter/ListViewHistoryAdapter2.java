package co.id.franknco.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.id.franknco.R;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.controller.InterfaceHelper;

/**
 * Created by GSP-NB-2015-0005 on 15-Dec-17.
 */

public class ListViewHistoryAdapter2 extends BaseAdapter implements ListAdapter {
    private Activity activity;
    private Temp3DES temp3DES;
    private JSONArray dataArray = new JSONArray();
    private InterfaceHelper interfaceHelper;

    public ListViewHistoryAdapter2(Activity anActivity, JSONArray historyDataArray)
    {
        this.activity = anActivity;
        this.temp3DES = new Temp3DES(anActivity.getApplicationContext());
        this.dataArray = historyDataArray;
        this.interfaceHelper = InterfaceHelper.getInstance();
    }


    @Override
    public int getCount() {
        if(this.dataArray == null)
        {
            return 0;
        }
        else return dataArray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        if(dataArray == null) return null;
        else return dataArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject obj = getItem(position);
        return obj.optLong("CARD_ID");
    }


    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        View rowView = customView;


        if(rowView == null)
        {
            rowView = activity.getLayoutInflater().inflate(R.layout.listview_history2, null);
        }

        TextView his_title = (TextView) rowView.findViewById(R.id.lvhis_item_title);
        TextView his_date = (TextView) rowView.findViewById(R.id.lvhis_item_date);
        TextView his_amount = (TextView) rowView.findViewById(R.id.lvhis_item_amount);

        JSONObject pointerItem = getItem(position);
        try {
            his_title.setText(temp3DES.decrypt(pointerItem.getString("transaction_type")));
            String amount = temp3DES.decrypt(pointerItem.getString("x761"));
            his_amount.setText(interfaceHelper.numberFormat(Integer.parseInt(amount)));

            String datetime = temp3DES.decrypt(pointerItem.getString("trans_date"));
            //DATE=2017-09-26 16:49:04
            String[] splitDateTime = datetime.split(" ");
            String resultDate = splitDateTime[0];
            String resultTime = splitDateTime[1];

            String dd = resultDate.substring(8,10);
            String mm = resultDate.substring(5,7);
            String yy = resultDate.substring(2,4);

            his_date.setText(dd + " / " + interfaceHelper.monthConvert(mm) + " / " + yy);


        }catch (JSONException je) {
            je.printStackTrace();
        }


        return rowView;
    }



}
