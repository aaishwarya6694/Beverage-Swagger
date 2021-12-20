package de.uniba.dsg.jaxrs;

import de.uniba.dsg.jaxrs.provider.BottleMessageBodyWriter;
import de.uniba.dsg.jaxrs.provider.CrateMessageBodyWriter;
import de.uniba.dsg.jaxrs.provider.OrderMessageBodyWriter;
import de.uniba.dsg.jaxrs.resources.*;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();
        // resources
        resources.add(OrderResource.class);
        resources.add(BeverageResource.class);
        resources.add(BottleResource.class);
        resources.add(CrateResource.class);
        // swagger-ui
        resources.add(SwaggerUI.class);
        // providers
        resources.add(OrderMessageBodyWriter.class);
        resources.add(BottleMessageBodyWriter.class);
        resources.add(CrateMessageBodyWriter.class);
        return resources;
    }
}
