package de.uniba.dsg.jaxrs.provider;

import de.uniba.dsg.jaxrs.model.dto.CrateDTO;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Produces(MediaType.TEXT_PLAIN)
public class CrateMessageBodyWriter implements MessageBodyWriter<CrateDTO>  {
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return type == CrateDTO.class;
    }

    @Override
    public long getSize(final CrateDTO order, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return -1;
    }

    @Override
    public void writeTo(final CrateDTO crate, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(crate.toString().getBytes());
        entityStream.flush();
    }
}
