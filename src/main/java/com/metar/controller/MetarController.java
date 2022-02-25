package com.metar.controller;

import com.metar.entity.Metar;
import com.metar.exception.MetarNotFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.service.MetarService;
import io.github.mivek.exception.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/airport")
public class MetarController {

    @Autowired
    private MetarService metarService;

    @GetMapping("/{icaoCode}/METAR")
    @ResponseStatus(value = HttpStatus.OK)
    public Metar getMetars(@PathVariable(value = "icaoCode") String icaoCode) throws SubscriptionNotFoundException, MetarNotFoundException {
        return metarService.getMetars(icaoCode);
    }

    @PostMapping("/{icaoCode}/METAR")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Metar addMetar(@PathVariable(value = "icaoCode") String icaoCode, @Valid @RequestBody Metar metar) throws SubscriptionNotFoundException, ParseException {
        return metarService.addMetar(icaoCode, metar);
    }
}
