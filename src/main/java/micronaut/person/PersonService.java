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
    List<Person> people = new ArrayList<>();

    public Person add(Person person) {
        person.setId(people.size() + 1);
        people.add(person);
        return person;
    }

    public Optional<Person> findById(Integer id) {
        LOG.info("people.size() {}", people.size());
        return people.stream().filter(it -> it.getId() == id).findFirst();
    }

    public List<Person> findAll(Integer max, Integer offset) {
        return people.stream().skip(offset == null ? 0 : offset).limit(max == null ? 10000 : max)
                .collect(Collectors.toList());
    }
}