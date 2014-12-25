package org.accidia.protoserver.providers;

import com.google.protobuf.Message;
import org.accidia.protoserver.misc.MediaTypes;

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
@Produces(MediaTypes.APPLICATION_PROTOBUF)
public class ProtobufMessageWriter implements MessageBodyWriter<Message> {

    @Override
    public boolean isWriteable(final Class<?> type,
                               final Type genericType,
                               final Annotation[] annotations,
                               final MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(final Message message,
                        final Class<?> type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType) {
        return message.getSerializedSize();
    }

    @Override
    public void writeTo(final Message message,
                        final Class type,
                        final Type genericType,
                        final Annotation[] annotations,
                        final MediaType mediaType,
                        final MultivaluedMap httpHeaders,
                        final OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(message.toByteArray());
    }
}
