package allhabiy.sda.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import allhabiy.sda.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setZoomGesturesEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String callerActivity = getIntent().getStringExtra("activity");
        if (callerActivity.equals("Admin")) {
            ArrayList<String> locations = (ArrayList<String>) bundle.getSerializable("locations");
            for (String location : locations) {
                String[] geoData = location.split(",");
                double latitude = Double.valueOf(geoData[0]);
                double longitude = Double.valueOf(geoData[1]);
                LatLng geoLoc = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(geoLoc).title("Marker"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(geoLoc));
            }

        } else if (callerActivity.equals("UserDetails")) {
            double latitude = Double.valueOf(bundle.getString("latitude", ""));
            double longitude = Double.valueOf(bundle.getString("longitude", ""));
            LatLng geoLoc = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(geoLoc).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoLoc,15));
        }

    }
}
