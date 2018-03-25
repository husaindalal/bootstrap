package io.husaind

import io.husaind.dto.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class PersonResourceSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate


    def "should create new user"() {
        given: "information about new person"
        def person = new Person(firstName: "Konstantin", lastName: "Khan")

        when: "saving the person is called"
        def result = restTemplate.postForEntity("/persons", person, Void.class)
        then: "should return success result"
        result
        result.statusCode == HttpStatus.OK
        and: "save the person in database"
        def saved = findPerson()
        saved["FIRST_NAME"] == "Konstantin"
        saved["LAST_NAME"] == "Khan"
    }

    def findPerson() {
        sql.firstRow(String.format("select * from Person"))
    }
}
