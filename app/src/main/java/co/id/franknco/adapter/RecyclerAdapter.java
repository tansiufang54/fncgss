package co.id.franknco.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.id.franknco.R;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public RecyclerAdapter(List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this.listDataChild = listChildData;
        this.listDataHeader = listDataHeader;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHeader;
        public TextView txtFooter;
        public Button buttonDrop;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            buttonDrop = (Button)  v.findViewById(R.id.buttonDrop);


        }
    }

    public void remove(int position) {
        notifyItemRemoved(position);
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String name = String.valueOf(listDataHeader.get(position));
        String desc = TextUtils.join(", ", listDataChild.get(this.listDataHeader.get(position)));
        holder.txtHeader.setText(name);
        holder.txtFooter.setText(desc);
        holder.buttonDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    view.setSelected(false);
                    holder.txtFooter.setVisibility(View.GONE);
                }else {
                    view.setSelected(true);
                    holder.txtFooter.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataChild.size();
    }
}
