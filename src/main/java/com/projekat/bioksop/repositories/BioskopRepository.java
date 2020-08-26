package com.projekat.bioksop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.projekat.bioksop.models.Bioskop;

@Repository
public interface BioskopRepository extends JpaRepository<Bioskop, Long>
{
    //pronalazak bioskopa po adresi i gradu kao i id-u
    Bioskop findByBioskopId(Long bioskopId);

    @Query(value = "select * from Bioskop b where b.adresa=:adresa and b.grad=:grad", nativeQuery = true)
    Bioskop nadjiPoAdresi(@Param("adresa") String adresa, @Param("grad") String grad);

    @Query(value = "select * from Bioskop b where b.adresa=:adresa and b.grad=:grad and b.bioskop_id=:bioskopId", nativeQuery = true)
    Bioskop nadjiPoAdresiId(@Param("adresa") String adresa, @Param("grad") String grad, @Param("bioskopId") Long bioskopId);
}
