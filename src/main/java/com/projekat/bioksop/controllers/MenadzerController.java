package com.projekat.bioksop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.projekat.bioksop.models.Rezervacija;
import com.projekat.bioksop.models.RezervisanaSedista;
import com.projekat.bioksop.repositories.RezervacijaRepository;
import com.projekat.bioksop.repositories.RezervisanaSedistaRepository;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class MenadzerController
{
    @Autowired
    RezervacijaRepository rezervacijaRepository;
    @Autowired
    RezervisanaSedistaRepository rezervisanaSedistaRepository;

    @RequestMapping("/izvestajRezervacija")
    public String listaSvihRezervacija(Model model)
    {
        LocalDateTime datum = LocalDateTime.now();
        int potvrdjene=0;
        int otkazane=0;
        int bezStatusa = 0;
        double zarada=0;
        List<Rezervacija> rezervacijas = rezervacijaRepository.findAll();
        List<RezervisanaSedista> rezervisanaSedistaSet = rezervisanaSedistaRepository.findAll();
        for(RezervisanaSedista rs: rezervisanaSedistaSet)
        {
            zarada+=rs.getCenaKarte();
            if(rs.getRezervacija().getPotvrdjena()==false)
            {
                zarada-=rs.getCenaKarte();
            }
            //prikaz svih rezervacija
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
        for(Rezervacija r: rezervacijas)
        {
            //pronalazanje potvrdjenih rezervacija

            if(r.getPotvrdjena()==null)
            {
                bezStatusa++;
            }
            else if(r.getPotvrdjena())
            {
                potvrdjene++;
            }
            else
            {

                otkazane++;
            }
            r.getProjekcija().getSala().getBioskop().getGrad();
            r.getProjekcija().getFilm().getNazivFilma();
            r.getProjekcija().getFilm().getZanr();
            r.getProjekcija().getFilm().getTehnologija();
            r.getProjekcija().getFilm().getTrajanje();
            r.getProjekcija().getPocetakProjekcije();
            r.getProjekcija().getSala().getBrojSale();
            model.addAttribute("rezervacijas", rezervacijas);
            model.addAttribute("otkazane", otkazane);
            model.addAttribute("potvrdjene", potvrdjene);
            model.addAttribute("bezStatusa", bezStatusa);
            model.addAttribute("zarada", zarada);
            model.addAttribute("datum", datum);
            int ukupno = otkazane+potvrdjene+bezStatusa;
            model.addAttribute("ukupno", ukupno);
        }
        return "izvestajRezervacija";
    }
}
