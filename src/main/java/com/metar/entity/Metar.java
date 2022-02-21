package com.metar.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "metar")
@Data
public class Metar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    @Hidden
    private Long id;

    @Column(name = "data", nullable = false)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    @Hidden
    private Subscription subscription;

}