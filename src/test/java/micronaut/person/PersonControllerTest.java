package micronaut.person;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class PersonControllerTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Test
    public void testAdd() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setAge(33);
        person.setGender(Person.Gender.MALE);

        try (RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class,
                embeddedServer.getURL())) {
            person = client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class);
            assertNotNull(person);
            assertEquals(Integer.valueOf(1), person.getId());
        }
    }

    @Test
    public void testAddNotValid() throws Exception {
        final Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setAge(-1);
        person.setGender(Person.Gender.MALE);

        try (RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class,
                embeddedServer.getURL())) {
            assertThrows(HttpClientResponseException.class,
                    () -> client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class),
                    "person.age: must be greater than or equal to 0");
        }
    }
}
