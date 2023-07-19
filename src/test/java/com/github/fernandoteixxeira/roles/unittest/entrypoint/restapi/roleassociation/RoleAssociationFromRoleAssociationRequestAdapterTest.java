package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationFromRoleAssociationRequestAdapter.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationRequestFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from RoleAssociationRequest to RoleAssociation")
public class RoleAssociationFromRoleAssociationRequestAdapterTest {
    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When RoleAssociationRequest object is non null then return adapted RoleAssociation object")
    @Test
    public void when_RoleAssociationRequest_object_is_non_null_then_return_adapted_RoleAssociation_object() {
        final RoleAssociationRequest roleAssociation = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val result = of(SCRUM_MASTER_ID, roleAssociation).adapt();
        final RoleAssociation expectedResponse = from(RoleAssociation.class).gimme(RoleAssociationFixture.Templates.SCRUM_MASTER);
        assertThat(result).isNotNull()
                .extracting("teamId", "userId", "roleId")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getTeamId(), expectedResponse.getUserId(), expectedResponse.getRoleId());
    }

    @DisplayName("When RoleAssociationRequest object is null then throw exception")
    @Test
    public void when_RoleAssociationRequest_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> of(SCRUM_MASTER_ID, null));
    }

    @DisplayName("When role_id is null then throw exception")
    @Test
    public void when_role_id_is_null_then_throw_exception() {
        final RoleAssociationRequest roleAssociation = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        assertThrows(NullPointerException.class, () -> of(null, roleAssociation));
    }
}
