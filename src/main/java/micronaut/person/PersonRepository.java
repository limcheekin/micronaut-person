package micronaut.person;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Page;
import io.micronaut.data.repository.PageableRepository;

@Repository
interface PersonRepository extends PageableRepository<Person, Long> {
    Page<Person> listOrderByLastName(Pageable pageable);
}
