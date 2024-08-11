package com.example.foodplanner.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CountryResponse {

    @SerializedName("meals")
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public static class Country {
        @SerializedName("strCountry")
        private String name;

        @SerializedName("strCountryThumb")
        private String thumbnailUrl;

        @SerializedName("strCountryDescription")
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
