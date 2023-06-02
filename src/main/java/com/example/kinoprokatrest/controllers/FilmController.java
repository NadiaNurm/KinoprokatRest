package com.example.kinoprokatrest.controllers;

import com.example.kinoprokatrest.models.Film;
import com.example.kinoprokatrest.models.Subscription;
import com.example.kinoprokatrest.models.User;
import com.example.kinoprokatrest.servises.FilmService;
import com.example.kinoprokatrest.servises.InitService;
import com.example.kinoprokatrest.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*
{
        "name"      : "name2",
        "data"      : "2023-09-03",
        "genres"    : ["romcom","documentary","horror"],
        "ageRestriction" : 18,
        "director"   : "director",
        "length"     : "length",
        "about"      : "about"

}
 */
@RestController
public class FilmController {
    @Autowired
    UserService userService;

    @Autowired
    FilmService filmService;
    @Value("${upload.path}")
    private String path;

    @Autowired
    InitService initService;

    @PostMapping(value = "/addFilm", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(@RequestPart("film") Film film, @RequestPart("file") MultipartFile file, Authentication auth) throws IOException {
        // Добавление фильма
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        if (!userService.isAdmin(username)) {
            return new ResponseEntity<>("403", HttpStatus.FORBIDDEN);
        }

        String fullName = "";
        String fileName = UUID.randomUUID().toString().substring(0, 9) + file.getOriginalFilename();
        if (!file.getOriginalFilename().equals("")) {
            fullName = path + "/" + fileName;
        }
        file.transferTo(new File(fullName));
        film.setFilename(fileName);
        String result = filmService.checkAdd(film);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/updateFilm")
    public ResponseEntity<?> updateFilm(@RequestPart("film") Film newFilm, @RequestPart(value = "file", required = false) MultipartFile file, Authentication auth) throws IOException {
       /* {
        "id"        : 20,
        "name"      : "name2",
        "data"      : "2023-09-03",
        "genres"    : ["romcom","documentary","horror"],
        "ageRestriction" : 18,
        "director"   : "director",
        "length"     : "length",
        "about"      : "about"

}*/
        // Обновление фильма
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        if (!userService.isAdmin(username)) {
            return new ResponseEntity<>("403", HttpStatus.FORBIDDEN);
        }
        if (!filmService.existsById(newFilm.getId())) {
            return new ResponseEntity<>("No such film", HttpStatus.NOT_FOUND);
        }
        Film film = filmService.getFilmByID(newFilm.getId());
        film.setName(newFilm.getName());
        film.setLength(newFilm.getLength());
        film.setData(newFilm.getData());
        film.setDirector(newFilm.getDirector());
        film.setAgeRestriction(newFilm.getAgeRestriction());
        film.setGenres(newFilm.getGenres());
        film.setAbout(newFilm.getAbout());
        if (file == null) {
            film.setFilename(newFilm.getFilename());
        } else {
            String fullName = "";
            String fileName = UUID.randomUUID().toString().substring(0, 9) + file.getOriginalFilename();
            if (!file.getOriginalFilename().equals("")) {
                fullName = path + "/" + fileName;
            }
            file.transferTo(new File(fullName));
            film.setFilename(fileName);
        }

        film.setUserList(newFilm.getUserList());
        Long id = filmService.addFilm(film);
        return new ResponseEntity<>("Film " + id + " updated", HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(Authentication auth) {
        // Получаем список пользователей
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        if (!userService.isAdmin(username)) {
            return new ResponseEntity<>("403", HttpStatus.FORBIDDEN);
        }
        // возвращает всех юзеров из БД
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>("No users yet", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam(name = "user_id") Long user_id, Authentication auth) {
        // Получаем пользователя
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        if (!userService.isAdmin(username)) {
            return new ResponseEntity<>("403", HttpStatus.FORBIDDEN);
        }
        // возвращает всех юзеров из БД
        try {
            User user = userService.getUserByID(user_id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getStatistics")
    public ResponseEntity<?> getStatistics(Authentication auth) {
        // Получаем статистику. Сколько раз какой фильм купили
        String username = (String) ((Jwt) auth.getCredentials()).getClaims().get("sub");
        if (!userService.isAdmin(username)) {
            return new ResponseEntity<>("403", HttpStatus.FORBIDDEN);
        }
        HashMap<String, Integer> frequency_dict = new HashMap<>();
        List<Film> filmList = filmService.getAllFilms();
        for (Film f : filmList) {
            frequency_dict.put(f.getName(), f.getUserList().size());
        }
        return new ResponseEntity<>(frequency_dict, HttpStatus.OK);
    }

    @PostMapping("/init")
    public void initBase() throws ParseException, IOException {
        // Считывает данные из файла с фильмами и добавляет их в базу данных
        BufferedReader bufReader = new BufferedReader(new FileReader("films.txt"));
        String s = "";
        while ((s = bufReader.readLine()) != null) {
            initService.init(s);
        }
        initService.createAdmin();
    }


}
