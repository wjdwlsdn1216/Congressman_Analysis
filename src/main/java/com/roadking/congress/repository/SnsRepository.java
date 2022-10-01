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

    public Sns findByMonaCd(String monaCd) {
        return em.createQuery("select s from Sns s where s.monaCd = :monaCd", Sns.class)
                .setParameter("monaCd", monaCd).getSingleResult();
    }
}
