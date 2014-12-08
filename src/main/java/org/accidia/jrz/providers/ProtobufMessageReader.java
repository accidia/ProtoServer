package org.accidia.jrz.providers;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.accidia.jrz.misc.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Message body reader implementation for reading Message bytes
 */
@Provider
@Consumes(MediaTypes.APPLICATION_PROTOBUF)
public class ProtobufMessageReader implements MessageBodyReader<Message> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
                            final InputStream inputStream) throws IOException, WebApplicationException {
        try {
            final Method newBuilder = type.getMethod("newBuilder");
            final GeneratedMessage.Builder builder = (GeneratedMessage.Builder) newBuilder.invoke(type);
            return builder.mergeFrom(inputStream).buildPartial();
        } catch (Exception e) {
            logger.error("exception caught on protobuf message body reader -> rethrowing ", e);
            throw new WebApplicationException("exception caught on protobuf message body reader", e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

