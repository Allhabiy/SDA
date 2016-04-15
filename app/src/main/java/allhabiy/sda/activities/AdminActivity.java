package allhabiy.sda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import allhabiy.sda.R;
import allhabiy.sda.adapters.NeedyAdapter;
import allhabiy.sda.listeners.RecyclerItemClickListener;
import allhabiy.sda.models.User;
import allhabiy.sda.utils.Config;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<User> users;
    private Button button_allUser1Locations;
    private Button button_box;
    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        button_allUser1Locations = (Button) findViewById(R.id.button_allUser1Locations);
        button_allUser1Locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Config.GET_ALL_USER_LOCATIONS_URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<String> locations = new ArrayList<String>();
                                try {
                                    JSONArray locationsJSONArray = response.getJSONArray("location");
                                    for (int i = 0; i < locationsJSONArray.length(); i++) {
                                        locations.add(locationsJSONArray.get(i).toString());
                                    }
                                    Intent intent = new Intent(AdminActivity.this, MapActivity.class);
                                    intent.putExtra("activity", "Admin");
                                    intent.putExtra("locations", locations);
                                    AdminActivity.this.startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(AdminActivity.this);
                requestQueue.add(jsonObjectRequest);
            }
        });

        button_box = (Button) findViewById(R.id.button_box);
        button_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, BoxActivity.class));
            }
        });

        users = new ArrayList<User>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Config.GET_ALL_USER1_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");
                                int family_members = jsonObject.getInt("family_members");
                                int income = jsonObject.getInt("income");

                                User user = new User(name, phone, latitude, longitude, family_members, income);
                                users.add(user);
                            }

                            adapter = new NeedyAdapter(AdminActivity.this, users);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                    }
                });

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(AdminActivity.this);
        requestQueue.add(jsonArrayRequest);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        User user = users.get(position);
                        Intent intent = new Intent(AdminActivity.this, NeedyDetailsActivity.class);
                        intent.putExtra("User", user);
                        AdminActivity.this.startActivity(intent);
                    }
                })
        );
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