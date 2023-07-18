package com.github.fernandoteixxeira.roles.fixture.dataprovider;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.DefaultValues.CREATED_AT;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture.Templates.SCRUM_MASTER;

public class RoleORMFixture {
    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(RoleORM.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("id", "SCRUM_MASTER");
            add("description", "Scrum Master");
            add("createdAt", CREATED_AT);
        }});
    }

    private static void loadProductOwner() {
        of(RoleORM.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("id", "PRODUCT_OWNER");
            add("description", "Product Owner");
            add("createdAt", CREATED_AT);
        }});
    }

    public interface Templates {
        String SCRUM_MASTER = "role_orm_scrum_master";
        String PRODUCT_OWNER = "role_orm_product_owner";
    }
}
