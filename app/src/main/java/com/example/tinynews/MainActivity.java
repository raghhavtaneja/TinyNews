package com.example.tinynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.onItemClickListener {
    private RecyclerView mRecyclerView;
    private ArrayList<News> mNewsArrayList;
    private NewsAdapter mNewsAdapter;
    private RequestQueue mRequestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewsArrayList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        fetchData();
//        adapter.setOnItemClickListener(new RecyclerViewAdapter.ListItemClickListener() {
//            @Override
//            public void onItemClick(int clickedItemposition) {
//                Toast.makeText(MainActivity.this,"item clicked at : " +clickedItemposition,Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void fetchData(){

            String url = "https://content.guardianapis.com/world/india?api-key=29fd90a7-94a6-46d5-a507-2d60c2ad1ad2&show-fields=thumbnail";

            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject responseObject = response.getJSONObject("response");
                        JSONArray newsJsonArray = responseObject.getJSONArray("results");
                        for(int i=0;i<newsJsonArray.length();i++){
                            JSONObject object = newsJsonArray.getJSONObject(i);
                            JSONObject imageObject = object.getJSONObject("fields");
                            String imageUrl = imageObject.getString("thumbnail");
                            String title = object.getString("webTitle");
                            String source = object.getString("sectionName");
                            String articleUrl = object.getString("webUrl");
                            News news = new News(title,source,imageUrl,articleUrl);
                            mNewsArrayList.add(news);
                        }
                        mNewsAdapter = new NewsAdapter(MainActivity.this,mNewsArrayList);
                        mRecyclerView.setAdapter(mNewsAdapter);
                        mNewsAdapter.setOnItemClickListener(MainActivity.this);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.v("json","error-> "+error);
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onItemClick(int position) {
        News currNews = mNewsArrayList.get(position);
//        Toast.makeText(this,"item selected : "+myurl.getUrl(),Toast.LENGTH_SHORT).show();

        String url = currNews.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }

    @Override
    public void onShareButtonClick(int position) {
        News currNews = mNewsArrayList.get(position);
        String url = currNews.getUrl();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Hey! Checkout this News");
        i.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(i, "Share URL"));
    }


}