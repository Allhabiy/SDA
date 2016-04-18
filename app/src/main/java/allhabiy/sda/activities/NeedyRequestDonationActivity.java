package allhabiy.sda.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class NeedyRequestDonationActivity extends AppCompatActivity {


    private static final String REQUEST_DONATION_URL = "http://m7sn.com/sda/app/RequestDonation.php";

    private Button BtnSendDonationRequest;

    private SharedPreferences prefs2;
    private UserLocation userLocation;
    public static final String KEY_PRIORITY1 = "priority1";
    public static final String KEY_PRIORITY2 = "priority2";
    public static final String KEY_PRIORITY3 = "priority3";

    //for later use in Location


    private ProgressDialog pDialog;

    RadioGroup radioGroup1;
    RadioButton radio1_1;
    RadioButton radio1_2;
    RadioButton radio1_3;
    RadioGroup radioGroup2;
    RadioButton radio2_1;
    RadioButton radio2_2;
    RadioButton radio2_3;
    RadioGroup radioGroup3;
    RadioButton radio3_1;
    RadioButton radio3_2;
    RadioButton radio3_3;
    TextView txt1, txt2, txt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needy_request);

        prefs2 = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userLocation = new UserLocation(this);

        BtnSendDonationRequest = (Button) findViewById(R.id.BtnSendDonationRequest);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);




        /*
           this part of the code to manage the priority
         */
        txt1 = (TextView) findViewById(R.id.txt1);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioG1);
        radio1_1 = (RadioButton) findViewById(R.id.radio1_1);
        radio1_2 = (RadioButton) findViewById(R.id.radio1_2);
        radio1_3 = (RadioButton) findViewById(R.id.radio1_3);

        radioGroup2 = (RadioGroup) findViewById(R.id.radioG2);
        radio2_1 = (RadioButton) findViewById(R.id.radio2_1);
        radio2_2 = (RadioButton) findViewById(R.id.radio2_2);
        radio2_3 = (RadioButton) findViewById(R.id.radio2_3);
        txt2 = (TextView) findViewById(R.id.txt2);

        radioGroup3 = (RadioGroup) findViewById(R.id.radioG3);
        radio3_1 = (RadioButton) findViewById(R.id.radio3_1);
        radio3_2 = (RadioButton) findViewById(R.id.radio3_2);
        radio3_3 = (RadioButton) findViewById(R.id.radio3_3);
        txt3 = (TextView) findViewById(R.id.txt3);


        /*

         */

        BtnSendDonationRequest.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (txt1.getText().toString().equals("") && txt2.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please choose the priority", Toast.LENGTH_SHORT)
                            .show();
                }

                if (!txt1.getText().toString().equals("") && !txt2.getText().toString().equals("")) {
                    // SEND donation request
                    SendDonationRequest();
                }


            }
        });


    }

    private void SendDonationRequest() {


        final String priority1 = txt1.getText().toString().trim();
        final String priority2 = txt2.getText().toString().trim();
        final String priority3 = txt3.getText().toString().trim();
        // To Register Needy


        pDialog.setMessage("Send ... wait");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_DONATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NeedyRequestDonationActivity.this, response, Toast.LENGTH_LONG).show();
                        hideDialog();
                        // finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NeedyRequestDonationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String id = prefs2.getString(Config.EMAIL_SHARED_PREF, "");
                double latitude = userLocation.getLastLocation().getLatitude();
                double longitude = userLocation.getLastLocation().getLongitude();

                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                params.put(Config.KEY_NATIONAL_ID, id);
                params.put(KEY_PRIORITY1, priority1);
                params.put(KEY_PRIORITY2, priority2);
                params.put(KEY_PRIORITY3, priority3);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //clear the value

        txt1.setText("");
        txt2.setText("");
        txt3.setText("");
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();


    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /*
   This part to mangage the priority radio button
    */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            //First RadioGroup
            case R.id.radio1_1:
                if (checked) {
                    txt1.setText(radio1_1.getText());
                    radio2_1.setVisibility(View.INVISIBLE);
                    radio2_2.setVisibility(View.VISIBLE);
                    radio2_3.setVisibility(View.VISIBLE);

                    txt2.setText("");

                }
                break;
            case R.id.radio1_2:
                if (checked) {
                    txt1.setText(radio1_2.getText());
                    radio2_1.setVisibility(View.VISIBLE);
                    radio2_2.setVisibility(View.INVISIBLE);
                    radio2_3.setVisibility(View.VISIBLE);

                    txt2.setText("");

                }

                break;
            case R.id.radio1_3:
                if (checked) {
                    txt1.setText(radio1_3.getText());
                    radio2_1.setVisibility(View.VISIBLE);
                    radio2_2.setVisibility(View.VISIBLE);
                    radio2_3.setVisibility(View.INVISIBLE);

                    txt2.setText("");
                }
                break;

            //Second RadioGroup
            case R.id.radio2_1:
                if (checked) {
                    if (radio1_1.isChecked() || radio1_2.isChecked() || radio1_3.isChecked()) {
                        txt2.setText(radio2_1.getText());
                        radio2_1.setChecked(true);

                    } else {
                        Toast.makeText(NeedyRequestDonationActivity.this, "check first choose before ", Toast.LENGTH_SHORT).show();
                        radio2_1.setChecked(false);
                        txt2.setText("");

                    }

                }
                break;
            case R.id.radio2_2:
                if (checked)
                    if (radio1_1.isChecked() || radio1_2.isChecked() || radio1_3.isChecked()) {
                        txt2.setText(radio2_2.getText());
                        radio2_2.setChecked(true);

                    } else {
                        Toast.makeText(NeedyRequestDonationActivity.this, "check first choose before ", Toast.LENGTH_SHORT).show();
                        radio2_2.setChecked(false);
                        txt2.setText("");

                    }
                break;
            case R.id.radio2_3:
                if (checked)
                    if (radio1_1.isChecked() || radio1_2.isChecked() || radio1_3.isChecked()) {
                        txt2.setText(radio2_3.getText());
                        radio2_3.setChecked(true);

                    } else {
                        Toast.makeText(NeedyRequestDonationActivity.this, "check first choose before ", Toast.LENGTH_SHORT).show();
                        radio2_3.setChecked(false);
                        txt2.setText("");

                    }
                break;

            //Third RadioGroup
            case R.id.radio3_1:
                if (checked)
                    txt3.setText(radio3_1.getText());

                break;
            case R.id.radio3_2:
                if (checked)
                    txt3.setText(radio3_2.getText());

                break;
            case R.id.radio3_3:
                if (checked)
                    txt3.setText(radio3_3.getText());

                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     */
}
