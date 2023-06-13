package com.bitwise.orderservice.controller;

import com.bitwise.orderservice.dto.Order;
import com.bitwise.orderservice.dto.OrderEvent;
import com.bitwise.orderservice.publisher.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    @Autowired
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();

        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order is in PENDING status");
        orderEvent.setOrder(order);

        orderProducer.sendOrder(orderEvent);

        return "Order sent to the RabbitMQ...";
    }
}
