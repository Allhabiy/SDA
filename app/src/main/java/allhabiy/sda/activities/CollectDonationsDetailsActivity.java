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
import allhabiy.sda.models.DonationCollection;
import allhabiy.sda.utils.Config;
import allhabiy.sda.utils.UserLocation;


public class CollectDonationsDetailsActivity extends AppCompatActivity {

    private TextView textView_DonorName;
    private TextView textView_DonorDate;
    private TextView textView_DonorTime;
    private TextView textView_DonorType;

    private Button button_drive;

    private DonationCollection donations;
    private UserLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_donations_details);

        donations = (DonationCollection) getIntent().getSerializableExtra("Donation");

        location = new UserLocation(this);

        textView_DonorName = (TextView) findViewById(R.id.textView_DonorName);
        textView_DonorName.setText(donations.getName());

        textView_DonorDate = (TextView) findViewById(R.id.textView_DonorDate);
        textView_DonorDate.setText(donations.getDate());

        textView_DonorTime = (TextView) findViewById(R.id.textView_DonorTime);
        textView_DonorTime.setText(donations.getTime());

        textView_DonorType = (TextView) findViewById(R.id.textView_DonorType);
        textView_DonorType.setText(donations.getType());


        button_drive = (Button) findViewById(R.id.button_drive);
        button_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = donations.getLatitude();
                String longitude = donations.getLongitude();

                Intent intent = new Intent(CollectDonationsDetailsActivity.this, MapActivity.class);
                intent.putExtra("activity", "UserDetails");
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                CollectDonationsDetailsActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        location.disconnect();
    }


}
