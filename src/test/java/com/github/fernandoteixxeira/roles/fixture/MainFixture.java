package com.github.fernandoteixxeira.roles.fixture;

import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.github.fernandoteixxeira.roles.fixture.core.ListOfRoleAssociationsFixture;
import com.github.fernandoteixxeira.roles.fixture.core.ListOfRolesFixture;
import com.github.fernandoteixxeira.roles.fixture.core.RoleAssociationFixture;
import com.github.fernandoteixxeira.roles.fixture.core.RoleFixture;
import com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleAssociationORMFixture;
import com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationRequestFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleAssociationResponseFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleRequestFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture;

import java.time.LocalDateTime;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;

public class MainFixture implements TemplateLoader {

    public static void loadContext() {
        loadTemplates("com.github.fernandoteixxeira.roles.fixture");
    }

    @Override
    public void load() {
        RoleFixture.load();
        RoleRequestFixture.load();
        RoleResponseFixture.load();
        RoleAssociationRequestFixture.load();
        RoleAssociationResponseFixture.load();
        ListOfRolesFixture.load();
        ListOfRoleAssociationsFixture.load();
        RoleAssociationFixture.load();
        RoleORMFixture.load();
        RoleAssociationORMFixture.load();
    }

    public interface DefaultValues {
        LocalDateTime CREATED_AT = LocalDateTime.parse("2023-07-01T12:00");
    }
}
