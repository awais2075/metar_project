package com.metar.service;

import com.metar.entity.Metar;
import com.metar.exception.MetarNotFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.repository.MetarRepository;
import com.metar.repository.SubscriptionRepository;
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

    public Metar addMetar(String icaoCode, Metar metar) throws SubscriptionNotFoundException {
        log.info("addMetar: {} {}", icaoCode, metar.toString());
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            log.error("addMetar: Subscription Not Found Exception");
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }
        metar.setId(null);
        metar.setSubscription(subscription);
        return metarRepository.save(metar);
    }
}
