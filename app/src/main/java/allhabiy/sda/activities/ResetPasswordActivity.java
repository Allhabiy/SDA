package allhabiy.sda.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
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


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextSendNationalID;
    private EditText editTextSendPhone;

    private EditText editTextOldPassword;
    private EditText editTextNewPassword;

    private AppCompatButton btnSendPassword;
    private AppCompatButton btnChangePassword;
    private View formChangePassword;

    private ProgressDialog pDialog;


    private static final String ForgetPassword_URL = "http://m7sn.com/sda/app/sms/GetPassword2.php";

    public static final String KEY_ForgetPass = "forgetpassword";
    public static final String KEY_ForgetPhone = "forgetphone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        editTextSendNationalID = (EditText) findViewById(R.id.forgetNationalID);
        editTextSendPhone = (EditText) findViewById(R.id.forgetPhone);
        editTextOldPassword = (EditText) findViewById(R.id.editTextPassword_Old);
        editTextNewPassword = (EditText) findViewById(R.id.editTextPassword_New);

        btnSendPassword = (AppCompatButton) findViewById(R.id.btnSendSMS);
        btnChangePassword = (AppCompatButton) findViewById(R.id.btnResetPassword);
        formChangePassword = (View) findViewById(R.id.formChangePassword);
        formChangePassword.setVisibility(View.INVISIBLE);


        //Receive the old password
        btnSendPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //     formChangePassword.setVisibility(View.VISIBLE);
                if (!editTextSendNationalID.getText().toString().equals("") && !editTextSendPhone.getText().toString().equals("")) {
                    GetPassword();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the information!", Toast.LENGTH_SHORT)
                            .show();
                }

            }

        });
        //change the password
        btnChangePassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ChangePassword();
            }

        });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }

    private void GetPassword() {
        final String NationalID = editTextSendNationalID.getText().toString().trim();
        final String Phone = editTextSendPhone.getText().toString().trim();

        pDialog.setMessage("wait ...");
        showDialog();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ForgetPassword_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Toast.makeText(ResetPasswordActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();

                        Toast.makeText(ResetPasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ForgetPass, NationalID);
                params.put(KEY_ForgetPhone, Phone);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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

    private void ChangePassword() {
    }
}

