package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationResponse;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationResponseFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationResponseFromRoleAssociationAdapter.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from RoleAssociation to RoleAssociationResponse")
public class RoleAssociationResponseFromRoleAssociationAdapterTest {
    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When RoleAssociation object is non null then return adapted RoleAssociationResponse object")
    @Test
    public void when_RoleAssociation_object_is_non_null_then_return_adapted_RoleAssociationResponse_object() {
        final RoleAssociation roleAssociation = from(RoleAssociation.class).gimme(SCRUM_MASTER);
        val result = of(roleAssociation, List.of()).adapt();
        final RoleAssociationResponse expectedResponse = from(RoleAssociationResponse.class).gimme(RoleAssociationResponseFixture.Templates.SCRUM_MASTER);
        assertThat(result).isNotNull()
                .extracting("teamId", "userId", "roleId")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getTeamId(), expectedResponse.getUserId(), expectedResponse.getRoleId());
    }

    @DisplayName("When RoleAssociation object is null then throw exception")
    @Test
    public void when_RoleAssociation_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> of(null, List.of()));
    }
}
