package micronaut.person;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Post
    public Person add(@Body @Valid Person person) {
        return personService.add(person);
    }

    @Get("/{id:4}")
    public Optional<Person> findById(@NotNull Long id) {
        return personService.findById(id);
    }
}