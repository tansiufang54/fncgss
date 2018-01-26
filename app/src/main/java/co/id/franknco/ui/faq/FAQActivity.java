package co.id.franknco.ui.faq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.adapter.RecyclerAdapter;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.dialog.CustomDialog;
import co.id.franknco.interfaces.FaqListener;
import co.id.franknco.model.Faq;

public class FAQActivity extends AppCompatActivity {

    public static final String TAG = FAQActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // @BindView(R.id.lvExp)ExpandableListView expListView;


    private ConfigurasiAPI function;
    private Temp3DES temp3DES;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Faq> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        temp3DES = new Temp3DES(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        setupToolbar();
        prepareListData();


    }


    //       expListView.setAdapter(listAdapter);

    //       expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

    //           @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
//                return false;
//            }
//        });

//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

//            @Override
    //           public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
    //           }
    //       });

    //       expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

//            @Override
    //           public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

//            }
//        });

//   }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    private void setAdapter(List<Faq> list) {
        if (mAdapter == null) {
            mAdapter = new RecyclerAdapter(list, temp3DES);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
        }else mAdapter.notifyDataSetChanged();

    }

    private void prepareListData() {
        function = new ConfigurasiAPI(this);
        function.Faq(new FaqListener() {
            @Override
            public void onResult(List<Faq> list) {
                setAdapter(list);
            }
        });
    }
}
