package com.example.android.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        mAdapter = new RecipeListAdapter(this, recipes);

        mBinding.recyclerViewRecipesId.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewRecipesId.setAdapter(mAdapter);
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
                System.out.println("ERRRRRORRRR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
