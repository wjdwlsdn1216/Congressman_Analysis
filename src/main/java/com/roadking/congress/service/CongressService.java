package com.roadking.congress.service;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.repository.CongressmanRepository;
import com.roadking.congress.repository.congressman.dto.CongressmanFlatDto;
import com.roadking.congress.repository.congressman.queryrepository.CongressmanQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CongressService {

    private final CongressmanRepository congressmanRepository;
    private final CongressmanQueryRepository congressmanQueryRepository;

    //저장
    @Transactional
    public void save(Congressman congressman) {
        congressmanRepository.save(congressman);
    }

    public Congressman findOne(Long id) {
        return congressmanRepository.findOne(id);
    }

    public List<Congressman> findAll() {
        return congressmanRepository.findAll();
    }

    @Transactional
    public void updateSnsId(Long id, String monaCd) {
        congressmanRepository.updateSnsId(id,monaCd);
    }


}
