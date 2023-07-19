package com.github.fernandoteixxeira.roles.fixture.entrypoint;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationRequestFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationRequestFixture.Templates.SCRUM_MASTER;

public class RoleAssociationRequestFixture {
    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(RoleAssociationRequest.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("teamId", "TEAM_ID_SCRUM_MASTER");
            add("userId", "USER_ID_SCRUM_MASTER");
        }});
    }

    private static void loadProductOwner() {
        of(RoleAssociationRequest.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("teamId", "TEAM_ID_PRODUCT_OWNER");
            add("userId", "USER_ID_PRODUCT_OWNER");
        }});
    }

    public interface Templates {
        String SCRUM_MASTER = "role_association_request_scrum_master";
        String PRODUCT_OWNER = "role_association_request_product_owner";
    }
}
