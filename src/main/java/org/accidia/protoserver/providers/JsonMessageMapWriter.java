package org.accidia.protoserver.providers;

import com.google.common.base.Charsets;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonMessageMapWriter implements MessageBodyWriter<Map<String, Message>> {
    @Override
    public boolean isWriteable(final Class<?> type,
                               final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public long getSize(final Map<String, Message> map,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        return mapToString(map).length();
    }

    @Override
    public void writeTo(final Map<String, Message> map,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(mapToString(map).getBytes(Charsets.UTF_8));
    }

    protected String mapToString(final Map<String, Message> map) {
        final StringBuilder builder = new StringBuilder("{");
        String comma = "";
        for (final Map.Entry<String, Message> entry : map.entrySet()) {
            builder.append(comma);
            builder.append("\"").append(entry.getKey()).append("\": ")
                    .append(JsonFormat.printToString(entry.getValue()));
            comma = ",";
        }
        builder.append("}");
        return builder.toString();
    }
}
