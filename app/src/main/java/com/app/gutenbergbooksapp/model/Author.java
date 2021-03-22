package com.app.gutenbergbooksapp.model;

import com.google.gson.annotations.SerializedName;

public class Author {
    String name;
    @SerializedName("birth_year")
    Integer birthYear;
    @SerializedName("death_year")
    Integer deathYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
}
