package io.husaind.controller;

import io.husaind.dto.Person;
import io.husaind.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @ApiOperation(
            value = "Find all persons"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Showing all people found")
    })
    public List<Person> findAll() {
        return personService.findAll();
    }

    // Create a new Person
    @PostMapping
    public Person create(@Valid @RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/{id}")
    public Person update(@NotNull @PathVariable(value = "id") Long personId,
                         @Valid @RequestBody Person person) {

        if (person.getId() == null) {
            person.setId(personId);
        }
        if (!person.getId().equals(personId)) {
            throw new IllegalArgumentException("Invalid: personId does not match");
        }
        return personService.save(person);
    }

    // Delete a Person
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long personId) {

        personService.delete(personId);

        return ResponseEntity.ok().build();
    }
}
