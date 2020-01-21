package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.POJOs.Recipe;

public class IngredientsFragment extends Fragment {
    private Recipe recipe;
    private OnBackButtonClickListener mCallback;


    public interface OnBackButtonClickListener {
        void onIngredientsBackButtonSelected();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnBackButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnBackButtonClickListener!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    public IngredientsFragment(Recipe recipe) {
        this.recipe = recipe;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ((TextView)view.findViewById(R.id.ingredients_list_id)).setText(recipe.ingredientsToString());

        ((Button)view.findViewById(R.id.back_button_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onIngredientsBackButtonSelected();
            }
        });

        return view;
    }
}
