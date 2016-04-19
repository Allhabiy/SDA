package allhabiy.sda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import allhabiy.sda.R;
import allhabiy.sda.models.DonationCollection;
import allhabiy.sda.models.DonationDistribute;
import allhabiy.sda.utils.UserLocation;


public class DistributeDonationsDetailsActivity extends AppCompatActivity {

    private TextView textView_NeedyName;
    private TextView textView_NeedyPriority1;
    private TextView textView_NeedyPriority2;
    private TextView textView_NeedyPriority3;

    private Button button_drive;

    private DonationDistribute distribute;
    private UserLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_donations_details);

        distribute = (DonationDistribute) getIntent().getSerializableExtra("Distribute");

        location = new UserLocation(this);

        textView_NeedyName = (TextView) findViewById(R.id.textView_NeedyName);
        textView_NeedyName.setText(distribute.getName());

        textView_NeedyPriority1 = (TextView) findViewById(R.id.textView_NeedyPriority1);
        textView_NeedyPriority1.setText(distribute.getPriority1());

        textView_NeedyPriority2 = (TextView) findViewById(R.id.textView_NeedyPriority2);
        textView_NeedyPriority2.setText(distribute.getPriority2());

        textView_NeedyPriority3 = (TextView) findViewById(R.id.textView_NeedyPriority3);
        textView_NeedyPriority3.setText(distribute.getPriority3());


        button_drive = (Button) findViewById(R.id.button_drive);
        button_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = distribute.getLatitude();
                String longitude = distribute.getLongitude();

                Intent intent = new Intent(DistributeDonationsDetailsActivity.this, MapActivity.class);
                intent.putExtra("activity", "UserDetails");
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                DistributeDonationsDetailsActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        location.disconnect();
    }


}
