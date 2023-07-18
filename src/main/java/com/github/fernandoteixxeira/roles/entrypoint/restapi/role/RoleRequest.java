package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RoleRequest {
    @Size(max = 50)
    @NotEmpty
    private String id;
    @Size(max = 200)
    @NotEmpty
    private String description;
}
