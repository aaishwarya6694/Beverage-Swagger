package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderStatus;
import de.uniba.dsg.jaxrs.resources.*;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = {"id", "orderItems", "price", "status", "href"})
public class OrderDTO {

    private int id;
    @XmlElement(required = true)
    private List<OrderItemDTO> orderItems;
    private double price;
    private OrderStatus status;
    private URI href;

    public OrderDTO(){

    }

    public OrderDTO (final Order order, final URI baseUri){
        this.id=order.getOrderId();
        this.orderItems=OrderItemDTO.marshall(order.getOrderItems(),baseUri);
        this.price=order.getPrice();
        this.status=order.getStatus();
        this.href = UriBuilder.fromUri(baseUri).path(OrderResource.class).path(OrderResource.class, "getOrder").build(this.id);
    }

    public static List<OrderDTO> marshall(final List<Order> orderList, final URI baseUri) {
        final ArrayList<OrderDTO> orders = new ArrayList<>();
        for (final Order order : orderList) {
            orders.add(new OrderDTO(order, baseUri));
        }
        return orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", price=" + price +
                ", status=" + status +
                ", href=" + href +
                '}';
    }
}
