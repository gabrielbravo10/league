package com.bravo.league.controllers;

import com.bravo.league.controllers.dto.CoachDTO;
import com.bravo.league.controllers.dto.PlayerDTO;
import com.bravo.league.controllers.dto.PlayerFormDTO;
import com.bravo.league.models.Player;
import com.bravo.league.models.Team;
import com.bravo.league.services.CoachService;
import com.bravo.league.services.PlayerService;
import com.bravo.league.services.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerService playerService;
    private TeamService teamService;
    private CoachService coachService;
    private ModelMapper modelMapper;

    @Autowired
    public PlayerController(PlayerService playerService,
                            TeamService teamService,
                            CoachService coachService,
                            ModelMapper modelMapper) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.coachService = coachService;
        this.modelMapper = modelMapper;
    }

    @Cacheable(value = "players")
    @GetMapping
    public ResponseEntity<Page<PlayerDTO>> index(@PageableDefault(sort = "name")Pageable pageable) {
        Page players = playerService.getAll(pageable)
                .map(player -> modelMapper.map(player, PlayerDTO.class));
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> show(@PathVariable Long id) {
        Player player = playerService.getOne(id);
        PlayerDTO playerDTO = modelMapper.map(player, PlayerDTO.class);
        return ResponseEntity.ok(playerDTO);
    }

    @CacheEvict(value = "players", allEntries = true)
    @Transactional
    @PostMapping
    public ResponseEntity<PlayerDTO> store(@RequestBody @Valid PlayerFormDTO playerFormDTO, UriComponentsBuilder uriComponentsBuilder) {
        Player player = modelMapper.map(playerFormDTO, Player.class);
        addTeam(player, playerFormDTO);
        addCoaches(player, playerFormDTO);
        PlayerDTO newPlayer = modelMapper.map(playerService.save(player), PlayerDTO.class);
//        return ResponseEntity.ok(newPlayer);
        URI uri = uriComponentsBuilder.path("/players/{id}").buildAndExpand(newPlayer.getId()).toUri();
        return ResponseEntity.created(uri).body(newPlayer);
    }

    @CacheEvict(value = "players", allEntries = true)
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable Long id, @RequestBody @Valid PlayerFormDTO playerFormDTO) {
        Player player = modelMapper.map(playerFormDTO, Player.class);
        player.setId(id);
        addTeam(player, playerFormDTO);
        addCoaches(player, playerFormDTO);
        PlayerDTO newPlayer = modelMapper.map(playerService.save(player), PlayerDTO.class);
        return ResponseEntity.ok(newPlayer);
    }

    @CacheEvict(value = "players", allEntries = true)
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void addTeam(Player player, PlayerFormDTO playerFormDTO) {
        Team team = teamService.getOne(playerFormDTO.getTeam());
        player.setTeam(team);
    }

    private void addCoaches(Player player, PlayerFormDTO playerFormDTO) {
        if (playerFormDTO.getCoaches() != null && !playerFormDTO.getCoaches().isEmpty()) {
            for (Long coach: playerFormDTO.getCoaches()) {
                player.addCoach(coachService.getOne(coach));
            }
        }
    }



}
