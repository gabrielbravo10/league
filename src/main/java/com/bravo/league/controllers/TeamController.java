package com.bravo.league.controllers;

import com.bravo.league.controllers.dto.TeamDTO;
import com.bravo.league.models.Team;
import com.bravo.league.services.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private TeamService teamService;
    private ModelMapper modelMapper;

    @Autowired
    public TeamController(TeamService teamService, ModelMapper modelMapper) {
        this.teamService = teamService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<TeamDTO>> index(@PageableDefault(sort = "name") Pageable pageable) {

        Page teams = teamService.getAll(pageable).map(team -> modelMapper.map(team, TeamDTO.class));

        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> show(@PathVariable Long id) {

        Team team = teamService.getOne(id);

        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);

        return ResponseEntity.ok(teamDTO);
    }




}
