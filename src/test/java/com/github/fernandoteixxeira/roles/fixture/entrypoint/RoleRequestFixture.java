package com.github.fernandoteixxeira.roles.fixture.entrypoint;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleRequest;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture.Templates.SCRUM_MASTER;

public class RoleRequestFixture {
    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(RoleRequest.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("id", "SCRUM_MASTER");
            add("description", "Scrum Master");
        }});
    }

    private static void loadProductOwner() {
        of(RoleRequest.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("id", "PRODUCT_OWNER");
            add("description", "Product Owner");
        }});
    }

    public interface Templates {
        String SCRUM_MASTER = "role_request_scrum_master";
        String PRODUCT_OWNER = "role_request_product_owner";
    }
}
