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

    private String ingredientsToString() {
        String result = "{ ";

        for (Ingredient i: ingredients) {
            result = result + "name = " + i.getName() + ", quantity = " + i.getQuantity() +
                    ", unit = " + i.getUnit() + "\n";
        }
        return result + " }";
    }

    private String stepsToString() {
        String result = "{ ";

        for (Step s: steps) {
            result = result + "id = " + s.getId() + ", shortDescr = " + s.getShortDescr() + ", fullDescr = " +
                    s.getFullDescr() + ", videoUrl = " + s.getVideoUrl() + ", thumbnailUrl = " + s.getThumbnailUrl() + "\n";
        }
        return result + " }";
    }
}
