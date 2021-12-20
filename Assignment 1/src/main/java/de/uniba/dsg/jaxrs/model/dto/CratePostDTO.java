package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"id", "bottle", "noOfBottles","price","inStock"})
public class CratePostDTO {
    @XmlElement(required = true)
    private BottleDTO bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    @Override
    public String toString() {
        return "CratePostDTO{" +
                "bottle=" + bottle +
                ", noOfBottles=" + noOfBottles +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }

    public BottleDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleDTO bottle) {
        this.bottle = bottle;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    public Crate unmarshall() {
        BottlePostDTO bottledto = new BottlePostDTO();
        bottledto.setName(this.bottle.getName());
        bottledto.setAlcoholic(this.bottle.isAlcoholic());
        bottledto.setInStock(this.bottle.getInStock());
        bottledto.setPrice(this.bottle.getPrice());
        bottledto.setSupplier(this.bottle.getSupplier());
        bottledto.setVolume(this.bottle.getVolume());
        bottledto.setVolumePercent(this.bottle.getVolumePercent());
        Bottle bottle = bottledto.unmarshall();
        return new Crate(0,bottle, this.noOfBottles, this.price, this.inStock);
    }
}
