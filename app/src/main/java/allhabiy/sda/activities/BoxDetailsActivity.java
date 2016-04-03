package allhabiy.sda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import allhabiy.sda.models.Box;
import allhabiy.sda.models.User;
import allhabiy.sda.utils.Config;
import allhabiy.sda.utils.UserLocation;


public class BoxDetailsActivity extends AppCompatActivity {

    private TextView textView_boxName;
    private TextView textView_boxStatus;
    private Button button_drive;
    private Button button_location;

    private Box box;
    private UserLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_details);

        box = (Box) getIntent().getSerializableExtra("Box");

        location = new UserLocation(this);

        textView_boxName = (TextView) findViewById(R.id.textView_boxName);
        textView_boxName.setText(box.getName());

        textView_boxStatus = (TextView) findViewById(R.id.textView_boxStatus);
        textView_boxStatus.setText(box.getStatus());

        button_drive = (Button) findViewById(R.id.button_drive);
        button_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = box.getLatitude();
                String longitude = box.getLongitude();

                Intent intent = new Intent(BoxDetailsActivity.this, MapActivity.class);
                intent.putExtra("activity", "UserDetails");
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                BoxDetailsActivity.this.startActivity(intent);
            }
        });

        button_location = (Button) findViewById(R.id.button_location);
        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeLocation();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        location.disconnect();
    }

    private void takeLocation() {
        // Creating a string request
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.UPDATE_BOX_LOCATION_URL,
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
                String name = box.getName();
                double latitude = location.getLastLocation().getLatitude();
                double longitude = location.getLastLocation().getLongitude();

                params.put("name", name);
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

}
