package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Users {

    @OneToMany(targetEntity = Pet.class)
    private List<Pet> pets = new ArrayList<Pet>();

    public List<Pet> getPets() {
        return pets;
    }

    private String notes;

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
