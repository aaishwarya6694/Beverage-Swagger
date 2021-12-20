package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BottleService;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.api.PaginatedBottles;
import de.uniba.dsg.jaxrs.model.dto.BottleDTO;
import de.uniba.dsg.jaxrs.model.dto.BottlePostDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@Path("bottles")
public class BottleResource implements BeverageResource {
    private static final Logger logger = Logger.getLogger("BottleResource");

    @GET
    @Path("{bottleId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(@Context final UriInfo info, @PathParam("bottleId") final int id) {
        logger.info("Get bottle with id " + id);
        final Bottle bottle = BottleService.instance.getBottle(id);
        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new BottleDTO(bottle, info.getBaseUri())).build();
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMinAndMaxBottle(@Context final UriInfo info,@QueryParam("min")final double min,@QueryParam("max")final double max)
    {
        logger.info("Get bottle with mininum price:" + min +" and maximum price:"+max);
        if(min< 0)
        {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "Minimum value of range cannot be less than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        else if(max<0)
        {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "Maximum value of range cannot be less than 0");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        else if(min> max)
        {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "Minimum value of range should be less than maximum value");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        List<BottleDTO> resultList = BottleDTO.marshallPagination(BottleService.instance.getMinAndMaxPriceBeverages(min,max), info.getBaseUri());
        if(resultList.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(new GenericEntity<List<BottleDTO>>(resultList){}).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getBeverages(@Context UriInfo info,@QueryParam("pageLimit") @DefaultValue("10") int pageLimit,@QueryParam("page") @DefaultValue("1") int page) {
        logger.info("Get all bottles. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

        // Parameter validation
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Bottle> helper = new PaginationHelper<>(BottleService.instance.getBeverages());
        final PaginatedBottles response = new PaginatedBottles(helper.getPagination(info, page, pageLimit), BottleDTO.marshallPagination(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @POST
    public Response createBottle(final BottlePostDTO newBottle, @Context final UriInfo uriInfo) {
        logger.info("Create Bottle " + newBottle);
        if (newBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        final Bottle bottle = newBottle.unmarshall();
        String validationResult = parameterValidation(bottle);
        if(validationResult.equalsIgnoreCase("success"))
        {
            BottleService.instance.addBottle(bottle);
            return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(BottleResource.class).path(BottleResource.class, "getBottle").build(bottle.getId())).build();
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,validationResult)).build();
        }
    }

    @PUT
    @Path("{bottle-id}")
    public Response updateBottle(@Context final UriInfo uriInfo, @PathParam("bottle-id") final int id, final BottlePostDTO updatedBottle) {
        logger.info("Update bottle " + updatedBottle);
        if (updatedBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Bottle bottle = BottleService.instance.getBottle(id);

        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String validationResult = parameterValidation(bottle);
        if(validationResult.equalsIgnoreCase("success")){
            final Bottle resultBottle = BottleService.instance.updateBottle(id, updatedBottle.unmarshall());
            return Response.ok().entity(new BottleDTO(resultBottle, uriInfo.getBaseUri())).build();
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,validationResult)).build();
        }
    }

    @DELETE
    @Path("{bottleId}")
    public Response deleteSpecificBottle(@PathParam("bottleId") final int id) {
        logger.info("Delete bottle with id " + id);
        final boolean deleted = BottleService.instance.deleteBottle(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }

    private String parameterValidation(Bottle bottle){
        String success="success";
        if(bottle.getName().equalsIgnoreCase("string"))
            return "Please enter bottle name.";
        if(bottle.getSupplier().equalsIgnoreCase("string"))
            return "Please enter supplier name";
        if(bottle.isAlcoholic())
            if(bottle.getVolumePercent()==0)
                return "Please enter alcohol volume percent";
        if(bottle.getPrice()==0)
            return "Please enter bottle price";
        if(bottle.getInStock()==0)
            return "Please enter bottles in stock";
        return success;
    }
}
