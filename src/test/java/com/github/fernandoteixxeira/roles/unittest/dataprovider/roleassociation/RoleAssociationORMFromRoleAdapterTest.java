package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationORMFromRoleAdapter.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from RoleAssociation to RoleAssociationORM")
public class RoleAssociationORMFromRoleAdapterTest {
    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When Role object is non null then return adapted RoleAssociationORM object")
    @Test
    public void when_RoleAssociation_object_is_non_null_then_return_adapted_RoleAssociationORM_object() {
        final RoleAssociation roleAssociation = from(RoleAssociation.class).gimme(SCRUM_MASTER);
        val result = of(roleAssociation).adapt();
        final RoleAssociationORM expectedResponse = from(RoleAssociationORM.class).gimme(RoleAssociationORMFixture.Templates.SCRUM_MASTER);
        assertThat(result).isNotNull()
                .extracting("id.teamId", "id.userId", "id.roleId")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getId().getTeamId(), expectedResponse.getId().getUserId(), expectedResponse.getId().getRoleId());
    }

    @DisplayName("When RoleAssociation object is null then throw exception")
    @Test
    public void when_RoleAssociation_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> of(null));
    }

}
