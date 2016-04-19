package allhabiy.sda.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EmployeeActivity extends AppCompatActivity {

    private static final String REGISTER_URL = "http://m7sn.com/sda/app/sms/SMSAPI.php";

    public static final String KEY_PHONE = "targetphone";
    public static final String KEY_TEXT = "sendingsms";

    private EditText editTextPhone;
    private EditText editTextMSG;
    private Button btnSend;
    private Button btnCollect;
    private Button btnDistribute;


    private ProgressDialog pDialog;
    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        editTextPhone = (EditText) findViewById(R.id.editTextPhoneE);
        editTextMSG = (EditText) findViewById(R.id.editTextMSGE);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnCollect = (Button) findViewById(R.id.btnCollect);
        btnDistribute = (Button) findViewById(R.id.btnDistribute);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeActivity.this, CollectDonationsActivity.class));
            }
        });

        btnDistribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeActivity.this, DistributeDonationsActivity.class));
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                SendSMS();

            }
        });

    }


    private void SendSMS() {
        final String targetphone = editTextPhone.getText().toString().trim();
        final String sendingsms = editTextMSG.getText().toString().trim();

        pDialog.setMessage("Sending SMS ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EmployeeActivity.this, response, Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EmployeeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PHONE, targetphone);
                params.put(KEY_TEXT, sendingsms);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //clear the value
        editTextPhone.setText("");
        editTextMSG.setText("");

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
