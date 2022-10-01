package com.roadking.congress.repository;

import com.roadking.congress.domain.Sns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SnsRepository {

    private final EntityManager em;
    public void save(Sns sns) {
        em.persist(sns);
    }
}
