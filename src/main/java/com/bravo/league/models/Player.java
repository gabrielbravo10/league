package com.bravo.league.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends Entidade {

	@Column(name = "name")
	private String name;

	@Column(name = "position")
	private String position;

	@ManyToOne
	@JoinColumn(name = "id_team", foreignKey = @ForeignKey(name = "fk_player_team"))
	private Team team;

	@ManyToMany
	@JoinTable(name = "coach_player",
		joinColumns = @JoinColumn(name = "player_id",
			foreignKey = @ForeignKey(name = "fk_coach_player_player")),
		inverseJoinColumns = @JoinColumn(name = "coach_id",
			foreignKey = @ForeignKey(name = "fk_coach_player_coach")))
	private Set<Coach> coaches;

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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Set<Coach> getCoaches() {
		if (coaches == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(coaches);
	}

	public void addCoach(Coach coach) {
		if (coaches == null) {
			coaches = new HashSet<>();
		}

		coaches.add(coach);
	}

}
