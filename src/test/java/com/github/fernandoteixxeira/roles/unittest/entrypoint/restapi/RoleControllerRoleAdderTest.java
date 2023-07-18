package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi;


import com.github.fernandoteixxeira.roles.application.configuration.LanguageConfiguration;
import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesSaverUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.handler.GlobalExceptionHandler;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleController;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleRequest;
import com.github.fernandoteixxeira.roles.fixture.core.RoleFixture;
import com.github.javafaker.Faker;
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

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit tests for REST API in route POST /role")
@WebMvcTest
@SpringJUnitWebConfig(classes = {RoleController.class, GlobalExceptionHandler.class, LanguageConfiguration.class})
public class RoleControllerRoleAdderTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RolesGetterUseCase rolesGetterUseCase;
    @MockBean
    RolesSaverUseCase rolesSaverUseCase;
    @Captor
    ArgumentCaptor<Role> roleCaptor;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When create role successfully then return 200")
    @Test
    void when_create_role_successfully_then_return_200() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();

        final Role role = from(Role.class).gimme(RoleFixture.Templates.SCRUM_MASTER);
        doReturn(role).when(rolesSaverUseCase).save(any(Role.class));

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(roleRequest.getId())))
                .andExpect(jsonPath("$.description", is(roleRequest.getDescription())))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andReturn();

        verify(rolesSaverUseCase).save(roleCaptor.capture());
        assertThat(roleCaptor.getValue())
                .extracting("id", "description")
                .doesNotContainNull()
                .containsExactly(roleRequest.getId(), roleRequest.getDescription());
    }

    @DisplayName("When create role having id is null then return 400 with error")
    @Test
    void when_create_role_having_id_is_null_then_return_400_with_error() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();
        roleJsonObject.put("id", null);

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].value", is("null")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be empty")))
                .andReturn();
    }

    @DisplayName("When create role having id_greater than 50 chars then return 400 with error")
    @Test
    void when_create_role_having_description_is_null_then_return_400_with_error() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();
        roleJsonObject.put("description", null);

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("description")))
                .andExpect(jsonPath("$.errors[0].value", is("null")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be empty")))
                .andReturn();
    }

    @DisplayName("When create role having id_greater than 50 chars then return 400 with error")
    @Test
    void when_create_role_having_id_is_empty_then_return_400_with_error() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();
        roleJsonObject.put("id", "");

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].value", is("")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be empty")))
                .andReturn();
    }

    @DisplayName("When create role having id_greater than 50 chars then return 400 with error")
    @Test
    void when_create_role_having_description_is_empty_then_return_400_with_error() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();
        roleJsonObject.put("description", "");

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("description")))
                .andExpect(jsonPath("$.errors[0].value", is("")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be empty")))
                .andReturn();
    }

    @DisplayName("When create role having id_greater than 50 chars then return 400 with error")
    @Test
    void when_create_role_having_id_greater_than_50_chars_then_return_400_with_error() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();
        roleJsonObject.put("id", Faker.instance().lorem().characters(51, 100));

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is("size must be between 0 and 50")))
                .andReturn();
    }

    @DisplayName("When create role having id_greater than 50 chars then return 400 with error")
    @Test
    void when_create_role_having_description_greater_than_200_chars_then_return_400_with_error() throws Exception {
        final RoleRequest roleRequest = from(RoleRequest.class).gimme(SCRUM_MASTER);
        val roleJsonObject = JSONObjectFromRoleRequestAdapter.of(roleRequest).adapt();
        roleJsonObject.put("description", Faker.instance().lorem().characters(201, 300));

        mockMvc.perform(post("/v1/roles")
                        .contentType("application/json")
                        .content(roleJsonObject.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("some fields contain errors")))
                .andExpect(jsonPath("$.errors[0].scope", is("attribute")))
                .andExpect(jsonPath("$.errors[0].field", is("description")))
                .andExpect(jsonPath("$.errors[0].message", is("size must be between 0 and 200")))
                .andReturn();
    }

}
