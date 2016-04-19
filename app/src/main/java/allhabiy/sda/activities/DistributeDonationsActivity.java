package allhabiy.sda.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import allhabiy.sda.R;
import allhabiy.sda.adapters.DonationDistributeAdapter;
import allhabiy.sda.listeners.RecyclerItemClickListener;
import allhabiy.sda.models.DonationDistribute;
import allhabiy.sda.utils.Config;


public class DistributeDonationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<DonationDistribute> distributes;

    private SharedPreferences prefs3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_donations);

        prefs3 = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = prefs3.getString(Config.EMAIL_SHARED_PREF, "");

        distributes = new ArrayList<DonationDistribute>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view3);
        recyclerView.setHasFixedSize(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Config.GET_ALL_DISTRUBTE_URL + "?admin_id=" + id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String priority1 = jsonObject.getString("priority1");
                                String priority2 = jsonObject.getString("priority2");
                                String priority3 = jsonObject.getString("priority3");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                DonationDistribute donationDistribute = new DonationDistribute(name, priority1, priority2, priority3, latitude, longitude);
                                distributes.add(donationDistribute);
                            }

                            adapter = new DonationDistributeAdapter(DistributeDonationsActivity.this, distributes);
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
                }


        ) ;

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(DistributeDonationsActivity.this);
        requestQueue.add(jsonArrayRequest);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DonationDistribute donationDistribute = distributes.get(position);
                        Intent intent = new Intent(DistributeDonationsActivity.this, DistributeDonationsDetailsActivity.class);
                        intent.putExtra("Distribute", donationDistribute);
                        DistributeDonationsActivity.this.startActivity(intent);
                    }
                })
        );
    }
}
