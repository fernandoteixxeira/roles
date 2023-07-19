package com.github.fernandoteixxeira.roles.unittest.core.usecase.role.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByTeamMemberUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByTeamMember;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.TeamMember;
import com.github.fernandoteixxeira.roles.fixture.core.TeamMemberFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.SCRUM_MASTER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for use case to get the list of role associations by TeamMember (team_id and user_id)")
public class RoleAssociationGetterByTeamMemberUseCaseTest {

    @InjectMocks
    RoleAssociationGetterByTeamMemberUseCase roleAssociationGetterByTeamMemberUseCase;
    @Mock
    RoleAssociationGetterByTeamMember roleAssociationGetterByRoleId;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When list of role associations is non empty then return a object with it")
    @Test
    void when_list_of_role_associations_is_non_empty_then_return_a_object_with_it() {
        val roleAssociation = from(RoleAssociation.class).gimme(SCRUM_MASTER);
        doReturn(List.of(roleAssociation)).when(roleAssociationGetterByRoleId).getByTeamMember(any(TeamMember.class));

        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);
        val result = roleAssociationGetterByTeamMemberUseCase.getByTeamMember(teamMember);

        assertThat(result).isNotNull();

        val listOfRolesReturned = result.transform(identity());

        assertThat(listOfRolesReturned)
                .isNotEmpty()
                .extracting("roleId")
                .contains(SCRUM_MASTER_ID);
    }

    @DisplayName("When list of role associations is empty then return a object with no role")
    @Test
    void when_list_of_role_associations_is_empty_then_return_a_object_with_no_role() {
        doReturn(emptyList()).when(roleAssociationGetterByRoleId).getByTeamMember(any(TeamMember.class));

        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);
        val result = roleAssociationGetterByTeamMemberUseCase.getByTeamMember(teamMember);

        assertThat(result).isNotNull();

        val listOfRolesReturned = result.transform(identity());

        assertThat(listOfRolesReturned).isEmpty();
    }
}
