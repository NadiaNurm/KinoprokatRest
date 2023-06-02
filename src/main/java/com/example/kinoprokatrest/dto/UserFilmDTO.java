package com.example.kinoprokatrest.dto;

import com.example.kinoprokatrest.models.Genres;
import com.example.kinoprokatrest.models.User;

import java.util.Date;
import java.util.List;

public class UserFilmDTO {
    /*
            Модель фильмов для представления пользователю с учетом наличия этих фильмов в покупках.
            Если пользователь купил фильм, то isFavorite true и наоборот.
    */
    private Long id;
    private String name;
    private Date data;
    private List<Genres> genres;
    private Integer ageRestriction;
    private String director;
    private String length;
    private String about;
    private String filename;
    private int amountSubscriptions;
    private boolean isFavorite; // все купленные фильмы помечены, как выбранные;

    public UserFilmDTO(String name, Date data, List<Genres> genres, Integer ageRestriction, String director, String length, String about, String filename, int amountSubscriptions, boolean isFavorite) {
        this.name = name;
        this.data = data;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.length = length;
        this.about = about;
        this.filename = filename;
        this.amountSubscriptions = amountSubscriptions;
        this.isFavorite = isFavorite;
    }

    public UserFilmDTO() {
    }

    public UserFilmDTO(Long id, String name, Date data, List<Genres> genres, Integer ageRestriction, String director, String length, String about, String filename, int amountSubscriptions, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.length = length;
        this.about = about;
        this.filename = filename;
        this.amountSubscriptions = amountSubscriptions;
        this.isFavorite = isFavorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getAmountSubscriptions() {
        return amountSubscriptions;
    }

    public void setAmountSubscriptions(int amountSubscriptions) {
        this.amountSubscriptions = amountSubscriptions;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
