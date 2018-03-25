package io.husaind.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Person {
    @Min(1)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
