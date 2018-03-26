package io.husaind

import io.husaind.dto.Person
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class PersonResourceSpec extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.class)

    @Autowired
    TestRestTemplate restTemplate

    String BASE_URL = "/persons"


    def "should create new user"() {
        given: "information about new person"
        Person person = new Person()
        person.setFirstName("Antony")
        person.setLastName("Zipkin")

        when: "saving the person is called"
        def createdPerson = restTemplate.postForObject(BASE_URL, person, Person.class)

        then: "should return success result"
        logger.debug("" + createdPerson.id)

        createdPerson.id != null
        createdPerson.firstName == person.firstName
        createdPerson.lastName == person.lastName

//        when: "find the person just created"
//        Person foundPerson = restTemplate.getForObject(BASE_URL+"/"+createdPerson.id, Person.class)
//        then:
//        foundPerson.firstName == createdPerson.firstName
//        foundPerson.lastName == createdPerson.lastName

    }

}
