package com.metar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metar.entity.Subscription;
import com.metar.exception.SubscriptionFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.repository.MetarRepository;
import com.metar.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MetarRepository metarRepository;

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

    @Transactional
    public ObjectNode deleteSubscription(String icaoCode) throws SubscriptionNotFoundException {
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }
        int metarCount = metarRepository.deleteBySubscriptionId(subscription.getId());
        int subscriptionCount = subscriptionRepository.deleteBySubscriptionId(subscription.getId());

        var node = new ObjectMapper().createObjectNode();
        node.put("subscriptionCount", subscriptionCount);
        node.put("metarCount", metarCount);
        return node;
    }
}
