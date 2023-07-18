package com.github.fernandoteixxeira.roles.unittest.core.usecase.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.role.RoleSaver;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesSaverUseCase;
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

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for use case to save a role")
public class RoleSaverUseCaseTest {
    @InjectMocks
    RolesSaverUseCase rolesSaverUseCase;
    @Mock
    RoleSaver roleSaver;
    @Captor
    ArgumentCaptor<Role> roleCaptor;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is a role then save and return it")
    @Test
    void when_there_is_a_role_then_save_and_return_it() {
        final Role role = from(Role.class).gimme(SCRUM_MASTER);
        doReturn(role).when(roleSaver).save(any(Role.class));

        val result = rolesSaverUseCase.save(role);

        verify(roleSaver).save(roleCaptor.capture());

        for (Role roleToAssert : List.of(roleCaptor.getValue(), result)) {
            assertThat(roleToAssert)
                    .extracting("id", "description")
                    .doesNotContainNull()
                    .containsExactly(role.getId(), role.getDescription());
        }
    }
}
