package com.github.fernandoteixxeira.roles.unittest.core.usecase.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetter;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.SCRUM_MASTER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for use case to get the list of roles")
public class RolesGetterUseCaseTest {

    @InjectMocks
    RolesGetterUseCase rolesGetterUseCase;
    @Mock
    RolesGetter rolesGetter;

    @BeforeAll
    static void setup() {
        loadTemplates("com.github.fernandoteixxeira.roles.fixture");
    }

    @DisplayName("When list of roles is non empty then return a object with them")
    @Test
    void when_list_of_roles_is_non_empty_then_return_a_object_with_them() {
        val listOfRoles = from(Role.class).gimme(2, SCRUM_MASTER, PRODUCT_OWNER);
        doReturn(listOfRoles).when(rolesGetter).getAll();

        val result = rolesGetterUseCase.getAllRoles();

        assertThat(result).isNotNull();

        val listOfRolesReturned = result.transform(identity());

        assertThat(listOfRolesReturned).isNotEmpty().extracting(Role::getId).contains(SCRUM_MASTER_ID, PRODUCT_OWNER_ID);
    }

    @DisplayName("When list of roles is empty then return a object with no role")
    @Test
    void when_list_of_roles_is_empty_then_return_a_object_with_no_role() {
        doReturn(emptyList()).when(rolesGetter).getAll();

        val result = rolesGetterUseCase.getAllRoles();

        assertThat(result).isNotNull();

        val listOfRolesReturned = result.transform(identity());

        assertThat(listOfRolesReturned).isEmpty();
    }
}
