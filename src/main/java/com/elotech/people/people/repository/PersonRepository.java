package com.elotech.people.people.repository;

import com.elotech.people.people.entity.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    List<Person> findByNameIgnoreCaseContaining(String name);
    int countByNameIgnoreCaseContaining(String name);
    List<Person> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    List<Person> findByName(String name, Pageable pageable);

}
