package micronaut.person;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonControllerTest {
    private static MicronautLambdaHandler handler;
    private static Context lambdaContext = new MockLambdaContext();
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setupSpec() {
        try {
            handler = new MicronautLambdaHandler();
            objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);

        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanupSpec() {
        handler.getApplicationContext().close();
    }

    private AwsProxyRequest postRequest(String path, Object body) throws JsonProcessingException {
        String json = (body instanceof String) ? ((String) body) : objectMapper.writeValueAsString(body);
        return new AwsProxyRequestBuilder(path, HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(json)
                .build();
    }

    private Person validPerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setAge(33);
        person.setGender(Person.Gender.MALE);
        return person;
    }

    @Test
    public void testAdd() throws Exception {
        Person person = validPerson();
        AwsProxyRequest request = postRequest("/people", person);
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        Assertions.assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
        person = objectMapper.readValue(response.getBody(), Person.class);
        assertNotNull(person);
        assertEquals(Integer.valueOf(1), person.getId());

        request = postRequest("/graphql", "{\"query\":\"{ findById(id: 1) { lastName firstName gender } }\" }");
        response = handler.handleRequest(request, lambdaContext);
        Assertions.assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
        assertEquals("{\"data\":{\"findById\":{\"lastName\":\"Smith\",\"firstName\":\"John\",\"gender\":\"MALE\"}}}", response.getBody());
    }

    @Test
    public void testAddNotValid() throws Exception {
        Person person = validPerson();
        person.setAge(-1);
        AwsProxyRequest request = postRequest("/people", person);
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        assertEquals(HttpStatus.BAD_REQUEST.getCode(), response.getStatusCode());
        assertTrue(response.getBody().contains("person.age: must be greater than or equal to 0"));
    }
}
