package allhabiy.sda.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import allhabiy.sda.R;
import allhabiy.sda.utils.Config;
import allhabiy.sda.utils.UserLocation;

public class SendDonationActivity extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private static final String SEND_DOATION_URL = "http://m7sn.com/sda/app/SendDonation.php";

    private SharedPreferences prefs2;
    private UserLocation userLocation;


    public static final String KEY_TYPE = "type";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    private static final String TIME_PATTERN = "HH:mm";

    private TextView lblDate;
    private TextView lblTime;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private ProfileActivity profileActivity;

    private Button BtnSendDonation;
    RadioGroup radioGroup;
    RadioButton radio_Clothes;
    RadioButton radio_Food;
    TextView txt_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_donation);

        prefs2 = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        userLocation = new UserLocation(this);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radio_Clothes = (RadioButton) findViewById(R.id.radio_Clothes);
        radio_Food = (RadioButton) findViewById(R.id.radio_Food);
        txt_select = (TextView) findViewById(R.id.txt_select);


        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        lblDate = (TextView) findViewById(R.id.lblDate);
        lblTime = (TextView) findViewById(R.id.lblTime);

        update();


        BtnSendDonation = (Button) findViewById(R.id.BtnSendDonation);
        BtnSendDonation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Send_Doantion();

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        userLocation.disconnect();
    }

    private void update() {
        lblDate.setText(dateFormat.format(calendar.getTime()));
        lblTime.setText(timeFormat.format(calendar.getTime()));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDatePicker:
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.btnTimePicker:
                TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void Send_Doantion() {
        if (lblTime.getText().charAt(1) == '8' || lblTime.getText().charAt(1) == '9' || lblTime.getText().charAt(1) == '0' ||
                lblTime.getText().charAt(1) == '1' || lblTime.getText().charAt(1) == '2' || lblTime.getText().charAt(1) == '3') {


            final String type = txt_select.getText().toString().trim();
            final String date = lblDate.getText().toString().trim();
            final String time = lblTime.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_DOATION_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SendDonationActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SendDonationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    double latitude = userLocation.getLastLocation().getLatitude();
                    double longitude = userLocation.getLastLocation().getLongitude();

                    params.put("latitude", String.valueOf(latitude));
                    params.put("longitude", String.valueOf(longitude));

                    String id = prefs2.getString(Config.EMAIL_SHARED_PREF, "");

                    params.put(Config.KEY_NATIONAL_ID, id);
                    params.put(KEY_TYPE, type);
                    params.put(KEY_DATE, date);
                    params.put(KEY_TIME, time);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        } else {
            Toast.makeText(getApplicationContext(), "Please choose from 8:00am to 1:00pm", Toast.LENGTH_SHORT).show();

        }


    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            //First RadioGroup
            case R.id.radio_Clothes:
                if (checked) {
                    txt_select.setText(radio_Clothes.getText());

                }
                break;
            case R.id.radio_Food:
                if (checked) {
                    txt_select.setText(radio_Food.getText());
                }

                break;

        }
    }

}

