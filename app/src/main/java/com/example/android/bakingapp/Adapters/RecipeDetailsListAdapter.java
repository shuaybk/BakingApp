package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.Fragments.DetailsListFragment;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.Step;
import com.example.android.bakingapp.R;
import com.squareup.picasso.Picasso;


public class RecipeDetailsListAdapter extends RecyclerView.Adapter<RecipeDetailsListAdapter.DetailsViewHolder> {

    private Context mContext;
    private Recipe recipe;
    private DetailsListFragment.OnDetailClickListener mCallback;

    public RecipeDetailsListAdapter(Context context, Recipe recipe, DetailsListFragment.OnDetailClickListener mCallback) {
        this.mContext = context;
        this.recipe = recipe;
        this.mCallback = mCallback;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item, parent, false);
        DetailsViewHolder viewHolder = new DetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, final int position) {
        //If it's the first item, we put ingredients, otherwise it's the steps
        if (position == 0) {
            int oneDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mContext.getResources().getDisplayMetrics());

            holder.ivThumbnail.setImageResource(R.drawable.ic_android);
            holder.tvHeading.setText("Ingredients List");
            holder.tvHeading.setPadding(0, 15*oneDip, 0, 0);
            holder.tvDescr.setVisibility(View.GONE);

            holder.clParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onDetailSelected(position);
                }
            });
        } else {
            Step step = recipe.getSteps().get(position-1);

            if (step.getThumbnailUrl().equals("")) {
                holder.ivThumbnail.setImageResource(R.drawable.ic_android);
            } else {
                Picasso.get().load(step.getThumbnailUrl()).into(holder.ivThumbnail);
            }

            holder.tvHeading.setText("Step " + position);
            holder.tvDescr.setText(step.getShortDescr());

            holder.clParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onDetailSelected(position);
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

        ConstraintLayout clParentLayout;
        ImageView ivThumbnail;
        TextView tvHeading;
        TextView tvDescr;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            clParentLayout = (ConstraintLayout) itemView.findViewById(R.id.detail_item_parentLayout_id);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.stepThumbnail_id);
            tvHeading = (TextView) itemView.findViewById(R.id.tv_detail_item_heading_id);
            tvDescr = (TextView) itemView.findViewById(R.id.tv_detail_item_descr_id);
        }
    }
}
