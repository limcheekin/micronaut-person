package micronaut.person;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/people")
public class PersonController {
    @Inject
    private PersonService personService;

    @Post
    public Person add(@Body @Valid Person person) {
        return personService.add(person);
    }

    @Get("/{id:4}")
    public Optional<Person> findById(@NotNull Integer id) {
        return personService.findById(id);
    }

    @Get("{?max,offset}")
    public List<Person> findAll(@Nullable Integer max, @Nullable Integer offset) {
        return personService.findAll(max, offset);
    }
}