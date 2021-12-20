package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.OrderItem;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderitem")
@XmlType(propOrder = {"number", "bottle","crate","quantity"})
public class OrderItemPostDTO {

    private int number;
    @XmlElement(required = true)
    private BottleDTO bottle;
    private CrateDTO crate;
    private int quantity;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BottleDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleDTO bottle) {
        this.bottle = bottle;
    }

    public CrateDTO getCrate() {
        return crate;
    }

    public void setCrate(CrateDTO crate) {
        this.crate = crate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemPostDTO{" +
                "number=" + number +
                ", bottle=" + bottle +
                ", crate=" + crate +
                ", quantity=" + quantity +
                '}';
    }
    public OrderItem unmarshall() {
        BottlePostDTO bottlePostDTO= new BottlePostDTO();
        bottlePostDTO.setVolumePercent(this.bottle.getVolumePercent());
        bottlePostDTO.setVolume(this.bottle.getVolume());
        bottlePostDTO.setPrice(this.bottle.getPrice());
        bottlePostDTO.setName(this.bottle.getName());
        bottlePostDTO.setSupplier(this.bottle.getSupplier());
        bottlePostDTO.setInStock(this.bottle.getInStock());
        bottlePostDTO.setAlcoholic(this.bottle.isAlcoholic());
        Bottle bottle = bottlePostDTO.unmarshall();
        CratePostDTO cratePostDTO = new CratePostDTO();
        cratePostDTO.setBottle(this.crate.getBottle());
        cratePostDTO.setInStock(this.crate.getInStock());
        cratePostDTO.setNoOfBottles(this.crate.getNoOfBottles());
        cratePostDTO.setPrice(this.crate.getPrice());
        Crate crate = cratePostDTO.unmarshall();
        return new OrderItem(this.number,bottle,crate,this.quantity);
    }
}
