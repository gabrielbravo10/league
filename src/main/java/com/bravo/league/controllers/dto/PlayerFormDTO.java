package com.bravo.league.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PlayerFormDTO {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    private String position;

    private Long team;

    private List<Long> coaches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }

    public List<Long> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Long> coaches) {
        this.coaches = coaches;
    }
}
