package com.projekat.bioksop.repositories;

import com.projekat.bioksop.models.Sediste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.projekat.bioksop.models.Projekcija;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProjekcijaRepository extends JpaRepository<Projekcija, Long>
{
    //upiti za projekcije
    @Query("select p from Projekcija  p where p.film.nazivFilma like ?1")
    Set<Projekcija> projekcijaPoFilmu(String nazivFilma);

    @Query("select rs.rasporedSedista from Projekcija rs where rs.projekcijaId=?1")
    Set<Sediste> nadjiSva(Long projekcijaId);

    @Query(value = "select * from Rezervacija r where r.projekcija_id=:projekcijaId and r.rezervacija_id=:rezervacijaId LIMIT 1", nativeQuery = true)
    Projekcija nadjiPoId(@Param("projekcijaId") Long projekcijaId, @Param("rezervacijaId") Long rezervacijaId);

    @Query(value = "select  p from Projekcija  p where  p.film.filmId=?1")
    Set<Projekcija> nadjiPoIdFilma(Long filmId);

    @Query(value = "select  p from Projekcija  p")
    Set<Projekcija>sveProjekcije();
}
