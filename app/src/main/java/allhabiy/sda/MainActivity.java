package allhabiy.sda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton buttonGoLogin;
    private AppCompatButton buttonAboutSDA;
    private AppCompatButton buttonAboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGoLogin = (AppCompatButton) findViewById(R.id.buttonGoLogin);
        buttonAboutSDA = (AppCompatButton) findViewById(R.id.buttonAboutSDA);
        buttonAboutUs = (AppCompatButton) findViewById(R.id.buttonAboutUs);

        buttonGoLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent open = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(open);

            }
        });

        buttonAboutSDA.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent open = new Intent(MainActivity.this, AboutSDAActivity.class);
                startActivity(open);

            }
        });

        buttonAboutUs.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent open = new Intent(MainActivity.this, AboutDevelopersActivity.class);
                startActivity(open);

            }
        });

    }
}
