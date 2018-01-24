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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.adapter.RecyclerAdapter;

public class FAQActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
   // @BindView(R.id.lvExp)ExpandableListView expListView;

    FAQExpandableAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listAdapter = new FAQExpandableAdapter(this, listDataHeader, listDataChild);

        setupToolbar();
        prepareListData();


        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(listDataHeader, listDataChild);
        recyclerView.setAdapter(mAdapter);


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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("FAQ 1");
        listDataHeader.add("FAQ 2");
        listDataHeader.add("FAQ 3");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        String faqValue = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet felis " +
                "eu justo elementum laoreet tempus sed ex. Fusce in tellus non urna ultrices vulputate " +
                "nec nec massa. Nulla vitae ex ante. Sed id ullamcorper eros. Pellentesque lobortis aliquam " +
                "metus ut feugiat. Curabitur ornare nibh ac rhoncus ultricies. Ut luctus pretium nulla. " +
                "Cras justo arcu, malesuada non mattis eget, blandit in neque.";

        top250.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet felis " +
                "eu justo elementum laoreet tempus sed ex. Fusce in tellus non urna ultrices vulputate " +
                "nec nec massa. Nulla vitae ex ante. Sed id ullamcorper eros. Pellentesque lobortis aliquam " +
                "metus ut feugiat. Curabitur ornare nibh ac rhoncus ultricies. Ut luctus pretium nulla. " +
                "Cras justo arcu, malesuada non mattis eget, blandit in neque.");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet felis " +
                "eu justo elementum laoreet tempus sed ex. Fusce in tellus non urna ultrices vulputate " +
                "nec nec massa. Nulla vitae ex ante. Sed id ullamcorper eros. Pellentesque lobortis aliquam " +
                "metus ut feugiat. Curabitur ornare nibh ac rhoncus ultricies. Ut luctus pretium nulla. " +
                "Cras justo arcu, malesuada non mattis eget, blandit in neque.");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet felis " +
                "eu justo elementum laoreet tempus sed ex. Fusce in tellus non urna ultrices vulputate " +
                "nec nec massa. Nulla vitae ex ante. Sed id ullamcorper eros. Pellentesque lobortis aliquam " +
                "metus ut feugiat. Curabitur ornare nibh ac rhoncus ultricies. Ut luctus pretium nulla. " +
                "Cras justo arcu, malesuada non mattis eget, blandit in neque.");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);

    }

}
