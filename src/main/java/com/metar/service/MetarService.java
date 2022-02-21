package com.metar.service;

import com.metar.entity.Metar;
import com.metar.repository.MetarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetarService {

    @Autowired
    private MetarRepository metarRepository;

    public List<Metar> getMetars(String icaoCode) {
        return metarRepository.findByIcaoCode(icaoCode);
    }

    public Metar addMetar(String icaoCode, Metar metar) {
        return metarRepository.save(metar);
    }
}
