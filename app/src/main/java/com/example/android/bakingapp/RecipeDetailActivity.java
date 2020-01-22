package com.example.android.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.bakingapp.Fragments.DetailsListFragment;
import com.example.android.bakingapp.Fragments.IngredientsFragment;
import com.example.android.bakingapp.Fragments.StepDetailFragment;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.databinding.ActivityRecipeDetailBinding;

public class RecipeDetailActivity extends AppCompatActivity implements
        DetailsListFragment.OnDetailClickListener, StepDetailFragment.OnBackButtonClickListener,
        IngredientsFragment.OnBackButtonClickListener {

    ActivityRecipeDetailBinding mBinding;
    private Recipe recipe;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Intent parentIntent = getIntent();
        recipe = (Recipe)parentIntent.getSerializableExtra(Intent.EXTRA_COMPONENT_NAME);

        if (findViewById(R.id.ll_parent_id) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

        FragmentManager fm = getSupportFragmentManager();

        if (isTwoPane) {
            Fragment fragment1 = new DetailsListFragment(recipe);
            Fragment fragment2 = new StepDetailFragment(recipe.getSteps().get(0));

            fm.beginTransaction()
                    .add(R.id.frag_recipe_details_list, fragment1)
                    .add(R.id.frag_recipe_step, fragment2)
                    .commit();
        } else {
            Fragment fragment = new DetailsListFragment(recipe);

            fm.beginTransaction()
                    .add(R.id.frag_recipe_details, fragment)
                    .commit();
        }

    }

    public void onDetailSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;

        if (isTwoPane) {
            if (position == 0) {
                fragment = new IngredientsFragment(recipe);
            } else {
                fragment = new StepDetailFragment(recipe.getSteps().get(position - 1));
            }

            fm.beginTransaction()
                    .replace(R.id.frag_recipe_step, fragment)
                    .commit();
        } else {
            if (position == 0) {
                fragment = new IngredientsFragment(recipe);
            } else {
                fragment = new StepDetailFragment(recipe.getSteps().get(position - 1));
            }

            fm.beginTransaction()
                    .replace(R.id.frag_recipe_details, fragment)
                    .commit();
        }
    }

    public void onStepBackButtonSelected() {
        FragmentManager fm = getSupportFragmentManager();

        if (isTwoPane) {
            //There is no back button to press
        } else {
            Fragment fragment = new DetailsListFragment(recipe);
            fm.beginTransaction()
                    .replace(R.id.frag_recipe_details, fragment)
                    .commit();
        }
    }

    public void onIngredientsBackButtonSelected() {
        FragmentManager fm = getSupportFragmentManager();

        if (isTwoPane) {
            //There is no back button to press
        } else {
            Fragment fragment = new DetailsListFragment(recipe);
            fm.beginTransaction()
                    .replace(R.id.frag_recipe_details, fragment)
                    .commit();
        }
    }


}
