package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.OrderItem;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderitem")
@XmlType(propOrder = {"number", "bottle","crate","quantity","href"})
public class OrderItemDTO {

    private int number;
    @XmlElement(required = true)
    private BottleShortDTO bottle;
    private CrateShortDTO crate;
    private int quantity;
    private URI href;

    public OrderItemDTO(){}

    public OrderItemDTO(final OrderItem orderItem, final URI baseUri){
        this.number=orderItem.getNumber();
        this.bottle= BottleShortDTO.marshall(orderItem.getBottle(),baseUri);
        this.crate = CrateShortDTO.marshall(orderItem.getCrate(),baseUri);
        this.quantity=orderItem.getQuantity();
    }

    public static List<OrderItemDTO> marshall(final List<OrderItem> orderItemsList, final URI baseUri) {
        final ArrayList<OrderItemDTO> orderItems = new ArrayList<>();
        for (final OrderItem orderItem : orderItemsList) {
            orderItems.add(new OrderItemDTO(orderItem, baseUri));
        }
        return orderItems;
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

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "number=" + number +
                ", bottle=" + bottle +
                ", crate=" + crate +
                ", quantity=" + quantity +
                ", href=" + href +
                '}';
    }

    public BottleShortDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleShortDTO bottle) {
        this.bottle = bottle;
    }

    public CrateShortDTO getCrate() {
        return crate;
    }

    public void setCrate(CrateShortDTO crate) {
        this.crate = crate;
    }
}
