package com.pteyer.jrz.providers;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
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
            final String data = CharStreams.toString(new InputStreamReader(entityStream, Charsets.UTF_8));
            JsonFormat.merge(data, this.extensionRegistry, builder);
            return builder.buildPartial();
        } catch (Exception e) {
            logger.warn("caught an exception", e);
            throw new WebApplicationException(e);
        }
    }
}
