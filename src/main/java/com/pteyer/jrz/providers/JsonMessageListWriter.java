package com.pteyer.jrz.providers;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

public class JsonMessageListWriter implements MessageBodyWriter<List<? extends Message>> {
    private static final Logger logger = LoggerFactory.getLogger(JsonMessageListWriter.class);
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public long getSize(final List<? extends Message> messages,
                        final Class<?> type, Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(final List<? extends Message> messages,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream outputStream) throws IOException, WebApplicationException {

        final StringBuffer buffer = new StringBuffer();
        if (messages != null && !messages.isEmpty()) {
            for (final Message message : messages) {
                JsonFormat.print(message, buffer);
            }
        }
        outputStream.write(buffer.toString().getBytes());
    }
}
