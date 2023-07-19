package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;


import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.role.RolesSaverUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private final RolesGetterUseCase rolesGetterUseCase;
    private final RolesSaverUseCase rolesSaverUseCase;

    @Transactional(propagation = NOT_SUPPORTED)
    @GetMapping
    @ResponseStatus(OK)
    public ListOfRolesResponse getRoles() {
        val roles = rolesGetterUseCase.getAllRoles().transform(role -> RoleResponseFromRoleAdapter.of(role).adapt());
        return ListOfRolesResponse.builder()
                .roles(roles)
                .build();
    }

    @Transactional
    @PostMapping
    @ResponseStatus(CREATED)
    public RoleResponse addRoles(@Valid @RequestBody final RoleRequest roleRequest) {
        val role = RoleFromRoleRequestAdapter.of(roleRequest).adapt();
        val roleSaved = rolesSaverUseCase.save(role);
        return RoleResponseFromRoleAdapter.of(roleSaved).adapt();
    }
}
