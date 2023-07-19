package com.github.fernandoteixxeira.roles.fixture.dataprovider;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationIdORM;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.MainFixture.DefaultValues.CREATED_AT;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.PRODUCT_OWNER_ID;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Values.SCRUM_MASTER_ID;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture.Templates.SCRUM_MASTER;
import static java.time.LocalDateTime.now;

public class RoleAssociationORMFixture {
    public static void load() {
        loadScrumMaster();
        loadProductOwner();
    }

    private static void loadScrumMaster() {
        of(RoleAssociationIdORM.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("teamId", "TEAM_ID_SCRUM_MASTER");
            add("userId", "USER_ID_SCRUM_MASTER");
            add("roleId", SCRUM_MASTER_ID);
        }});

        of(RoleAssociationORM.class).addTemplate(SCRUM_MASTER, new Rule() {{
            add("id", one(RoleAssociationIdORM.class, SCRUM_MASTER));
            add("createdAt", CREATED_AT);
        }});
    }

    private static void loadProductOwner() {
        of(RoleAssociationIdORM.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("teamId", "TEAM_ID_PRODUCT_OWNER");
            add("userId", "USER_ID_PRODUCT_OWNER");
            add("roleId", PRODUCT_OWNER_ID);
        }});

        of(RoleAssociationORM.class).addTemplate(PRODUCT_OWNER, new Rule() {{
            add("id", one(RoleAssociationIdORM.class, PRODUCT_OWNER));
            add("createdAt", CREATED_AT);
        }});
    }

    public interface Templates {
        String SCRUM_MASTER = "role_association_orm_scrum_master";
        String PRODUCT_OWNER = "role_association_orm_product_owner";
    }
}
