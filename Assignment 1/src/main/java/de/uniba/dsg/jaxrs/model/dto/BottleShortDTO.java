package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Bottle;

import javax.xml.bind.annotation.*;
import java.net.URI;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = {"name","price"})
public class BottleShortDTO {

    @XmlElement(required = true)
    private String name;
    private double price;

    public BottleShortDTO(){}

    public BottleShortDTO(Bottle bottle, URI baseUri) {
        this.name = bottle.getName();
        this.price = bottle.getPrice();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "BottleShortDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
    public static BottleShortDTO marshall(final Bottle bottle, final URI baseUri) {
        return new BottleShortDTO(bottle, baseUri);
    }
}
