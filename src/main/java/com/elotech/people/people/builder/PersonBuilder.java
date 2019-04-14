package com.elotech.people.people.builder;

import com.elotech.people.people.entity.Person;

import java.util.Date;

public final class PersonBuilder {
    protected long id;
    private String nome;
    private String rg;
    private Date birthDate;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        return new PersonBuilder();
    }

    public PersonBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PersonBuilder withRg(String rg) {
        this.rg = rg;
        return this;
    }

    public PersonBuilder withBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PersonBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public Person build() {
        Person person = new Person();
        person.setName(nome);
        person.setRg(rg);
        person.setBirthDate(birthDate);
        person.setId(id);
        return person;
    }
}
