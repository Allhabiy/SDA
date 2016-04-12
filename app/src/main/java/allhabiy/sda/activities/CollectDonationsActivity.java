package allhabiy.sda.activities;

import android.content.Intent;
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
import allhabiy.sda.adapters.BoxAdapter;
import allhabiy.sda.adapters.DonationCollectionAdapter;
import allhabiy.sda.listeners.RecyclerItemClickListener;
import allhabiy.sda.models.Box;
import allhabiy.sda.models.DonationCollection;
import allhabiy.sda.utils.Config;


public class CollectDonationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<DonationCollection> collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        collections = new ArrayList<DonationCollection>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Config.GET_ALL_COLLECTION_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String date = jsonObject.getString("date");
                                String time = jsonObject.getString("time");
                                String type = jsonObject.getString("type");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                DonationCollection donationCollection = new DonationCollection(name, date, time, type, latitude, longitude);
                                collections.add(donationCollection);
                            }

                            adapter = new DonationCollectionAdapter(CollectDonationsActivity.this, collections);
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
        RequestQueue requestQueue = Volley.newRequestQueue(CollectDonationsActivity.this);
        requestQueue.add(jsonArrayRequest);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DonationCollection donationCollection = collections.get(position);
                        Intent intent = new Intent(CollectDonationsActivity.this, CollectDonationsDetailsActivity.class);
                        intent.putExtra("Donation", donationCollection);
                        CollectDonationsActivity.this.startActivity(intent);
                    }
                })
        );
    }
}