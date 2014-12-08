package org.accidia.jrz.providers;

import com.google.protobuf.Message;
import org.accidia.jrz.misc.MediaTypes;

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
import java.util.List;

@Provider
@Produces(MediaTypes.APPLICATION_PROTOBUF)
public class ProtobufMessageListWriter implements MessageBodyWriter<List<Message>> {

    @Override
    public boolean isWriteable(final Class<?> type,
                               final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return MediaTypes.APPLICATION_PROTOBUF.equals(mediaType.toString().trim().toLowerCase());
    }

    @Override
    public long getSize(final List<Message> messages,
                        final Class<?> clazz,
                        final Type type,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        long size = 0;
        for (final Message message : messages) {
            size += message.getSerializedSize();
        }
        return size;
    }

    @Override
    public void writeTo(final List<Message> messages,
                        final Class<?> clazz,
                        final Type type,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap<String, Object> stringObjectMultivaluedMap,
                        final OutputStream outputStream) throws IOException, WebApplicationException {
        for (final Message message : messages) {
            outputStream.write(message.toByteArray());
        }
    }
}
