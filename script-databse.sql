use database postgres   ;

-- Schema for PostgreSQL
CREATE TABLE IF NOT EXISTS blueprints (
    author VARCHAR(100) NOT NULL,
    name   VARCHAR(200) NOT NULL,
    PRIMARY KEY (author, name)
);

CREATE TABLE IF NOT EXISTS blueprint_points (
    id     BIGSERIAL PRIMARY KEY,
    author VARCHAR(100) NOT NULL,
    name   VARCHAR(200) NOT NULL,
    x      INTEGER NOT NULL,
    y      INTEGER NOT NULL,
    CONSTRAINT fk_blueprint
        FOREIGN KEY (author, name)
        REFERENCES blueprints(author, name)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_points_blueprint ON blueprint_points(author, name);

-- Seed sample data matching InMemory blueprint examples
INSERT INTO blueprints(author, name) VALUES ('john', 'house') ON CONFLICT DO NOTHING;
INSERT INTO blueprints(author, name) VALUES ('john', 'garage') ON CONFLICT DO NOTHING;
INSERT INTO blueprints(author, name) VALUES ('jane', 'garden') ON CONFLICT DO NOTHING;

-- Points for john:house
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'house', 0, 0);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'house', 10, 0);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'house', 10, 10);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'house', 0, 10);

-- Points for john:garage
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'garage', 5, 5);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'garage', 15, 5);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('john', 'garage', 15, 15);

-- Points for jane:garden
INSERT INTO blueprint_points(author, name, x, y) VALUES ('jane', 'garden', 2, 2);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('jane', 'garden', 3, 4);
INSERT INTO blueprint_points(author, name, x, y) VALUES ('jane', 'garden', 6, 7);