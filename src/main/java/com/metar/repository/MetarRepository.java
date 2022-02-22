package com.metar.repository;

import com.metar.entity.Metar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetarRepository extends JpaRepository<Metar, Long> {
    @Query("select m from Metar m where m.subscription.icaoCode = ?1")
    List<Metar> findAllByIcaoCode(String icaoCode);

    Optional<Metar> findFirstBySubscription_IcaoCodeEqualsOrderByIdDesc(String icaoCode);


}