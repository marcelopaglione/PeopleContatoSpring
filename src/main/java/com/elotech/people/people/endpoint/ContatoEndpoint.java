package com.elotech.people.people.endpoint;

import com.elotech.people.people.entity.Contato;
import com.elotech.people.people.entity.Person;
import com.elotech.people.people.entity.PersonContato;
import com.elotech.people.people.repository.ContatoRepository;
import com.elotech.people.people.repository.PersonRepository;
import com.elotech.people.people.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:9876"})
@RestController
@RequestMapping("v1")
public class ContatoEndpoint {

    private final ContatoRepository contatoDao;
    private final PersonRepository personDao;

    @Autowired
    public ContatoEndpoint(ContatoRepository contatoDao, PersonRepository personDao) {
        this.contatoDao = contatoDao;
        this.personDao = personDao;
    }

    @GetMapping(path = "contatos")
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(contatoDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "contatos/{id}")
    public ResponseEntity<?> getEntityById(@PathVariable("id") long id) {
        Util.verifyIfEntityExists(id, contatoDao);
        Contato entity = contatoDao.findById(id).get();
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @GetMapping(path = "contatos/findByPersonId/{id}")
    public ResponseEntity<?> getEntityByPersonId(@PathVariable("id") long id) {
        List<Contato> contatos = contatoDao.findByPersonId(id);
        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }

    @GetMapping(path = "contatos/findByName/{name}")
    public ResponseEntity<?> findEntityByName(@PathVariable String name) {
        return new ResponseEntity<>(contatoDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "contatos")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Contato entity) {
        if (entity.getPerson() != null && entity.getPerson().getId() == 0) {
            Person newPerson = personDao.save(entity.getPerson());
            Util.verifyIfEntityExists(newPerson.getId(), personDao);
            entity.getPerson().setId(newPerson.getId());
        }
        Contato newEntity = contatoDao.save(entity);
        Util.verifyIfEntityExists(newEntity.getId(), contatoDao);
        return new ResponseEntity<>(newEntity, HttpStatus.CREATED);
    }

    @PostMapping(path = "contatos/savePersonAndContatos/")
    @Transactional
    public ResponseEntity<?> saveContatosAndPeople(@RequestBody PersonContato entity) {
        if (entity.getPerson() != null && entity.getPerson().getId() == 0) {
            Person newPerson = personDao.save(entity.getPerson());
            Util.verifyIfEntityExists(newPerson.getId(), personDao);
            entity.getPerson().setId(newPerson.getId());
        }
        entity.getContatos().forEach(contato -> {
            contato.getPerson().setId(entity.getPerson().getId());
        });
        PersonContato response = new PersonContato();
        Iterable<Contato> savedContatos = contatoDao.saveAll(entity.getContatos());
        response.setPerson(entity.getPerson());
        savedContatos.forEach(response.getContatos()::add);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "contatos/updatePersonAndContatos/")
    @Transactional
    public ResponseEntity<?> updateContatosAndPeople(@RequestBody PersonContato entity) {
        Util.verifyIfEntityExists(entity.getPerson().getId(), personDao);
        entity.setPerson(personDao.save(entity.getPerson()));
        List<Contato> contatosResponse = new ArrayList<>();
        entity.getContatos().forEach(contato -> {
            Util.verifyIfEntityExists(contato.getId(), contatoDao);
            contatosResponse.add(contatoDao.save(contato));
        });
        entity.setContatos(contatosResponse);
        return new ResponseEntity<>(contatosResponse, HttpStatus.OK);
    }

    @DeleteMapping(path = "contatos/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Util.verifyIfEntityExists(id, contatoDao);
        contatoDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "contatos")
    public ResponseEntity<?> update(@Valid @RequestBody Contato entity) {
        Util.verifyIfEntityExists(entity.getId(), contatoDao);
        return new ResponseEntity<>(contatoDao.save(entity), HttpStatus.OK);
    }
}
