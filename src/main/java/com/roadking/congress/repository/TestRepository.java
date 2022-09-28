package com.roadking.congress.repository;

import com.roadking.congress.domain.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TestRepository {

    private final EntityManager entityManager;

    public void save(Test test) {
        entityManager.persist(test);
    }

}
