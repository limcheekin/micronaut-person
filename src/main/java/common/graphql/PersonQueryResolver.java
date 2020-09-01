package common.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import micronaut.person.Person;
import micronaut.person.PersonService;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@SuppressWarnings("Duplicates")
public class PersonQueryResolver implements GraphQLQueryResolver {
    private static final Logger LOG = LoggerFactory.getLogger(PersonQueryResolver.class);

    private final PersonService personService;

    public PersonQueryResolver(PersonService personService) {
        this.personService = personService;
    }

    public Optional<Person> findById(Integer id) {
        LOG.info("findById(id = {})", id);
        return personService.findById(id);
    }

    public List<Person> findAll(Integer max, Integer offset) {
        return personService.findAll(max, offset);
    }
}
