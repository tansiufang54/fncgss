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
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.model.Faq;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Faq> list = new ArrayList<>();
    private Temp3DES temp3DES;

    public RecyclerAdapter(List<Faq> list, Temp3DES temp3DES) {
        this.list = list;
        this.temp3DES = temp3DES;
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
        Faq faq = list.get(position);
        holder.txtHeader.setText(temp3DES.decrypt(faq.question));
        holder.txtFooter.setText(temp3DES.decrypt(faq.answer));
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
        return list.size();
    }
}
