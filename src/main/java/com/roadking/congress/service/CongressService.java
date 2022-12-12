package com.roadking.congress.service;

import com.roadking.congress.controller.SearchDto;
import com.roadking.congress.domain.Congressman;
import com.roadking.congress.repository.CongressmanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    // 현재 크롤링한 데이터와 학습된 모델을 날려버려서 ai모델 사용불가 상태로 인한
    // 랜덤 의원 조회로 대체
    public Map<Congressman, Long> findRandom() {
        Random random = new Random(System.currentTimeMillis());
        long randomNo = random.nextInt(298);
        long congressmanId = randomNo + 305;

        double percent = random.nextDouble();
        long randomPercent = Math.round(percent * 100);

        Map<Congressman, Long> map = new HashMap<>();
        map.put(congressmanRepository.findOne(congressmanId), randomPercent);
        return map;
    }





}
