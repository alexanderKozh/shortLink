DROP TABLE IF EXISTS link;
CREATE TABLE link
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    original VARCHAR(1000) NOT NULL,
    simple   VARCHAR(1000) NOT NULL
);

CREATE INDEX i_link_original ON link (original);
CREATE INDEX i_link_simple ON link (simple);

DROP TABLE IF EXISTS redirect;
CREATE TABLE redirect
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    link_id BIGINT,
    moment  TIMESTAMP NOT NULL,
    FOREIGN KEY (link_id) REFERENCES link (id)
);