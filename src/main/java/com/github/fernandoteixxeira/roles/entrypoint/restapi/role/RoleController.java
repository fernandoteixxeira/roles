package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;


import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private final RolesGetterUseCase rolesGetterUseCase;

    @Transactional(propagation = NOT_SUPPORTED)
    @GetMapping
    public ListOfRolesResponse getRoles() {
        val roles = rolesGetterUseCase.getAllRoles().transform(role -> RoleResponseAdapter.of(role).adapt());
        return ListOfRolesResponse.builder()
                .roles(roles)
                .build();
    }
}
