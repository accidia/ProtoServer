package org.accidia.jrz.misc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

public class AsyncResponses {
    public static final Logger logger = LoggerFactory.getLogger(AsyncResponses.class);

    public static void addTimeoutHandler(final AsyncResponse asyncResponse, long timeout, final TimeUnit timeUnit) {
        logger.debug("addTimeoutHandler(asyncResponse)");
        checkArgument(asyncResponse != null, "null asyncResponse");
        checkArgument(timeUnit != null, "null timeUnit");

        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(final AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.REQUEST_TIMEOUT).entity("operation timeout").build());
            }
        });
        asyncResponse.setTimeout(timeout, timeUnit);
    }

    public static void addCompletionCallback(final AsyncResponse asyncResponse) {
        logger.debug("addCompletionCallback(asyncResponse)");
        checkArgument(asyncResponse != null, "null asyncResponse");

        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(final Throwable throwable) {
                if (throwable == null) {
                    logger.debug("completed");
                } else {
                    logger.warn("exception thrown: ", throwable);
                }
            }
        });
    }

    public static <ProtobufType extends Message> void addCallbackForListenableFuture(final AsyncResponse asyncResponse,
                                                                                     final ListenableFuture<ProtobufType> future) {
        logger.debug("addCallbackForListenableFuture(asyncResponse,future)");
        checkArgument(asyncResponse != null, "null asyncResponse");
        checkArgument(future != null, "null future");

        Futures.addCallback(future, new FutureCallback<ProtobufType>() {
            @Override
            public void onSuccess(final Message message) {
                asyncResponse.resume(message);
            }

            @Override
            public void onFailure(final Throwable throwable) {
                asyncResponse.resume(throwable);
            }
        });
    }

    public static <ProtobufType extends Message> void addCallbackForListenableFutureList(final AsyncResponse asyncResponse,
                                                                                     final ListenableFuture<List<ProtobufType>> future) {
        logger.debug("addCallbackForListenableFuture(asyncResponse,future)");
        checkArgument(asyncResponse != null, "null asyncResponse");
        checkArgument(future != null, "null future");

        Futures.addCallback(future, new FutureCallback<List<ProtobufType>>() {
            @Override
            public void onSuccess(final List<ProtobufType> messages) {
                asyncResponse.resume(messages);
            }

            @Override
            public void onFailure(final Throwable throwable) {
                asyncResponse.resume(throwable);
            }
        });
    }
}

