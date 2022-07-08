package com.github.astafex.springcourse.dao;

import com.github.astafex.springcourse.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private final List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(PEOPLE_COUNT++, "Tom", 29, "tomhan91@gmail.com"));
        people.add(new Person(PEOPLE_COUNT++, "Bob", 19, "dur0a@yandex.ru"));
        people.add(new Person(PEOPLE_COUNT++, "Mike", 32, "psnturkcom@yahoo.com"));
        people.add(new Person(PEOPLE_COUNT++, "Katy", 41, "silveranal@mail.ru"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream()
                .filter(person -> person.getId() == id)
                .findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(PEOPLE_COUNT++);
        people.add(person);
    }

    public void update(int id, Person updatePerson) {
        Person person = people.get(id);
        person.setName(updatePerson.getName());
        person.setAge(updatePerson.getAge());
        person.setEmail(updatePerson.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}