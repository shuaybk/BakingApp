package com.example.android.bakingapp.POJOs;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //For debugging
    public String toString() {
        return "{ id = " + id + ", name = " + name + ", ingredients = " + ingredientsToString() + ", steps = " +
                stepsToString() + ", servings = " + servings + ", image = " + image + " }";
    }

    public String ingredientsToString() {
        String result = "{ ";

        for (Ingredient i: ingredients) {
            result = result + "name = " + i.getName() + ", quantity = " + i.getQuantity() +
                    ", unit = " + i.getUnit() + "\n";
        }
        return result + " }";
    }

    public String stepsToString() {
        String result = "{ ";

        for (Step s: steps) {
            result = result + "id = " + s.getId() + ", shortDescr = " + s.getShortDescr() + ", fullDescr = " +
                    s.getFullDescr() + ", videoUrl = " + s.getVideoUrl() + ", thumbnailUrl = " + s.getThumbnailUrl() + "\n";
        }
        return result + " }";
    }

    public String getFormattedIngredientsText() {
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
