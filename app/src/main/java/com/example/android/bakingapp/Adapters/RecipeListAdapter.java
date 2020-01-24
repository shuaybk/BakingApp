package com.example.android.bakingapp.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {


    private int appWidgetId;
    private Context mContext;
    private ArrayList<Recipe> recipeList;

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipeList, int appWidgetId) {
        this.mContext = context;
        this.recipeList = recipeList;
        this.appWidgetId = appWidgetId;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);

        if (recipe.getImage().equals("")) {
            holder.ivRecipeImage.setImageResource(R.drawable.ic_android);
        } else {
            Picasso.get().load(recipe.getImage()).into(holder.ivRecipeImage);
        }
        holder.tvRecipeName.setText(recipe.getName());
        holder.tvRecipeServings.setText("Servings: "+recipe.getServings());


        holder.clItemParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra(Intent.EXTRA_COMPONENT_NAME, recipe);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        //TO DO: Change to use data binding************************************************************************************************************************

        ImageView ivRecipeImage;
        TextView tvRecipeName;
        TextView tvRecipeServings;
        ConstraintLayout clItemParentLayout;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.recipeImage_id);
            tvRecipeName = (TextView) itemView.findViewById(R.id.recipeName_id);
            tvRecipeServings = (TextView) itemView.findViewById(R.id.recipeServings_id);
            clItemParentLayout = (ConstraintLayout) itemView.findViewById(R.id.item_parentLayout_id);
        }
    }
}
