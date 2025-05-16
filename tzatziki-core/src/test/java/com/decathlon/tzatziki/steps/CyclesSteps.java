package com.decathlon.tzatziki.steps;

import com.decathlon.tzatziki.cycle.Order;
import io.cucumber.java.en.And;

public class CyclesSteps {
    private final ObjectSteps objects;

    public CyclesSteps(ObjectSteps objects) {
        this.objects = objects;
    }

    @And("orderLines reference order")
    public void orderlinesReferenceOrder() {
        Order order = objects.get("order");
        order.getOrderLines().forEach( orderLine -> orderLine.setOrder(order));
    }
}
