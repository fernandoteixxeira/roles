package com.github.fernandoteixxeira.roles.fixture.core;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.TeamMember;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.core.TeamMemberFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.TeamMemberFixture.Templates.SCRUM_MASTER;

public class TeamMemberFixture {
    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(TeamMember.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("teamId", "TEAM_ID_SCRUM_MASTER");
            add("userId", "USER_ID_SCRUM_MASTER");
        }});
    }

    private static void loadProductOwner() {
        of(TeamMember.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("teamId", "TEAM_ID_PRODUCT_OWNER");
            add("userId", "USER_ID_PRODUCT_OWNER");
        }});
    }

    public interface Templates {
        String SCRUM_MASTER = "team_member_scrum_master";
        String PRODUCT_OWNER = "team_member_product_owner";
    }
}
