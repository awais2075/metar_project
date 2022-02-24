package com.metar.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Hidden
    private Long id;

    @Column(name = "icao_code", nullable = false, unique = true)
    @NotNull(message = "{icaocode.notnull}")
    @NotBlank(message = "{icaocode.notblank}")
    private String icaoCode;

    @Column(name = "active", nullable = false)
    @Hidden
    private Boolean active;

}
