package co.id.franknco.adapter;

/**
 * Created by GSS-NB-2017-0013 on 9/29/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import co.id.franknco.R;
import co.id.franknco.ui.history.HistoryActivity;

public class ListViewHistoryAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewHistoryAdapter(Context context,
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtTransactionType = null;
        TextView txtCardId = null;
        TextView txtCardNumber = null;
        TextView txtAmount = null;
        TextView txtDate = null;
        TextView txtLocation = null;
        TextView txtCashier = null;
        TextView txtPaymentMethod = null;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_history, parent, false);
        resultp = data.get(position);

        txtTransactionType = (TextView) convertView.findViewById(R.id.txt_transaction_type);
        //txtCardId = (TextView) convertView.findViewById(R.id.txt_card_id);
        txtCardNumber = (TextView) convertView.findViewById(R.id.txt_card_number);
        txtAmount = (TextView) convertView.findViewById(R.id.txt_amount);
        txtDate = (TextView) convertView.findViewById(R.id.txt_date);
        txtLocation = (TextView) convertView.findViewById(R.id.txt_location);


        txtTransactionType.setText(resultp.get(HistoryActivity.TRANSACTION_TYPE));
        //txtCardId.setText(resultp.get(HistoryActivity.CARD_ID));
        txtCardNumber.setText(resultp.get(HistoryActivity.CARD_NUMBER));
        txtAmount.setText(resultp.get(HistoryActivity.AMOUNT));
        txtDate.setText(resultp.get(HistoryActivity.DATE));
        //txtLocation.setText(resultp.get(HistoryActivity.LOCATION));

        return convertView;
    }
}




