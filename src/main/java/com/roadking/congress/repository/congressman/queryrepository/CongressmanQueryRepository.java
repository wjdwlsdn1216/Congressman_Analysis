package com.roadking.congress.repository.congressman.queryrepository;

import com.roadking.congress.repository.congressman.dto.CongressmanFlatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CongressmanQueryRepository {

    private final EntityManager em;

    public CongressmanFlatDto findCongressmanWithSns(Long id) {
        return em.createQuery("select new com.roadking.congress.repository.congressman.dto.CongressmanFlatDto(c.name, c.hjName, c.enName, c.bthGbnNm, c.bthDate, c.jobResNm, c.polyNm, c.origNm, c.electGbnNm, c.cmitNm, c.cmits, c.reeleGbnNm, c.units, c.sex, c.telNo, c.email, c.homepage, c.staff, c.secretary, c.secretary2, c.monaCd, c.memTitle, c.assemAddr, s.twitterUrl, s.facebookUrl, s.utubeUrl, s.blogUrl) from Congressman c join Sns s on c.monaCd = s.monaCd where c.id = :id", CongressmanFlatDto.class).setParameter("id", id).getSingleResult();
    }
}
