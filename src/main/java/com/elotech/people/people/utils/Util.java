package com.elotech.people.people.utils;

import com.elotech.people.people.builder.EntityBuilder;
import com.elotech.people.people.entity.AbstractEntity;
import com.elotech.people.people.entity.Contato;
import com.elotech.people.people.entity.Person;
import com.elotech.people.people.error.ResourceNotFoundException;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.*;

public class Util {
    public static void verifyIfEntityExists(Long id, PagingAndSortingRepository dao) {
        Optional<AbstractEntity> entity = dao.findById(id);
        entity.orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
    }

    public static void insertTestData(PagingAndSortingRepository contatoDao, PagingAndSortingRepository personDao) {
        Person person;
        List<Person> people = new ArrayList<>();
        int totalToAdd = 50;
        for (int i = 0; i < totalToAdd; i++) {
            person = new EntityBuilder().createPerson();
            person.setId(0);
            person.setName(person.getName() + " " + (i + 1));
            people.add((Person) personDao.save(person));
        }

        Contato contato;
        for (int i = 0; i < totalToAdd*4; i++) {
            contato = new EntityBuilder().createContato();
            contato.setId(0);
            contato.setName(contato.getName() + " " + (i + 1));
            contato.setPerson(people.get(i / 4));
            contatoDao.save(contato);
        }
    }

}
