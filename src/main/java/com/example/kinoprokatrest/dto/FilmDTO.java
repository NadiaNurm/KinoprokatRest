package com.example.kinoprokatrest.dto;

public class FilmDTO {
    /*
        Модель фильмов для представления пользователю без авторизации (то есть не учитывается, есть ли эти фильмы у пользователя).
    */
    private long id;
    private String name;
    private String about;
    //private int place;
    private String filename;

    public FilmDTO(long id, String name, String about, String filename) {
        this.id = id;
        this.name = name;
        if (about.length() <= 50) {
            this.about = about;
        } else {
            this.about = about.substring(0, 51);
        }
        this.filename = filename;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
