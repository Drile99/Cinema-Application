package com.projekat.bioksop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projekat.bioksop.models.Film;
import com.projekat.bioksop.repositories.FilmRepository;

import java.util.List;

@Service
public class FilmService
{
    @Autowired
    FilmRepository filmRepository;

    public List<Film> sviFilmovi()
    {
        return this.filmRepository.findAll();
    }
}
