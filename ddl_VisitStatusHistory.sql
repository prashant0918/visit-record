CREATE TABLE visit_status_history
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    visit_id    BIGINT                NOT NULL,
    from_status VARCHAR(255)          NULL,
    to_status   VARCHAR(255)          NULL,
    changed_at  datetime              NULL,
    actor_type  VARCHAR(255)          NULL,
    actor_id    BIGINT                NULL,
    reason      VARCHAR(255)          NULL,
    CONSTRAINT pk_visitstatushistory PRIMARY KEY (id)
);

ALTER TABLE visit_status_history
    ADD CONSTRAINT FK_VISITSTATUSHISTORY_ON_VISIT FOREIGN KEY (visit_id) REFERENCES visit (id);