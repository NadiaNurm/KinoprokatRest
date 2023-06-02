package com.example.kinoprokatrest.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Film {
    /*
        Модель фильма. Хранит id, информацию о фильме, включая ограничение по возрасту и список жанров.
        В БД так же хранится путь до постера к фильму, сами картинки хранятся в пректе по адресу kinoprokatRest\src\main\resources\static\img
        У фильма есть список пользователей, которые его купили.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date data;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genres> genres;
    private Integer ageRestriction;
    private String director;
    private String length;
    @Column(length = 500)
    private String about;
    private String filename;
    @JsonIgnore
    @ManyToMany(mappedBy = "filmList")
    List<User> userList;

    public Film(String name, Date data, List<Genres> genres, Integer ageRestriction, String director, String length, String about, String filename) {
        this.name = name;
        this.data = data;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.length = length;
        this.about = about;
        this.filename = filename;
    }

    public Film() {
    }

    public Film(Long id, String name, Date data, List<Genres> genres, Integer ageRestriction, String director, String length, String about, String filename, List<User> userList) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.length = length;
        this.about = about;
        this.filename = filename;
        this.userList = userList;
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

    public String getStrGenres() {

        return genres.toString().replace("[", "").replace("]", "");
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id.equals(film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data=" + data +
                ", genres=" + genres +
                ", ageRestriction=" + ageRestriction +
                ", director='" + director + '\'' +
                ", length='" + length + '\'' +
                ", about='" + about + '\'' +
                ", filename='" + filename + '\'' +
                ", userList=" + userList +
                '}';
    }
}
