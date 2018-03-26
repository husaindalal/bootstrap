package io.husaind.service;

import io.husaind.dto.Person;
import io.husaind.entity.PersonEntity;
import io.husaind.exception.CustomException;
import io.husaind.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return Mapper.toDto(personRepository.findAll());
    }

    public Optional<Person> findById(Long id) {
        if (id == null || id < 1) {
            return Optional.empty();
        }
        return personRepository.findById(id).map(Mapper::toDto);
    }

    public Person save(@Valid Person person) {
        try {
            return Mapper.toDto(personRepository.save(Mapper.toDao(person)));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public void delete(Long personId) {
        Optional<PersonEntity> person = personRepository.findById(personId);
        if (person.isPresent()) {
            personRepository.deleteById(personId);

        } else {
            throw new CustomException("Person not found");
        }

    }

    static class Mapper {
        static Person toDto(PersonEntity personEntity) {
            Person person = new Person();
            person.setId(personEntity.getId());
            person.setFirstName(personEntity.getFirstName());
            person.setLastName(personEntity.getLastName());

            return person;
        }

        static List<Person> toDto(List<PersonEntity> personEntities) {
            return personEntities.stream().map(Mapper::toDto).collect(Collectors.toList());
        }

        static PersonEntity toDao(Person person) {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setId(person.getId());
            personEntity.setFirstName(person.getFirstName());
            personEntity.setLastName(person.getLastName());

            return personEntity;
        }

        static List<PersonEntity> toDao(List<Person> persons) {
            return persons.stream().map(Mapper::toDao).collect(Collectors.toList());
        }
    }
}
