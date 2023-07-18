package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fernandoteixxeira.roles.application.configuration.LanguageConfiguration;
import com.github.fernandoteixxeira.roles.core.usecase.role.ListOfRoles;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesSaverUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.handler.GlobalExceptionHandler;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleController;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRolesFixture.Templates.EMPTY;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRolesFixture.Templates.SCRUM_MASTER_AND_PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit tests for REST API in route GET /role")
@WebMvcTest
@SpringJUnitWebConfig(classes = {RoleController.class, GlobalExceptionHandler.class, LanguageConfiguration.class})
public class RoleControllerRolesGetterTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RolesGetterUseCase rolesGetterUseCase;
    @MockBean
    RolesSaverUseCase rolesSaverUseCase;

    @BeforeAll
    static void setup() {
        loadTemplates("com.github.fernandoteixxeira.roles.fixture");
    }

    @Test
    @DisplayName("Wen there is a empty list of roles then return status code 200")
    void when_there_is_a_empty_list_of_roles_then_return_200() throws Exception {
        val listOfRoles = from(ListOfRoles.class).gimme(EMPTY);
        doReturn(listOfRoles).when(rolesGetterUseCase).getAllRoles();

        mockMvc.perform(get("/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").isEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Wen there is a non empty list of roles then return status code 200")
    void when_there_is_a_non_empty_list_of_roles_then_return_200() throws Exception {
        val listOfRoles = from(ListOfRoles.class).gimme(SCRUM_MASTER_AND_PRODUCT_OWNER);
        doReturn(listOfRoles).when(rolesGetterUseCase).getAllRoles();

        mockMvc.perform(get("/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").isNotEmpty())
                .andExpect(jsonPath("$.roles[0].id", matchesPattern("^" + SCRUM_MASTER_ID + "|" + PRODUCT_OWNER_ID + "$")))
                .andExpect(jsonPath("$.roles[1].id", matchesPattern("^" + SCRUM_MASTER_ID + "|" + PRODUCT_OWNER_ID + "$")))
                .andReturn();
    }

}
