package com.github.fernandoteixxeira.roles.fixture.core;

import br.com.six2six.fixturefactory.Rule;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.ListOfRoleAssociations;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;

import static br.com.six2six.fixturefactory.Fixture.of;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRoleAssociationsFixture.Templates.EMPTY;
import static com.github.fernandoteixxeira.roles.fixture.core.ListOfRoleAssociationsFixture.Templates.SCRUM_MASTER_LIST;
import static com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture.Templates.SCRUM_MASTER;
import static java.util.Collections.emptyList;

public class ListOfRoleAssociationsFixture {
    public static void load() {
        loadEmpty();
        loadScrumMasterAndProductOwner();
    }

    private static void loadEmpty() {
        of(ListOfRoleAssociations.class).addTemplate(EMPTY, new Rule() {{
            add("roleAssociations", emptyList());
        }});
    }

    private static void loadScrumMasterAndProductOwner() {
        of(ListOfRoleAssociations.class).addTemplate(SCRUM_MASTER_LIST, new Rule() {{
            add("roleAssociations", has(1).of(RoleAssociation.class, SCRUM_MASTER));
        }});
    }

    public interface Templates {
        String EMPTY = "list_of_role_associations_empty";
        String SCRUM_MASTER_LIST = "list_of_role_associations_scrum_master";
    }
}
