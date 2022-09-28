package com.roadking.congress.service;

import com.roadking.congress.domain.Test;
import com.roadking.congress.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public void insertMessage(Test test) {
        testRepository.save(test);
    }
}
