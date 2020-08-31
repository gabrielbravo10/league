package com.bravo.league.services;

import com.bravo.league.exceptions.RecordNotFoundException;
import com.bravo.league.models.Player;
import com.bravo.league.models.Team;
import com.bravo.league.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Page<Player> getAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Player getOne(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("player.notFound"));
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public void delete(Long id) {
        Player player = getOne(id);
        playerRepository.delete(player);
    }

}
