package com.projekat.bioksop.services;

import com.projekat.bioksop.models.Korisnik;

import java.util.Optional;

public interface IKorisnikService
{
    Optional<Korisnik> findById(Long id);

    public void sacuvajKorisnika(Korisnik korisnik);

    public boolean postojiKorisnik(Korisnik korisnik);
}
