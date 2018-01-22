package co.id.franknco.ui.nearby;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import co.id.franknco.adapter.ListViewNearbyAdapter;
import co.id.franknco.controller.AppController;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.GetUrlName;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.crypto.Temp3DES;
import co.id.franknco.ui.main.MainActivity;

/**
 * Created by GSS-NB-2017-0013 on 10/1/2017.
 */

public class NearbyActivity extends Fragment {
    private static final String TAG = "NearbyActivity";

   /* @BindView(R.id.toolbar)
    Toolbar toolbar;*/
    @BindView(R.id.img_fnc)
    ImageView _img_fnc;
    @BindView(R.id.img_mondial)
    ImageView _img_mondial;
    @BindView(R.id.img_missmondial)
    ImageView _img_missmondial;
    @BindView(R.id.img_the_palace)
    ImageView _img_thepalace;


    public NearbyActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_nearby, container, false);
        ButterKnife.bind(this, rootView);
        //setupToolbar();

        getBranch_fnc();
        getBranch_missmondial();
        getBranch_mondial();
        getBranch_theP();
        return rootView;
    }

   /* private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }*/

    /**
     * Frank n Co
     */
    void getBranch_fnc() {
        _img_fnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(getActivity(), NearbyActivityMap.class);
                Bundle bundleA = new Bundle();
                bundleA.putInt("outlet", Integer.parseInt("1"));
                intentA.putExtras(bundleA);
               // getActivity().finish();
                startActivity(intentA);

            }
        });
    }

    /**
     * Miss mondial
     */
    void getBranch_missmondial() {
        _img_missmondial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(getActivity(), NearbyActivityMap.class);
                Bundle bundleA = new Bundle();
                bundleA.putInt("outlet", Integer.parseInt("3"));
                intentA.putExtras(bundleA);
               // getActivity().finish();
                startActivity(intentA);

            }
        });
    }

    /**
     * The Palace
     */
    void getBranch_theP() {
        _img_thepalace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(getActivity(), NearbyActivityMap.class);
                Bundle bundleA = new Bundle();
                bundleA.putInt("outlet", Integer.parseInt("4"));
                intentA.putExtras(bundleA);
               // getActivity().finish();
                startActivity(intentA);
            }
        });
    }

    /**
     * The Mondial
     */
    void getBranch_mondial() {
        _img_mondial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(getActivity(), NearbyActivityMap.class);
                Bundle bundleA = new Bundle();
                bundleA.putInt("outlet", Integer.parseInt("2"));
                intentA.putExtras(bundleA);
               // getActivity().finish();
                startActivity(intentA);

            }
        });
    }


   /* private void setupAdapter() {
        List<App> apps = getApps();

        ListViewNearbyAdapter adapter = new ListViewNearbyAdapter(NearbyActivity.this , arraylist);
        _listCollege.setAdapter(adapter);


    }

    private List<App> getApps() {
        List<App> apps = new ArrayList<>();
        [{TITLE=FRANK N CO PLAZA INDONESIA, LATITUDE=-6.193276, LONGITUDE=106.821879, TELP1=021 8888 8888, TELP2;=021 7777 7777, ADDRESS=Mall Taman Anggrek Level U 91 Kavling 28, Jalan Let Jend S Parman, RT.12/RW.1, Tj. Duren Sel., Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470, Indonesia}
        apps.add(new App("FRANK N CO PLAZA INDONESIA", R.drawable.creditcard, 4.6f));
        apps.add(new App("FRANK N CO TAMAN ANGGREK", R.drawable.creditcard, 4.8f));
        apps.add(new App("4444 5555 0000 1111", R.drawable.creditcard, 4.5f));
        apps.add(new App("1111 3333 5555 7777", R.drawable.creditcard, 4.2f));
        return apps;
    }*/
}