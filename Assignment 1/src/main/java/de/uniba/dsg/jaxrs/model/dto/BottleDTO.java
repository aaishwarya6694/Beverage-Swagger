package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.resources.BottleResource;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = {"id", "name", "volume","isAlcoholic","volumePercent","price","supplier","inStock","href"})
public class BottleDTO {

    private int id;
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

    private URI href;

    public BottleDTO(){}

    public BottleDTO(Bottle bottle,final URI baseUri){
        this.id=bottle.getId();
        this.name=bottle.getName();
        this.volume=bottle.getVolume();
        this.isAlcoholic=bottle.isAlcoholic();
        this.volumePercent=bottle.getVolumePercent();
        this.price=bottle.getPrice();
        this.supplier=bottle.getSupplier();
        this.inStock=bottle.getInStock();
        this.href = UriBuilder.fromUri(baseUri).path(BottleResource.class).path(BottleResource.class, "getBottle" +
                "" +
                "" +
                "").build(this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    public static List<BottleDTO >marshallPagination(final List<Bottle> bottleList, final URI baseUri) {
        final ArrayList<BottleDTO> bottles = new ArrayList<>();
        for (final Bottle bottle : bottleList) {

            bottles.add(new BottleDTO(bottle, baseUri));
        }
        return bottles;
    }
    public static BottleDTO marshall(final Bottle bottle, final URI baseUri) {
        return new BottleDTO(bottle, baseUri);
    }
}
