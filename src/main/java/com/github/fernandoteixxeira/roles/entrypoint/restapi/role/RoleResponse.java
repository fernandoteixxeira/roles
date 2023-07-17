package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RoleResponse {
    private String id;
    private String description;
    private LocalDateTime createdAt;
}
