package com.github.fernandoteixxeira.roles.core.usecase.role;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Role {
    private String id;
    private String description;
    private LocalDateTime createdAt;
}
