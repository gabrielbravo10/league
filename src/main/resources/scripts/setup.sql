CREATE TABLE teams (
     id SERIAL NOT NULL,
     name VARCHAR(250) NOT NULL,
     PRIMARY KEY(id)
);


CREATE TABLE players (
	id SERIAL NOT NULL,
	name VARCHAR(250) NOT NULL,
	position VARCHAR(250) NOT NULL,
	id_team INT NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE players ADD CONSTRAINT fk_player_team FOREIGN KEY (id_team) REFERENCES teams (id);

CREATE TABLE coaches (
     id SERIAL NOT NULL,
     name VARCHAR(250) NOT NULL,
     PRIMARY KEY(id)
);

CREATE TABLE coach_player (
    coach_id INT NOT NULL,
    player_id INT NOT NULL,
    PRIMARY KEY(coach_id, player_id)
);

ALTER TABLE coach_player ADD CONSTRAINT fk_coach_player_coach FOREIGN KEY (coach_id) REFERENCES coaches (id);
ALTER TABLE coach_player ADD CONSTRAINT fk_coach_player_player FOREIGN KEY (player_id) REFERENCES players (id);


INSERT INTO teams(name) VALUES ('BravoFC');
INSERT INTO teams(name) VALUES ('Barcelona');
INSERT INTO teams(name) VALUES ('Real Madrid');
INSERT INTO teams(name) VALUES ('Liverpool');


INSERT INTO coaches(name) VALUES ('Gabriel Bravo');
INSERT INTO coaches(name) VALUES ('John Wick');
INSERT INTO coaches(name) VALUES ('Adam Sandler');
INSERT INTO coaches(name) VALUES ('Will Smith');


