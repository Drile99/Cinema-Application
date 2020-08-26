package com.projekat.bioksop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.projekat.bioksop.models.*;
import com.projekat.bioksop.repositories.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BioskopController
{
    @Autowired
    BioskopRepository bioskopRepository;

    @Autowired
    SalaRepository salaRepository;

    @Autowired
    ProjekcijaRepository projekcijaRepository;

    @Autowired
    RezervacijaRepository rezervacijaRepository;

    @Autowired
    RezervisanaSedistaRepository rezervisanaSedistaRepository;

    @Autowired
    SedisteRepository sedisteRepository;

    //maping za dodavanje novog buoskopa sa metodom GET
    @RequestMapping(value = "/dodavanjeBioskopa", method = RequestMethod.GET)
    public ModelAndView noviBioskopView()
    {
        ModelAndView modelAndView = new ModelAndView();
        Bioskop bioskop = new Bioskop();
        modelAndView.addObject("bioskop", bioskop);
        modelAndView.setViewName("noviBioskop");
        return modelAndView;
    }
    @RequestMapping(value = "/dodavanjeBioskopa", method = RequestMethod.POST)
    public ModelAndView dodavanjeNovogBioskopa(Bioskop bioskop, BindingResult bindingResult, ModelMap modelMap)
    {
        Bioskop b = bioskopRepository.nadjiPoAdresi(bioskop.getAdresa(), bioskop.getGrad());
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors())
        {
            modelAndView.addObject("poruka", "Molimo Vas da ispravite greške");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if (b!=null)
        {
            modelAndView.addObject("poruka", "Bioskop na ovoj adresi i u ovom gradu već postoji!");
        }
        else
        {
            bioskopRepository.save(bioskop);
            modelAndView.addObject("poruka", "Uspešno ste dodali novi bioskop!");
            modelAndView.setViewName("noviBioskop");
        }
        modelAndView.addObject("bioskop", new Bioskop());
        modelAndView.setViewName("noviBioskop");
        return modelAndView;
    }

    @RequestMapping(value = "/pregledBioskopaAdmin", method = RequestMethod.GET)
    public String pregledBioskopaAdmin(Model model)
    {
        List<Bioskop> bioskopi = bioskopRepository.findAll();
        model.addAttribute("bioskop", bioskopi);
        return "pregledBioskopaAdmin";
    }
    @RequestMapping(value = "/izmenaBioskopa/{bioskop_id}", method = RequestMethod.GET)
    public String izmenaBioskopaView(Model model, @PathVariable("bioskop_id") Long bioskop_id)
    {
        Bioskop bioskop = bioskopRepository.findByBioskopId(bioskop_id);
        model.addAttribute("bioskop", bioskop);
        return "izmenaBioskopa";
    }
    @RequestMapping(value = "/izmenaBioskopa/{bioskop_id}", method = RequestMethod.POST)
    public String izmenaBioskopa(Model model, @PathVariable("bioskop_id") Long bioskop_id, @Valid Bioskop bioskop)
    {
        bioskop.setBioskopId(bioskop_id);
        Bioskop b = bioskopRepository.nadjiPoAdresi(bioskop.getAdresa(), bioskop.getGrad());
        Bioskop b1 = bioskopRepository.nadjiPoAdresiId(bioskop.getAdresa(), bioskop.getGrad(), bioskop.getBioskopId());
        if(b1!=null)
        {
            bioskopRepository.save(bioskop);
            return "redirect:/pregledBioskopaAdmin";
        }
        else if(b!=null)
        {
            String greska = "Bioskop na ovoj adresi i ovom gradu već postoji!";
            model.addAttribute("greska", greska);
            return "izmenaBioskopa";
        }
        else
        {
            bioskopRepository.save(bioskop);
            return "redirect:/pregledBioskopaAdmin";
        }
    }
    @GetMapping(value = "/brisanjeBioskopa/{bioskop_id}")
    public String brisanjeBioskopa(@PathVariable("bioskop_id") Long bioskopId)
    {
        Bioskop bioskop = bioskopRepository.findByBioskopId(bioskopId);
        List<Rezervacija> rezervacije = rezervacijaRepository.findAll();
        List<RezervisanaSedista> rezervisanaSedista = rezervisanaSedistaRepository.findAll();
        List<Sala> sale = salaRepository.findAll();
        List<Projekcija> projekcije = projekcijaRepository.findAll();
        List<Sediste> sedista = sedisteRepository.findAll();
        //prolazak kroz sve rezervacija
        for(Rezervacija r: rezervacije)
        {
            for(RezervisanaSedista rs : rezervisanaSedista)
            {
                if(rs.getRezervacija().getProjekcija().getSala().getBioskop().getBioskopId()==bioskopId)
                {
                    rezervisanaSedistaRepository.delete(rs);
                }
            }
            if(r.getProjekcija().getSala().getBioskop().getBioskopId()==bioskopId)
            {
                rezervacijaRepository.delete(r);
            }
        }
        for(Projekcija p : projekcije)
        {
            if(p.getSala().getBioskop().getBioskopId()==bioskopId)
            {
                projekcijaRepository.delete(p);
            }
        }
        for(Sala s: sale)
        {
            for(Sediste sediste : sedista)
            {
                if(sediste.getSala().getBioskop().getBioskopId()==bioskopId)
                {
                    sedisteRepository.delete(sediste);
                }
            }
            if(s.getBioskop().getBioskopId()==bioskopId)
            {
                salaRepository.delete(s);
            }
        }
        bioskopRepository.delete(bioskop);
        return "redirect:/pregledBioskopaAdmin";
    }
    @RequestMapping(value = "/pregledBioskopa", method = RequestMethod.GET)
    public String pregledBioskopa(Model model)
    {
        List<Bioskop> bioskopi = bioskopRepository.findAll();
        model.addAttribute("bioskop", bioskopi);
        return "pregledBioskopa";
    }
}
