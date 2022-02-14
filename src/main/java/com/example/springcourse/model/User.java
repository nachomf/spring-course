package com.example.springcourse.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@JsonFilter("UsersFilter")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, message = "name '${validatedValue}' should have 2 characters at least")
    private String name;

    @Past(message = "birth date should be in the past")
    private Date birthDate;

    @Size(min = 3, max = 15, message = "password should have between 3 and 15 characters long")
    @JsonIgnore
    private String password;

    protected User() {
    }

    public User(Long id, String name, Date birthDate, String password) {
        super();
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.password = password;
    }

    public User(String name, Date birthDate, String password) {
        super();
        this.name = name;
        this.birthDate = birthDate;
        this.password = Objects.requireNonNullElseGet(password, () -> RandomStringUtils.randomAlphanumeric(15));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return String.format("User [id=%s,name=%s,birthDate=%s,password=%s]", id, name, birthDate, password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
