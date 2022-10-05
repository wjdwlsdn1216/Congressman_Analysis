package com.roadking.congress.repository.congressman.queryrepository;
import com.roadking.congress.repository.congressman.dto.CongressmanFlatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CongressmanQueryRepository {

    private final EntityManager em;
    
}
