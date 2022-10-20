package com.roadking.congress.service;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.repository.CongressmanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CongressServiceTest {

    @Autowired
    CongressmanRepository congressmanRepository;
    @Autowired
    CongressService congressService;
    @Autowired
    EntityManager em;

    @Test
    public void 뷰_카운트() throws Exception {
        //given
        Congressman congressman = new Congressman("aa");

        //when
        congressmanRepository.save(congressman);
        congressman.viewUp();

        //then
        Integer result = em.createQuery("select c.view from Congressman c where c.name = :name", Integer.class)
                .setParameter("name", "aa")
                .getSingleResult();

        assertEquals(1 , result, "뷰카운트가 올라가야한다.");
    }
}