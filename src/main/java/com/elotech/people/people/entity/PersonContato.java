package com.elotech.people.people.entity;

import java.util.ArrayList;
import java.util.List;

public class PersonContato {
    private Person person;
    private List<Contato> contatos;

    public PersonContato() {
        person = new Person();
        contatos = new ArrayList<>();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }
}
