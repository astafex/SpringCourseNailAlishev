package com.github.astafex.springcourse.util;

import com.github.astafex.springcourse.dao.PersonDAO;
import com.github.astafex.springcourse.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> aClass) {

        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken!");
        }
    }
}