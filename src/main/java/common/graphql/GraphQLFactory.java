package common.graphql;

import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.kickstart.tools.SchemaParserBuilder;
import graphql.schema.GraphQLSchema;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
@SuppressWarnings("Duplicates")
public class GraphQLFactory {

    @Bean
    @Singleton
    public GraphQL graphQL(PersonQueryResolver personQueryResolver) {

        // Parse the schema.
        SchemaParserBuilder builder = SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(personQueryResolver);

        // Create the executable schema.
        GraphQLSchema graphQLSchema = builder.build().makeExecutableSchema();

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
