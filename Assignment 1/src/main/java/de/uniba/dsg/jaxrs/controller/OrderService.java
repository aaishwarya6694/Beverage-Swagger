package de.uniba.dsg.jaxrs.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.Order;
import java.util.List;

public class OrderService {
    public static final OrderService instance = new OrderService();
    
    private final DB db;

    public OrderService() {
        this.db=new DB();
    }

    public List<Order> getAllOrders(){
        return this.db.getAllOrders();
    }

    public Order getOrder(final int id){
        return this.db.getOrder(id);
    }

    public String addOrder(Order order){
        if(order==null){
            return null;
        }
        return this.db.addOrder(order);
    }

    public Order updateOrder(final int id,Order updateOrder){
        return this.db.updateOrder(id,updateOrder);
    }

    public boolean deleteOrder(final int id){
        return this.db.deleteOrder(id);
    }

    public List<Order> getOrderBetweenRange(final double min,final double max){
        return this.db.getOrderBetweenRange(min,max);
    }

    public boolean cancelOrder(final int id){
        return  this.db.cancelOrder(id);
    }
}
