package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@DisplayName("Integration test to find all RoleAssociationORM on database")
@DataJpaTest
public class RoleAssociationRepositorySaverTest {

    @Autowired
    RoleAssociationRepository roleAssociationRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When request to save on database then save and return RoleAssociationORM records")
    @Test
    @Sql(value = "/sql/roleassociation/DELETE_ALL_ROLE_ASSOCIATION.sql", executionPhase = AFTER_TEST_METHOD)
    void when_request_to_save_on_database_then_save_and_return_RoleAssociationORM_records() {
        final RoleAssociationORM roleAssociation = from(RoleAssociationORM.class).gimme(PRODUCT_OWNER);

        val result = roleAssociationRepository.save(roleAssociation);
        roleAssociationRepository.flush();

        assertThat(result)
                .extracting("id.teamId", "id.userId", "id.roleId")
                .doesNotContainNull()
                .containsExactly(roleAssociation.getId().getTeamId(), roleAssociation.getId().getUserId(), roleAssociation.getId().getRoleId());

        val roleInDB = jdbcTemplate.queryForList("SELECT * FROM role_user_team_association WHERE role_id = ?1 AND team_id = ?2 AND user_id = ?3",
                PRODUCT_OWNER_ID, roleAssociation.getId().getTeamId(), roleAssociation.getId().getUserId());
        assertThat(roleInDB)
                .isNotEmpty()
                .extracting("team_id", "user_id", "role_id")
                .doesNotContainNull()
                .containsExactly(tuple(roleAssociation.getId().getTeamId(), roleAssociation.getId().getUserId(), roleAssociation.getId().getRoleId()));
    }

    @DisplayName("When request to save on database there is no role then throw exception")
    @Test
    void when_request_to_save_on_database_there_is_no_role_then_throw_exception() {
        final RoleAssociationORM roleAssociation = from(RoleAssociationORM.class).gimme(SCRUM_MASTER);
        roleAssociationRepository.save(roleAssociation);
        assertThrows(DataIntegrityViolationException.class, () -> roleAssociationRepository.flush());
    }
}
