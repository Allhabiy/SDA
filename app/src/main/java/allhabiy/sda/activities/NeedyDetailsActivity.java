package allhabiy.sda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import allhabiy.sda.R;
import allhabiy.sda.models.User;

/**
 * Created by Shareef on 3/16/2016.
 */
public class NeedyDetailsActivity extends AppCompatActivity {

    private TextView textView_userName;
    private TextView textView_userPhone;
    private TextView textView_userFamily;
    private TextView textView_userIncome;
    private Button button_drive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needy_details);

        final User user = (User) getIntent().getSerializableExtra("User");

        textView_userName = (TextView) findViewById(R.id.textView_userName);
        textView_userName.setText(user.getName());

        textView_userPhone = (TextView) findViewById(R.id.textView_userPhone);
        textView_userPhone.setText(user.getPhone());

        textView_userFamily = (TextView) findViewById(R.id.textView_userFamily);
        textView_userFamily.setText("" + user.getFamily_member());

        textView_userIncome = (TextView) findViewById(R.id.textView_userIncome);
        textView_userIncome.setText("" + user.getIncome());

        button_drive = (Button) findViewById(R.id.button_drive);
        button_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = user.getLatitude();
                String longitude = user.getLongitude();

                Intent intent = new Intent(NeedyDetailsActivity.this, MapActivity.class);
                intent.putExtra("activity", "UserDetails");
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                NeedyDetailsActivity.this.startActivity(intent);
            }
        });


    }
}
