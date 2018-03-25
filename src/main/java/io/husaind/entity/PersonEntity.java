package io.husaind.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "person")

public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
}
