package com.roadking.congress.repository;

import com.roadking.congress.controller.SearchDto;
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

    public List<SearchDto> findByNameLike(String name) {
        return em.createQuery(
                        "select new com.roadking.congress.controller.SearchDto(c.id, c.name, c.bthDate, c.polyNm)" +
                                " from Congressman c where c.name like :name", SearchDto.class)
                .setParameter("name", name + "%")
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
    }

    public void updateSnsId(Long id, String monaCd) {
        em.createNativeQuery("update congressman set sns_id = :id where mona_cd = :monaCd")
                .setParameter("id", id)
                .setParameter("monaCd", monaCd)
                .executeUpdate();
        em.clear();
    }

    public void updateView(Long id) {
        em.createQuery("update Congressman c set c.view = c.view+1 where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<Congressman> findOrderbyView() {
        return em.createQuery("select c from Congressman c order by c.view desc", Congressman.class)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
    }
}
