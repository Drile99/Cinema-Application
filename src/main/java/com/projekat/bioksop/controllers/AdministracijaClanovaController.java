package com.projekat.bioksop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
public class AdministracijaClanovaController
{
    private KorisnikRepository korisnikRepository;
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    public AdministracijaClanovaController(KorisnikRepository korisnikRepository)
    {
        this.korisnikRepository = korisnikRepository;
    }

    @GetMapping("/korisnici/update/{korisnik_id}")
    public String formaZaUpdate(@PathVariable("korisnik_id") Long korisnik_id, Model model)
    {
        Korisnik korisnik = korisnikRepository.findById(korisnik_id)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika:" + korisnik_id));
        model.addAttribute("korisnik", korisnik);
        return "updateKorisnika";
    }


    @PostMapping("/korisnici/update/{korisnik_id}")
    public String updateKorisnika(@PathVariable("korisnik_id") Long korisnik_id, @Valid Korisnik korisnik, Model model)
    {
        korisnik.setKorisnikId(korisnik_id);
        korisnikRepository.save(korisnik);
        model.addAttribute("korisnici", korisnikRepository.findAll());
        return "updateKorisnika";
    }

    @GetMapping("/korisnici/delete/{korisnik_id}")
    public String brisanjeKorisnika(@PathVariable("korisnik_id") long korisnik_id, Model model)
    {
        Korisnik korisnik = korisnikRepository.findById(korisnik_id)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika:" + korisnik_id));
        korisnikRepository.delete(korisnik);
        model.addAttribute("korisnik", korisnikRepository.findAll());
        return "administriranjeClanova";
    }

    @RequestMapping("/administriranjeClanova/korisnici")
    public String spisakKorisnika(Model model)
    {
        List<Korisnik> korisnik = this.korisnikService.findAll();
        model.addAttribute("korisnik", korisnik);
        return "administriranjeClanova";
    }
}
