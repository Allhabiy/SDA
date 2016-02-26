package allhabiy.sda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
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


public class LoginActivity extends AppCompatActivity {

    //Defining views
    private EditText editTextNationalID;
    private EditText editTextPassword;
    private AppCompatButton buttonLogin;

    private ProgressDialog pDialog;


    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing views
        editTextNationalID = (EditText) findViewById(R.id.editTextNationalID);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword_login);

        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);

        //Adding click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // check if the user enter info. in the login page
                if (!editTextNationalID.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    // login user
                    login();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

                // login();
            }

        });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        //Getting values from edit texts
        final String nationlid = editTextNationalID.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        pDialog.setMessage("Logging in ...");
        showDialog();


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //If we are getting needy from server
                        //in the server there is echo "needy"; that mean the user in a needy
                        //execute this code otherwise go to else if to see if the user is admin
                        //else there is nothing in the database
                        if (response.equalsIgnoreCase(Config.LOGIN_NEEDY)) {

                            hideDialog();
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, nationlid);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity

                            Intent intent = new Intent(LoginActivity.this, NeedyLoginActivity.class);
                            startActivity(intent);

                        }
                        //If we are getting doner from server
                        //in the server there is echo "doner"; that mean the user in a doner
                        else if (response.equalsIgnoreCase(Config.LOGIN_DONER)) {

                            hideDialog();
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, nationlid);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity

                            Intent intent = new Intent(LoginActivity.this, DonatorLoginActivity.class);
                            startActivity(intent);

                        }
                        //else there is nothing in the database
                        else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Invalid National ID or password", Toast.LENGTH_LONG).show();
                            hideDialog();

                        }
                    }
                },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                    }
                }

        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_NATIONAL_ID, nationlid);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void NotRegister(View v) {
        Intent open = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(open);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
//            pDialog.show();
            pDialog.show();

    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
