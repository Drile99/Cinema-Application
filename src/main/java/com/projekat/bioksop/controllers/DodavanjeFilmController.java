package com.projekat.bioksop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.projekat.bioksop.models.Film;
import com.projekat.bioksop.models.Projekcija;
import com.projekat.bioksop.models.Rezervacija;
import com.projekat.bioksop.models.RezervisanaSedista;
import com.projekat.bioksop.repositories.FilmRepository;
import com.projekat.bioksop.repositories.ProjekcijaRepository;
import com.projekat.bioksop.repositories.RezervacijaRepository;
import com.projekat.bioksop.repositories.RezervisanaSedistaRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class DodavanjeFilmController
{
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    ProjekcijaRepository projekcijaRepository;
    @Autowired
    RezervacijaRepository rezervacijaRepository;
    @Autowired
    RezervisanaSedistaRepository rezervisanaSedistaRepository;

    @RequestMapping(value = "/dodavanjeFilmova", method = RequestMethod.GET)
    public ModelAndView noviFilmView()
    {
        ModelAndView modelAndView = new ModelAndView();
        Film film = new Film();
        modelAndView.addObject("film", film);
        modelAndView.setViewName("noviFilm");
        return modelAndView;
    }
    @RequestMapping(value = "/dodavanjeFilmova", method = RequestMethod.POST)
    public ModelAndView dodavanjeNovogFilma(Film film, BindingResult bindingResult, ModelMap modelMap)
    {
        Film f = filmRepository.nadjiPoNazivuFilma(film.getNazivFilma());
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors())
        {
            modelAndView.addObject("poruka", "Molimo Vas da ispravite greške");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if (f!=null)
        {
            modelAndView.addObject("poruka", "Film sa unetim naslovom već postoji!");
        }
        else
        {
            filmRepository.save(film);
            modelAndView.addObject("poruka", "Uspešno ste dodali novi film!");
            modelAndView.setViewName("noviFilm");
        }
        modelAndView.addObject("film", new Film());
        modelAndView.setViewName("noviFilm");
        return modelAndView;
    }
    @RequestMapping(value = "/izmenaFilmova/{id}", method = RequestMethod.GET)
    public String izmenaFilmovaView(Model model, @PathVariable("id") Long filmId)
    {
        Film film = filmRepository.getOne(filmId);
        model.addAttribute("film", film);
        return "izmenaFilmova";
    }
    @RequestMapping(value = "/izmenaFilmova/{id}", method = RequestMethod.POST)
    public String izmenaFilmova(Model model, @PathVariable("id") Long filmId, @Valid Film film)
    {
        film.setFilmId(filmId);
        Film f = filmRepository.nadjiPoNazivuFilma(film.getNazivFilma());
        Film f1 = filmRepository.nadjiPoNazivuFilmaId(film.getNazivFilma(), film.getFilmId());
        String msg;
        //proveravamo da li je uneti film prazan a kasnije da li takav film postoji u bazi
        if(f1!=null)
        {
            filmRepository.save(film);
            return "redirect:/pregledFilmovaAdmin";

        }
        else if(f!=null)
        {
            msg = "Film sa ovim nazivom već postoji";
            model.addAttribute("msg", msg);
            return "izmenaFilmova";
        }
        else
        {
            filmRepository.save(film);
            return "redirect:/pregledFilmovaAdmin";
        }
    }
    @GetMapping(value = "/brisanjeFilmova/{id}")
    public String brisanjeFilmova(@PathVariable("id") Long filmId)
    {
        Film f = filmRepository.getOne(filmId);
        Set<Projekcija> projekcije = projekcijaRepository.nadjiPoIdFilma(filmId);
        List<Rezervacija> rezervacije = rezervacijaRepository.findAll();
        List<RezervisanaSedista> rezervisanaSedistas = rezervisanaSedistaRepository.findAll();
        //prolazak kroz sve rezervacije i brisanje po id-u
        for(Rezervacija r: rezervacije)
        {
            for(RezervisanaSedista rs : rezervisanaSedistas)
            {
                if(rs.getRezervacija().getProjekcija().getFilm().getFilmId()==filmId)
                {
                    rezervisanaSedistaRepository.delete(rs);
                }
            }
            if(r.getProjekcija().getFilm().getFilmId()==filmId)
            {
                rezervacijaRepository.delete(r);
            }
        }
        for(Projekcija p: projekcije)
        {
            projekcijaRepository.delete(p);
        }
        filmRepository.delete(f);
        return "redirect:/pregledFilmovaAdmin";
    }
}
