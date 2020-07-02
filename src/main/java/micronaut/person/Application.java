package micronaut.person;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(
    title = "Person Sample Application", 
    version = "1.0", description = "Sample API", 
    license = @License(name = "Apache 2.0", url = ""),
    contact = @Contact(url = "", name = "Lim Chee Kin", email = "limcheekin@vobject.com")))
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}