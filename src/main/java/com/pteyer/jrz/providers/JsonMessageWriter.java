package com.pteyer.jrz.providers;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(JsonMessageWriter.class);

    @Override
    public long getSize(final Message arg0,
                        final Class arg1,
                        final Type arg2,
                        final Annotation[] arg3,
                        final MediaType arg4) {
        return -1;
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
                        final Class arg1,
                        final Type arg2,
                        final Annotation[] arg3,
                        final MediaType arg4,
                        final MultivaluedMap arg5,
                        final OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(JsonFormat.printToString(message).getBytes());
    }
}


