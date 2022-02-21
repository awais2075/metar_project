package com.metar.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    @Hidden
    private Long id;

    @Column(name = "icao_code", nullable = false, unique = true)
    private String icaoCode;

}
