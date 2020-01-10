package com.example.android.bakingapp.utilities;

import com.example.android.bakingapp.POJOs.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    private static final String RECIPE_ID_KEY = "id";
    private static final String RECIPE_NAME_KEY = "name";
    private static final String RECIPE_INGREDIENTS_KEY = "ingredients";
    private static final String RECIPE_STEPS_KEY = "steps";
    private static final String RECIPE_SERVINGS_KEY = "servings";
    private static final String RECIPE_IMAGE_KEY = "image";


    public static ArrayList<Recipe> parseRecipeJson (String recipeJson) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        if (recipeJson != null) {
            try {
                JSONArray recipeListJson = new JSONArray(recipeJson);

                for (int i = 0; i < recipeListJson.length(); i++) {
                    JSONObject currRecipe = recipeListJson.getJSONObject(i);

                    int id = currRecipe.getInt(RECIPE_ID_KEY);
                    String name = currRecipe.getString(RECIPE_NAME_KEY);
                    String ingredients = currRecipe.getJSONArray(RECIPE_INGREDIENTS_KEY).toString();
                    String steps = currRecipe.getJSONArray(RECIPE_STEPS_KEY).toString();
                    int servings = currRecipe.getInt(RECIPE_SERVINGS_KEY);
                    String image = currRecipe.getString(RECIPE_IMAGE_KEY);

                    Recipe recipe = new Recipe(id, name, ingredients, steps, servings, image);
                    recipes.add(recipe);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return recipes;
    }
}
