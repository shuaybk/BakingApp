package com.example.android.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.utilities.JsonUtils;
import com.example.android.bakingapp.utilities.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    ArrayList<Recipe> recipes;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        fetchJsonData();

    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewId.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(this, recipes);

        mBinding.recyclerViewId.setLayoutManager(mLayoutManager);
        mBinding.recyclerViewId.setAdapter(mAdapter);
    }

    private void fetchJsonData() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, NetworkUtils.getDataUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setRecipes(response);
                initRecyclerView();
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
        for (Recipe r: recipes) {
            mBinding.testTextId.setText(r.toString());
        }
    }
}
