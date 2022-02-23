package com.metar.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metar.entity.Subscription;
import com.metar.exception.SubscriptionFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Subscription> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Subscription addSubscription(@Valid @RequestBody Subscription subscription) throws SubscriptionFoundException {
        return subscriptionService.addSubscription(subscription);
    }

    @DeleteMapping("/{icaoCode}")
    @ResponseStatus(value = HttpStatus.OK)
    public ObjectNode deleteSubscription(@PathVariable(value = "icaoCode") String icaoCode) throws SubscriptionNotFoundException {
        return subscriptionService.deleteSubscription(icaoCode);
    }
}
