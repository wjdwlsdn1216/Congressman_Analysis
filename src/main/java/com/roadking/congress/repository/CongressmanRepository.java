package com.roadking.congress.repository;

import com.roadking.congress.domain.Congressman;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CongressmanRepository {

    private final EntityManager em;

    public void save(Congressman congressman) {
        em.persist(congressman);
    }

    public Congressman findOne(Long id) {
        return em.find(Congressman.class, id);
    }

    public Congressman findOneWithSns(Long id) {
        return em.createQuery("select c from Congressman c join Sns s on c.monaCd = s.monaCd where c.id = :id", Congressman.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Congressman> findAll() {
        return em.createQuery("select c from Congressman c", Congressman.class)
                .getResultList();
    }

    public List<Congressman> findByName(String name) {
        return em.createQuery("select c from Congressman c where c.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

}
