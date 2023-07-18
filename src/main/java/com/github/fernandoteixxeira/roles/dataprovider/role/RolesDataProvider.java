package com.github.fernandoteixxeira.roles.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.role.RoleSaver;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RolesDataProvider implements RolesGetter, RoleSaver {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll().stream()
                .map(role -> RoleFromRoleORMAdapter.of(role).adapt())
                .toList();
    }

    @Override
    public Role save(Role role) {
        val roleOrm = RoleORMFromRoleAdapter.of(role).adapt();
        val roleSaved = roleRepository.save(roleOrm);
        return RoleFromRoleORMAdapter.of(roleSaved).adapt();
    }
}
