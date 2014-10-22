package com.pteyer.jrz.providers;

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

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonMessageWriter implements MessageBodyWriter<Message> {

    @Override
    public long getSize(final Message message,
                        final Class clazz,
                        final Type type,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        return JsonFormat.printToString(message).length();
    }

    @Override
    public boolean isWriteable(final Class clazz,
                               final Type type,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public void writeTo(final Message message,
                        final Class clazz,
                        final Type type,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap multivaluedMap,
                        final OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(JsonFormat.printToString(message).getBytes());
    }
}


