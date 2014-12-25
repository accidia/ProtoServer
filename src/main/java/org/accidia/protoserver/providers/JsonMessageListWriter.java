package org.accidia.protoserver.providers;

import com.google.common.base.Charsets;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Message body writer implementation for objects of type List<Message> to Json
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonMessageListWriter implements MessageBodyWriter<List<Message>> {
    private static final Logger logger = LoggerFactory.getLogger(JsonMessageListWriter.class);

    @Override
    public boolean isWriteable(final Class<?> type,
                               final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public long getSize(final List<Message> messages,
                        final Class<?> type, Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        try {
            return messageListToString(messages).length();
        } catch (IOException e) {
            logger.warn("exception caught while calculating message size: ", e);
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void writeTo(final List<Message> messages,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(messageListToString(messages).getBytes(Charsets.UTF_8));
    }

    protected String messageListToString(final List<Message> messages) throws IOException {
        if (messages == null || messages.isEmpty()) {
            return "[]";
        }

        final StringBuilder stringBuilder = new StringBuilder("[");
        boolean first = true;
        for (final Message message : messages) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(",");
            }
            JsonFormat.print(message, stringBuilder);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
