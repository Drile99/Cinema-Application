package com.projekat.bioksop.models;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SEDISTE")
public class Sediste
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sediste_id", unique = true)
    private Long sedisteId;

    @Column(name = "tipSedista", length = 20, unique = false, nullable = false)
    private String tipSedista;


    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @OneToMany(mappedBy = "sediste")
    private Set<RezervisanaSedista> rezervisanaSedista;

    @ManyToMany(mappedBy = "rasporedSedista", cascade = CascadeType.MERGE)
    Set<Projekcija> projekcijeSedista;

    //Getteri, setteri i konstruktori
    public Long getSedisteId()
    {
        return sedisteId;
    }

    public void setSedisteId(Long sedisteId)
    {
        this.sedisteId = sedisteId;
    }

    public String getTipSedista()
    {
        return tipSedista;
    }

    public void setTipSedista(String tipSedista)
    {
        this.tipSedista = tipSedista;
    }

    public Sala getSala()
    {
        return sala;
    }

    public void setSala(Sala sala)
    {
        this.sala = sala;
    }

    public Set<Projekcija> getProjekcijeSedista()
    {
        return projekcijeSedista;
    }

    public void setProjekcijeSedista(Set<Projekcija> projekcijeSedista)
    {
        this.projekcijeSedista = projekcijeSedista;
    }

    public Sediste()
    {
    }

    public Sediste(Long sedisteId, String tipSedista)
    {
        this.sedisteId = sedisteId;
        this.tipSedista = tipSedista;
    }

    public Sediste(Sala sala)
    {
        this.sala = sala;
    }


    public Sediste(Set<Projekcija> projekcijeSedista)
    {
        this.projekcijeSedista = projekcijeSedista;
    }

    public Set<RezervisanaSedista> getRezervisanaSedista()
    {
        return rezervisanaSedista;
    }

    public void setRezervisanaSedista(Set<RezervisanaSedista> rezervisanaSedista)
    {
        this.rezervisanaSedista = rezervisanaSedista;
    }

}