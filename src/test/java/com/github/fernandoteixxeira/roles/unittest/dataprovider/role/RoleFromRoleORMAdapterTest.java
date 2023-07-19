package com.github.fernandoteixxeira.roles.unittest.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import com.github.fernandoteixxeira.roles.fixture.core.RoleFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.dataprovider.role.RoleFromRoleORMAdapter.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@DisplayName("Unit test for Adapter that adapts from RoleORM to Role")
public class RoleFromRoleORMAdapterTest {
    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When Role object is non null then return adapted RoleORM object")
    @Test
    public void when_Role_object_is_non_null_then_return_adapted_RoleORM_object() {
        final RoleORM role = from(RoleORM.class).gimme(SCRUM_MASTER);
        val result = of(role).adapt();
        final Role expectedResponse = from(Role.class).gimme(RoleFixture.Templates.SCRUM_MASTER);
        assertThat(result).isNotNull()
                .extracting("id", "description")
                .doesNotContainNull()
                .containsSequence(expectedResponse.getId(), expectedResponse.getDescription());
    }

    @DisplayName("When RoleORM object is null then throw exception")
    @Test
    public void when_Role_object_is_null_then_throw_exception() {
        assertThrows(NullPointerException.class, () -> of(null));
    }
}
