package com.example.android.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.databinding.ActivityRecipeDetailBinding;

public class RecipeDetailActivity extends AppCompatActivity {

    ActivityRecipeDetailBinding mBinding;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Intent parentIntent = getIntent();
        recipe = (Recipe)parentIntent.getSerializableExtra(Intent.EXTRA_COMPONENT_NAME);

        Fragment fragment = new DetailsListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.frag_recipe_details, fragment)
                .commit();


    }
}
