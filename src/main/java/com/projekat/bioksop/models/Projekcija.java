package com.projekat.bioksop.models;


import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "PROJEKCIJA")
public class Projekcija
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projekcija_id", unique = true)
    private Long projekcijaId;

    @Column(name = "PocetakProjekcije", length = 50, nullable = false, unique=false)
    private LocalDateTime pocetakProjekcije;

    @Column(name = "KrajProjekcije", length = 50, nullable = false, unique=false)
    private LocalDateTime krajProjekcije;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @OneToMany(mappedBy = "projekcija")
    private Set<Rezervacija> rezervacije;

    @ManyToMany
    @JoinTable(
            name = "projekcija_sediste",
            joinColumns = @JoinColumn(name = "projekcija_id"),
            inverseJoinColumns = @JoinColumn(name = "sediste_id"))
    Set<Sediste> rasporedSedista;

    //Getteri, Setteri i konstruktori
    public Long getProjekcijaId()
    {
        return projekcijaId;
    }
    public LocalDateTime getPocetakProjekcije()
    {
        return pocetakProjekcije;
    }
    public void setPocetakProjekcije(LocalDateTime pocetakProjekcije)
    {
        this.pocetakProjekcije = pocetakProjekcije;
    }

    public LocalDateTime getKrajProjekcije()
    {
        return krajProjekcije;
    }

    public void setKrajProjekcije(LocalDateTime krajProjekcije)
    {
        this.krajProjekcije = krajProjekcije;
    }

    public Film getFilm()
    {
        return film;
    }

    public void setFilm(Film film)
    {
        this.film = film;
    }

    public Set<Rezervacija> getRezervacije()
    {
        return rezervacije;
    }

    public void setRezervacije(Set<Rezervacija> rezervacije)
    {
        this.rezervacije = rezervacije;
    }

    public Set<Sediste> getRasporedSedista()
    {
        return rasporedSedista;
    }

    public void setRasporedSedista(Set<Sediste> rasporedSedista)
    {
        this.rasporedSedista = rasporedSedista;
    }

    public void setProjekcijaId(Long projekcijaId)
    {
        this.projekcijaId = projekcijaId;
    }

    public Sala getSala()
    {
        return sala;
    }

    public void setSala(Sala sala)
    {
        this.sala = sala;
    }



    public Projekcija()
    {
    }

    public Projekcija(Long projekcijaId, LocalDateTime pocetakProjekcije, LocalDateTime krajProjekcije, Sala sala, Film film)
    {
        this.pocetakProjekcije=pocetakProjekcije;
        this.krajProjekcije=krajProjekcije;
        this.projekcijaId = projekcijaId;
        this.sala = sala;
        this.film = film;
    }

}