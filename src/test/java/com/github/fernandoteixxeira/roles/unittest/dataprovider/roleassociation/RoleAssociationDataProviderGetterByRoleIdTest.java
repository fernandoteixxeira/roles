package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationDataProvider;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationRepository;
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
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@DisplayName("Unit test for data provider of RoleAssociation to test Getter by role_id")
@ExtendWith(MockitoExtension.class)
public class RoleAssociationDataProviderGetterByRoleIdTest {
    @InjectMocks
    RoleAssociationDataProvider roleAssociationDataProvider;
    @Mock
    RoleAssociationRepository roleAssociationRepository;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is one records of RoleAssociation where role_id is SCRUM_MASTER then return a list with it")
    @Test
    void when_there_is_two_records_of_one_records_of_RoleAssociation_where_role_id_is_SCRUM_MASTER_then_return_a_list_with_id() {
        val roleAssociationORM = from(RoleAssociationORM.class).gimme(SCRUM_MASTER);
        doReturn(List.of(roleAssociationORM)).when(roleAssociationRepository).findByRoleId(eq(SCRUM_MASTER_ID));
        val result = roleAssociationDataProvider.getByRoleId(SCRUM_MASTER_ID);
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .extracting("roleId")
                .contains(SCRUM_MASTER_ID);
    }

}
