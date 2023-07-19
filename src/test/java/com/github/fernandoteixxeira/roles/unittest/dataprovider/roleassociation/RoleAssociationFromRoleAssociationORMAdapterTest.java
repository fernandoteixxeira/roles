package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationFromRoleAssociationORMAdapter.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from RoleAssociationORM to RoleAssociation")
public class RoleAssociationFromRoleAssociationORMAdapterTest {
    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When RoleAssociation object is non null then return adapted RoleAssociationORM object")
    @Test
    public void when_RoleAssociation_object_is_non_null_then_return_adapted_RoleAssociationORM_object() {
        final RoleAssociationORM roleAssociationORM = from(RoleAssociationORM.class).gimme(SCRUM_MASTER);
        val result = of(roleAssociationORM).adapt();
        final RoleAssociation expectedResponse = from(RoleAssociation.class).gimme(RoleAssociationFixture.Templates.SCRUM_MASTER);
        assertThat(result).isNotNull()
                .extracting("teamId", "userId", "roleId")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getTeamId(), expectedResponse.getUserId(), expectedResponse.getRoleId());
    }

    @DisplayName("When RoleAssociationORM object is null then throw exception")
    @Test
    public void when_RoleAssociationORM_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> of(null));
    }
}
