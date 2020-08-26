package com.projekat.bioksop.repositories;

import com.projekat.bioksop.models.Sediste;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projekat.bioksop.models.RezervisanaSedista;
import org.springframework.data.jpa.repository.Query;

public interface RezervisanaSedistaRepository extends JpaRepository<RezervisanaSedista, Long>
{
    //upiti za rezervisana sedista gde nalazimo sedista po id-u i po rezervaciji
    @Query("select s from Sediste s where s.sedisteId=?1")
    Sediste nadjiPo(Long sedisteId);

    @Query("select rs from RezervisanaSedista rs where rs.rezervacija.rezervacijaId=?1")
    Sediste nadjiPoRezervaciji(Long rezervacijaId);
}
