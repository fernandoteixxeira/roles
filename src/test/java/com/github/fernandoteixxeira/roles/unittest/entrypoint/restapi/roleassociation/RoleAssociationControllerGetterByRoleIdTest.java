package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.application.configuration.LanguageConfiguration;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.ListOfRoleAssociations;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByRoleIdUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaverUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.handler.GlobalExceptionHandler;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationController;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.TeamLinkResponseFactory;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.UserLinkResponseFactory;
import com.github.fernandoteixxeira.roles.fixture.MainFixture;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit tests for REST API in route GET /v1/roles/{role}/associations")
@WebMvcTest
@SpringJUnitWebConfig(classes = {RoleAssociationController.class, UserLinkResponseFactory.class, TeamLinkResponseFactory.class
        , GlobalExceptionHandler.class, LanguageConfiguration.class})
public class RoleAssociationControllerGetterByRoleIdTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RoleAssociationSaverUseCase roleAssociationSaverUseCase;
    @MockBean
    RoleAssociationGetterByRoleIdUseCase roleAssociationGetterByRoleIdUseCase;
    @Captor
    ArgumentCaptor<RoleAssociation> roleAssociationCaptor;

    @BeforeAll
    static void setup() {
        MainFixture.loadContext();
    }

    @DisplayName("When there is a empty list of role associations by SCRUM_MASTER then return 200")
    @Test
    void when_there_is_a_empty_list_of_role_associations_by_SCRUM_MASTER_then_return_200() throws Exception {
        final ListOfRoleAssociations listOfRoleAssociations = from(ListOfRoleAssociations.class).gimme(EMPTY);
        doReturn(listOfRoleAssociations).when(roleAssociationGetterByRoleIdUseCase).getByRoleId(eq(SCRUM_MASTER_ID));

        mockMvc.perform(get("/v1/roles/{role}/associations", SCRUM_MASTER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.associations").isEmpty())
                .andReturn();
    }

    @DisplayName("When there is a non empty list of role associations by SCRUM_MASTER then return 200")
    @Test
    void when_there_is_a_non_empty_list_of_role_associations_by_SCRUM_MASTER_then_return_200() throws Exception {
        final ListOfRoleAssociations listOfRoleAssociations = from(ListOfRoleAssociations.class).gimme(SCRUM_MASTER_LIST);
        doReturn(listOfRoleAssociations).when(roleAssociationGetterByRoleIdUseCase).getByRoleId(eq(SCRUM_MASTER_ID));
        val roleAssociation = listOfRoleAssociations.transform(Function.identity()).stream().toList().get(0);

        mockMvc.perform(get("/v1/roles/{role}/associations", SCRUM_MASTER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.associations[0].team_id", is(roleAssociation.getTeamId())))
                .andExpect(jsonPath("$.associations[0].user_id", is(roleAssociation.getUserId())))
                .andExpect(jsonPath("$.associations[0].role_id", is(SCRUM_MASTER_ID)))
                .andExpect(jsonPath("$.associations[0].links[0].rel", is("user")))
                .andExpect(jsonPath("$.associations[0].links[1].rel", is("team")))
                .andExpect(jsonPath("$.associations[0].created_at").isNotEmpty())
                .andReturn();
    }

    @DisplayName("When try to get role association having role_id is empty then return 400 with error")
    @Test
    void when_try_to_get_role_association_having_role_id_is_empty_then_return_400_with_error() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();

        mockMvc.perform(get("/v1/roles/{roleId}/associations", " ")
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("path")))
                .andExpect(jsonPath("$.errors[0].field", is("getRoleAssociationsByRoleId.roleId")))
                .andExpect(jsonPath("$.errors[0].value", is(" ")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }
}
