package com.github.fernandoteixxeira.roles.fixture;

import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.github.fernandoteixxeira.roles.fixture.core.ListOfRolesFixture;
import com.github.fernandoteixxeira.roles.fixture.core.RoleFixture;
import com.github.fernandoteixxeira.roles.fixture.dataprovider.RoleORMFixture;
import com.github.fernandoteixxeira.roles.fixture.entrypoint.RoleResponseFixture;

import java.time.LocalDateTime;

public class MainFixture implements TemplateLoader {

    @Override
    public void load() {
        RoleFixture.load();
        RoleResponseFixture.load();
        ListOfRolesFixture.load();
        RoleORMFixture.load();
    }

    public interface DefaultValues {
        LocalDateTime CREATED_AT = LocalDateTime.parse("2023-07-01T12:00");
    }
}
