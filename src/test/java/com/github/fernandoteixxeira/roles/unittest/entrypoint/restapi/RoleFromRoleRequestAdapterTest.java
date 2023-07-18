package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleRequest;
import com.github.fernandoteixxeira.roles.fixture.core.RoleFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleFromRoleRequestAdapter.of;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from RoleRequest to Role")
public class RoleFromRoleRequestAdapterTest {
    @BeforeAll
    static void setup() {
        loadTemplates("com.github.fernandoteixxeira.roles.fixture");
    }

    @DisplayName("When RoleRequest object is non null then return adapted Role object")
    @Test
    public void when_Role_object_is_non_null_then_return_adapted_RoleResponse_object() {
        final RoleRequest role = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val result = of(role).adapt();
        final Role expectedResponse = from(Role.class).gimme(RoleFixture.Templates.SCRUM_MASTER);
        assertThat(result).isNotNull()
                .extracting("id", "description")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getId(), expectedResponse.getDescription());
    }

    @DisplayName("When RoleRequest object is null then throw exception")
    @Test
    public void when_Role_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> of(null));
    }
}
