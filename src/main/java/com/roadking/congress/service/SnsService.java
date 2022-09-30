package com.roadking.congress.service;

import com.roadking.congress.domain.Sns;
import com.roadking.congress.repository.SnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SnsService {

    private final SnsRepository snsRepository;


    @Transactional
    public void save(Sns sns) {
        snsRepository.save(sns);
    }
}
