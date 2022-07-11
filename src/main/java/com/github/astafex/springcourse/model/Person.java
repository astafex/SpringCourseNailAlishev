package com.github.astafex.springcourse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters!")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @NotEmpty(message = "Email should not be empty!")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters!")
    @Email
    private String email;

    // Страна, Город, Индекс(6 цифр)
    @Pattern(regexp = "")
    private String address;
}
