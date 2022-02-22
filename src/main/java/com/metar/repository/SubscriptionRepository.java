package com.metar.repository;

import com.metar.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.icaoCode = :icaoCode")
    Optional<Subscription> findByIcaoCode(@Param("icaoCode") String icaoCode);


}