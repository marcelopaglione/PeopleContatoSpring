package com.elotech.people.people.builder;

import com.elotech.people.people.entity.Contato;
import com.elotech.people.people.entity.Person;
import com.elotech.people.people.entity.PersonContato;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

public class EntityBuilder {
    public Person createPerson() {
        return PersonBuilder
                .aPerson()
                .withId(1)
                .withNome("Person Full Name")
                .withRg("1010101010")
                .withBirthDate(new Date())
                .build();
    }

    public Contato createContato() {
        return ContatoBuilder
                .aContato()
                .withId(1)
                .withNome("Contato Full Name")
                .withPerson(createPerson())
                .build();
    }

    public PersonContato createPersonContato() {
        Person person = createPerson();
        person.setId(0);

        Contato contato1 = createContato();
        Contato contato2 = createContato();
        contato1.setId(0);
        contato2.setId(0);

        contato1.setPerson(person);
        contato2.setPerson(person);

        List<Contato> contatos = asList(contato1, contato2);
        return PersonContatoBuilder
                .aPersonContato()
                .withContatos(contatos)
                .withPerson(person)
                .build();
    }
}
