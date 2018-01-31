package co.id.franknco.ui.main;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.controller.ConfigurasiAPI;
import co.id.franknco.controller.SessionManager;
import co.id.franknco.fragment.BottomNav1;
import co.id.franknco.ui.nearby.NearbyActivity;
import co.id.franknco.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity/* implements NavigationView.OnNavigationItemSelectedListener*/ {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.view_pager)AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)AHBottomNavigation bottomNavigation;

    private FragmentManager fragmentManager;
    private MainPagerAdapter2 mPagerAdapter;
    private boolean useMenuResource = true;
    private AHBottomNavigationAdapter navigationAdapter;
    private int[] tabColors;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    SessionManager sessionManager;
    ConfigurasiAPI function;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        function = new ConfigurasiAPI(this);
        setupToolbar();
       // function.CheckToken();

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        ImageView imgHeader =  (ImageView)header.findViewById(R.id.img_header);
        TextView txtName = (TextView)header.findViewById(R.id.txt_header_name);
        txtName.setText(sessionManager.getUser());*/
        fragmentManager = getFragmentManager();

        initUI();

        mPagerAdapter = new MainPagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void initUI() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        if (!useMenuResource) {
            tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
            navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation);
            navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);

            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
            bottomNavigation.setAccentColor(Color.parseColor("#870040"));
            bottomNavigation.setInactiveColor(Color.parseColor("#CCCCCC"));
            bottomNavigation.setForceTint(true);
            bottomNavigation.setBehaviorTranslationEnabled(false);

        } else {
            AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_cardgrey, R.color.gold);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_locationgrey, R.color.gold);
            AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_profilegrey, R.color.gold);

            bottomNavigationItems.add(item1);
            bottomNavigationItems.add(item2);
            bottomNavigationItems.add(item3);

            bottomNavigation.addItems(bottomNavigationItems);

            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
            bottomNavigation.setAccentColor(Color.parseColor("#4a4a4a"));
            bottomNavigation.setInactiveColor(Color.parseColor("#999999"));
            bottomNavigation.setForceTint(true);
            bottomNavigation.setBehaviorTranslationEnabled(false);
            bottomNavigation.setTitleTypeface(Typeface.createFromAsset(getAssets(), "font/AvenirNextLTPro-Regular.otf"));
            bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        }

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position == 0 && !wasSelected){
                    BottomNav1 bottomNav1 = new BottomNav1();
                    Bundle arguments = new Bundle();
                    //                    arguments.putString("Id", txtId);
                    bottomNav1.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, bottomNav1, "bottomNav1")
                            .commit();

                }
                if (position == 1 && !wasSelected) {
                    NearbyActivity bottomNav2 = new NearbyActivity();
                    Bundle arguments = new Bundle();
                    //                    arguments.putString("Id", txtId);
                    bottomNav2.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, bottomNav2, "bottomNav2")
                            .commit();

                }
                if (position == 2 && !wasSelected){
                    SettingsActivity bottomNav3 = new SettingsActivity();
                    Bundle arguments = new Bundle();
                    //                    arguments.putString("Id", txtId);
                    bottomNav3.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, bottomNav3, "bottomNav3")
                            .commit();

                }

                return true;
            }
        });

    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_drawer_nearby) {
            startActivity(new Intent(this, NearbyActivity.class));
        }   else if (id == R.id.nav_drawer_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }  else if (id == R.id.nav_drawer_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        }else if (id == R.id.nav_drawer_logout) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

}
