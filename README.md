# Beverage-Swagger
## JAX-RS REST API 

Implemented a beverage store as specified in the assignment description.
The server is available at `localhost:9998/v1` per default. 
This can be configured via `src/main/resources/config.properties`.
 
In addition, [Swagger UI](https://swagger.io/tools/swagger-ui/) is added to this project and you can access it at: [`http://localhost:9998/v1/swagger-ui/index.html?url=openapi.yaml`](http://localhost:9998/v1/swagger-ui/index.html?url=openapi.yaml) per default.

In this assignment we have consider to separate endpoints for the Bottle and Crate
So we can add,delete,update,get and even search bottle and crate separately.

We have made change in the model(OrderItem)
We have added Bottle and Crate object into order item,since anyone can place order with bottle and crate as beverage.
So there are corresponding changes in DTO and Order.

We have implemented the search for the Order:-
    i)We need minimum and maximum values of the price and according you get the orders from that range
    ii)You need to give the order id and you can see particular order.
    
The functionalities of the assignments are as follows:-
1)GET all orders
2)GET specific order
3)ADD(POST) order
4)MODIFY(UPDATE) order.
5)CANCEL(PUT) order.(Only submitted order can be cancelled.Processed order cannot be deleted.)
6)DELETE order.
7)GET orders within the given price range.
8)PAGINATION
Similarly for Bottle and Crate 
1)GET all 2)GET specific 3)POST 4)PUT 5)DELETE 6)PAGINATION
7)GET within range for the bottle.

Once the order is submitted the orderitems are iterated and calculate the order number of bottles and 
subtracted it from the no of bottles in stock.
Proper error message is provided if the no of bottles orders are more then the in stock.

Proper validation is provided for the parameters sent from the UI before it get used.

We have used openapi.yaml(swagger UI) for the front end.
Used Java REST API and JAX-RS specification.
Pagination is implemented for the objects.
JAX-RS annotations are used properly(@Path,@PathParam,@QueryParam,@Produces,@Consumes,@Context,@ApplicationPath)

Have used almost all HTTP verbs and implemented its working for the Business Logic.

insomnia_Beverage.json is also provided which we created for testing of our assignment.

We are used following schemas in this assignment:-
1)bottleDTO - has all the fields which is required for the bottle.
2)bottleShortDTO - has only few fields which need to show to user when they want to see order.Used inside order items.
3)bottlePostDTO - has the field which require to add bottle(since the server handles ids).
4)orderItemDTO - has all the fields which is required for the order item.
5)orderItemPostDTO - has the field which require to add order item(since the server handles ids).
6)crateDTO - has all the fields which is required for the crate.
7)crateShortDTO - has only few fields which need to show to user when they want to see order.Used inside order items.
8)cratePostDTO - has the field which require to add crate(since the server handles ids).
9)orderDTO - has all the fields which is required for the order.
10)orderPostDTO - has the field which require to add order(since the server handles ids).
11)errorMessage - used to display error message properly.
12)pagination - used to provide pagination to bottle,crate and orders.
