package micronaut.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PersonService {
    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person add(Person person) {
        return repository.save(person);
    }

    public Optional<Person> findById(Long id) {
        LOG.info("id {}", id);
        return repository.findById(id);
    }
}