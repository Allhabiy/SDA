package allhabiy.sda.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class RegistrationActivity extends AppCompatActivity {


    private static final String REGISTER_URL = "http://m7sn.com/sda/app/DonatorRegister.php";
    private static final String REGISTER_URL2 = "http://m7sn.com/sda/app/NeedyRegister.php";


    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NATIONAL_ID = "nationlid";
    public static final String KEY_PHONE = "phone";

    public static final String KEY_FAMILY_MEMBER = "fmailymember";
    public static final String KEY_INCOME = "income";
    public static final String KEY_NEIGHBORHOOD = "neighborhood";


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

    String[] list1_neighborhood = {"Seialehia", "Khalidiya", "Rashidiya", "Muhammadiyah", "Bandariah"};


    TextView txt_selected_neighborhood;
    Spinner mySpinner;

//    RadioGroup radioGroup1;
//    RadioButton radio1_1;
//    RadioButton radio1_2;
//    RadioButton radio1_3;
//    RadioGroup radioGroup2;
//    RadioButton radio2_1;
//    RadioButton radio2_2;
//    RadioButton radio2_3;
//    RadioGroup radioGroup3;
//    RadioButton radio3_1;
//    RadioButton radio3_2;
//    RadioButton radio3_3;
//    TextView txt1, txt2, txt3;


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
        txt_selected_neighborhood = (TextView) findViewById(R.id.txt1_neighborhood);

        //Manage the Needy people layout form
        formNeedy = (View) findViewById(R.id.formNeedy);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLog = (Button) findViewById(R.id.buttonLog);

        editTextNationalID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editTextNationalID.getText().length() != 10) {
                    editTextNationalID.setError("Please Enter correct National ID");
                }
            }
        });

        editTextPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!editTextPhone.getText().toString().startsWith("966") || editTextPhone.getText().length() != 12) {
                    editTextPhone.setError("Please start with 966");
                }
            }
        });


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


        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        /*

         */

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!editTextPhone.getText().toString().startsWith("966") || editTextPhone.getText().length() != 12) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter All values! in correct format", Toast.LENGTH_SHORT)
                            .show();
                    editTextPhone.setText("");
                }
                if (editTextNationalID.getText().length() != 10) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter All values! in correct format", Toast.LENGTH_SHORT)
                            .show();
                    editTextNationalID.setText("");
                }

                if (!editTextUsername.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")
                        && !editTextNationalID.getText().toString().equals("") && !editTextPhone.getText().toString().equals("")) {
                    // register user
                    registerUser();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter All values!", Toast.LENGTH_SHORT)
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

        txt_selected_neighborhood = (TextView) findViewById(R.id.txt1_neighborhood);


        //Spinner_neighborhood
        mySpinner = (Spinner) findViewById(R.id.spinner_neighborhood);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_neighborhood, R.id.txt_list1, list1_neighborhood);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // show the selected item text
                txt_selected_neighborhood.setText(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
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
        final String neighborhood = txt_selected_neighborhood.getText().toString().trim();

//        final String priority1 = txt1.getText().toString().trim();
//        final String priority2 = txt2.getText().toString().trim();
//        final String priority3 = txt3.getText().toString().trim();
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
                    params.put(KEY_NEIGHBORHOOD, neighborhood);


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
            txt_selected_neighborhood.setText("");
//            txt1.setText("");
//            txt2.setText("");
//            txt3.setText("");
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
                    params.put(KEY_NEIGHBORHOOD, neighborhood);

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
            txt_selected_neighborhood.setText("");

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
