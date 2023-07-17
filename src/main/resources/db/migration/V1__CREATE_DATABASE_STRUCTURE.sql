CREATE TABLE role
(
    id          VARCHAR(50)  NOT NULL,
    description VARCHAR(200) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    CONSTRAINT PK_ROLE PRIMARY KEY (ID)
);

CREATE TABLE role_user_team_association
(
    role_id    VARCHAR(50) NOT NULL,
    user_id    VARCHAR(50) NOT NULL,
    team_id    VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    CONSTRAINT PK_RUT_ASSOC PRIMARY KEY (role_id, user_id, team_id),
    CONSTRAINT PK_RUT_ASSOC__ROLE FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE INDEX IDX_RUT_ASSOC__ROLE_ID ON role_user_team_association (role_id);
CREATE INDEX IDX_RUT_ASSOC__USER_ID_TEAM_ID ON role_user_team_association (user_id, team_id);