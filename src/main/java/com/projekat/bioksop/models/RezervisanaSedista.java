package com.projekat.bioksop.models;

import javax.persistence.*;

@Entity
@Table(name = "REZERVISANA_SEDISTA")
public class RezervisanaSedista
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezevisanaSedista_id", unique = true)
    private Long rezevisanaSedista_id;

    @Column(name = "cenaKarte", unique = false)
    private double cenaKarte;

    @ManyToOne
    @JoinColumn(name = "rezervacija_id", nullable = false)
    private Rezervacija rezervacija;

    @ManyToOne
    @JoinColumn(name = "sediste_id", nullable = false)
    private Sediste sediste;

    //Getteri, setteri i konstruktori
    public Long getRezevisanaSedista_id()
    {
        return rezevisanaSedista_id;
    }

    public void setRezevisanaSedista_id(Long rezevisanaSedista_id)
    {
        this.rezevisanaSedista_id = rezevisanaSedista_id;
    }

    public Rezervacija getRezervacija()
    {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija)
    {
        this.rezervacija = rezervacija;
    }

    public Sediste getSediste()
    {
        return sediste;
    }

    public void setSediste(Sediste sediste)
    {
        this.sediste = sediste;
    }

    public double getCenaKarte() {
        return cenaKarte;
    }

    public void setCenaKarte(double cenaKarte) {
        this.cenaKarte = cenaKarte;
    }

    public RezervisanaSedista()
    {
    }


    public RezervisanaSedista(Rezervacija rezervacija, Sediste sediste)
    {
        this.rezervacija = rezervacija;
        this.sediste = sediste;
    }

}