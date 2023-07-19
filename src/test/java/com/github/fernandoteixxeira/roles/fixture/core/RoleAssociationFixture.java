package com.github.fernandoteixxeira.roles.fixture.core;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;

import java.time.LocalDateTime;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.SCRUM_MASTER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static java.time.LocalDateTime.now;

public class RoleAssociationFixture {
    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(RoleAssociation.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("teamId", "TEAM_ID_SCRUM_MASTER");
            add("userId", "USER_ID_SCRUM_MASTER");
            add("roleId", SCRUM_MASTER_ID);
            add("createdAt", now());
        }});
    }

    private static void loadProductOwner() {
        of(RoleAssociation.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("teamId", "TEAM_ID_PRODUCT_OWNER");
            add("userId", "USER_ID_PRODUCT_OWNER");
            add("roleId", PRODUCT_OWNER_ID);
            add("createdAt", now());
        }});
    }

    public interface Templates {
        String SCRUM_MASTER = "role_association_scrum_master";
        String PRODUCT_OWNER = "role_association_product_owner";
    }
}
