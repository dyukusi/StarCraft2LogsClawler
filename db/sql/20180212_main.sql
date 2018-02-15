DROP DATABASE sc2logs;

CREATE DATABASE sc2logs;

CREATE TABLE sc2logs.profile_log (
       id INT NOT NULL,
       name VARCHAR(32) NOT NULL,
       battle_tag VARCHAR(32) NOT NULL,
       league_id INT NOT NULL,
       season_id INT NOT NULL,
       queue_id INT NOT NULL,
       team_type INT NOT NULL,
       race VARCHAR(32) NOT NULL,
       played_count INT NOT NULL,
       rating INT NOT NULL,
       points INT NOT NULL,
       wins INT NOT NULL,
       losses INT NOT NULL,
       ties INT NOT NULL,
       longest_win_streak INT NOT NULL,
       current_win_streak INT NOT NULL,
       current_rank INT NOT NULL,
       highest_rank INT NOT NULL,
       previous_rank INT NOT NULL,
       clan_id INT DEFAULT null,
       clan_tag VARCHAR(32) DEFAULT null,
       clan_name VARCHAR(32) DEFAULT null,
       clan_icon_url TEXT DEFAULT null,
       clan_decal_url TEXT DEFAULT null,
       joined_at DATETIME NOT NULL,
       last_played_at DATETIME NOT NULL,
       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
       INDEX (id, name)
) ENGINE=InnoDB;

CREATE TABLE sc2logs.season (
       id INT NOT NULL,
       year INT NOT NULL,
       number INT NOT NULL,
       start_at DATETIME NOT NULL,
       end_at DATETIME NOT NULL,
       PRIMARY KEY(id)
) ENGINE=InnoDB;