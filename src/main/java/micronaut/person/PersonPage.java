package micronaut.person;

import java.util.List;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class PersonPage {
    private List<Person> people;
    private long total;

    public PersonPage() {
    }

    public PersonPage(List<Person> people, long total) {
        this.people = people;
        this.total = total;
    }

    public List<Person> getPeople() {
        return this.people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" + " people='" + people + "'" + ", total='" + total + "'" + "}";
    }    
}
