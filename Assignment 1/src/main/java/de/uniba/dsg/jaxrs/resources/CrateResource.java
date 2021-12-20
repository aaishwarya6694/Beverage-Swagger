package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.CrateService;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.api.PaginatedCrates;
import de.uniba.dsg.jaxrs.model.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.dto.CratePostDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("crates")
public class CrateResource implements BeverageResource{
    private static final Logger logger = Logger.getLogger("CrateResource");

    @GET
    @Path("{crateId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getCrate(@Context final UriInfo info, @PathParam("crateId") final int id) {
        logger.info("Get crate with id " + id);
        final Crate crate = CrateService.instance.getCrate(id);

        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new CrateDTO(crate , info.getBaseUri())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getBeverages(@Context UriInfo info,@QueryParam("pageLimit") @DefaultValue("10") int pageLimit,@QueryParam("page") @DefaultValue("1") int page) {
        logger.info("Get all crates. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

        // Parameter validation
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Crate> helper = new PaginationHelper<>(CrateService.instance.getBeverages());
        final PaginatedCrates response = new PaginatedCrates(helper.getPagination(info, page, pageLimit), CrateDTO.marshallPagination(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @POST
    public Response createCrate(final CratePostDTO newCrate, @Context final UriInfo uriInfo) {
        logger.info("Create crate " + newCrate);
        if (newCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        final Crate crate = newCrate.unmarshall();
        String validationResult = parameterValidation(crate);
        if(validationResult.equalsIgnoreCase("success")){
            CrateService.instance.addCrate(crate);

            return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(CrateResource.class).path(CrateResource.class, "getCrate").build(crate.getId())).build();
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,validationResult)).build();
        }
    }

    @PUT
    @Path("{crateId}")
    public Response updateCrate(@Context final UriInfo uriInfo, @PathParam("crateId") final int id, final CratePostDTO updatedCrate) {
        logger.info("Update crate " + updatedCrate);
        if (updatedCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Crate crate = CrateService.instance.getCrate(id);

        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String validationResult = parameterValidation(crate);
        if(validationResult.equalsIgnoreCase("success")){
            final Crate resultCrate = CrateService.instance.updateCrate(id, updatedCrate.unmarshall());

            return Response.ok().entity(new CrateDTO(resultCrate, uriInfo.getBaseUri())).build();
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER,validationResult)).build();
        }
    }

    @DELETE
    @Path("{crateId}")
    public Response deleteSpecificCrate(@PathParam("crateId") final int id) {
        logger.info("Delete Crate with id " + id);
        final boolean deleted = CrateService.instance.deleteCrate(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }

    private String parameterValidation(Crate crate){
        String success="success";

        if(crate.getBottle()==null)
            return "Crate should have a bottle";
        if(crate.getNoOfBottles() == 0)
            return "Number of bottles cannot be 0";
        if(crate.getPrice()==0)
            return "Crate Price cannot be 0";
        if(crate.getBottle().getName().equalsIgnoreCase("string"))
            return "Please enter bottle name";
        if(crate.getBottle().getInStock()==0)
            return "Value of in stock cannot be 0";
        if (crate.getBottle().getPrice()==0)
            return "Bottle Price cannot be 0";
        if(crate.getBottle().getSupplier().equalsIgnoreCase("string"))
            return "Please enter supplier name";
        if(crate.getBottle().isAlcoholic())
            if(crate.getBottle().getVolumePercent()==0)
                return "Please enter alcohol volume percent";

        return success;
    }
}
