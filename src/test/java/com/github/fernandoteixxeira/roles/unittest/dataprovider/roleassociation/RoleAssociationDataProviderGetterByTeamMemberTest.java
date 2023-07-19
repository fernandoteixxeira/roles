package com.github.fernandoteixxeira.roles.unittest.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.TeamMember;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationDataProvider;
import com.github.fernandoteixxeira.roles.dataprovider.roleassociation.RoleAssociationRepository;
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
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.SCRUM_MASTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@DisplayName("Unit test for data provider of RoleAssociation to test Getter by team member (team_id and user_id)")
@ExtendWith(MockitoExtension.class)
public class RoleAssociationDataProviderGetterByTeamMemberTest {
    @InjectMocks
    RoleAssociationDataProvider roleAssociationDataProvider;
    @Mock
    RoleAssociationRepository roleAssociationRepository;

    @BeforeAll
    static void setup() {
        loadContext();
    }

    @DisplayName("When there is one records of RoleAssociation where TeamMember is SCRUM_MASTER then return a list with it")
    @Test
    void when_there_is_one_records_of_RoleAssociation_where_TeamMember_is_SCRUM_MASTER_then_return_a_list_with_id() {
        final RoleAssociationORM roleAssociationORM = from(RoleAssociationORM.class).gimme(SCRUM_MASTER);
        doReturn(List.of(roleAssociationORM)).when(roleAssociationRepository)
                .findByTeamIdAndMemberId(eq(roleAssociationORM.getId().getTeamId()), eq(roleAssociationORM.getId().getUserId()));
        final TeamMember teamMember = from(TeamMember.class).gimme(TeamMemberFixture.Templates.SCRUM_MASTER);
        val result = roleAssociationDataProvider.getByTeamMember(teamMember);
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .extracting("roleId")
                .contains(SCRUM_MASTER_ID);
    }

}
