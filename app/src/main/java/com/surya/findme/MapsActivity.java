package com.surya.findme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST = 5;
    public static final String EXTRA_LATLNG = "latlng";
    public static final String EXTRA_PLACE = "place";
    public static final String EXTRA_DISTANCE = "distance";
    public static final String EXTRA_RATING = "rating";
    private GoogleMap mMap;
    @BindView(R.id.bottom_card)
    CardView bottom_card;
    @BindView(R.id.place_name)
    TextView mPlaceName;
    @BindView(R.id.place_rating)
    TextView mPlaceRating;
    @BindView(R.id.place_distance)
    TextView mPlaceDistance;
    @BindView(R.id.place_image)
    ImageView mPlaceImage;
    private LatLng currentLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search){

            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
                startActivityForResult(intent,PLACE_AUTOCOMPLETE_REQUEST_CODE);

            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(28.8406737,77.1053533);
        mMap.addMarker(new MarkerOptions().position(sydney)/*.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_location_on))*/.title("Marker 1").anchor(0.5f,1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        sydney = new LatLng(28.8390103,77.1157173);
        mMap.addMarker(new MarkerOptions().position(sydney)/*.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_location_on))*/.title("Marker 2"));

        sydney = new LatLng(28.5968795,77.076313);
        mMap.addMarker(new MarkerOptions().position(sydney)/*.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_location_on))*/.title("Marker 3"));

        sydney = new LatLng(28.8575278,77.0943884);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker 4"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                showBottomCard(marker.getPosition());

                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (bottom_card.getVisibility() == View.VISIBLE) {
                    //Load animation
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_down);

                    // Start animation
                    bottom_card.startAnimation(slide_down);

                    bottom_card.setVisibility(View.GONE);
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    private void showBottomCard(LatLng latLng) {

        currentLatlng = latLng;

        bottom_card.setVisibility(View.VISIBLE);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        // Start animation
        bottom_card.startAnimation(slide_up);

    }

    @OnClick(R.id.route_button)
    public void navigate() {
        if (currentLatlng!= null)
            Utils.openMaps(this,currentLatlng);
        else
            Toast.makeText(this, "Error in opening maps", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bottom_card)
    public void openDetails(){

        if (currentLatlng != null) {

            Intent intent = new Intent(MapsActivity.this, Detail.class);
            intent.putExtra(EXTRA_LATLNG,currentLatlng.latitude + "," + currentLatlng.longitude);
            intent.putExtra(EXTRA_RATING,mPlaceRating.getText().toString());
            intent.putExtra(EXTRA_DISTANCE,mPlaceDistance.getText().toString());
            intent.putExtra(EXTRA_PLACE,mPlaceName.getText().toString());
            startActivity(intent);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e(TAG, "Place: " + place.getName());

                if (mMap != null){

                    mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));

                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST){
            if (resultCode == RESULT_OK){

            }else{
                Toast.makeText(this, "This app does not run without location permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}
