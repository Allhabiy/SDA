package allhabiy.sda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import allhabiy.sda.R;
import allhabiy.sda.models.Box;

/**
 * Created by Shareef on 3/16/2016.
 */
public class BoxDetailsActivity extends AppCompatActivity {

    private TextView textView_boxName;
    private TextView textView_boxStatus;
    private Button button_drive;
    private Button button_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_details);

        final Box box = (Box) getIntent().getSerializableExtra("Box");

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
                String latitude = box.getLatitude();
                String longitude = box.getLongitude();

                Intent intent = new Intent(BoxDetailsActivity.this, MapActivity.class);
                intent.putExtra("activity", "UserDetails");
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                BoxDetailsActivity.this.startActivity(intent);
            }
        });


    }
}
