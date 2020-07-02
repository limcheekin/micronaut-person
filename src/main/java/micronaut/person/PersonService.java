package micronaut.person;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import common.graphql.GraphQLService;

@GraphQLService
public class PersonService {
    List<Person> people = new ArrayList<>();

    public Person add(Person person) {
        person.setId(people.size() + 1);
        people.add(person);
        return person;
    }

    @GraphQLQuery
    public Optional<Person> findById(@GraphQLArgument(name = "id") Integer id) {
        return people.stream().filter(it -> it.getId().equals(id)).findFirst();
    }

    public List<Person> findAll(Integer max, Integer offset) {
        return people.stream().skip(offset == null ? 0 : offset).limit(max == null ? 10000 : max)
                .collect(Collectors.toList());
    }
}