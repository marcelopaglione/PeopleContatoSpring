package com.elotech.people.people.builder;

import com.elotech.people.people.entity.Contato;
import com.elotech.people.people.entity.Person;

public final class ContatoBuilder {
    protected long id;
    private String nome;
    private Person person;

    private ContatoBuilder() {
    }

    public static ContatoBuilder aContato() {
        return new ContatoBuilder();
    }

    public ContatoBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContatoBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ContatoBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    public Contato build() {
        Contato contato = new Contato();
        contato.setName(nome);
        contato.setId(id);
        contato.setPerson(person);
        return contato;
    }
}
