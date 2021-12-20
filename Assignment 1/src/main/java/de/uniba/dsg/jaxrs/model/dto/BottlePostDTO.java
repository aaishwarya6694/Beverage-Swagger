package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = {"id", "name", "volume","isAlcoholic","volumePercent","price","supplier","inStock"})
public class BottlePostDTO {

    @XmlElement(required = true)
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    @XmlElement(required = true)
    private double price;
    private String supplier;
    @XmlElement(required = true)
    private int inStock;

    @Override
    public String toString() {
        return "BottlePostDTO{" +
                "name='" + name + '\'' +
                ", volume=" + volume +
                ", isAlcoholic=" + isAlcoholic +
                ", volumePercent=" + volumePercent +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                ", inStock=" + inStock +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    public Bottle unmarshall() {
        return new Bottle(0, this.name, this.volume, this.isAlcoholic, this.volumePercent,this.price,this.supplier,this.inStock);
    }
}
