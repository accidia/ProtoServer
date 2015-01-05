package org.accidia.protoserver.providers;

import com.google.common.io.ByteStreams;
import com.google.protobuf.Message;
import org.accidia.protoserver.misc.MediaTypes;
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
import java.lang.reflect.Type;

@Provider
@Consumes(MediaTypes.APPLICATION_PROTOBUF)
public class BinaryMessageReader implements MessageBodyReader<byte[]> {
    private static final Logger logger = LoggerFactory.getLogger(BinaryMessageReader.class);

    @Override
    public boolean isReadable(final Class<?> type,
                              final Type genericType,
                              final Annotation[] annotations,
                              final MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public byte[] readFrom(final Class<byte[]> type,
                                 final Type genericType,
                                 final Annotation[] annotations,
                                 final MediaType mediaType,
                                 final MultivaluedMap<String, String> httpHeaders,
                                 final InputStream inputStream) throws IOException, WebApplicationException {
        return ByteStreams.toByteArray(inputStream);
    }
}
