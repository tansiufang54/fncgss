package co.id.franknco.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.ui.main.MainActivity;

/**
 * Created by darwin on 1/24/18.
 */

public class MyPointsActivity extends AppCompatActivity {

    public static final String TAG = MyPointsActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecyclerView rvPointList;
    private LinearLayoutManager layoutManager;
    private PointsAdapter adapter;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_points);
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
        rvPointList = (RecyclerView) findViewById(R.id.rv_point_list);
        layoutManager               = new LinearLayoutManager(this);
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new PointsAdapter(this, listDataChild);
            rvPointList.setLayoutManager(layoutManager);
            rvPointList.setAdapter(adapter);
        }else adapter.notifyDataSetChanged();
    }

    private void fetchData() {
        listDataChild = new HashMap<String, List<String>>();

        List<String> listName = new ArrayList<>();
        listName.add("Bag");
        listName.add("Earing");
        listName.add("Necklace");
        listName.add("Ring");
        listName.add("Shoes");

        List<String> listPoints = new ArrayList<>();
        listPoints.add("150");
        listPoints.add("200");
        listPoints.add("150");
        listPoints.add("300");
        listPoints.add("175");

        List<String> listDesc = new ArrayList<>();
        for (int i = 1; i<6 ; i++) {
            listDesc.add("Pondok Indah Mall " + i);
        }

        listDataChild.put("name", listName);
        listDataChild.put("description", listDesc);
        listDataChild.put("points", listPoints);
    }

}
