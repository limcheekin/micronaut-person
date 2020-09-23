package micronaut.person;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @PositiveOrZero
    private int age;
    @NotNull
    private Gender gender;
    @JsonManagedReference
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Contact> contacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public List<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(List<Contact> contacts) {
        if (!contacts.isEmpty()) {
            contacts.forEach( c -> c.setPerson(this) );
        }
        this.contacts = contacts;
    }

    public void addToContacts(Contact contact) {
        contact.setPerson(this);
        this.contacts.add(contact);
    }

    public enum Gender {
        MALE, FEMALE;
    }
}