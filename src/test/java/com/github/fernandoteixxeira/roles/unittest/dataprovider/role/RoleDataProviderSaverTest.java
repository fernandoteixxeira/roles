package com.github.fernandoteixxeira.roles.unittest.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import com.github.fernandoteixxeira.roles.dataprovider.role.RoleRepository;
import com.github.fernandoteixxeira.roles.dataprovider.role.RolesDataProvider;
import com.github.fernandoteixxeira.roles.fixture.core.RoleFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@DisplayName("Unit test for data provider of RoleORM to test Saver")
@ExtendWith(MockitoExtension.class)
public class RoleDataProviderSaverTest {
    @InjectMocks
    RolesDataProvider roleDataProvider;
    @Mock
    RoleRepository roleRepository;
    @Captor
    ArgumentCaptor<RoleORM> roleORMCaptor;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is a RoleORM saved then return Role")
    @Test
    void when_there_is_a_RoleORM_saved_then_return_Role() {
        val roleOrm = from(RoleORM.class).gimme(SCRUM_MASTER);
        doReturn(roleOrm).when(roleRepository).save(any(RoleORM.class));

        final Role role = from(Role.class).gimme(RoleFixture.Templates.SCRUM_MASTER);
        val roleSaved = roleDataProvider.save(role);

        assertThat(roleSaved)
                .isNotNull()
                .extracting("id", "description")
                .doesNotContainNull()
                .containsExactly(role.getId(), role.getDescription());

        verify(roleRepository).save(roleORMCaptor.capture());

        assertThat(roleORMCaptor.getValue())
                .isNotNull()
                .extracting("id", "description")
                .doesNotContainNull()
                .containsExactly(role.getId(), role.getDescription());

    }

}
