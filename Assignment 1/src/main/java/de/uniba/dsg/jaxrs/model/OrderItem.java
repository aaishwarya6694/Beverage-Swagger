package de.uniba.dsg.jaxrs.model;

public class OrderItem {

    private int number;
    private Bottle bottle;
    private Crate crate;
    private int quantity;

    public OrderItem(int number, Bottle bottle,Crate crate, int quantity) {
        this.number = number;
        this.bottle = bottle;
        this.crate = crate;
        this.quantity = quantity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "number=" + number +
                ", bottle=" + bottle +
                ", crate=" + crate +
                ", quantity=" + quantity +
                '}';
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public Crate getCrate() {
        return crate;
    }

    public void setCrate(Crate crate) {
        this.crate = crate;
    }
}
