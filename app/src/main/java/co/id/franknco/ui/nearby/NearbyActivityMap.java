package co.id.franknco.ui.nearby;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
import co.id.franknco.model.General;
import co.id.franknco.ui.login.LoginActivity;
import retrofit2.http.PUT;

import static android.widget.LinearLayout.*;

public class NearbyActivityMap extends AppCompatActivity implements OnMapReadyCallback,
        AppBarLayout.OnOffsetChangedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.proListNearby)
    ProgressBar _proCollageList;
    @BindView(R.id.logo)
    ImageView _imgLogo;
    @BindView(R.id.listNearby)
    ListView _listCollege;
    @BindView(R.id.sd_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.sd_appbar_layout)
    AppBarLayout appBarLayout;

    public static final String TAG = NearbyActivity.class.getSimpleName();

    ConfigurasiAPI function;
    SessionManager sessionManager;


    private SupportMapFragment mapFragment;

    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private ArrayList<LatLng> points; //added
    Polyline line; //added

    private Temp3DES temp3DES;
    private int key;
    private String keyoutlet;
    private GoogleMap googleMap;
    private LatLngBounds bounds;

    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();

    public static String TITLE = "TITLE";
    public static String ADDRESS = "ADDRESS";
    public static String TELP1 = "TELP1";
    public static String TELP2 = "TELP2";
    public static String LATITUDE = "LATITUDE";
    public static String LONGITUDE = "LONGITUDE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_map);
        ButterKnife.bind(this);
        points = new ArrayList<LatLng>();

        temp3DES = new Temp3DES(this);
        function = new ConfigurasiAPI(this);
        sessionManager = new SessionManager(this);

        Bundle bundlePage1 = getIntent().getExtras();
        key = bundlePage1.getInt("outlet");

        if (key == 1) {
            keyoutlet = "1";

        } else if (key == 2) {
            keyoutlet = "2";

        } else if (key == 3) {
            keyoutlet = "3";

        } else if (key == 4) {
            keyoutlet = "4";

        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appBarLayout.addOnOffsetChangedListener(this);

        getAddressBranch(keyoutlet);
        setupToolbar(keyoutlet);
      /*  String latitudeDest = this.getIntent().getStringExtra("latitude_dest");
        String longitudeDest = this.getIntent().getStringExtra("longitude_dest");
        try {
            if (!latitudeDest.isEmpty() && !longitudeDest.isEmpty()) {

                addMarker(latitudeDest, longitudeDest);
            }
        }catch (NullPointerException e){

        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (appBarLayout != null)appBarLayout.removeOnOffsetChangedListener(this);
        //stop location updates when Activity is no longer active
      /*  if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbarLayout.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            params.height = (int) (height / 2.30);
            mapFragment.getView().setLayoutParams(params);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null)return;
        this.googleMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.214620, 106.845130), 10));
    }

    /**
     * MAP
     */
   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setMaxZoomPreference(20);
        mGoogleMap.setMinZoomPreference(5);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(150000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, NearbyActivityMap.this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));


    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(NearbyActivityMap.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void addMarker(String lat2, String lng2) {
        //you can use those value here then.

        final LatLng latLng = new LatLng(Double.parseDouble(lat2), Double.parseDouble(lng2));
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("My      Marker"));
    }

    private void redrawLine() {

        mGoogleMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        // addMarker(); //add Marker in current position
        line = mGoogleMap.addPolyline(options); //add Polyline
    }
*/

