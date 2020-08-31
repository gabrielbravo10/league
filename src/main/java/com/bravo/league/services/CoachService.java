package com.bravo.league.services;

import com.bravo.league.exceptions.RecordNotFoundException;
import com.bravo.league.models.Coach;
import com.bravo.league.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    public Page<Coach> getAll(Pageable pageable) {
        return coachRepository.findAll(pageable);
    }

    public Coach getOne(Long id) {
        return coachRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("coach.notFound"));
    }

}
