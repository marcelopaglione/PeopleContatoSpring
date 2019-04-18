package com.elotech.people.people.endpoint;

import com.elotech.people.people.entity.Person;
import com.elotech.people.people.repository.PersonRepository;
import com.elotech.people.people.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:9876"})
@RestController
@RequestMapping("v1")
public class PersonEndpoint {

    private final PersonRepository dao;

    @Autowired
    public PersonEndpoint(PersonRepository dao) {
        this.dao = dao;
    }

    @GetMapping(path = "people")
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Person> people = dao.findAll(pageable);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping(path = "people/{id}")
    public ResponseEntity<?> getEntityById(@PathVariable("id") long id) {
        Util.verifyIfEntityExists(id, dao);
        Person entity = dao.findById(id).get();
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @GetMapping(path = "people/findByName/{name}")
    public ResponseEntity<?> findEntityByName(@PathVariable String name, Pageable pageable){
        int total = dao.countByNameIgnoreCaseContaining(name.trim());
        List<Person> people = dao.findByNameIgnoreCaseContaining(name.trim(), pageable);
        Page<Person> peoplePage = new PageImpl<>(people, pageable, total);
        return new ResponseEntity<>(peoplePage, HttpStatus.OK);
    }

    @GetMapping(path = "people/findByExactName/{name}")
    public ResponseEntity<?> findEntityByExactName(@PathVariable String name, Pageable pageable){
        int total = dao.countByNameIgnoreCaseContaining(name);
        List<Person> people = dao.findByName(name, pageable);
        Page<Person> peoplePage = new PageImpl<>(people, pageable, total);
        return new ResponseEntity<>(peoplePage, HttpStatus.OK);
    }

    @PostMapping(path = "people")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Person entity){
        Person newEntity = dao.save(entity);
        Util.verifyIfEntityExists(newEntity.getId(), dao);
        return new ResponseEntity<>(newEntity, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "people/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Util.verifyIfEntityExists(id, dao);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "people")
    public ResponseEntity<?> update(@Valid @RequestBody Person entity){
        Util.verifyIfEntityExists(entity.getId(), dao);
        return new ResponseEntity<>(dao.save(entity), HttpStatus.OK);
    }

}
