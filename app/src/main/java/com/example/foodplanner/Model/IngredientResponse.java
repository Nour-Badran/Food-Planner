package com.example.foodplanner.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IngredientResponse {

    @SerializedName("meals")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public static class Ingredient {
        @SerializedName("idIngredient")
        private String id;

        @SerializedName("strIngredient")
        private String name;

        @SerializedName("strDescription")
        private String description;

        @SerializedName("strType")
        private String type;

        private String measure;

        // This is a new field for the thumbnail URL
        private String thumbnailUrl;

        // Getter and Setter methods
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        // Method to construct the URL for the thumbnail
        public String buildThumbnailUrl() {
            // Construct the URL using the ingredient name
            return "https://www.themealdb.com/images/ingredients/" + name.replace(" ", "%20") + ".png";
        }
    }
}
