package com.metar.service;

import com.metar.entity.Metar;
import com.metar.exception.MetarNotFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.repository.MetarRepository;
import com.metar.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MetarService {

    @Autowired
    private MetarRepository metarRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Metar getMetars(String icaoCode) throws SubscriptionNotFoundException, MetarNotFoundException {
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }

        var metar = metarRepository.findFirstBySubscription_IcaoCodeEqualsOrderByIdDesc(icaoCode).orElse(null);
        if(Objects.isNull(metar)) {
            throw new MetarNotFoundException("Metar Data not found against icaoCode : "+icaoCode);
        }

        return metar;
    }

    public Metar addMetar(String icaoCode, Metar metar) throws SubscriptionNotFoundException {
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }
        metar.setSubscription(subscription);
        return metarRepository.save(metar);
    }
}
