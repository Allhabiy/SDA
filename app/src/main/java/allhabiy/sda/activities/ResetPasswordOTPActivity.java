package allhabiy.sda.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
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

import org.json.JSONException;

import allhabiy.sda.R;
import allhabiy.sda.utils.Config;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordOTPActivity extends AppCompatActivity {

    //Creating views
    private EditText editTextUsername;

    private EditText editTextConfirmOtp;
    private EditText editTextNewPassword;

    private AppCompatButton buttonRegister;
    private AppCompatButton buttonConfirm;

    //Volley RequestQueue
    private RequestQueue requestQueue;

    //String variables to hold username password and phone
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_otp);

        //Initializing Views
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        buttonRegister = (AppCompatButton) findViewById(R.id.buttonRegister);

        //Initializing the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        //Adding a listener to button
        buttonRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // check if the user enter info. in this page
                if (!editTextUsername.getText().toString().equals("")) {
                    // login user
                    register();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the National ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //This method would confirm the otp
    private void confirmOtp() throws JSONException {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        editTextNewPassword = (EditText) confirmDialog.findViewById(R.id.editTextNewPassword);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        //On the click of the confirm button from alert dialog
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if the user enter info. in this page
                if (!editTextConfirmOtp.getText().toString().equals("") && !editTextNewPassword.getText().toString().equals("")) {

                    //Hiding the alert dialog
                    alertDialog.dismiss();

                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(ResetPasswordOTPActivity.this, "Authenticating", "Please wait while we check the entered code", false, false);

                    //Getting the user entered otp from edittext
                    final String otp = editTextConfirmOtp.getText().toString().trim();
                    final String newP = editTextNewPassword.getText().toString().trim();

                    //Creating an string request
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CONFIRM_OTP_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response2) {
                                    //if the server response is success
                                    //     if (response2.equalsIgnoreCase(Config.LOGIN_NEEDY)) {
                                    //dismissing the progressbar
                                    loading.dismiss();

                                    //Starting a new activity
                                    finish();

                                    //   } else {    //  startActivity(new Intent(MainActivity.this, Success.class));

                                    //Displaying a toast if the otp entered is wrong
                                    Toast.makeText(ResetPasswordOTPActivity.this, response2.toString(), Toast.LENGTH_LONG).show();

                                    //Asking user to enter otp again


                                    // }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alertDialog.dismiss();
                                    Toast.makeText(ResetPasswordOTPActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username
                            params.put(Config.KEY_OTP, otp);
                            params.put(Config.KEY_NEW_PASSWORD, newP);
                            params.put(Config.KEY_USERNAME, username);
                            return params;
                        }
                    };

                    //Adding the request to the queue
                    requestQueue.add(stringRequest);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the required information!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //this method will register the user
    private void register() {

        //Displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);


        //Getting user data
        username = editTextUsername.getText().toString().trim();

        //Again creating the string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CREATE_OTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        try {
                            //Creating the json object from the response
                            Toast.makeText(ResetPasswordOTPActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                            confirmOtp();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Asking user to confirm otp


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(ResetPasswordOTPActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding the parameters to the request
                params.put(Config.KEY_USERNAME, username);

                return params;
            }
        };

        //Adding request the the queue
        requestQueue.add(stringRequest);
    }


}
