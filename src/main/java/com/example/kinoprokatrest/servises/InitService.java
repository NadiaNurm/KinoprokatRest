package com.example.kinoprokatrest.servises;

import com.example.kinoprokatrest.models.Film;
import com.example.kinoprokatrest.models.Genres;
import com.example.kinoprokatrest.models.Roles;
import com.example.kinoprokatrest.models.User;
import com.example.kinoprokatrest.repositories.FilmRepository;
import com.example.kinoprokatrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InitService {
    /*
    Сервис для заполнения базы данных. Добавляет фильмы и создает пользователя админа.
    */
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void init(String filmString) throws ParseException {
        Film film = new Film();
        String[] f = filmString.split(";");
        film.setName(f[0]);
        film.setDirector(f[1]);
        film.setLength(f[2]);
        film.setAbout(f[3]);
        if (f[4].contains("+")) {
            film.setAgeRestriction(Integer.valueOf(f[4].replace("+", "")));
        } else {
            film.setAgeRestriction(0);
        }
        String dateStr = f[5];
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date date = (Date) formatter.parse(dateStr);
        film.setData(date);
        //[Biography, Drama, History]
        String genres = f[6].replace("[", "").replace("]", "").replace("-", "_").toLowerCase();
        List<Genres> genresList = new ArrayList<>();
        for (String s : genres.split(",")) {
            genresList.add(Genres.valueOf(s.trim()));
        }
        film.setGenres(genresList);

        film.setFilename(f[7]);
        //System.out.println(film);
        filmRepository.save(film);
    }

    public void createAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("111"));
        admin.setRoles(List.of(Roles.admin));
        admin.setEnabled(true);
        userRepository.save(admin);
    }
}
