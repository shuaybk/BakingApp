package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;


public class RecipeDetailsListAdapter extends RecyclerView.Adapter<RecipeDetailsListAdapter.DetailsViewHolder> {

    private Context mContext;
    private Recipe recipe;

    public RecipeDetailsListAdapter(Context context, Recipe recipe) {
        this.mContext = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item, parent, false);
        DetailsViewHolder viewHolder = new DetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //List items for each step and 1 more for the ingredients
        return recipe.getSteps().size() + 1;
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {

        //Change to use data binding************************************************************************************************************************

        public DetailsViewHolder(View itemView) {
            super(itemView);

        }
    }
}
