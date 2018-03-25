package io.husaind.service;

import io.husaind.dto.Person;
import io.husaind.entity.PersonEntity;
import io.husaind.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    static class Mapper {
        static Person toDto(PersonEntity personEntity) {
            return Person.builder()
                    .id(personEntity.getId())
                    .firstName(personEntity.getFirstName())
                    .lastName(personEntity.getLastName())
                    .build();
        }

        static List<Person> toDto(List<PersonEntity> personEntities) {
            return personEntities.stream().map(Mapper::toDto).collect(Collectors.toList());
        }
    }
}
