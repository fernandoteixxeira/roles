package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fernandoteixxeira.roles.core.usecase.role.ListOfRoles;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.ListOfRolesResponse;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleController;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleResponse;
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
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit tests for REST API in route GET /role")
@WebMvcTest
@SpringJUnitWebConfig(classes = RoleController.class)
public class RoleControllerRoleGetterTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RolesGetterUseCase rolesGetterUseCase;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        loadTemplates("com.github.fernandoteixxeira.roles.fixture");
    }

    @Test
    @DisplayName("Wen there is a empty list of roles then return status code 200")
    void when_there_is_a_empty_list_of_roles_then_return_200() throws Exception {
        val listOfRoles = from(ListOfRoles.class).gimme(EMPTY);
        doReturn(listOfRoles).when(rolesGetterUseCase).getAllRoles();

        val mvnResult = mockMvc.perform(get("/v1/roles"))
                .andExpect(status().isOk()).andReturn();
        val responseBody = mvnResult.getResponse().getContentAsString();
        val listOfRolesResponse = objectMapper.readValue(responseBody, ListOfRolesResponse.class);

        assertThat(listOfRolesResponse).isNotNull()
                .extracting(ListOfRolesResponse::getRoles)
                .isNotNull()
                .asList()
                .isEmpty();
    }

    @Test
    @DisplayName("Wen there is a non empty list of roles then return status code 200")
    void when_there_is_a_non_empty_list_of_roles_then_return_200() throws Exception {
        val listOfRoles = from(ListOfRoles.class).gimme(SCRUM_MASTER_AND_PRODUCT_OWNER);
        doReturn(listOfRoles).when(rolesGetterUseCase).getAllRoles();

        val mvnResult = mockMvc.perform(get("/v1/roles"))
                .andExpect(status().isOk()).andReturn();
        val responseBody = mvnResult.getResponse().getContentAsString();
        val listOfRolesResponse = objectMapper.readValue(responseBody, ListOfRolesResponse.class);

        val scrumMaster = from(RoleResponse.class).gimme(SCRUM_MASTER);
        val productOwner = from(RoleResponse.class).gimme(PRODUCT_OWNER);
        assertThat(listOfRolesResponse).isNotNull()
                .extracting(ListOfRolesResponse::getRoles)
                .isNotNull()
                .asList()
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(scrumMaster, productOwner);
    }

}
