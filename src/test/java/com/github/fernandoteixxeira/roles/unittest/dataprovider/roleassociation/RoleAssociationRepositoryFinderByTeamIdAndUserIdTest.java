package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import br.com.six2six.fixturefactory.Fixture;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationRepository;
import com.github.fernandoteixxeira.roles.fixture.MainFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.PRODUCT_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DisplayName("Integration test to find by role_id RoleAssociationORM on database")
@DataJpaTest
public class RoleAssociationRepositoryFinderByTeamIdAndUserIdTest {

    @Autowired
    RoleAssociationRepository roleAssociationRepository;

    @BeforeAll
    static void setup() {
        MainFixture.loadContext();
    }

    @DisplayName("When find by role_id on database then return a list of RoleAssociationRM with one records")
    @Test
    @SqlGroup({
            @Sql(value = "/sql/roleassociation/INSERTION_OF_SCRUM_MASTER_ROLE_ASSOCIATION.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "/sql/roleassociation/DELETE_ALL_ROLE_ASSOCIATION.sql", executionPhase = AFTER_TEST_METHOD)
    })
    void when_find_by_role_id_on_database_then_return_a_list_of_RoleAssociationRM_with_one_records() {
        final RoleAssociationORM roleAssociationORM = Fixture.from(RoleAssociationORM.class).gimme(PRODUCT_OWNER);
        val result = roleAssociationRepository.findByRoleId(PRODUCT_OWNER_ID);
        assertThat(result).isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1)
                .extracting("id.roleId", "id.userId", "id.teamId")
                .contains(tuple(roleAssociationORM.getId().getRoleId(), roleAssociationORM.getId().getUserId(), roleAssociationORM.getId().getTeamId()));
    }
}
