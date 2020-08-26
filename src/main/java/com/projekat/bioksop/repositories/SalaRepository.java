package com.projekat.bioksop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projekat.bioksop.models.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long>
{
    Sala findBySalaId(Long salaId);
}
