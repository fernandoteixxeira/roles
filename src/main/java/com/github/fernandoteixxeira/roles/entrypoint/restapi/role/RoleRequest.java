package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RoleRequest {
    @Size(max = 50)
    @NotBlank
    private String id;
    @Size(max = 200)
    @NotBlank
    private String description;
}
