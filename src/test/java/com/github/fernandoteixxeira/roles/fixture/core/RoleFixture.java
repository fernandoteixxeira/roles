package com.github.fernandoteixxeira.roles.fixture.core;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.core.usecase.role.Role;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.DefaultValues.CREATED_AT;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.SCRUM_MASTER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;

public class RoleFixture {
    public interface Templates {
        String SCRUM_MASTER = "role_scrum_master";
        String PRODUCT_OWNER = "role_product_owner";
    }

    public interface Values {
        String SCRUM_MASTER_ID = "SCRUM_MASTER";
        String PRODUCT_OWNER_ID = "PRODUCT_OWNER";
    }

    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(Role.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("id", SCRUM_MASTER_ID);
            add("description", "Scrum Master");
            add("createdAt", CREATED_AT);
        }});
    }

    private static void loadProductOwner() {
        of(Role.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("id", PRODUCT_OWNER_ID);
            add("description", "Product Owner");
            add("createdAt", CREATED_AT);
        }});
    }
}
