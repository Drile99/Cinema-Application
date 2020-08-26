package com.projekat.bioksop.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SALA")
public class Sala
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id", unique = true)
    private Long salaId;

    @Column(name = "BrojSale", length = 50, nullable = false, unique = false)
    private int brojSale;

    @OneToMany(mappedBy = "sala")
    private Set<Projekcija> projekcije;

    @ManyToOne
    @JoinColumn(name = "bioskop_id", nullable = false)
    private Bioskop bioskop;

    @OneToMany(mappedBy = "sala")
    private Set<Sediste> sedista;

    //Getteri, setteri i konstruktori
    public Long getSalaId()
    {
        return salaId;
    }

    public void setSalaId(Long salaId)
    {
        this.salaId = salaId;
    }

    public int getBrojSale()
    {
        return brojSale;
    }

    public void setBrojSale(int brojSale)
    {
        this.brojSale = brojSale;
    }

    public Set<Projekcija> getProjekcije()
    {
        return projekcije;
    }

    public void setProjekcije(Set<Projekcija> projekcije)
    {
        this.projekcije = projekcije;
    }

    public Bioskop getBioskop()
    {
        return bioskop;
    }

    public void setBioskop(Bioskop bioskop)
    {
        this.bioskop = bioskop;
    }

    public Set<Sediste> getSedista()
    {
        return sedista;
    }

    public void setSedista(Set<Sediste> sedista)
    {
        this.sedista = sedista;
    }

    public Sala()
    {
    }

    public Sala(Long salaId, int brojSale, Set<Projekcija> projekcije)
    {
        this.salaId = salaId;
        this.brojSale = brojSale;
        this.projekcije = projekcije;
    }


}