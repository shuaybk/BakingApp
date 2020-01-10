package com.example.android.bakingapp.POJOs;

public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, String ingredients, String steps, int servings, String image) {
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
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
        return "{" + "id = " + id + ", " + "name = " + name + ", " + "ingredients = " + ingredients + ", " +
                "steps = " + steps + ", " + "servings = " + servings + ", " + "image = " + image + " }";
    }
}
