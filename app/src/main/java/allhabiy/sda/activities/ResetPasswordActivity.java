package allhabiy.sda.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import allhabiy.sda.R;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextSendNationalID;
    private EditText editTextOldPassword;
    private EditText editTextNewPassword;

    private AppCompatButton btnSendPassword;
    private AppCompatButton btnChangePassword;
    private View formChangePassword;


    private static final String ForgetPassword_URL = "http://m7sn.com/sda/app/sms/GetPassword.php";

    public static final String KEY_ForgetPass = "forgetpassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        editTextSendNationalID = (EditText) findViewById(R.id.forgetNationalID);
        editTextOldPassword = (EditText) findViewById(R.id.editTextPassword_Old);
        editTextNewPassword = (EditText) findViewById(R.id.editTextPassword_New);

        btnSendPassword = (AppCompatButton) findViewById(R.id.btnSendSMS);
        btnChangePassword = (AppCompatButton) findViewById(R.id.btnResetPassword);
        formChangePassword = (View) findViewById(R.id.formChangePassword);
        formChangePassword.setVisibility(View.INVISIBLE);


        //Receive the old password
        btnSendPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                formChangePassword.setVisibility(View.VISIBLE);
                GetPassword();

            }

        });
        //change the password
        btnChangePassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ChangePassword();
            }

        });

    }

    private void GetPassword() {
        final String NationalID = editTextSendNationalID.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ForgetPassword_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ResetPasswordActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResetPasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ForgetPass, NationalID);
                return params;
            }
        };


    }

    private void ChangePassword() {
    }
}

