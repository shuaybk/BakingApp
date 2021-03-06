package com.example.android.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingapp.Adapters.RecipeListAdapter;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.utilities.JsonUtils;
import com.example.android.bakingapp.utilities.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String BUNDLE_JSON_DATA_KEY = "JSON DATA KEY";

    CountingIdlingResource idlingResource = new CountingIdlingResource("DATA_LOADER");
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    ActivityMainBinding mBinding;
    ArrayList<Recipe> recipes;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String jsonResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        idlingResource.increment();

        Intent launcherIntent = getIntent();

        if ((launcherIntent.getAction()).equals(RecipeWidgetProvider.ACTION_OPEN_RECIPE)) {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.setAction("default action");
            intent.putExtra(Intent.EXTRA_COMPONENT_NAME, (Recipe)launcherIntent.getSerializableExtra(Intent.EXTRA_COMPONENT_NAME));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, launcherIntent.getIntExtra
                    (AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID));
            startActivity(intent);
        }

        Bundle extras = launcherIntent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (savedInstanceState != null) {
            if ((jsonResponse = savedInstanceState.getString(BUNDLE_JSON_DATA_KEY)) != null) {
                initAllData();
            } else {
                fetchJsonData();
            }
        } else {
            fetchJsonData();
        }
    }

    private void initRecyclerView() {
        //If it's a tablet, display gridview.  Otherwise display list
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            mLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
        }
        mBinding.recyclerViewRecipesId.setHasFixedSize(true);
        mAdapter = new RecipeListAdapter(this, recipes, appWidgetId);

        mBinding.recyclerViewRecipesId.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewRecipesId.setAdapter(mAdapter);
        idlingResource.decrement();
    }

    private void fetchJsonData() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, NetworkUtils.getDataUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonResponse = response;
                initAllData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!!");
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void setRecipes(String recipeJson) {
        recipes = JsonUtils.parseRecipeJson(recipeJson);
    }


    private void initAllData() {
        setRecipes(jsonResponse);
        initRecyclerView();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (jsonResponse != null) {
            outState.putString(BUNDLE_JSON_DATA_KEY, jsonResponse);
        }
        super.onSaveInstanceState(outState);
    }
}
