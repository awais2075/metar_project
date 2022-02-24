package com.metar.repository;

import com.metar.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.icaoCode = :icaoCode")
    Optional<Subscription> findByIcaoCode(@Param("icaoCode") String icaoCode);

    @Modifying
    @Query("delete from Subscription s where s.id = :id")
    Integer deleteBySubscriptionId(@Param("id") Long id);

    @Query("select s from Subscription s where s.active = :active")
    Page<Subscription> findAllActiveSubscriptions(@Param("active") Boolean active, Pageable pageable);

}