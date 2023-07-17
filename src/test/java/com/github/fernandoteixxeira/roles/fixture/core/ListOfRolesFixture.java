package com.github.fernandoteixxeira.roles.fixture.core;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.core.usecase.role.ListOfRoles;
import com.github.fernandoteixxeira.roles.core.usecase.role.Role;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRolesFixture.Templates.EMPTY;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRolesFixture.Templates.SCRUM_MASTER_AND_PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.PRODUCT_OWNER;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleFixture.Templates.SCRUM_MASTER;
import static java.util.Collections.emptyList;

public class ListOfRolesFixture {

    public interface Templates {
        String EMPTY = "list_of_roles_empty";
        String SCRUM_MASTER_AND_PRODUCT_OWNER = "list_of_roles_scrum_master_and_product_owner";
    }

    public static void load() {
        loadEmpty();
        loadScrumMasterAndProductOwner();
    }

    private static void loadEmpty() {
        of(ListOfRoles.class).addTemplate(EMPTY, new Rule() {{
            add("roles", emptyList());
        }});
    }

    private static void loadScrumMasterAndProductOwner() {
        of(ListOfRoles.class).addTemplate(SCRUM_MASTER_AND_PRODUCT_OWNER, new Rule() {{
            add("roles", has(2).of(Role.class, SCRUM_MASTER, PRODUCT_OWNER));
        }});
    }
}
