package micronaut.person;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.function.aws.proxy.MicronautLambdaContainerHandler;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static final Logger LOG = LoggerFactory.getLogger(StreamLambdaHandler.class);

    private MicronautLambdaContainerHandler handler;

    public StreamLambdaHandler() {
        try {
            handler = new MicronautLambdaContainerHandler(); 
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            LOG.error("StreamLambdaHandler()", e);
            throw new RuntimeException("Could not initialize Micronaut", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LOG.info("invoked handleRequest()");
        try {
            //LOG.info("invoked handleRequest():\n{}", inputStreamToString(inputStream));
            handler.proxyStream(inputStream, outputStream, context);
        } catch (IOException e) {
            LOG.error("handleRequest()", e);
            throw e;
        }
    }
}