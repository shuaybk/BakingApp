package com.example.android.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Fragments.DetailsListFragment;
import com.example.android.bakingapp.Fragments.IngredientsFragment;
import com.example.android.bakingapp.Fragments.StepDetailFragment;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.databinding.ActivityRecipeDetailBinding;

public class RecipeDetailActivity extends AppCompatActivity implements
        DetailsListFragment.OnDetailClickListener {

    private final String BUNDLE_CURRENT_STEP_KEY = "CURRENT STEP KEY";
    private final String BUNDLE_FRAGMENT_DISPLAYED_KEY = "CURRENT FRAGMENT DISPLAYED KEY";

    private int appWidgetId;
    ActivityRecipeDetailBinding mBinding;
    private Recipe recipe;
    private boolean isTwoPane, isLandscape;
    private int currentStep, currentFrag;  //This is to keep track of which step was last accessed (initially 0)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Intent parentIntent = getIntent();
        recipe = (Recipe)parentIntent.getSerializableExtra(Intent.EXTRA_COMPONENT_NAME);
        appWidgetId = parentIntent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        setWidgetText();

        if (findViewById(R.id.ll_parent_id) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }



        if (savedInstanceState != null) {
            //Stores which step was last displayed
            currentStep = savedInstanceState.getInt(BUNDLE_CURRENT_STEP_KEY);

            if (isTwoPane) {
                //Display both fragments with the last displayed step displayed again
                Fragment f1 = getSupportFragmentManager().getFragment(savedInstanceState, "fragment1");
                Fragment f2 = getSupportFragmentManager().getFragment(savedInstanceState, "fragment2");
                dualPaneSetFragments(currentStep, f1, f2);
            } else {
                Fragment f1 = getSupportFragmentManager().getFragment(savedInstanceState, "fragment1");
                //Determine which type of fragment was last displayed (detail list or steps?)
                //and display that one again
                currentFrag = savedInstanceState.getInt(BUNDLE_FRAGMENT_DISPLAYED_KEY);
                if (currentFrag == 0) {
                    singlePaneShowDetailsFragment(f1);
                } else {
                    singlePaneShowStepDetailFragment(currentStep, f1);
                }
            }
        } else {
            //Otherwise initialize from scratch
            if (isTwoPane) {
                dualPaneSetFragments(0, null, null);
            } else {
                currentFrag = 0;
                singlePaneShowDetailsFragment(null);
            }
        }

        //Determine if landscape and if so, make it full screen on the Step Detail fragment
        if (findViewById(R.id.landscape_layout_id) != null) {
            isLandscape = true;
            if (currentFrag == 1) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                getSupportActionBar().hide();
            }
        } else {
            isLandscape = false;
        }

        if (!isTwoPane) {
            mBinding.activityBackButtonId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singlePaneShowDetailsFragment(null);
                }
            });
        }
        if (isLandscape) {
            mBinding.activityBackButtonId.setVisibility(View.GONE);
        }

        getSupportActionBar().setTitle(recipe.getName());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onDetailSelected(int position) {
        currentStep = position;

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
            singlePaneShowStepDetailFragment(position, null);
        }
    }


    //Helper method to set the single pane display (non-tablet) to the details list fragment
    public void singlePaneShowDetailsFragment(Fragment fragment) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentFrag = 0;
        mBinding.activityBackButtonId.setVisibility(View.GONE);

        FragmentManager fm = getSupportFragmentManager();
        //Fragment fragment = new DetailsListFragment(recipe);
        if (fragment == null) {
            fragment = new DetailsListFragment(recipe);
        }

        if (fm.findFragmentById(R.id.frag_recipe_details) != null) {
            fm.beginTransaction()
                    .replace(R.id.frag_recipe_details, fragment)
                    .commit();
        } else {
            fm.beginTransaction()
                    .add(R.id.frag_recipe_details, fragment)
                    .commit();
        }
    }

    //Helper method to set the single pane display (non-tablet) to the steps detail fragment
    public void singlePaneShowStepDetailFragment(int position, Fragment fragment) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        currentFrag = 1;
        currentStep = position;

        mBinding.activityBackButtonId.setVisibility(View.VISIBLE);


        FragmentManager fm = getSupportFragmentManager();

        if (fragment == null) {
            if (position == 0) {
                fragment = new IngredientsFragment(recipe);
            } else {
                fragment = new StepDetailFragment(recipe.getSteps().get(position - 1));
            }
        }

        if (fm.findFragmentById(R.id.frag_recipe_details) != null) {
            fm.beginTransaction()
                    .replace(R.id.frag_recipe_details, fragment)
                    .commit();
        } else {
            fm.beginTransaction()
                    .add(R.id.frag_recipe_details, fragment)
                    .commit();
        }
    }

    //Helper method to set the dual pane display (tablet) to the specified step
    public void dualPaneSetFragments(int position, Fragment fragment1, Fragment fragment2) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();

        if (fragment1 == null) {
            fragment1 = new DetailsListFragment(recipe);
        }
        currentStep = position;

        if (fragment2 == null) {
            if (position == 0) {
                fragment2 = new IngredientsFragment(recipe);
            } else {
                fragment2 = new StepDetailFragment(recipe.getSteps().get(position - 1));
            }
        }


        if (fm.findFragmentById(R.id.frag_recipe_details_list) != null) {
            fm.beginTransaction()
                    .replace(R.id.frag_recipe_details_list, fragment1)
                    .commit();
        } else {
            fm.beginTransaction()
                    .add(R.id.frag_recipe_details_list, fragment1)
                    .commit();
        }

        if (fm.findFragmentById(R.id.frag_recipe_step) != null) {
            fm.beginTransaction()
                    .replace(R.id.frag_recipe_step, fragment2)
                    .commit();
        } else {
            fm.beginTransaction()
                    .add(R.id.frag_recipe_step, fragment2)
                    .commit();
        }
    }

    public void setWidgetText() {
        RecipeWidgetProvider.updateWidgetText(this, AppWidgetManager.getInstance(this), appWidgetId, recipe);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Save data about which fragment is displayed on single pane (details or steps?)
        FragmentManager fm = getSupportFragmentManager();

        if (!isTwoPane) {
            Fragment f = fm.findFragmentById(R.id.frag_recipe_details);

            fm.putFragment(outState, "fragment1", f);

            if (f instanceof IngredientsFragment || f instanceof StepDetailFragment) {
                outState.putInt(BUNDLE_FRAGMENT_DISPLAYED_KEY, 1);
            } else {
                outState.putInt(BUNDLE_FRAGMENT_DISPLAYED_KEY, 0);
            }
        } else {
            Fragment f1 = fm.findFragmentById(R.id.frag_recipe_details_list);
            Fragment f2 = fm.findFragmentById(R.id.frag_recipe_step);

            fm.putFragment(outState, "fragment1", f1);
            fm.putFragment(outState, "fragment2", f2);
        }


        outState.putInt(BUNDLE_CURRENT_STEP_KEY, currentStep);
        super.onSaveInstanceState(outState);

    }



}
