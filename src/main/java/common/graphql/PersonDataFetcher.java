package common.graphql;

import graphql.schema.DataFetcher;
import micronaut.person.Person;
import micronaut.person.PersonPage;
import micronaut.person.PersonService;

import javax.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PersonDataFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(PersonDataFetcher.class);

    private final PersonService personService;

    public PersonDataFetcher(PersonService personService) {
        this.personService = personService;
    }

    public DataFetcher<Optional<Person>> findById() {
        return env -> {
            Long id = env.getArgument("id");
            LOG.info("get(id = {})", id);
            return personService.findById(id);
        };
    }

    public DataFetcher<PersonPage> listOrderByLastName() {
        return env -> {
            int page = env.getArgument("page");
            int size = env.getArgument("size");
            LOG.info("listOrderByLastName(page = {}, size = {})", page, size);
            return personService.listOrderByLastName(page, size);
        };
    }
}