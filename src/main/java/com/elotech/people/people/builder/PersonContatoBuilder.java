package com.elotech.people.people.builder;

import com.elotech.people.people.entity.Contato;
import com.elotech.people.people.entity.Person;
import com.elotech.people.people.entity.PersonContato;

import java.util.List;

public final class PersonContatoBuilder {
    private Person person;
    private List<Contato> contatos;

    private PersonContatoBuilder() {
    }

    public static PersonContatoBuilder aPersonContato() {
        return new PersonContatoBuilder();
    }

    public PersonContatoBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    public PersonContatoBuilder withContatos(List<Contato> contatos) {
        this.contatos = contatos;
        return this;
    }

    public PersonContato build() {
        PersonContato personContato = new PersonContato();
        personContato.setPerson(person);
        personContato.setContatos(contatos);
        return personContato;
    }
}
