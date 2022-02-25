package com.metar.service;

import com.metar.entity.Metar;
import com.metar.exception.MetarNotFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.repository.MetarRepository;
import com.metar.repository.SubscriptionRepository;
import io.github.mivek.exception.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class MetarService {

    @Autowired
    private MetarRepository metarRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Metar getMetars(String icaoCode) throws SubscriptionNotFoundException, MetarNotFoundException {
        log.info("getMetars: icaoCode: {}", icaoCode);
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            log.error("getMetars: Subscription Not Found {}", icaoCode);
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }

        var metar = metarRepository.findFirstBySubscription_IcaoCodeEqualsOrderByIdDesc(icaoCode).orElse(null);
        if(Objects.isNull(metar)) {
            log.error("getMetars: Metar Not Found Exception {}", icaoCode);
            throw new MetarNotFoundException("Metar Data not found against icaoCode : "+icaoCode);
        }

        return metar;
    }

    public Metar addMetar(String icaoCode, Metar metar) throws SubscriptionNotFoundException, ParseException {
        log.info("addMetar: {} {}", icaoCode, metar.toString());
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            log.error("addMetar: Subscription Not Found Exception");
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }

        decodeMetarData(metar);
        metar.setId(null);
        metar.setSubscription(subscription);
        return metarRepository.save(metar);
    }

    private void decodeMetarData(Metar metar) throws ParseException {
        var metarData = io.github.mivek.service.MetarService.getInstance().decode(metar.getData().replace("METAR", "").trim());
        metar.setTimestamp("Day: "+metarData.getDay()+"; Time: "+metarData.getTime()+ " UTC");
        metar.setWind("Speed: "+metarData.getWind().getSpeed()+metarData.getWind().getUnit()+"; Direction: "+metarData.getWind().getDirectionDegrees()+" "+metarData.getWind().getDirection());
        metar.setVisibility(metarData.getVisibility().getMainVisibility());
        metar.setTemperature(metarData.getTemperature()+" Celsius");
        metar.setDew(metarData.getDewPoint()+" Celsius");
    }
}