package com.github.fernandoteixxeira.roles.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.core.usecase.role.RoleGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RoleDataProvider implements RoleGetter {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll().stream()
                .map(role -> RoleAdapter.of(role).adapt())
                .toList();
    }
}
