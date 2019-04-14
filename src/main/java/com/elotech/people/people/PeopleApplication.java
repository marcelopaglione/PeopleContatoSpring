package com.elotech.people.people;

import com.elotech.people.people.repository.ContatoRepository;
import com.elotech.people.people.repository.PersonRepository;
import com.elotech.people.people.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeopleApplication {

	private PersonRepository personDao;
	private ContatoRepository contatoDao;

	@Autowired
	public PeopleApplication(PersonRepository personDao, ContatoRepository contatoDao) {
		this.personDao = personDao;
		this.contatoDao = contatoDao;
		Util.insertTestData(contatoDao, personDao);
	}

	public static void main(String[] args) {
		SpringApplication.run(PeopleApplication.class, args);
	}

}
