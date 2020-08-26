package com.projekat.bioksop.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.projekat.bioksop.models.Korisnik;
import java.util.Collection;
import java.util.Collections;

public class KorisnikPrincipal implements UserDetails
{
    private Korisnik korisnik;

    public KorisnikPrincipal(Korisnik korisnik)
    {
        super();
        this.korisnik = korisnik;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority(korisnik.getTipKorisnika()));
    }

    @Override
    public String getPassword()
    {
        return korisnik.getSifra();
    }

    @Override
    public String getUsername()
    {
        return korisnik.getEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
