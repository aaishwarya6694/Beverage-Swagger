package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;

import java.util.List;

public class OrderItemService {
    public static final OrderService instance = new OrderService();

    private final DB db;

    public OrderItemService() {
        this.db=new DB();
    }

}
