package allhabiy.sda.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import allhabiy.sda.R;
import allhabiy.sda.utils.Config;
import allhabiy.sda.utils.UserLocation;

public class DonatorLoginActivity extends AppCompatActivity {

    private Button updateLocation2;
    private Button Send_donation;

    private SharedPreferences prefs;
    private UserLocation userLocation;
    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donator);

        prefs = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        userLocation = new UserLocation(this);

        updateLocation2 = (Button) findViewById(R.id.user2Location);
        updateLocation2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateLocation();
            }
        });

        Send_donation = (Button) findViewById(R.id.Send_donation_btn);
        Send_donation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent open = new Intent(DonatorLoginActivity.this, SendDonationActivity.class);
                startActivity(open);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        userLocation.connect();
//    }

    @Override
    protected void onStop() {
        super.onStop();
        userLocation.disconnect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void updateLocation() {
        // Creating a string request
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.UPDATE_USER2_LOCATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volley", response);
                        Toast.makeText(getApplicationContext(), "Location updated successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Adding parameters to request
                String id = prefs.getString(Config.EMAIL_SHARED_PREF, "");
                double latitude = userLocation.getLastLocation().getLatitude();
                double longitude = userLocation.getLastLocation().getLongitude();

                params.put(Config.KEY_NATIONAL_ID, id);
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            profileActivity.logout();
        }
        return super.onOptionsItemSelected(item);
    }
}

