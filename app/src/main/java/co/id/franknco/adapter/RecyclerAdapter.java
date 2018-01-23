package co.id.franknco.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import co.id.franknco.R;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<String> values;

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


    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public RecyclerAdapter(List<String> myDataset) {
        values = myDataset;
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
        final String name = values.get(position);
        final String def = "The point of this article is to help you grasp the big picture of getting six pack abs.";
        holder.txtHeader.setText(name);
        holder.buttonDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtFooter.setVisibility(holder.txtFooter.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(position);
            }
        });


        holder.txtFooter.setText("" + def);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }




}
