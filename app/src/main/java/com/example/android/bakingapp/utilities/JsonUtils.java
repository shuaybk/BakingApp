package com.example.android.bakingapp.utilities;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.Step;

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
    private static final String INGREDIENT_NAME_KEY = "ingredient";
    private static final String INGREDIENT_QUANTITY_KEY = "quantity";
    private static final String INGREDIENT_UNIT_KEY = "measure";
    private static final String STEP_ID_KEY = "id";
    private static final String STEP_SHORT_DESCRIPTION_KEY = "shortDescription";
    private static final String STEP_FULL_DESCRIPTION_KEY = "description";
    private static final String STEP_VIDEO_URL_KEY = "videoURL";
    private static final String STEP_THUMBNAIL_URL_KEY = "thumbnailURL";


    public static ArrayList<Recipe> parseRecipeJson (String recipeJson) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        if (recipeJson != null) {
            try {
                JSONArray recipeListJson = new JSONArray(recipeJson);

                for (int i = 0; i < recipeListJson.length(); i++) {
                    JSONObject currRecipe = recipeListJson.getJSONObject(i);

                    int id = currRecipe.getInt(RECIPE_ID_KEY);
                    String name = currRecipe.getString(RECIPE_NAME_KEY);
                    ArrayList<Ingredient> ingredients = parseIngredientJson(currRecipe.getJSONArray(RECIPE_INGREDIENTS_KEY));
                    ArrayList<Step> steps = parseStepJson(currRecipe.getJSONArray(RECIPE_STEPS_KEY));
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

    //Helper method for parsing recipes
    private static ArrayList<Ingredient> parseIngredientJson (JSONArray ingredientListJson) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            for (int i = 0; i < ingredientListJson.length(); i++) {
                JSONObject currIngredient = ingredientListJson.getJSONObject(i);

                String name = currIngredient.getString(INGREDIENT_NAME_KEY);
                double quantity = currIngredient.getDouble(INGREDIENT_QUANTITY_KEY);
                String unit = currIngredient.getString(INGREDIENT_UNIT_KEY);

                Ingredient ingredient = new Ingredient(name, quantity, unit);
                ingredients.add(ingredient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    //Helper method for parsing recipes
    private static ArrayList<Step> parseStepJson (JSONArray stepListJson) {
        ArrayList<Step> steps = new ArrayList<>();

        try {
            for (int i = 0; i < stepListJson.length(); i++) {
                JSONObject currStep = stepListJson.getJSONObject(i);

                int id = currStep.getInt(STEP_ID_KEY);
                String shortDescr = currStep.getString(STEP_SHORT_DESCRIPTION_KEY);
                String fullDescr = currStep.getString(STEP_FULL_DESCRIPTION_KEY);
                String videoUrl = currStep.getString(STEP_VIDEO_URL_KEY);
                String thumbnailUrl = currStep.getString(STEP_THUMBNAIL_URL_KEY);

                Step step = new Step(id, shortDescr, fullDescr, videoUrl, thumbnailUrl);
                steps.add(step);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return steps;
    }
}
