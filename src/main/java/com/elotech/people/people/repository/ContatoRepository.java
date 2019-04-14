package com.elotech.people.people.repository;

import com.elotech.people.people.entity.Contato;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface ContatoRepository extends PagingAndSortingRepository<Contato, Long> {
    Set<Contato> findByNameIgnoreCaseContaining(String name);
    List<Contato> findByPersonId(long id, Pageable pageable);
    List<Contato> findByPersonId(long id);
}
