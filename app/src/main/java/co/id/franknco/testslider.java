package co.id.franknco;

/**
 * Created by GSS-NB-2017-0013 on 10/1/2017.
 */

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.id.franknco.adapter.MyCustomPagerAdapter;

public class testslider extends AppCompatActivity {
    ViewPager viewPager;
    int images[] = {R.drawable.creditcard, R.drawable.creditcard, R.drawable.creditcard, R.drawable.creditcard};
    MyCustomPagerAdapter myCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_test);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

      /*  myCustomPagerAdapter = new MyCustomPagerAdapter(testslider.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);*/
    }
}