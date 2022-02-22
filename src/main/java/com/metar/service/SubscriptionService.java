package com.metar.service;

import com.metar.entity.Subscription;
import com.metar.exception.SubscriptionFoundException;
import com.metar.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription addSubscription(Subscription subscription) throws SubscriptionFoundException {
        var sub = subscriptionRepository.findByIcaoCode(subscription.getIcaoCode()).orElse(null);
        if(Objects.nonNull(sub)) {
            throw new SubscriptionFoundException(subscription.getIcaoCode()+ " already exists in subscription list");
        }
        return subscriptionRepository.save(subscription);
    }
}
