package com.github.astafex.springcourse.dao;

import com.github.astafex.springcourse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?", new BeanPropertyRowMapper<>(Person.class), email).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name,age,email) values(?,?,?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?,age=?,email=? WHERE id=?", person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }


    ////////////////////////////////////////////
    //Тест производительности пакетной вставки//
    ////////////////////////////////////////////
    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long start = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person values(?,?,?,?)", person.getId(), person.getName(), person.getAge(), person.getEmail());
        }
        long finish = System.currentTimeMillis();
        System.out.println("Time Multiply: " + (finish - start));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person values(?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, people.get(i).getId());
                ps.setString(2, people.get(i).getName());
                ps.setInt(3, people.get(i).getAge());
                ps.setString(4, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
        long finish = System.currentTimeMillis();
        System.out.println("Time Batch: " + (finish - start));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "mail.ru"));
        }
        return people;
    }
}