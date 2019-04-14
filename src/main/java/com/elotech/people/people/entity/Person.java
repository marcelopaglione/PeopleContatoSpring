package com.elotech.people.people.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PERSON")
public class Person extends AbstractEntity{
    private String name;
    private String rg;
    private Date birthDate;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


}
