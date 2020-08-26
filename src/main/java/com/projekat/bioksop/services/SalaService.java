package com.projekat.bioksop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projekat.bioksop.models.Sala;
import com.projekat.bioksop.repositories.SalaRepository;

import java.util.List;

@Service
public class SalaService
{
    @Autowired
    SalaRepository salaRepository;

    public List<Sala> sveSale()
    {
       return this.salaRepository.findAll();
    }
}
