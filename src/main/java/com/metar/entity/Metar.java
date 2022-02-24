package com.metar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "metar")
@Data
public class Metar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Hidden
    private Long id;

    @Column(name = "data", nullable = false)
    @NotNull(message = "{data.notnull}")
    @NotBlank(message = "{data.notblank}")
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    @Hidden
    @JsonIgnore
    private Subscription subscription;

}