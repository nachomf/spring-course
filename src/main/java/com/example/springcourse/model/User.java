package com.example.springcourse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {

    private Integer id;

    @Size(min = 2, message = "name '${validatedValue}' should have 2 characters at least")
    private String name;

    @Past(message = "birth date should be in the past")
    private Date birthDate;

    @Size(min = 3, max = 15, message = "password should have between 3 and 15 characters long")
    @JsonIgnore
    private String password;

    public User(Integer id, String name, Date birthDate, String password) {
        super();
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
