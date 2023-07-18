package com.github.fernandoteixxeira.roles.unittest.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import com.github.fernandoteixxeira.roles.dataprovider.role.RoleRepository;
import com.github.fernandoteixxeira.roles.dataprovider.role.RolesDataProvider;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.loadContext;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@DisplayName("Unit test for data provider of RoleORM to test All Getter")
@ExtendWith(MockitoExtension.class)
public class RoleDataProviderAllGetterTest {
    @InjectMocks
    RolesDataProvider roleDataProvider;
    @Mock
    RoleRepository roleRepository;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is two records of RoleORM then return a list with them")
    @Test
    void when_there_is_two_records_of_RoleORM_then_return_a_list_with_them() {
        val listOfRoles = from(RoleORM.class).gimme(2, SCRUM_MASTER, PRODUCT_OWNER);
        doReturn(listOfRoles).when(roleRepository).findAll();
        val result = roleDataProvider.getAll();
        assertThat(result)
                .isNotEmpty()
                .hasSize(2)
                .extracting(Role::getId)
                .contains(SCRUM_MASTER_ID, PRODUCT_OWNER_ID);
    }

}
