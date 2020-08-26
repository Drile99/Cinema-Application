package com.projekat.bioksop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projekat.bioksop.models.Sediste;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SedisteRepository extends JpaRepository<Sediste, Long>
{
    @Query("SELECT s from Sediste s order by s.sedisteId asc")
    Set<Sediste> pronadjiSva();
}
