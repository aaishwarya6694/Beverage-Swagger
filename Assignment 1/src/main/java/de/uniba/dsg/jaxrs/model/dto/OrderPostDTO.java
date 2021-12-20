package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;
import de.uniba.dsg.jaxrs.model.OrderStatus;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = {"id", "orderItems", "price", "status"})
public class OrderPostDTO {
    @XmlElement(required = true)
    private List<OrderItemPostDTO> orderItems;
    private double price;
    private OrderStatus status;

    public List<OrderItemPostDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemPostDTO> orderItems) {
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

    @Override
    public String toString() {
        return "OrderPostDTO{" +
                "orderItems=" + orderItems +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

    public Order unmarshall(){
        List<OrderItem> orderItemDTOList = new ArrayList<>();
        for(final OrderItemPostDTO orderItemDTO:this.orderItems){
                orderItemDTOList.add(orderItemDTO.unmarshall());
        }
        return new Order(0,orderItemDTOList,this.price,this.status);
    }
}
