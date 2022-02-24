package com.metar.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.metar.dto.SubscriptionStatusDto;
import com.metar.entity.Subscription;
import com.metar.exception.InvalidPageSizeException;
import com.metar.exception.NegativePageIndexException;
import com.metar.exception.SubscriptionFoundException;
import com.metar.exception.SubscriptionNotFoundException;
import com.metar.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ObjectNode getSubscriptions(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize) throws NegativePageIndexException, InvalidPageSizeException {
        return subscriptionService.getSubscriptions(pageNo, pageSize);
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

    @PutMapping("/{icaoCode}")
    @ResponseStatus(value = HttpStatus.OK)
    public Subscription updateSubscriptionStatus(@PathVariable(value = "icaoCode") String icaoCode, @Valid @RequestBody SubscriptionStatusDto dto) throws SubscriptionNotFoundException {
        return subscriptionService.updateSubscriptionStatus(icaoCode, dto);
    }
}
