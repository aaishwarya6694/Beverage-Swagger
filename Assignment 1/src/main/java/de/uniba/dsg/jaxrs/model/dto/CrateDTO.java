package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.resources.CrateResource;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"id", "bottle", "noOfBottles","price","inStock","href"})
public class CrateDTO {
    private int id;
    @XmlElement(required = true)
    private BottleDTO bottle;
    private int noOfBottles;
    private double price;
    private int inStock;
    private URI href;

    public CrateDTO(){}

    public CrateDTO(Crate crate,final URI baseUri){
        this.id=crate.getId();
        this.bottle=BottleDTO.marshall(crate.getBottle(),baseUri);
        this.noOfBottles=crate.getNoOfBottles();
        this.price=crate.getPrice();
        this.inStock=crate.getInStock();
        this.href= UriBuilder.fromUri(baseUri).path(CrateResource.class).path(CrateResource.class, "getCrate" +
                "" +
                "" +
                "").build(this.id);
    }

    public static CrateDTO marshall(final Crate crate, final URI baseUri) {
        return new CrateDTO(crate, baseUri);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    public static List<CrateDTO> marshallPagination(final List<Crate> crateList, final URI baseUri) {
        final ArrayList<CrateDTO> crates = new ArrayList<>();
        for (final Crate crate : crateList) {
            crates.add(new CrateDTO(crate, baseUri));
        }
        return crates;
    }
}
