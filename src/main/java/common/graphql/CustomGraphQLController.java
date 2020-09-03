package common.graphql;

import io.micronaut.configuration.graphql.GraphQLController;
import io.micronaut.configuration.graphql.GraphQLExecutionResultHandler;
import io.micronaut.configuration.graphql.GraphQLInvocation;
import io.micronaut.configuration.graphql.GraphQLJsonSerializer;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;

import javax.annotation.Nullable;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Please the details of workaround at the following URL:
 * https://github.com/micronaut-projects/micronaut-aws/issues/93
 */

@Controller("/v1")
public class CustomGraphQLController extends GraphQLController {

    private GraphQLJsonSerializer graphQLJsonSerializer;
    private static final Logger LOG = LoggerFactory.getLogger(CustomGraphQLController.class);

    public CustomGraphQLController(GraphQLInvocation graphQLInvocation,
            GraphQLExecutionResultHandler graphQLExecutionResultHandler, 
            GraphQLJsonSerializer graphQLJsonSerializer) {
        super(graphQLInvocation, graphQLExecutionResultHandler, graphQLJsonSerializer);
        this.graphQLJsonSerializer = graphQLJsonSerializer;
    }

    @Post(value = "graphql", 
          produces = MediaType.APPLICATION_JSON, 
          consumes = MediaType.APPLICATION_GRAPHQL)
    public Publisher<String> post(
            @Nullable @QueryValue String query, 
            @Nullable @QueryValue String operationName,
            @Nullable @QueryValue String variables, 
            @Nullable @Body String body,
            HttpRequest httpRequest) {
        LOG.info("Handling graphQL Request : {}", body);
        return super.post(query, operationName, variables, body, httpRequest);
    }
}