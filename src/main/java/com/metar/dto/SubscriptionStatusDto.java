package com.metar.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SubscriptionStatusDto {

    @NotNull(message = "{active.notnull}")
    private Boolean active;
}
