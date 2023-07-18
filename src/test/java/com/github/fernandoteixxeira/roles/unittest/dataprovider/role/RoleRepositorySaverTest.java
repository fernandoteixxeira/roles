package com.github.fernandoteixxeira.roles.unittest.dataprovider.role;

import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import com.github.fernandoteixxeira.roles.dataprovider.role.RoleRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@DisplayName("Integration test to find all RoleORM on database")
@DataJpaTest
public class RoleRepositorySaverTest {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When find all on database then return a list of RoleORM with three or more records")
    @Test
    @Sql(value = "/sql/role/DELETE_ROLE_ID_SCRUM_MASTER.sql", executionPhase = AFTER_TEST_METHOD)
    void when_find_all_on_database_then_return_a_list_of_RoleORM_with_three_or_more_records() {
        final RoleORM role = from(RoleORM.class).gimme(SCRUM_MASTER);

        val result = roleRepository.save(role);
        roleRepository.flush();

        assertThat(result)
                .extracting(RoleORM::getId)
                .isEqualTo(SCRUM_MASTER_ID);

        val roleInDB = jdbcTemplate.queryForList("SELECT * FROM role WHERE id = ?1", SCRUM_MASTER_ID);
        assertThat(roleInDB)
                .isNotEmpty()
                .extracting("id", "description")
                .doesNotContainNull()
                .containsExactly(tuple(role.getId(), role.getDescription()));
    }
}
