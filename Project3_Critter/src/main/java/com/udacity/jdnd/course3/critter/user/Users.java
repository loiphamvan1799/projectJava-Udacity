package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String phoneNumber;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
