package de.uniba.dsg.jaxrs.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import de.uniba.dsg.jaxrs.controller.OrderService;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;
import de.uniba.dsg.jaxrs.model.OrderStatus;
import de.uniba.dsg.jaxrs.model.dto.OrderDTO;
import de.uniba.dsg.jaxrs.model.dto.OrderPostDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.api.PaginatedOrders;
import java.util.List;
import java.util.logging.Logger;

@Path("orders")
public class OrderResource {

    private static final Logger logger = Logger.getLogger("OrderResource");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@Context final UriInfo info,
                            @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                            @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all orders. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

        // Parameter validation
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Order> helper = new PaginationHelper<>(OrderService.instance.getAllOrders());
        final PaginatedOrders response = new PaginatedOrders(helper.getPagination(info, page, pageLimit), OrderDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getOrder(@Context final UriInfo info, @PathParam("orderId") final int id) {
        logger.info("Get order with id " + id);
        final Order order = OrderService.instance.getOrder(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new OrderDTO(order , info.getBaseUri())).build();
    }

    @POST
    public Response createOrder(final OrderPostDTO newOrder, @Context final UriInfo uriInfo) {
        logger.info("Create order " + newOrder);
        if (newOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        final Order order = newOrder.unmarshall();

        String validationResult = parameterValidation(order);

        if(validationResult.equalsIgnoreCase("success"))
        {
            String orderResult=OrderService.instance.addOrder(order);
            if(orderResult.equalsIgnoreCase("success"))
                return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderResource.class).path(OrderResource.class, "getOrder").build(order.getOrderId())).build();
            else
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.ORDER_PROCESSING_FAILED,orderResult)).build();
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,validationResult)).build();
        }
    }

    @PUT
    @Path("{orderId}")
    public Response updateOrder(@Context final UriInfo uriInfo, @PathParam("orderId") final int id, final OrderPostDTO updatedOrder) {
        logger.info("Update order " + updatedOrder);
        if (updatedOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        final Order order = OrderService.instance.getOrder(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else if(order.getStatus().equals(OrderStatus.PROCESSED)){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorMessage(ErrorType.ORDER_ALREADY_PROCESSED,"Order is already processed,you cannot update this order")).build();
        }
        else{
            String validationResult = parameterValidation(order);

            if(validationResult.equalsIgnoreCase("success")){
                final Order resultOrder = OrderService.instance.updateOrder(id, updatedOrder.unmarshall());
                return Response.ok().entity(new OrderDTO(resultOrder, uriInfo.getBaseUri())).build();
            }
            else
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,validationResult)).build();
            }
        }

    }

    @PUT
    @Path("cancel/{orderId}")
    public Response cancelOrder(@Context final UriInfo uriInfo, @PathParam("orderId") final int id) {
        logger.info("Cancel order with id:" + id);
        final Order order = OrderService.instance.getOrder(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else if(order.getStatus().equals(OrderStatus.PROCESSED)){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorMessage(ErrorType.ORDER_ALREADY_PROCESSED,"Order is already processed,you cannot cancel this order")).build();
        }
        else
        {
            OrderService.instance.cancelOrder(id);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{orderID}")
    public Response deleteSpecificOrder(@PathParam("orderID") final int id) {
        logger.info("Delete order with id " + id);
        final boolean deleted = OrderService.instance.deleteOrder(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMinAndMaxBottle(@Context final UriInfo info,@QueryParam("min")final double minPrice,@QueryParam("max")final double maxPrice)
    {
        logger.info("Get order with mininum price:" + minPrice +" and maximum price:"+maxPrice);
        if(minPrice< 0)
        {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "Minimum value of range cannot be less than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        else if(maxPrice<0)
        {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "Maximum value of range cannot be less than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        else if(minPrice> maxPrice)
        {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "Minimum value of range should be less than maximum value");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        List<OrderDTO> resultList =OrderDTO.marshall(OrderService.instance.getOrderBetweenRange(minPrice,maxPrice), info.getBaseUri());
        if(resultList.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(new GenericEntity<List<OrderDTO>>(resultList) {}).build();
    }

    private String parameterValidation(Order neworder){
        String success="success";
        if(neworder.getPrice()==0)
            return "Order Price cannot be zero(0)";
        if(neworder.getOrderItems().size()==0)
            return "Orders cannot be without order items";
        for(OrderItem orderItem:neworder.getOrderItems())
        {
            if(orderItem.getCrate().getBottle()==null)
                return "Crate should have a bottle";
            if(orderItem.getCrate().getNoOfBottles() == 0)
                return "Number of bottles cannot be 0";
            if(orderItem.getCrate().getPrice()==0)
                return "Crate Price cannot be 0";
            if(orderItem.getCrate().getBottle().getName().equalsIgnoreCase("string"))
                return "Please enter bottle name";
            if(orderItem.getCrate().getBottle().getInStock()==0)
                return "Value of in stock cannot be 0";
            if (orderItem.getCrate().getBottle().getPrice()==0)
                return "Bottle Price cannot be 0";
            if(orderItem.getCrate().getBottle().getSupplier().equalsIgnoreCase("string"))
                return "Please enter supplier name";
            if(orderItem.getCrate().getBottle().isAlcoholic())
                if(orderItem.getCrate().getBottle().getVolumePercent()==0)
                    return "Please enter alcohol volume percent";
        }
        return success;
    }
}

