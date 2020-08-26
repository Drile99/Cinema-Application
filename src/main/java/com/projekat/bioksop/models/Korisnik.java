package com.projekat.bioksop.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "KORISNIK")
public class Korisnik
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id", unique = true)
    private Long korisnikId;

    public Long getKorisnikId() {

        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public void setPoeni(Integer poeni) {
        this.poeni = poeni;
    }

    @Column(name = "Ime", length = 50, nullable = false, unique = false)
    private String ime;
    @Column(name = "Prezime", length = 50, nullable = false, unique = false)
    private String prezime;
    @Column(name = "Email", length = 50, nullable = false, unique = true)
    private String email;
    @Column(name = "Sifra", length = 120, nullable = false, unique = false)
    private String sifra;
    @Column(name = "TipKorisnika", length = 20, nullable = false, unique = false)
    private String tipKorisnika;
    @Column(name = "Poeni", length = 20, nullable = true, unique = false)
    private Integer poeni;
    @Column(name = "ClanKluba", length = 20, nullable = false, unique = false)
    private boolean clanKluba;

    @OneToMany(mappedBy = "korisnik")
    private Set<Rezervacija> rezervacije;

    //Getteri, setteri i konstuktori
    public String getIme()
    {
        return ime;
    }

    public void setIme(String ime)
    {
        this.ime = ime;
    }

    public String getPrezime()
    {
        return prezime;
    }

    public void setPrezime(String prezime)
    {
        this.prezime = prezime;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTipKorisnika()
    {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika)
    {
        this.tipKorisnika = tipKorisnika;
    }

    public int getPoeni()
    {
        return poeni;
    }

    public void setPoeni(int poeni)
    {
        this.poeni = poeni;
    }

    public boolean isClanKluba()
    {
        return clanKluba;
    }

    public void setClanKluba(boolean clanKluba)
    {
        this.clanKluba = clanKluba;
    }

    public String getSifra()
    {
        return sifra;
    }

    public void setSifra(String sifra)
    {
        this.sifra = sifra;
    }

    public Korisnik()
    {

    }


    public Korisnik(Long korisnikId, String ime, String prezime, String email, String sifra, String tipKorisnika, int poeni, boolean clanKluba)
    {
        this.korisnikId = korisnikId;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.sifra = sifra;
        this.tipKorisnika = tipKorisnika;
        this.poeni = poeni;
        this.clanKluba = clanKluba;
    }

    public Set<Rezervacija> getRezervacije()
    {
        return rezervacije;
    }

    public void setRezervacije(Set<Rezervacija> rezervacije)
    {
        this.rezervacije = rezervacije;
    }
}
