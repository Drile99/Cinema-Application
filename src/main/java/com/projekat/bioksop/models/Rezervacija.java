package com.projekat.bioksop.models;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "REZERVACIJA")
public class Rezervacija
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervacija_id", unique = true)
    private Long rezervacijaId;

    @Column(name = "potvrdjena", unique = false, nullable = true)
    private Boolean potvrdjena;



    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "projekcija_id", nullable = false)
    private Projekcija projekcija;

    @OneToMany(mappedBy = "rezervacija")
    private Set<RezervisanaSedista> rezervisanaSedista;


    //Getteri, setteri i konstruktori
    public Long getRezervacijaId()
    {
        return rezervacijaId;
    }

    public void setRezervacijaId(Long rezervacijaId)
    {
        this.rezervacijaId = rezervacijaId;
    }

    public Boolean getPotvrdjena()
    {
        return potvrdjena;
    }

    public void setPotvrdjena(Boolean potvrdjena)
    {
        this.potvrdjena = potvrdjena;
    }



    public Korisnik getKorisnik()
    {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik)
    {
        this.korisnik = korisnik;
    }

    public Projekcija getProjekcija()
    {
        return projekcija;
    }

    public void setProjekcija(Projekcija projekcija)
    {
        this.projekcija = projekcija;
    }

    public Set<RezervisanaSedista> getRezervisanaSedista()
    {
        return rezervisanaSedista;
    }

    public void setRezervisanaSedista(Set<RezervisanaSedista> rezervisanaSedista)
    {
        this.rezervisanaSedista = rezervisanaSedista;
    }

    public Rezervacija()
    {
    }

}