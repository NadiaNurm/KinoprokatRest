package com.example.kinoprokatrest.controllers;

import com.example.kinoprokatrest.dto.FilmDTO;
import com.example.kinoprokatrest.dto.UserFilmDTO;
import com.example.kinoprokatrest.models.*;
import com.example.kinoprokatrest.servises.FilmService;
import com.example.kinoprokatrest.servises.SubscriptionService;
import com.example.kinoprokatrest.servises.UserService;
import com.example.kinoprokatrest.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

/*
{
	"username": "admin",
    "password": "111"
}
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FilmService filmService;
    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody User user) {
        // Регистрирует пользователей с уникальным именем
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username \"" + user.getUsername() + "\" is taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Roles.user));
        user.setEnabled(true);
        Long id = userService.addUser(user);
        // возвращаем id созданного юзера
        return new ResponseEntity<>("User created, id: " + id, HttpStatus.OK);
    }


    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam(name = "password") String password, Authentication auth) {
        // меняет пароль пользователю
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        User user = userService.getUserByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userService.addUser(user);
        return new ResponseEntity<>("Password updated!", HttpStatus.OK);
    }


    @GetMapping("/mainPage")
    public ResponseEntity<?> printFilms() {
        // Главная страница, на которой отображается краткая информация обо всех фильмах
        List<Film> filmList = filmService.getAllFilms();
        ArrayList<FilmDTO> filmDTOList = Util.allFilmsDto(filmList);
        return new ResponseEntity<>(filmDTOList, HttpStatus.OK);
    }

    @GetMapping("/userFilms")
    public ResponseEntity<?> printUserFilms(Authentication auth) {
        // Для пользователя, получаем все купленные им фильмы
        List<Film> filmList = filmService.getAllFilms();
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        User user = userService.getUserByUsername(username);
        ArrayList<UserFilmDTO> UserFilmDTOList = Util.UserFilmDTO(user, filmList);
        ArrayList<UserFilmDTO> finalDTOList = new ArrayList<>();
        for (UserFilmDTO u : UserFilmDTOList) {
            if (u.isFavorite()) {
                finalDTOList.add(u);
            }
        }
        return new ResponseEntity<>(finalDTOList, HttpStatus.OK);
    }

    @GetMapping("/userAllFilms")
    public ResponseEntity<?> printAllUserFilms(Authentication auth) {
        // Для пользователя, получаем купленные им фильмы и все остальные в порядке популярности
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        User user = userService.getUserByUsername(username);
        ArrayList<UserFilmDTO> userFilmDTOList = Util.UserFilmDTO(user, filmService.getAllFilms());
        userFilmDTOList = Util.sortByPopularity(userFilmDTOList);
        return new ResponseEntity<>(userFilmDTOList, HttpStatus.OK);
    }

    @GetMapping("/about/film/{id}")
    public ResponseEntity<?> printFilm(@PathVariable(name = "id") Long id, Authentication auth) {
        // Получаем полную информацию о конкретном фильме
        if (!filmService.existsById(id)) {
            return new ResponseEntity<>("No film", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Film film = filmService.getFilmByID(id);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PutMapping("personal/buyFilm")
    public ResponseEntity<?> buyFilm(@RequestParam(name = "film_id") Long film_id, Authentication auth) {
        // Пользователь может купить фильм
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        User user = userService.getUserByUsername(username);
        Film film = filmService.getFilmByID(film_id);
        if (user.getFilmList().contains(film)) {
            return new ResponseEntity<>("Film already added", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.getFilmList().add(film);
        film.getUserList().add(user);
        userService.addUser(user);
        filmService.addFilm(film);
        return new ResponseEntity<>("Film bought!", HttpStatus.OK);

    }

    @PutMapping("personal/buySubscription")
    public ResponseEntity<?> buySubscription(@RequestBody Subscription subscription, Authentication auth) {
            /*
            {  "subTypes": ["year"]  }
             */
        // Пользователь может купить подписку
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        User user = userService.getUserByUsername(username);
        if (user.getSubscriptions() != null) {
            return new ResponseEntity<>("Yau already have subscription", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Long id_sub = subscriptionService.addSubscription(subscription);
        Subscription subscriptionNew = subscriptionService.findSubByID(id_sub);

        // получаем список подписок нужного юзера и добавляем подписку
        user.setSubscriptions(subscriptionNew);
        userService.addUser(user);
        return new ResponseEntity<>("Subscription purchased!", HttpStatus.OK);
    }

    @GetMapping("/findFilmByGenre")
    public ResponseEntity<?> buyFilmByGenre(@RequestParam(name = "genre") Genres genres) {
        // Получаем все фильмы с конкретным жанром
        List<Film> films = filmService.filmByGenres(genres);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @PutMapping("/findFilmByGenres")
    public ResponseEntity<?> buyFilmByGenres(@RequestParam(name = "genresList") List<Genres> genresList, Authentication auth) {
        // Получаем все фильмы с выбранными жанрами
        ArrayList<Film> filmArrayList = filmService.filmByManyGenres(genresList);
        return new ResponseEntity<>(filmArrayList, HttpStatus.OK);
    }


}
