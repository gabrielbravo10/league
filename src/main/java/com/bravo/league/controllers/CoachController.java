package com.bravo.league.controllers;

import com.bravo.league.controllers.dto.CoachDTO;
import com.bravo.league.models.Coach;
import com.bravo.league.services.CoachService;
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
@RequestMapping("/coaches")
public class CoachController {

    private CoachService coachService;
    private ModelMapper modelMapper;

    @Autowired
    public CoachController(CoachService coachService, ModelMapper modelMapper) {
        this.coachService = coachService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<CoachDTO>> index(@PageableDefault(sort = "name") Pageable pageable) {

        Page coaches = coachService.getAll(pageable).map(coach -> modelMapper.map(coach, CoachDTO.class));

        return ResponseEntity.ok(coaches);
    }


    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<CoachDTO> show(@PathVariable Long id) {
        Coach coach = coachService.getOne(id);
        CoachDTO coachDTO = modelMapper.map(coach, CoachDTO.class);

        return ResponseEntity.ok(coachDTO);

    }
}
