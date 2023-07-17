package com.github.fernandoteixxeira.roles.fixture.entrypoint;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleResponse;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.DefaultValues.CREATED_AT;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture.Templates.SCRUM_MASTER;

public class RoleResponseFixture {
    public interface Templates {
        String SCRUM_MASTER = "role_response_scrum_master";
        String PRODUCT_OWNER = "role_response_product_owner";
    }

    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(RoleResponse.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("id", "SCRUM_MASTER");
            add("description", "Scrum Master");
            add("createdAt", CREATED_AT);
        }});
    }

    private static void loadProductOwner() {
        of(RoleResponse.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("id", "PRODUCT_OWNER");
            add("description", "Product Owner");
            add("createdAt", CREATED_AT);
        }});
    }
}
