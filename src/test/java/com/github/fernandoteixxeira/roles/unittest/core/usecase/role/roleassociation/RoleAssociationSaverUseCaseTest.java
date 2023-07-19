package com.github.fernandoteixxeira.roles.unittest.core.usecase.role.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaver;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaverUseCase;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
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
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for use case to save a role association")
public class RoleAssociationSaverUseCaseTest {
    @InjectMocks
    RoleAssociationSaverUseCase roleAssociationSaverUseCase;
    @Mock
    RoleAssociationSaver roleAssociationSaver;
    @Captor
    ArgumentCaptor<RoleAssociation> roleAssociationCaptor;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is a role association then save and return it")
    @Test
    void when_there_is_a_role_then_save_and_return_it() {
        final RoleAssociation roleAssociation = from(RoleAssociation.class).gimme(SCRUM_MASTER);
        doReturn(roleAssociation).when(roleAssociationSaver).save(any(RoleAssociation.class));

        val result = roleAssociationSaverUseCase.save(roleAssociation);

        verify(roleAssociationSaver).save(roleAssociationCaptor.capture());

        for (RoleAssociation roleAssociationToAssert : List.of(roleAssociationCaptor.getValue(), result)) {
            assertThat(roleAssociationToAssert)
                    .extracting("teamId", "userId", "roleId")
                    .doesNotContainNull()
                    .containsExactly(roleAssociation.getTeamId(), roleAssociation.getUserId(), roleAssociation.getRoleId());
        }
    }
}
