package com.roadking.congress.service;

import com.roadking.congress.controller.SearchDto;
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

    public Congressman findByName(String name) {
        return congressmanRepository.findByName(name);
    }

    public List<SearchDto> findByNameLike(String name) {
        return congressmanRepository.findByNameLike(name);
    }

    @Transactional
    public void updateView(Congressman congressman) {
        congressman.viewUp();
    }

    public List<Congressman> findOrderbyView() {
        return  congressmanRepository.findOrderbyView();
    }

    public List<Congressman> findOrderbySimilarView() {
        return congressmanRepository.findOrderbySimilarView();
    }

    //더티체킹 방식으로 업데이트
    @Transactional
    public void updateSimilarView(Congressman congressman) {
        congressman.similarViewUp();
    }

}
