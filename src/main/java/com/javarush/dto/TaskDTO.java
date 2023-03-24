package com.javarush.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private int id;
    @NotEmpty(message = "This field can't be empty")
    private String description;
    private String status;

}
