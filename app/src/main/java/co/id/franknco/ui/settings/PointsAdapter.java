package co.id.franknco.ui.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import co.id.franknco.R;

/**
 * Created by darwin on 1/25/18.
 */

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder>{

    private Context context;
    private HashMap<String, List<String>> listDataChild;

    public PointsAdapter(Context context, HashMap<String, List<String>> listDataChild) {
        this.listDataChild = listDataChild;
        this.context = context;
    }

    @Override
    public PointsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_points_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PointsAdapter.ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return listDataChild.get("name").size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvName, tvDesc, tvPoints, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_product_title);
            tvName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_product_desc);
            tvPoints = (TextView) itemView.findViewById(R.id.tv_product_points);
            tvDate = (TextView) itemView.findViewById(R.id.tv_product_date);
        }

        public void bindData(int position) {
            if (position == 0) {
                tvTitle.setVisibility(View.VISIBLE);
            }

            String pName = listDataChild.get("name").get(position);
            String pDesc = listDataChild.get("description").get(position);
            String pPoints = listDataChild.get("points").get(position);

            tvName.setText(pName);
            tvDesc.setText(pDesc);
            tvPoints.setText(pPoints);
            tvDate.setText("24 / Oct / 17");
        }
    }
}
