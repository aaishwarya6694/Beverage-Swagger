package de.uniba.dsg.jaxrs.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public interface BeverageResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeverages(@Context final UriInfo info,
                          @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                          @QueryParam("page") @DefaultValue("1") final int page);
}
