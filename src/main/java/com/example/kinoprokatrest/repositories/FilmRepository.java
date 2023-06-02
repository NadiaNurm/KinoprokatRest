package com.example.kinoprokatrest.repositories;

import com.example.kinoprokatrest.models.Film;
import com.example.kinoprokatrest.models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    public List<Film> findByGenresContaining(Genres genres);

    public Film findFilmByName(String name);

    public boolean existsById(Long id);

}
