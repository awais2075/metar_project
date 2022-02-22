package com.metar.controller;

import com.metar.entity.Subscription;
import com.metar.exception.SubscriptionFoundException;
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
    public List<Subscription> getSubscriptions() throws Exception {
        return subscriptionService.getSubscriptions();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Subscription addSubscription(@Valid @RequestBody Subscription subscription) throws SubscriptionFoundException {
        return subscriptionService.addSubscription(subscription);
    }
}
