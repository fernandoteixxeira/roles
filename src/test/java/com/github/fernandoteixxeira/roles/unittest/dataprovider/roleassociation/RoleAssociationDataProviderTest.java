package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationDataProvider;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationRepository;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
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
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("Unit test for data provider of RoleAssociationORM to test Saver")
@ExtendWith(MockitoExtension.class)
public class RoleAssociationDataProviderTest {
    @InjectMocks
    RoleAssociationDataProvider roleAssociationDataProvider;
    @Mock
    RoleAssociationRepository roleAssociationRepository;
    @Captor
    ArgumentCaptor<RoleAssociationORM> roleAssociationORMCaptor;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is a RoleAssociationORM saved then return RoleAssociation")
    @Test
    void when_there_is_a_RoleAssociationORM_saved_then_return_RoleAssociation() {
        final RoleAssociationORM roleAssociationORM = from(RoleAssociationORM.class).gimme(SCRUM_MASTER);
        doReturn(roleAssociationORM).when(roleAssociationRepository).save(any(RoleAssociationORM.class));

        final RoleAssociation roleAssociation = from(RoleAssociation.class).gimme(RoleAssociationFixture.Templates.SCRUM_MASTER);
        val roleSaved = roleAssociationDataProvider.save(roleAssociation);

        assertThat(roleSaved)
                .isNotNull()
                .extracting("teamId", "userId", "roleId")
                .doesNotContainNull()
                .containsExactly(roleAssociationORM.getId().getTeamId(), roleAssociationORM.getId().getUserId(), roleAssociationORM.getId().getRoleId());

        verify(roleAssociationRepository).save(roleAssociationORMCaptor.capture());

        assertThat(roleAssociationORMCaptor.getValue())
                .isNotNull()
                .extracting("id.teamId", "id.userId", "id.roleId")
                .doesNotContainNull()
                .containsExactly(roleAssociation.getTeamId(), roleAssociation.getUserId(), roleAssociation.getRoleId());
    }
}
