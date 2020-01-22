package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.RecipeDetailsListAdapter;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;


public class DetailsListFragment extends Fragment {

    private final String BUNDLE_RECIPE_KEY = "DETAILS LIST RECIPE KEY";

    Recipe recipe;
    RecipeDetailsListAdapter mAdapter;
    OnDetailClickListener mCallback;


    public DetailsListFragment() {}

    public DetailsListFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details_list, container, false);

        if (savedInstanceState != null) {
            recipe = (Recipe)savedInstanceState.getSerializable(BUNDLE_RECIPE_KEY);
        }

        initRecyclerView(view);

        return view;

    }

    public interface OnDetailClickListener {
        void onDetailSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnDetailClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDetailClickListener!!!!!!!!!!!!!!!!!!!!!");
        }

    }



    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewRecipeDetails_id);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecipeDetailsListAdapter(getContext(), recipe, mCallback);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_RECIPE_KEY, recipe);
        super.onSaveInstanceState(outState);

    }

}
