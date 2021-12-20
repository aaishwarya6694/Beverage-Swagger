package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.*;
import java.net.URI;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"bottle", "noOfBottles","price"})
public class CrateShortDTO {
    @XmlElement(required = true)
    private BottleShortDTO bottle;
    private int noOfBottles;
    private double price;

    public CrateShortDTO(){}

    public CrateShortDTO(Crate crate, URI baseUri) {
        this.bottle = BottleShortDTO.marshall(crate.getBottle(),baseUri);
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();

    }

    public BottleShortDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleShortDTO bottle) {
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

    @Override
    public String toString() {
        return "CrateShortDTO{" +
                "bottle=" + bottle +
                ", noOfBottles=" + noOfBottles +
                ", price=" + price +
                '}';
    }

    public static CrateShortDTO marshall(final Crate crate, final URI baseUri) {

        return new CrateShortDTO(crate, baseUri);
    }
}
