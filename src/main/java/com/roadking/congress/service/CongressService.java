package com.roadking.congress.service;

import com.roadking.congress.domain.Congressman;
import com.roadking.congress.repository.CongressmanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CongressService {

    private final CongressmanRepository congressmanRepository;

    //저장
    @Transactional
    public Long save(Congressman congressman) {
        congressmanRepository.save(congressman);
        return congressman.getId();
    }

    public Congressman findOne(Long id) {
        return congressmanRepository.findOne(id);
    }

    public Congressman findWithSns(Long id) {
        return congressmanRepository.findOneWithSns(id);
    }

    public List<Congressman> findAll() {
        return congressmanRepository.findAll();
    }


}
