package co.id.franknco.ui.inbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import co.id.franknco.R;
import co.id.franknco.ui.settings.PointsAdapter;

/**
 * Created by darwin on 1/25/18.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder>{

    private Context context;
    private HashMap<String, List<String>> msgDataChild;

    public InboxAdapter(Context context, HashMap<String, List<String>> msgDataChild) {
        this.msgDataChild = msgDataChild;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_inbox_adapter, parent, false);
        return new InboxAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return msgDataChild.get("name").size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, message;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            message = (TextView) itemView.findViewById(R.id.tv_msg);
        }

        public void bindData(int position) {
            String nameInbox = String.valueOf(msgDataChild.get("name").get(position));
            String messageInbox = String.valueOf(msgDataChild.get("message").get(position));

            name.setText(nameInbox);
            message.setText(messageInbox);
        }
    }
}
