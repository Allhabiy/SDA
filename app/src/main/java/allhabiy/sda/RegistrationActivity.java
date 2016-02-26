package allhabiy.sda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class RegistrationActivity extends AppCompatActivity {

    private static final String REGISTER_URL = "http://m7sn.com/sda/app/DonatorRegister.php";
    private static final String REGISTER_URL2 = "http://m7sn.com/sda/app/NeedyRegister.php";


    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NATIONAL_ID = "nationlid";
    public static final String KEY_PHONE = "phone";

    public static final String KEY_FAMILY_MEMBER = "fmailymember";
    public static final String KEY_INCOME = "income";

    //for later use in Location
    public static final String KEY_LOCATIONX = "locationx";
    public static final String KEY_LOCATIONY = "locationy";


    private EditText editTextUsername;
    private EditText editTextNationalID;
    private EditText editTextPassword;
    private EditText editTextPhone;

    private EditText editTextFamilyMember;
    private EditText editTextIcome;

    private Button buttonRegister;
    private Button buttonLog;
    private View formNeedy;

    private CheckBox checkBox;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextNationalID = (EditText) findViewById(R.id.editTextNational_id);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextFamilyMember = (EditText) findViewById(R.id.editTextFamilyMember);
        editTextIcome = (EditText) findViewById(R.id.editTextIcome);

        //Manage the Needy people layout form
        formNeedy = (View) findViewById(R.id.formNeedy);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLog = (Button) findViewById(R.id.buttonLog);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        checkBox = (CheckBox) findViewById(R.id.checkbox_id);
        //To make the form for the needy people INVISIBLE in the Start
        formNeedy.setVisibility(View.INVISIBLE);

        //Mange the checkBox
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    formNeedy.setVisibility(View.VISIBLE);
                } else {
                    formNeedy.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if (!editTextUsername.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")
                        && !editTextNationalID.getText().toString().equals("") && !editTextPhone.getText().toString().equals("")) {
                    // register user
                    registerUser();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter All values!", Toast.LENGTH_LONG)
                            .show();
                }


            }
        });


        buttonLog.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent open = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(open);

            }
        });
    }

    private void registerUser() {


        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String nationlid = editTextNationalID.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();

        final String fmailymember = editTextFamilyMember.getText().toString().trim();
        final String income = editTextIcome.getText().toString().trim();
        // To Register Needy

        if (checkBox.isChecked()) {
            pDialog.setMessage("Registering ...");
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_NATIONAL_ID, nationlid);
                    params.put(KEY_PHONE, phone);
                    params.put(KEY_FAMILY_MEMBER, fmailymember);
                    params.put(KEY_INCOME, income);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            //clear the value
            editTextUsername.setText("");
            editTextPassword.setText("");
            editTextNationalID.setText("");
            editTextPhone.setText("");
            editTextFamilyMember.setText("");
            editTextIcome.setText("");
        }


        // To rigister Doner
        else {
            pDialog.setMessage("Registering ...");
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_NATIONAL_ID, nationlid);
                    params.put(KEY_PHONE, phone);

                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            //clear the value
            editTextUsername.setText("");
            editTextPassword.setText("");
            editTextNationalID.setText("");
            editTextPhone.setText("");

        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
