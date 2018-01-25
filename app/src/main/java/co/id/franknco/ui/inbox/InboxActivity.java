package co.id.franknco.ui.inbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.ui.settings.PointsAdapter;

/**
 * Created by darwin on 1/25/18.
 */

public class InboxActivity extends AppCompatActivity{

    public static final String TAG = InboxActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecyclerView rvInboxList;
    private LinearLayoutManager layoutManager;
    private InboxAdapter adapter;
    private HashMap<String, List<String>> msgDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inbox);
        ButterKnife.bind(this);
        setupToolbar();
        initView();
        fetchData();
        setAdapter();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    private void initView() {
        rvInboxList = (RecyclerView) findViewById(R.id.rv_list_inbox);
        layoutManager               = new LinearLayoutManager(this);
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new InboxAdapter(this, msgDataChild);
            rvInboxList.setLayoutManager(layoutManager);
            rvInboxList.setAdapter(adapter);
        }else adapter.notifyDataSetChanged();
    }

    private void fetchData() {
        msgDataChild = new HashMap<String, List<String>>();

        List<String> listName = new ArrayList<>();
        listName.add("Meaning of wedding ring");
        listName.add("Purchase successful");
        listName.add("Top up successful!");
        listName.add("The Function Of The Logo");
        listName.add("Advertisers Embrace Rich Media Format");

        List<String> listDesc = new ArrayList<>();
        for (int i = 1; i<6 ; i++) {
            listDesc.add("Pondok Indah Mall " + i);
        }

        msgDataChild.put("name", listName);
        msgDataChild.put("message", listDesc);
    }
}
