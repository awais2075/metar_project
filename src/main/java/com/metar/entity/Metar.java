package com.metar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @Column(name = "data", nullable = false)
    @NotNull(message = "{data.notnull}")
    @NotBlank(message = "{data.notblank}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String data;

    @Column(name = "timestamp")
    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp;

    @Column(name = "wind")
    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String wind;

    @Column(name = "visibility")
    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String visibility;

    @Column(name = "temperature")
    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String temperature;

    @Column(name = "dew")
    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dew;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private Subscription subscription;

}