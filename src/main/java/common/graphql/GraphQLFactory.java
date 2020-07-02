package common.graphql;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.inject.qualifiers.Qualifiers;
import graphql.GraphQL;

@Factory
public class GraphQLFactory {

    public static final Logger logger = LoggerFactory.getLogger(GraphQLFactory.class);

    @Inject
    protected BeanContext beanContext;

    @Bean
    @Singleton
    public GraphQL graphQL() {
        GraphQLSchemaGenerator schemaGenerator = new GraphQLSchemaGenerator(); // 1

        Collection graphQLServices = beanContext.getBeansOfType(Object.class,
                Qualifiers.byStereotype(GraphQLService.class)); // 2

        if (graphQLServices.isEmpty()) { // 3
            logger.debug("No GraphQL services found, returning empty schema");
            return GraphQL.newGraphQL(GraphQLSchema.newSchema().build()).build();
        } else { // 4
            for (Object graphQLService : graphQLServices) {
                Class graphQLServiceClass = graphQLService.getClass();
                if (graphQLServiceClass.getSimpleName().contains("$Intercepted"))
                    graphQLServiceClass = graphQLServiceClass.getSuperclass(); // 5

                logger.debug("Registering GraphQL service: {}", graphQLServiceClass.getSimpleName());
                schemaGenerator.withOperationsFromSingleton(graphQLService, graphQLServiceClass); // 6
            }
        }

        return GraphQL.newGraphQL(schemaGenerator.generate()).build();
    }

}