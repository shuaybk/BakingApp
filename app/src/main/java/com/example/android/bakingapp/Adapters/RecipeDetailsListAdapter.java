package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.Step;
import com.example.android.bakingapp.R;


public class RecipeDetailsListAdapter extends RecyclerView.Adapter<RecipeDetailsListAdapter.DetailsViewHolder> {

    private Context mContext;
    private Recipe recipe;

    public RecipeDetailsListAdapter(Context context, Recipe recipe) {
        this.mContext = context;
        this.recipe = recipe;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item, parent, false);
        DetailsViewHolder viewHolder = new DetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        //If it's the first item, we put ingredients.  Else it's the steps
        if (position == 0) {
            holder.tvHeading.setText("Ingredientsssssss");
            holder.tvDescr.setVisibility(View.GONE);

            holder.liParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Clicked on ingredients", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            System.out.println("THIS IS STEEEEEEEEEEEEEP" + position);
            Step step = recipe.getSteps().get(position-1);
            holder.tvHeading.setText("Step " + position);
            holder.tvDescr.setText(step.getShortDescr());
            System.out.println(holder.tvHeading.getText().toString());
            System.out.println(holder.tvDescr.getText().toString());

            holder.liParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Clicked on a step", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //List items for each step and 1 more for the ingredients
        return recipe.getSteps().size() + 1;
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout liParentLayout;
        TextView tvHeading;
        TextView tvDescr;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            liParentLayout = (LinearLayout) itemView.findViewById(R.id.detail_item_parentLayout_id);
            tvHeading = (TextView) itemView.findViewById(R.id.tv_detail_item_heading_id);
            tvDescr = (TextView) itemView.findViewById(R.id.tv_detail_item_descr_id);
        }
    }
}
