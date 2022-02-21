package com.metar.controller;

import com.metar.entity.Metar;
import com.metar.service.MetarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airport")
public class MetarController {

    @Autowired
    private MetarService metarService;

    @GetMapping("/{icaoCode}/METAR")
    public List<Metar> getMetars(@PathVariable(value = "icaoCode") String icaoCode) {
        return metarService.getMetars(icaoCode);
    }

    @PostMapping("/{icaoCode}/METAR")
    public Metar addMetar(@PathVariable(value = "icaoCode") String icaoCode, @RequestBody Metar metar) {
        return metarService.addMetar(icaoCode, metar);
    }
}
