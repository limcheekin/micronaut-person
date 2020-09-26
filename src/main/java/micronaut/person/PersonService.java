package micronaut.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

@Singleton
public class PersonService {
    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository repository;
    private static final int MAX_PAGE_SIZE = 100;

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

    public PersonPage listOrderByLastName(int page, int size) {
        size = Math.min(size, MAX_PAGE_SIZE);
        Page<Person> results = repository.listOrderByLastName(Pageable.from(page, size));
        return new PersonPage(results.getContent(), results.getTotalSize());
    }
}