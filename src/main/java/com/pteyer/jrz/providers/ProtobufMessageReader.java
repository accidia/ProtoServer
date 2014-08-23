package com.pteyer.jrz.providers;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Provider
@Consumes("application/x-protobuf")
public class ProtobufMessageReader implements MessageBodyReader<Message> {
    @Override
    public boolean isReadable(final Class<?> type,
                              final Type genericType,
                              final Annotation[] annotations,
                              final MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public Message readFrom(final Class<Message> type,
                            final Type genericType,
                            final Annotation[] annotations,
                            final MediaType mediaType,
                            final MultivaluedMap<String, String> httpHeaders,
                            final InputStream entityStream) throws IOException, WebApplicationException {
        try {
            final Method newBuilder = type.getMethod("newBuilder");
            final GeneratedMessage.Builder builder = (GeneratedMessage.Builder) newBuilder.invoke(type);
            return builder.mergeFrom(entityStream).build();
        } catch (Exception e) {
            throw new WebApplicationException("caught an exception", e);
        }
    }
}

