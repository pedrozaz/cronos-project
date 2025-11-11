DROP TABLE IF EXISTS lap_times;
DROP TABLE IF EXISTS pit_stops;
DROP TABLE IF EXISTS results;

DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS seasons;
DROP TABLE IF EXISTS circuits;
DROP TABLE IF EXISTS drivers;
DROP TABLE IF EXISTS constructors;
DROP TABLE IF EXISTS status;

SET client_min_messages TO WARNING;

CREATE TABLE IF NOT EXISTS circuits (
                                        circuit_id    BIGINT PRIMARY KEY,
                                        circuit_ref   VARCHAR(255) NOT NULL UNIQUE,
                                        name          VARCHAR(255) NOT NULL,
                                        location      VARCHAR(255),
                                        country       VARCHAR(255),
                                        latitude      FLOAT,
                                        longitude     FLOAT,
                                        altitude      INT,
                                        created_at    TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'utc')
);

CREATE TABLE IF NOT EXISTS seasons (
                                       year          INT PRIMARY KEY,
                                       url           VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS races (
                                     race_id       BIGINT PRIMARY KEY,
                                     year          INT NOT NULL REFERENCES seasons(year),
                                     round         INT NOT NULL,
                                     circuit_id    BIGINT NOT NULL REFERENCES circuits(circuit_id),
                                     name          VARCHAR(255) NOT NULL,
                                     date          DATE NOT NULL,
                                     "time"        TIME,
                                     url           VARCHAR(255) UNIQUE,
                                     created_at    TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'utc'),
                                     CONSTRAINT uk_year_round UNIQUE (year, round)
);

CREATE TABLE IF NOT EXISTS constructors (
                                            constructor_id    BIGINT PRIMARY KEY,
                                            constructor_ref   VARCHAR(255) NOT NULL UNIQUE,
                                            name              VARCHAR(255) NOT NULL UNIQUE,
                                            nationality       VARCHAR(255),
                                            url               VARCHAR(255),
                                            created_at        TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'utc')
);

CREATE TABLE IF NOT EXISTS drivers (
                                       driver_id         BIGINT PRIMARY KEY,
                                       driver_ref        VARCHAR(255) NOT NULL UNIQUE,
                                       number            INT,
                                       code              VARCHAR(3),
                                       forename          VARCHAR(255) NOT NULL,
                                       surname           VARCHAR(255) NOT NULL,
                                       dob               DATE,
                                       nationality       VARCHAR(255),
                                       url               VARCHAR(255),
                                       created_at        TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'utc')
);

CREATE TABLE IF NOT EXISTS status (
                                      status_id     BIGINT PRIMARY KEY,
                                      status        VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS results (
                                       result_id         BIGINT PRIMARY KEY,
                                       race_id           BIGINT NOT NULL REFERENCES races(race_id),
                                       driver_id         BIGINT NOT NULL REFERENCES drivers(driver_id),
                                       constructor_id    BIGINT NOT NULL REFERENCES constructors(constructor_id),
                                       number            INT,
                                       grid              INT NOT NULL,
                                       "position"        INT,
                                       position_text     VARCHAR(255),
                                       points            FLOAT NOT NULL,
                                       laps              INT NOT NULL,
                                       "time"            VARCHAR(255),
                                       milliseconds      INT,
                                       fastest_lap       INT,
                                       rank              INT,
                                       fastest_lap_time  VARCHAR(255),
                                       fastest_lap_speed FLOAT,
                                       status_id         BIGINT NOT NULL REFERENCES status(status_id),
                                       CONSTRAINT uk_race_driver UNIQUE (race_id, driver_id)
);

CREATE TABLE IF NOT EXISTS pit_stops (
                                         race_id       BIGINT NOT NULL REFERENCES races(race_id),
                                         driver_id     BIGINT NOT NULL REFERENCES drivers(driver_id),
                                         stop          INT NOT NULL,
                                         lap           INT NOT NULL,
                                         "time"        TIME NOT NULL,
                                         duration      VARCHAR(255),
                                         milliseconds  INT,
                                         PRIMARY KEY (race_id, driver_id, stop)
);

CREATE TABLE IF NOT EXISTS lap_times (
                                         race_id       BIGINT NOT NULL REFERENCES races(race_id),
                                         driver_id     BIGINT NOT NULL REFERENCES drivers(driver_id),
                                         lap           INT NOT NULL,
                                         "position"    INT,
                                         "time"        VARCHAR(255),
                                         milliseconds  INT NOT NULL,
                                         PRIMARY KEY (race_id, driver_id, lap)
);

CREATE INDEX IF NOT EXISTS idx_races_circuit_id ON races(circuit_id);
CREATE INDEX IF NOT EXISTS idx_results_race_id ON results(race_id);
CREATE INDEX IF NOT EXISTS idx_results_driver_id ON results(driver_id);
CREATE INDEX IF NOT EXISTS idx_results_constructor_id ON results(constructor_id);
CREATE INDEX IF NOT EXISTS idx_pit_stops_driver_id ON pit_stops(driver_id);
CREATE INDEX IF NOT EXISTS idx_lap_times_driver_id ON lap_times(driver_id);