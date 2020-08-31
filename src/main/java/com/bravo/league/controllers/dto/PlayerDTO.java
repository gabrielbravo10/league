package com.bravo.league.controllers.dto;

import java.util.List;

public class PlayerDTO {

    private Long id;

    private String name;

    private String position;

    private TeamDTO team;

    private List<CoachDTO> coaches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public List<CoachDTO> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<CoachDTO> coaches) {
        this.coaches = coaches;
    }
}
