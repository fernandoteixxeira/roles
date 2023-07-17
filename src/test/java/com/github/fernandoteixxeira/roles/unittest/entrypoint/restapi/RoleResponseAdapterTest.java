package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleResponse;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleResponseAdapter;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from Role to RoleResponse")
public class RoleResponseAdapterTest {
    @BeforeAll
    static void setup() {
        loadTemplates("com.github.fernandoteixxeira.roles.fixture");
    }

    @DisplayName("When Role object is non null then return adapted RoleResponse object")
    @Test
    public void when_Role_object_is_non_null_then_return_adapted_RoleResponse_object() {
        final Role role = from(Role.class).gimme(SCRUM_MASTER);
        final RoleResponse response = RoleResponseAdapter.of(role).adapt();
        final RoleResponse expectedResponse = from(RoleResponse.class).gimme(RoleResponseFixture.Templates.SCRUM_MASTER);
        assertThat(response).isNotNull()
                .extracting("id", "description")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getId(), expectedResponse.getDescription());
    }

    @DisplayName("When Role object is null then throw exception")
    @Test
    public void when_Role_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> RoleResponseAdapter.of(null));
    }
}
