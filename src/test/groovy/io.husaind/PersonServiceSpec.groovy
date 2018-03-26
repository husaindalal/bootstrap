package io.husaind

import io.husaind.dto.Person
import io.husaind.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = Application.class)

class PersonServiceSpec extends Specification {

    @Autowired
    PersonService personService

    def "Person Create, Update, Get, Delete - Happy path "() {
        given: "init person"
        Person person = Person.builder()
                .firstName("Test-first")
                .lastName("Test-last")
                .build()

        when: "Person created"
        def createdPerson = personService.save(person)
        def foundPerson = personService.findById(createdPerson.id).get()

        then: "verify names are saved and retrieved correctly"
        foundPerson.firstName == person.firstName
        foundPerson.lastName == person.lastName

        when: "delete"
        personService.delete(createdPerson.id)
        def foundPersonAfterDelete = personService.findById(createdPerson.id)

        then: "should not be found"
        !foundPersonAfterDelete.isPresent()

    }
}
