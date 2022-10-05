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

    public List<Congressman> findAll() {
        return em.createQuery("select c from Congressman c", Congressman.class)
                .getResultList();
    }

    public Congressman findByName(String name) {
        return em.createQuery("select c from Congressman c where c.name = :name", Congressman.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void updateSnsId(Long id, String monaCd) {
        em.createNativeQuery("update congressman set sns_id = :id where mona_cd = :monaCd")
                .setParameter("id", id)
                .setParameter("monaCd", monaCd)
                .executeUpdate();
        em.clear();
    }

}