//   private void setClickEvents() {
//       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//           @Override
//           public void onRefresh() {
//               swipeRefreshLayout.setRefreshing(false);
//           }
//       });
//   }

    private void setupToolbar(String outlet) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

            if (outlet.contains("1")) {
                _imgLogo.setImageResource(R.drawable.fnc_logo);
                getSupportActionBar().setDisplayUseLogoEnabled(true);
            } else if (outlet.contains("2")) {
                _imgLogo.setImageResource(R.drawable.mondial_logo);
                getSupportActionBar().setDisplayUseLogoEnabled(true);

            } else if (outlet.contains("3")) {
                _imgLogo.setImageResource(R.drawable.missmondial_logo);
                getSupportActionBar().setDisplayUseLogoEnabled(true);

            } else if (outlet.contains("4")) {
                _imgLogo.setImageResource(R.drawable.thepalace_logo);
                getSupportActionBar().setDisplayUseLogoEnabled(true);
            }


        } else if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }


    /**
     * GET CARD LIST TRANSACTION
     */
    //MINTA WISELY BUATIN
    public void getAddressBranch(String code_place) {
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        String tag_string_req = "req_getaddressbranch";
        String DataMSG = "map";
        DataMSG = temp3DES.encrypt(code_place);
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("code", "2000");
        postParam.put("msg", DataMSG);
        postParam.put("token", sessionManager.getTokenId());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                         try {
                            String code = response.getString("code");
                            String data = response.getString("msg");

                            if (code.equals("2010")) {

                                JSONArray cardlist = response.getJSONArray("msg");
                                 for (int i = 0; i < cardlist.length(); i++) {

                                    HashMap<String, String> map = new HashMap<String, String>();

                                    map.put("TITLE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("branch_name")));
                                    map.put("ADDRESS", temp3DES.decrypt(cardlist.getJSONObject(i).getString("branch_address")));
                                    map.put("TELP1", temp3DES.decrypt(cardlist.getJSONObject(i).getString("branch_phone")));
                                    map.put("TELP2", temp3DES.decrypt(cardlist.getJSONObject(i).getString("branch_phone2")));
                                    map.put("LATITUDE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("latitude")));
                                    map.put("LONGITUDE", temp3DES.decrypt(cardlist.getJSONObject(i).getString("longitude")));

                                    arraylist.add(map);

                                    double lat = Double.valueOf(temp3DES.decrypt(cardlist.getJSONObject(i).getString("latitude")).isEmpty()
                                            ? "0.0" : temp3DES.decrypt(cardlist.getJSONObject(i).getString("latitude")));
                                    double lon = Double.valueOf(temp3DES.decrypt(cardlist.getJSONObject(i).getString("longitude")).isEmpty()
                                            ? "0.0" : temp3DES.decrypt(cardlist.getJSONObject(i).getString("longitude")));

                                    if (lat != 0.0 & lon != 0.0) {
                                        MarkerOptions sMarker = CreateMarker(
                                                temp3DES.decrypt(cardlist.getJSONObject(i).getString("branch_name")),
                                                lat, lon);
                                        googleMap.addMarker(sMarker);
                                        builder.include(sMarker.getPosition());
                                        bounds = builder.build();

                                        Log.i(TAG, "onResponse: Get location all data in web service: " + sMarker.getPosition());
                                    }
                                 }

                                if (arraylist != null) {
                                    _proCollageList.setVisibility(View.GONE);
                                    _listCollege.setVisibility(View.VISIBLE);
                                      ListViewNearbyAdapter adapter = new ListViewNearbyAdapter(NearbyActivityMap.this, arraylist);
                                    _listCollege.setAdapter(adapter);

                                   if (bounds != null) {
                                       int width = getResources().getDisplayMetrics().widthPixels;
                                       int height = getResources().getDisplayMetrics().heightPixels;
                                       int padding = (int) (width * 0.48);
                                       googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
                                   }
                                }

                            } else if (code.equals("1920")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(NearbyActivityMap.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                   /*     Intent myintent = new Intent(NearbyActivity.this, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        NearbyActivity.this.finish();
                                        NearbyActivity.this.startActivity(myintent);*/
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            } else if (code.equals("1930")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(NearbyActivityMap.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                     /*   Intent myintent = new Intent(NearbyActivity.this, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        NearbyActivity.this.finish();
                                        NearbyActivity.this.startActivity(myintent);*/
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            } else if (code.equals("1940")) {
                                AlertDialog.Builder hasiljalur;
                                AlertDialog dialogjalur;
                                hasiljalur = new AlertDialog.Builder(NearbyActivityMap.this, 0);
                                hasiljalur.setMessage(data).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                      /*  Intent myintent = new Intent(NearbyActivity.this, MainActivity.class);
                                        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        NearbyActivity.this.finish();
                                        NearbyActivity.this.startActivity(myintent);*/
                                    }
                                });
                                dialogjalur = hasiljalur.create();
                                dialogjalur.setTitle("Confirmation");
                                try {
                                    dialogjalur.show();
                                } catch (Exception e) {
                                    // WindowManager$BadTokenException will be caught and the app would not display
                                    // the 'Force Close' message
                                }

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    String errMsg;
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                         errMsg = new String(error.networkResponse.data);
                    } else {
                        errMsg = error.getMessage();
                    }

                    Log.i(TAG, "onErrorResponse: Parse error msg: " + errMsg);
                    Log.i(TAG, "onErrorResponse: Parse error msg: " + error);

                    Toast.makeText(NearbyActivityMap.this,
                            "Connection Problem!", Toast.LENGTH_LONG).show();
                    _proCollageList.setVisibility(GONE);

                } else { //DATA GIVEN
                   /* Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();*/
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        //for timeout and make data do not send twice
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GetUrlName.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private MarkerOptions CreateMarker(String title, double latitude, double longitude) {
        LatLng sLocation = new LatLng(latitude, longitude);
        MarkerOptions markerStore = new MarkerOptions();
        markerStore.title(title);
        markerStore.position(sLocation);
        return markerStore;
    }
}
