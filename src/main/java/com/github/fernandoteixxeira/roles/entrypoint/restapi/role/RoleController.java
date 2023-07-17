package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;


import com.github.fernandoteixxeira.roles.core.usecase.role.RolesGetterUseCase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private final RolesGetterUseCase rolesGetterUseCase;

    @GetMapping
    public ListOfRolesResponse getRoles() {
        val roles = rolesGetterUseCase.getAllRoles().transform(role -> RoleResponseAdapter.of(role).adapt());
        return ListOfRolesResponse.builder()
                .roles(roles)
                .build();
    }
}
