package de.uniba.dsg.jaxrs.db;

import com.sun.org.apache.xpath.internal.operations.Or;
import de.uniba.dsg.jaxrs.model.*;

import java.util.*;

public class DB {

    private final List<Bottle> bottles;
    private final List<Crate> crates;
    private final List<Order> orders;

    public DB() {
        this.bottles = initBottles();
        this.crates = initCases();
        this.orders = initOrder();
    }

    private List<Bottle> initBottles() {
        return new ArrayList<>(Arrays.asList(
                new Bottle(1, "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34),
                new Bottle(2, "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17),
                new Bottle(3, "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11),
                new Bottle(4, "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5),
                new Bottle(5, "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3),
                new Bottle(6, "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66),
                new Bottle(7, "Premium Tafelwasser", 0.7, false, 0.0, 4.29, "Franken Brunnen", 12),
                new Bottle(8, "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57),
                new Bottle(9, "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42),
                new Bottle(10, "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12),
                new Bottle(11, "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34),
                new Bottle(12, "Cola", 1.5, false, 0.0, 1.79, "CCC", 69),
                new Bottle(13, "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12),
                new Bottle(14, "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25),
                new Bottle(15, "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55),
                new Bottle(16, "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8)
        ));
    }

    private List<Crate> initCases() {
        return new ArrayList<>(Arrays.asList(
                new Crate(1, this.bottles.get(0), 20, 14.99, 3),
                new Crate(2, this.bottles.get(1), 20, 15.99, 5),
                new Crate(3, this.bottles.get(2), 6, 30.00, 7),
                new Crate(4, this.bottles.get(7), 12, 1.99, 11),
                new Crate(5, this.bottles.get(8), 20, 11.99, 13),
                new Crate(6, this.bottles.get(11), 6, 10.99, 4),
                new Crate(7, this.bottles.get(12), 6, 11.99, 5),
                new Crate(8, this.bottles.get(13), 20, 35.00, 7),
                new Crate(9, this.bottles.get(14), 12, 20.00, 9)
        ));
    }

    private List<Order> initOrder() {
        return new ArrayList<>(Arrays.asList(
                new Order(1, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(3),this.crates.get(2), 2),
                        new OrderItem(20,this.bottles.get(2), this.crates.get(3), 1),
                        new OrderItem(30, this.bottles.get(7),this.crates.get(1), 1)
                )), 32.56, OrderStatus.SUBMITTED),
                new Order(2, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(13),this.crates.get(8),2),
                        new OrderItem(20, this.bottles.get(9),this.crates.get(4),2),
                        new OrderItem(30, this.bottles.get(1),this.crates.get(0), 1)
                )), 22.95, OrderStatus.PROCESSED),
                new Order(3, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(2),this.crates.get(7), 1)
                )), 5.79, OrderStatus.SUBMITTED)
        ));
    }

    public List<Order> getAllOrders() {
        return this.orders;
    }

    public Order getOrder(final int id){
        return this.orders.stream().filter(o -> o.getOrderId() == id).findFirst().orElse(null);
    }

    public  List<Bottle> getAllBottles(){
        return this.bottles;
    }

    public Bottle getBottle(final int id){
        return this.bottles.stream().filter(b->b.getId() == id).findFirst().orElse(null);
    }

    public List<Crate> getAllCrates(){
        return this.crates;
    }

    public Crate getCrate(final int id){
        return this.crates.stream().filter(c->c.getId() == id).findFirst().orElse(null);
    }

    public Bottle addBottle(Bottle newBottle){
        this.bottles.add(newBottle);
        return newBottle;
    }

    public Bottle updateBottle(final int id,final Bottle updatedBottle){
        final Bottle bottle = this.getBottle(id);
        if(bottle==null  || updatedBottle==null){
            return null;
        }
        Optional.ofNullable(updatedBottle.getInStock()).ifPresent(d -> bottle.setInStock(d));
        Optional.ofNullable(updatedBottle.getName()).ifPresent(d -> bottle.setName(d));
        Optional.ofNullable(updatedBottle.getPrice()).ifPresent(d -> bottle.setPrice(d));
        bottle.setAlcoholic(updatedBottle.isAlcoholic());
        bottle.setSupplier(updatedBottle.getSupplier());
        bottle.setVolume(updatedBottle.getVolume());
        bottle.setVolumePercent(updatedBottle.getVolumePercent());
        return bottle;
    }

    public boolean deleteBottle(final int id){
        Bottle bottle = this.getBottle(id);
        return this.bottles.remove(bottle);
    }

    public Crate addCrate(Crate crate){
        this.crates.add(crate);
        return crate;
    }

    public Crate updateCrate(final int id,Crate updatecrate){
        final Crate crate = this.getCrate(id);
        if(crate == null || updatecrate==null){
            return null;
        }
        Optional.ofNullable(updatecrate.getBottle()).ifPresent(d -> crate.setBottle(d));
        crate.setInStock(updatecrate.getInStock());
        crate.setBottle(updatecrate.getBottle());
        crate.setPrice(updatecrate.getPrice());
        return crate;
    }

    public boolean deleteCrate(final int id){
        Crate crate = this.getCrate(id);
        return this.crates.remove(crate);
    }

    public List<Bottle> getMinAndMaxPriceBeverages(final double min,final double max){
        List<Bottle> finalList = new ArrayList<Bottle>();
        for(Bottle bottle:this.bottles){
            if(bottle.getPrice() >= min && bottle.getPrice() <=max){
                finalList.add(bottle);
            }
        }
        return finalList;
    }

    public List<Order> getOrderBetweenRange(final double min,final double max){
        List<Order> finalList = new ArrayList<Order>();
        for(Order order:this.orders){
            if(order.getPrice() >= min && order.getPrice() <=max){
                finalList.add(order);
            }
        }
        return finalList;
    }

    public String addOrder(Order order){
        String orderResult="success";
        for(OrderItem orderItem:order.getOrderItems()){
            Bottle bottleDB = this.getBottleByName(orderItem.getBottle().getName());
            int remainingBottleInStock =bottleDB.getInStock()-orderItem.getCrate().getNoOfBottles();
            if(remainingBottleInStock >=0)
                bottleDB.setInStock(remainingBottleInStock);
            else
                return "Bottles ordered are more then the in stock bottle.Order cannot be processed";
        }
        order.setStatus(OrderStatus.PROCESSED);
        this.orders.add(order);
        return orderResult;
    }

    public Bottle getBottleByName(String bottleName){
        return this.bottles.stream().filter(b->b.getName().equalsIgnoreCase(bottleName)).findFirst().orElse(null);
    }

    public Order updateOrder(final int id,Order updateOrder){
        final Order order = this.getOrder(id);
        if(order == null || updateOrder==null){
            return null;
        }
        Optional.ofNullable(updateOrder.getOrderItems()).ifPresent(d -> order.setOrderItems(d));
        order.setStatus(updateOrder.getStatus());
        order.setPrice(updateOrder.getPrice());
        return order;
    }

    public boolean deleteOrder(final int id){
        Order order = this.getOrder(id);
        return this.orders.remove(order);
    }

    public boolean cancelOrder(final int id){
        Order order = this.getOrder(id);
        order.setStatus(OrderStatus.CANCELLED);
        return false;
    }
}
