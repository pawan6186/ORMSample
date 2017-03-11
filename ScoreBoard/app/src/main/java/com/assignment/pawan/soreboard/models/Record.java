package com.assignment.pawan.soreboard.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by RIG on 08-01-2017.
 */
@DatabaseTable
public class Record extends BaseModel {
    @DatabaseField(id = true, unique = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String image;

    @DatabaseField
    @SerializedName("total_score")
    private int totalScore;

    @DatabaseField
    private String description;

    @DatabaseField
    @SerializedName("matches_played")
    private int matchesPlayed;

    @DatabaseField
    private String country;

    @DatabaseField
    private boolean isFavorite;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
