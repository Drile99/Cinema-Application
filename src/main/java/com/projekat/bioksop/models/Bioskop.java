package com.projekat.bioksop.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BIOSKOP")
public class Bioskop
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bioskop_id", unique = true)
    private Long bioskopId;

    @Column(name = "Naziv", length = 50, nullable = false, unique = false)
    private String naziv;

    @Column(name = "Adresa", length = 50, nullable = false, unique = false)
    private String adresa;

    @Column(name = "Grad", length = 50, nullable = false, unique = false)
    private String grad;

    @OneToMany(mappedBy = "bioskop")
    private Set<Sala> sale;

    //Getteri, setteri i konstruktori
    public Long getBioskopId()
    {
        return bioskopId;
    }

    public void setBioskopId(Long bioskopId)
    {
        this.bioskopId = bioskopId;
    }

    public String getNaziv()
    {
        return naziv;
    }

    public void setNaziv(String naziv)
    {
        this.naziv = naziv;
    }

    public String getAdresa()
    {
        return adresa;
    }

    public void setAdresa(String adresa)
    {
        this.adresa = adresa;
    }

    public String getGrad()
    {
        return grad;
    }

    public void setGrad(String grad)
    {
        this.grad = grad;
    }

    public Set<Sala> getSale()
    {
        return sale;
    }

    public void setSale(Set<Sala> sale)
    {
        this.sale = sale;
    }

    public Bioskop(Long bioskopId, String naziv, String adresa, String grad)
    {
        this.bioskopId = bioskopId;
        this.naziv = naziv;
        this.adresa = adresa;
        this.grad = grad;
    }

    public Bioskop()
    {

    }
}
