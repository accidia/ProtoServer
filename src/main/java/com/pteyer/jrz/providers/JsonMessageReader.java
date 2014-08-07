package com.pteyer.jrz.providers;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JsonMessageReader implements MessageBodyReader<Message> {
    private static final Logger logger = LoggerFactory.getLogger(JsonMessageReader.class);

    public static final String LS = System.getProperty("line.separator");
    private final ExtensionRegistry extensionRegistry = ExtensionRegistry.newInstance();

    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Message readFrom(final Class type,
                           final Type genericType,
                           final Annotation[] annotations,
                           final MediaType mediaType,
                           final MultivaluedMap httpHeaders,
                           final InputStream entityStream) throws IOException, WebApplicationException {
        try {
            final Method newBuilder = type.getMethod("newBuilder");
            final GeneratedMessage.Builder builder = (GeneratedMessage.Builder) newBuilder.invoke(type);
            final String data = convertInputStreamToString(entityStream);
            JsonFormat.merge(data, extensionRegistry, builder);
            return builder.build();
        } catch (Exception e) {
            logger.warn("caught an exception", e);
            throw new WebApplicationException(e);
        }
    }

    private String convertInputStreamToString(InputStream io) {
        final StringBuilder sb = new StringBuilder();
        try {
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(io));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append(LS);
                line = reader.readLine();
            }
        } catch (IOException e) {
            logger.warn("caught an exception", e);
            throw new RuntimeException("cannot get inputstream", e);
        }
        return sb.toString();
    }
}
