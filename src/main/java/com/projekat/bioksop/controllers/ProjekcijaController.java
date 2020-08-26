package com.projekat.bioksop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.projekat.bioksop.models.*;
import com.projekat.bioksop.repositories.*;
import com.projekat.bioksop.controllers.FilmService;
import com.projekat.bioksop.services.SalaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class ProjekcijaController
{
    @Autowired
    FilmService filmService;
    @Autowired
    SalaService salaService;
    @Autowired
    ProjekcijaRepository projekcijaRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    SalaRepository salaRepository;
    @Autowired
    RezervacijaRepository rezervacijaRepository;
    @Autowired
    RezervisanaSedistaRepository rezervisanaSedistaRepository;

    @RequestMapping(value = "/novaProjekcija", method = RequestMethod.GET)
    public String novaProjekcija(Model model)
    {
        List<Film> filmovi = this.filmService.sviFilmovi();
        model.addAttribute("film", filmovi);
        List<Sala> sale = this.salaService.sveSale();
        model.addAttribute("sala", sale);
        return "novaProjekcija";
    }
    @RequestMapping(value = "/novaProjekcija", method = RequestMethod.POST)
    public String unosNoveProjekcije(Model model, @RequestParam("film") Long filmId, @RequestParam("sala") Long salaId, @RequestParam("pocetakProjekcije")String pocetakProjekcije)
    {
        Film film = filmRepository.getOne(filmId);
        Projekcija projekcija = new Projekcija();
        projekcija.setFilm(film);
        Sala sala = salaRepository.getOne(salaId);
        Set<Projekcija> setProjekcija = sala.getProjekcije();
        setProjekcija.remove(projekcija);
        String msg;
        LocalDateTime pocetakProjekcija = LocalDateTime.parse(pocetakProjekcije);
        LocalDateTime krajProjekcije = pocetakProjekcija.plusMinutes(film.getTrajanje());
        for(Projekcija pr: setProjekcija)
        {
            if(pocetakProjekcija.isAfter(pr.getPocetakProjekcije()) && pocetakProjekcija.isBefore(pr.getKrajProjekcije()) || krajProjekcije.isAfter(pr.getPocetakProjekcije()) && krajProjekcije.isBefore(pr.getKrajProjekcije()))
            {
                msg = "Već postoji projekcija u ovo vreme u izabranoj sali";
                model.addAttribute("msg", msg);
                List<Film> filmovi = this.filmService.sviFilmovi();
                model.addAttribute("film", filmovi);
                List<Sala> sale = this.salaService.sveSale();
                model.addAttribute("sala", sale);
                return "novaProjekcija";
            }
        }
        if(pocetakProjekcija.isBefore(LocalDateTime.now()) || pocetakProjekcija.equals(LocalDateTime.now()))
        {
            msg = "Nije moguće dodati projekciju!" + "\n" + "Prva projekcija može biti: " + LocalDateTime.from(LocalDateTime.now()).plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        }
        else
        {
            projekcija.setSala(sala);
            projekcija.setPocetakProjekcije(pocetakProjekcija);
            projekcija.setKrajProjekcije(krajProjekcije);
            projekcijaRepository.save(projekcija);
            msg = "Uspešno ste dodali novu projekciju";
        }
        model.addAttribute("msg", msg);
        List<Film> filmovi = this.filmService.sviFilmovi();
        model.addAttribute("film", filmovi);
        List<Sala> sale = this.salaService.sveSale();
        model.addAttribute("sala", sale);
        return "novaProjekcija";
    }
    @RequestMapping(value = "/pregledProjekcijaAdmin/{film}", method = RequestMethod.GET)
    public String pregledProjekcijaAdmin(Model model, @PathVariable("film") String izabranFilm)
    {
        Set<Projekcija> projekcija = projekcijaRepository.projekcijaPoFilmu(izabranFilm);
        model.addAttribute("projekcija", projekcija);
        return "pregledProjekcijaAdmin";
    }
    @RequestMapping(value = "/pregledFilmovaAdmin", method = RequestMethod.GET)
    public String pregledFilmovaAdmin(Model model)
    {
        List<Film>filmovi = this.filmService.sviFilmovi();
        model.addAttribute("film", filmovi);
        return "pregledFilmovaAdmin";
    }
    @RequestMapping(value = "/izmenaProjekcija/{projekcijaId}", method = RequestMethod.GET)
    public String izmenaProjekcijaView(Model model, @PathVariable("projekcijaId") Long projekcijaId)
    {
        Projekcija projekcija = projekcijaRepository.getOne(projekcijaId);
        model.addAttribute("projekcija", projekcija);
        List<Sala> sale = this.salaService.sveSale();
        model.addAttribute("sala", sale);
        return "izmenaProjekcija";
    }
    @RequestMapping(value = "/izmenaProjekcija/{projekcijaId}", method = RequestMethod.POST)
    public String izmenaProjekcija(Model model, @PathVariable("projekcijaId") Long projekcijaId, @RequestParam("filmId") Long filmId, @RequestParam("pocetakProjekcije") String pocetakProjekcije, @RequestParam("sala") Long salaId)
    {
        Film film = filmRepository.getOne(filmId);
        Projekcija projekcija = projekcijaRepository.getOne(projekcijaId);
        model.addAttribute("projekcija", projekcija);
        projekcija.setFilm(film);
        projekcija.setProjekcijaId(projekcijaId);
        Sala sala = salaRepository.getOne(salaId);
        Set<Projekcija> setProjekcija = sala.getProjekcije();
        setProjekcija.remove(projekcija);
        String msg;
        LocalDateTime pocetakProjekcija = LocalDateTime.parse(pocetakProjekcije);
        LocalDateTime krajProjekcije = pocetakProjekcija.plusMinutes(film.getTrajanje());
        for(Projekcija pr: setProjekcija)
        {
            if(pocetakProjekcija.isAfter(pr.getPocetakProjekcije()) && pocetakProjekcija.isBefore(pr.getKrajProjekcije()) || krajProjekcije.isAfter(pr.getPocetakProjekcije()) && krajProjekcije.isBefore(pr.getKrajProjekcije()))
            {
                msg = "Već postoji projekcija u ovo vreme u izabranoj sali";
                model.addAttribute("msg", msg);
                model.addAttribute("film", film);
                List<Sala> sale = this.salaService.sveSale();
                model.addAttribute("sala", sale);
                return "izmenaProjekcija";
            }
        }
        if(pocetakProjekcija.isBefore(LocalDateTime.now()) || pocetakProjekcija.equals(LocalDateTime.now()))
        {
            msg = "Nije moguće izmeniti projekciju!" + "\n" + "Prva projekcija može biti: " + LocalDateTime.from(LocalDateTime.now()).plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            List<Sala> sale = this.salaService.sveSale();
            model.addAttribute("sala", sale);
        }
        else
        {
            projekcija.setSala(sala);
            projekcija.setPocetakProjekcije(pocetakProjekcija);
            projekcija.setKrajProjekcije(krajProjekcije);
            projekcijaRepository.save(projekcija);
            msg = "Projekcija je uspešno izmenjena";
            model.addAttribute("sala", sala);
            return "izmenaProjekcija";
        }
        model.addAttribute("msg", msg);
        return "izmenaProjekcija";
    }
    @GetMapping("/otkazivanjeProjekcija/{id}")
    public String otkazivanjeProjekcija(@PathVariable("id") Long projekcijaId)
    {
        Projekcija p = projekcijaRepository.getOne(projekcijaId);
        List<Rezervacija> rezervacije = rezervacijaRepository.findAll();
        List<RezervisanaSedista> rezervisanaSedistas = rezervisanaSedistaRepository.findAll();
        for(Rezervacija r: rezervacije)
        {
            for(RezervisanaSedista rs : rezervisanaSedistas)
            {
                if(rs.getRezervacija().getProjekcija().getProjekcijaId()==projekcijaId)
                {
                    rezervisanaSedistaRepository.delete(rs);
                }
            }
            if(r.getProjekcija().getProjekcijaId()==projekcijaId)
            {
                rezervacijaRepository.delete(r);
            }
        }
        projekcijaRepository.delete(p);
        return "redirect:/pregledFilmovaAdmin";
    }
}
