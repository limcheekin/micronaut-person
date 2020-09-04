package common.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import micronaut.person.Person;
import micronaut.person.PersonService;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PersonDataFetcher implements DataFetcher<Optional<Person>> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonDataFetcher.class);

    private final PersonService personService;

    public PersonDataFetcher(PersonService personService) {
        this.personService = personService;
    }

    public Optional<Person> get(DataFetchingEnvironment env) {
        Long id = env.getArgument("id");
        LOG.info("get(id = {})", id);
        return personService.findById(id);
    }
}