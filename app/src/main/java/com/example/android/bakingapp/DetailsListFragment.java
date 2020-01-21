package com.example.android.bakingapp;

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


public class DetailsListFragment extends Fragment {

    Recipe recipe;
    RecipeDetailsListAdapter mAdapter;
    OnDetailClickListener mCallback;

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

    public DetailsListFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details_list, container, false);

        initRecyclerView(view);

        return view;

    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewRecipeDetails_id);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecipeDetailsListAdapter(getContext(), recipe, mCallback);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
