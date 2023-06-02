package com.example.kinoprokatrest.util;

import com.example.kinoprokatrest.dto.FilmDTO;
import com.example.kinoprokatrest.dto.UserFilmDTO;
import com.example.kinoprokatrest.models.Film;
import com.example.kinoprokatrest.models.SubType;
import com.example.kinoprokatrest.models.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class Util {
    public static ArrayList<FilmDTO> allFilmsDto(List<Film> filmList) {
        /*
        Возвращает список всех фильмов для представления пользователю без авторизации (то есть не учитывается, есть ли эти фильмы у пользователя).
         */
        ArrayList<FilmDTO> filmDTOList = new ArrayList<>();
        for (Film f : filmList) {
            FilmDTO film = new FilmDTO(f.getId(), f.getName(), f.getAbout(), f.getFilename());
            filmDTOList.add(film);
        }
        return filmDTOList;
    }

    public static ArrayList<UserFilmDTO> UserFilmDTO(User user, List<Film> filmList) {
       /*
            Возвращает список всех фильмов для представления пользователю с учетом наличия этих фильмов в покупках.
            Если пользователь купил фильм, то ставим true и наоборот.
        */
        ArrayList<UserFilmDTO> filmDTOList = new ArrayList<>();
        for (Film f : filmList) {
            if (user.getFilmList().contains(f)) {
                UserFilmDTO film = new UserFilmDTO(f.getId(), f.getName(), f.getData(), f.getGenres(), f.getAgeRestriction(), f.getDirector(), f.getLength(), f.getAbout(), f.getFilename(), f.getUserList().size(), true);
                filmDTOList.add(film);
            } else {
                UserFilmDTO film = new UserFilmDTO(f.getId(), f.getName(), f.getData(), f.getGenres(), f.getAgeRestriction(), f.getDirector(), f.getLength(), f.getAbout(), f.getFilename(), f.getUserList().size(), false);
                filmDTOList.add(film);
            }
        }
        return filmDTOList;
    }

    public static LocalDate subscriptionEnd(SubType subType) {
       /*
        Вычисляет дату окончания подписки в зависимости от ее типа.
        */
        Calendar calendar = Calendar.getInstance();
        switch (subType) {
            case week:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case month:
                calendar.add(Calendar.MONTH, 1);
                break;
            case year:
                calendar.add(Calendar.YEAR, 1);
                break;
            default:
                System.out.println("Oooops, something wrong !");
        }
        return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    public static ArrayList<UserFilmDTO> sortByPopularity(ArrayList<UserFilmDTO> filmDTOList) {
        /*
        Сортируем все фильмы для пользователя. Сперва идут купленные им и убывают по популярности.
        */
        filmDTOList.sort(new Comparator<UserFilmDTO>() {
            @Override
            public int compare(UserFilmDTO o1, UserFilmDTO o2) {
                if (o1.isFavorite() & o2.isFavorite()) {
                    return o2.getAmountSubscriptions() - o1.getAmountSubscriptions();
                } else {
                    return o2.getAmountSubscriptions() - o1.getAmountSubscriptions();
                }
            }
        });
        return filmDTOList;
    }


}
