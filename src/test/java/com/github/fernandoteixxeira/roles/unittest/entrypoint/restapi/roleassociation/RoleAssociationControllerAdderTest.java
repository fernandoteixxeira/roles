package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.application.configuration.LanguageConfiguration;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaverUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.handler.GlobalExceptionHandler;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleRequest;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationController;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;
import com.github.fernandoteixxeira.roles.fixture.MainFixture;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture;
import com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.role.JSONObjectFromRoleRequestAdapter;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationRequestFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit tests for REST API in route GET /v1/roles/{role}/associations")
@WebMvcTest
@SpringJUnitWebConfig(classes = {RoleAssociationController.class, GlobalExceptionHandler.class, LanguageConfiguration.class})
public class RoleAssociationControllerAdderTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RoleAssociationSaverUseCase roleAssociationSaverUseCase;
    @Captor
    ArgumentCaptor<RoleAssociation> roleAssociationCaptor;

    @BeforeAll
    static void setup() {
        MainFixture.loadContext();
    }

    @DisplayName("When was input a role association to save then save and returned")
    @Test
    void when_was_input_a_role_association_to_save_then_save_and_returned() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();

        final RoleAssociation roleAssociation = from(RoleAssociation.class).gimme(RoleAssociationFixture.Templates.SCRUM_MASTER);
        doReturn(roleAssociation).when(roleAssociationSaverUseCase).save(any(RoleAssociation.class));

        mockMvc.perform(post("/v1/roles/{role}/associations", SCRUM_MASTER_ID)
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.teamId", is(roleAssociation.getTeamId())))
                .andExpect(jsonPath("$.userId", is(roleAssociation.getUserId())))
                .andExpect(jsonPath("$.roleId", is(SCRUM_MASTER_ID)))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andReturn();

        verify(roleAssociationSaverUseCase).save(roleAssociationCaptor.capture());
        assertThat(roleAssociationCaptor.getValue())
                .extracting("teamId", "userId", "roleId")
                .doesNotContainNull()
                .containsExactly(roleAssociationRequest.getTeamId(), roleAssociationRequest.getUserId(), SCRUM_MASTER_ID);
    }

    @DisplayName("When create role association having team_id is null then return 400 with error")
    @Test
    void when_create_role_association_having_team_id_is_null_then_return_400_with_error() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();
        roleAssociationJsonObject.put("teamId", null);

        mockMvc.perform(post("/v1/roles/{role}/associations", SCRUM_MASTER_ID)
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("teamId")))
                .andExpect(jsonPath("$.errors[0].value", is("null")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }

    @DisplayName("When create role association having user_id is null then return 400 with error")
    @Test
    void when_create_role_association_having_user_id_is_null_then_return_400_with_error() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();
        roleAssociationJsonObject.put("userId", null);

        mockMvc.perform(post("/v1/roles/{role}/associations", SCRUM_MASTER_ID)
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("userId")))
                .andExpect(jsonPath("$.errors[0].value", is("null")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }

    @DisplayName("When create role association having team_id is empty then return 400 with error")
    @Test
    void when_create_role_association_having_team_id_is_empty_then_return_400_with_error() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();
        roleAssociationJsonObject.put("teamId", " ");

        mockMvc.perform(post("/v1/roles/{role}/associations", SCRUM_MASTER_ID)
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("teamId")))
                .andExpect(jsonPath("$.errors[0].value", is(" ")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }

    @DisplayName("When create role association having user_id is empty then return 400 with error")
    @Test
    void when_create_role_association_having_user_id_is_empty_then_return_400_with_error() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();
        roleAssociationJsonObject.put("userId", " ");

        mockMvc.perform(post("/v1/roles/{role}/associations", SCRUM_MASTER_ID)
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("userId")))
                .andExpect(jsonPath("$.errors[0].value", is(" ")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }

    @DisplayName("When create role association having role_id is empty then return 400 with error")
    @Test
    void when_create_role_association_having_role_id_is_empty_then_return_400_with_error() throws Exception {
        final RoleAssociationRequest roleAssociationRequest = from(RoleAssociationRequest.class).gimme(SCRUM_MASTER);
        val roleAssociationJsonObject = JSONObjectFromRoleAssociationRequestAdapter.of(roleAssociationRequest).adapt();

        mockMvc.perform(post("/v1/roles/{roleId}/associations", " ")
                        .contentType("application/json")
                        .content(roleAssociationJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("path")))
                .andExpect(jsonPath("$.errors[0].field", is("addRoleAssociationsByRoleId.roleId")))
                .andExpect(jsonPath("$.errors[0].value", is(" ")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andReturn();
    }
}
