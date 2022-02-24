package com.metar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metar.dto.SubscriptionStatusDto;
import com.metar.entity.Subscription;
import com.metar.exception.InvalidPageSizeException;
import com.metar.exception.NegativePageIndexException;
import com.metar.exception.SubscriptionFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.repository.MetarRepository;
import com.metar.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MetarRepository metarRepository;

    @Value("${paging.subscription.size}")
    private Integer allowedPageSize;

    @Autowired
    private ObjectNode objectNode;

    public ObjectNode getSubscriptions(Integer pageNo, Integer pageSize, Boolean active) throws NegativePageIndexException, InvalidPageSizeException {
        if(pageNo < 0) {
            throw new NegativePageIndexException("Page No Should Not be Negative");
        }

        if(pageSize > allowedPageSize) {
            throw new InvalidPageSizeException("Page Size is greater than Allowed Page Size i.e."+allowedPageSize);
        }

        Page<Subscription> page;
        if(active) {
            page = subscriptionRepository.findAllActiveSubscriptions(true, PageRequest.of(pageNo, pageSize));
        } else {
            page = subscriptionRepository.findAll(PageRequest.of(pageNo, pageSize));
        }

        objectNode.put("totalItems", page.getTotalElements());
        objectNode.put("totalPages", page.getTotalPages());
        objectNode.put("currentPage", page.getNumber());
        objectNode.putPOJO("subscriptions", page.getContent());
        return objectNode;
    }

    public Subscription addSubscription(Subscription subscription) throws SubscriptionFoundException {
        var sub = subscriptionRepository.findByIcaoCode(subscription.getIcaoCode()).orElse(null);
        if(Objects.nonNull(sub)) {
            throw new SubscriptionFoundException(subscription.getIcaoCode()+ " already exists in subscription list");
        }
        subscription.setId(null);
        subscription.setActive(true);
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

    public Subscription updateSubscriptionStatus(String icaoCode, SubscriptionStatusDto dto) throws SubscriptionNotFoundException {
        var subscription = subscriptionRepository.findByIcaoCode(icaoCode).orElse(null);
        if(Objects.isNull(subscription)) {
            throw new SubscriptionNotFoundException(icaoCode+" subscription not found in list");
        }
        subscription.setActive(dto.getActive());

        return subscriptionRepository.save(subscription);
    }
}
