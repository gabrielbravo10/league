package com.bravo.league.services;

import com.bravo.league.exceptions.RecordNotFoundException;
import com.bravo.league.models.Coach;
import com.bravo.league.models.Team;
import com.bravo.league.repositories.CoachRepository;
import com.bravo.league.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Page<Team> getAll(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    public Team getOne(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("team.notFound"));
    }

}
