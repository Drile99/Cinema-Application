package com.projekat.bioksop.controllers;

import com.projekat.bioksop.models.RezervisanaSedista;
import com.projekat.bioksop.repositories.ProjekcijaRepository;
import com.projekat.bioksop.repositories.RezervacijaRepository;
import com.projekat.bioksop.repositories.SedisteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.projekat.bioksop.models.Korisnik;
import com.projekat.bioksop.repositories.KorisnikRepository;
import com.projekat.bioksop.services.KorisnikService;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class KorisnikController {
    @Autowired
    RezervacijaRepository rezervacijaRepository;
    @Autowired
    ProjekcijaRepository projekcijaRepository;
    @Autowired
    KorisnikRepository korisnikRepository;
    @Autowired
    SedisteRepository sedisteRepository;

    @RequestMapping("/mojeRezervacije")
    public String rezervacijeKorisnika(Model model, Authentication authentication)
    {
        Korisnik k = korisnikRepository.findByEmail(authentication.getName());
        model.addAttribute("k", k);
        Set<RezervisanaSedista> rezervisanaSedistaSet = rezervacijaRepository.nadjiPoEmailu(authentication.getName());
        //prolazak kroz rezervisana sedista i prikaz rezervacija korisnika
        for(RezervisanaSedista rs: rezervisanaSedistaSet)
        {
            rs.getRezervacija().getProjekcija().getSala().getBioskop().getGrad();
            rs.getRezervacija().getProjekcija().getFilm().getNazivFilma();
            rs.getRezervacija().getProjekcija().getFilm().getZanr();
            rs.getRezervacija().getProjekcija().getFilm().getTehnologija();
            rs.getRezervacija().getProjekcija().getFilm().getTrajanje();
            rs.getRezervacija().getProjekcija().getPocetakProjekcije();
            rs.getRezervacija().getProjekcija().getSala().getBrojSale();
            rs.getSediste().getSedisteId();
            model.addAttribute("rezervisanaSedistaSet", rezervisanaSedistaSet);
        }
        return "mojeRezervacije";
    }

    @RequestMapping("/korisnikProfil")
    public String korisnikProfil(Model model, Authentication authentication)
    {
        Korisnik korisnik = korisnikRepository.findByEmail(authentication.getName());
        model.addAttribute("korisnik", korisnik);
        return "korisnikProfil";
    }

    @PostMapping("/korisnikProfil/update")
    public String updateProfila(@Valid Korisnik korisnik, Model model, Authentication authentication)
    {
        korisnik.setEmail(authentication.getName());
        korisnikRepository.save(korisnik);
        model.addAttribute("korisnici", korisnikRepository.findAll());

            String ms = "Uspešno ste izmenili podatke!";
        model.addAttribute("poruka", ms);
        return "korisnikProfil";
    }

    @RequestMapping("/korisnikProfil/update")
    public String updateProfilaView(Model model, Authentication authentication)
    {
        Korisnik korisnik = korisnikRepository.findByEmail(authentication.getName());
        model.addAttribute("korisnik", korisnik);
        return "izmenaKorisnickogProfila";
    }


}
