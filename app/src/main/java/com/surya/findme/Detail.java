package com.surya.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Detail extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.place_name)
    TextView mPlaceName;
    @BindView(R.id.place_rating)
    TextView mPlaceRating;
    @BindView(R.id.place_distance)
    TextView mPlaceDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlaceName.setText(getIntent().getStringExtra(MapsActivity.EXTRA_PLACE));
        mPlaceRating.setText(getIntent().getStringExtra(MapsActivity.EXTRA_RATING));
        mPlaceDistance.setText(getIntent().getStringExtra(MapsActivity.EXTRA_DISTANCE));

        mViewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(new ReviewsAdapter(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings){

            return true;
        }

        if (id == R.id.action_complain){
            startActivity(new Intent(Detail.this,ComplianTicket.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.write_review)
    public void writeReview(){
        //TODO Create a dialog


    }

    @OnClick(R.id.fab)
    public void navigate(){
        String latlng = getIntent().getStringExtra(MapsActivity.EXTRA_LATLNG);
        Utils.openMaps(this,new LatLng(Double.valueOf(latlng.split(",")[0]),Double.valueOf(latlng.split(",")[0])));
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter{

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putStringArray("urls",null);
            bundle.putInt("position",position);

            ImageFragment fragment = new ImageFragment();

            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
