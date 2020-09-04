package micronaut.person;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/people")
@Validated
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