package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.application.configuration.LanguageConfiguration;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.ListOfRoleAssociations;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByRoleIdUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByTeamMemberUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaverUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.TeamMember;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.handler.GlobalExceptionHandler;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationController;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.TeamLinkResponseFactory;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.UserLinkResponseFactory;
import com.github.fernandoteixxeira.roles.fixture.MainFixture;
import com.github.fernandoteixxeira.roles.fixture.core.TeamMemberFixture;
import com.github.fernandoteixxeira.roles.unittest.core.usecase.role.roleassociation.RoleAssociationGetterByTeamMemberUseCaseTest;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.function.Function;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRoleAssociationsFixture.Templates.EMPTY;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRoleAssociationsFixture.Templates.SCRUM_MASTER_LIST;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationRequestFixture.Templates.SCRUM_MASTER;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit tests for REST API in route GET /v1/teams/{team_id}/users/{user_id}/roles/associations")
@WebMvcTest
@SpringJUnitWebConfig(classes = {RoleAssociationController.class, UserLinkResponseFactory.class, TeamLinkResponseFactory.class
        , GlobalExceptionHandler.class, LanguageConfiguration.class})
public class RoleAssociationControllerGetterByTeamMemberTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RoleAssociationSaverUseCase roleAssociationSaverUseCase;
    @MockBean
    RoleAssociationGetterByRoleIdUseCase roleAssociationGetterByRoleIdUseCase;
    @MockBean
    RoleAssociationGetterByTeamMemberUseCase roleAssociationGetterByTeamMemberUseCase;

    @BeforeAll
    static void setup() {
        MainFixture.loadContext();
    }

    @DisplayName("When there is a empty list of role associations by SCRUM_MASTER then return 200")
    @Test
    void when_there_is_a_empty_list_of_role_associations_by_SCRUM_MASTER_then_return_200() throws Exception {
        final ListOfRoleAssociations listOfRoleAssociations = from(ListOfRoleAssociations.class).gimme(EMPTY);
        doReturn(listOfRoleAssociations).when(roleAssociationGetterByTeamMemberUseCase).getByTeamMember(any(TeamMember.class));
        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);

        mockMvc.perform(get("/v1/teams/{team_id}/users/{user_id}/roles/associations", teamMember.getTeamId(), teamMember.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.associations").isEmpty())
                .andReturn();
    }

    @DisplayName("When there is a non empty list of role associations by SCRUM_MASTER then return 200")
    @Test
    void when_there_is_a_non_empty_list_of_role_associations_by_SCRUM_MASTER_then_return_200() throws Exception {
        final ListOfRoleAssociations listOfRoleAssociations = from(ListOfRoleAssociations.class).gimme(SCRUM_MASTER_LIST);
        doReturn(listOfRoleAssociations).when(roleAssociationGetterByTeamMemberUseCase).getByTeamMember(any(TeamMember.class));
        val roleAssociation = listOfRoleAssociations.transform(Function.identity()).stream().toList().get(0);
        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);

        mockMvc.perform(get("/v1/teams/{team_id}/users/{user_id}/roles/associations", teamMember.getTeamId(), teamMember.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.associations[0].team_id", is(roleAssociation.getTeamId())))
                .andExpect(jsonPath("$.associations[0].user_id", is(roleAssociation.getUserId())))
                .andExpect(jsonPath("$.associations[0].role_id", is(SCRUM_MASTER_ID)))
                .andExpect(jsonPath("$.associations[0].links[0].rel", is("user")))
                .andExpect(jsonPath("$.associations[0].links[1].rel", is("team")))
                .andExpect(jsonPath("$.associations[0].created_at").isNotEmpty())
                .andReturn();
    }

    @DisplayName("When try to get role association having team_id is empty then return 400 with error")
    @Test
    void when_try_to_get_role_association_having_team_id_is_empty_then_return_400_with_error() throws Exception {
        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);

        mockMvc.perform(get("/v1/teams/{team_id}/users/{user_id}/roles/associations", " ", teamMember.getUserId()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("path")))
                .andExpect(jsonPath("$.errors[0].field", is("getRoleAssociationsByTeamMember.teamId")))
                .andExpect(jsonPath("$.errors[0].value", is(" ")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }

    @DisplayName("When try to get role association having user_id is empty then return 400 with error")
    @Test
    void when_try_to_get_role_association_having_user_id_is_empty_then_return_400_with_error() throws Exception {
        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);

        mockMvc.perform(get("/v1/teams/{team_id}/users/{user_id}/roles/associations", teamMember.getTeamId(), " "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("path")))
                .andExpect(jsonPath("$.errors[0].field", is("getRoleAssociationsByTeamMember.userId")))
                .andExpect(jsonPath("$.errors[0].value", is(" ")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }
}
