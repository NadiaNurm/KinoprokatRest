package com.example.kinoprokatrest.servises;

import com.example.kinoprokatrest.models.AgeRestriction;
import com.example.kinoprokatrest.models.Film;
import com.example.kinoprokatrest.models.Genres;
import com.example.kinoprokatrest.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmService {
    /*
    Сервис связывает сущность Film и репозиторий FilmRepository.
   */
    @Autowired
    FilmRepository filmRepository;

    public String checkAdd(Film film) {
        // Функция для добавления фильма и проверки полноты данных.
        List<Genres> genresList = new ArrayList<>();
        if (film.getName().equals("")) {
            return "Enter name";
        }
        if (film.getData() == null) {
            return "Enter data";
        }
        if (film.getGenres().size() == 0) {
            return "Enter genres";
        }
        if (film.getAgeRestriction() == null) {
            return "Enter ageRestriction";
        }
        if (film.getDirector().equals("")) {
            return "Enter director";
        }
        if (film.getLength().equals("")) {
            return "Enter length";
        }
        if (film.getAbout() == null) {
            return "Enter about";
        }
        for (String s : film.getGenres().toString().replace("[", "").replace("]", "").replace("-", "_").toLowerCase().split(",")) {
            try {
                genresList.add(Genres.valueOf(s.trim()));
            } catch (IllegalArgumentException e) {
                return "genre " + s + " does not exist.";
            }
        }
        return String.valueOf(filmRepository.save(film));
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmByID(String id) {
        return filmRepository.findById(Long.valueOf(id)).get();
    }

    public Film getFilmByID(Long id) {
        return filmRepository.findById(id).get();
    }

    public Long addFilm(Film film) {
        return filmRepository.save(film).getId();

    }

    public List<Film> filmByGenres(Genres genres) {
        return filmRepository.findByGenresContaining(genres);
    }

    public boolean existsById(String id) {
        return filmRepository.existsById(Long.valueOf(id));
    }

    public boolean existsById(Long id) {
        return filmRepository.existsById(id);
    }

    public ArrayList<Film> filmByManyGenres(List<Genres> genresList) {
        HashSet<Film> films = new HashSet<>();
        for (Genres g : genresList) {
            films.addAll(filmByGenres(g));
        }
        ArrayList<Film> filmArrayList = new ArrayList<>(films);
        filmArrayList.sort(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return o2.getUserList().size() - o1.getUserList().size();
            }
        });
        return filmArrayList;
    }

}
