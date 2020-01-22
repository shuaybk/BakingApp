package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

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

        ((TextView)view.findViewById(R.id.ingredients_list_id)).setText(getFormattedIngredientsText());

        ((Button)view.findViewById(R.id.back_button_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onIngredientsBackButtonSelected();
            }
        });

        return view;
    }

    private String getFormattedIngredientsText() {
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        String result = "";

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);

            //Get rid of trailing zeros on the quantity
            String quantity = Double.toString(ingredient.getQuantity());
            if ((ingredient.getQuantity() - ((int)ingredient.getQuantity())) == 0) {
                quantity = quantity.substring(0,quantity.length()-2);
            }
            result += quantity;

            String unit = getFormattedUnit(ingredient.getUnit());
            result += unit;

            result += " " + ingredient.getName();
            result += "\n\n";
        }

        return result;
    }

    private String getFormattedUnit(String unit) {
        String result = "";

        switch (unit) {
            case "UNIT":
                //Leave blank
                break;
            case "CUP":
                result = " cup";
                break;
            case "TBLSP":
                result = " tbsp";
                break;
            case "TSP":
                result = " tsp";
                break;
            case "K":
                result = "kg";
                break;
            case "G":
                result = "g";
                break;
            case "OZ":
                result = "oz";
                break;
        }

        return result;
    }
}
