package com.github.fernandoteixxeira.roles.core.usecase.role;

import lombok.Builder;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
public class ListOfRoles {
    private List<Role> roles;

    public <T> Collection<T> transform(final Function<Role, T> transformingMethod) {
        return roles.stream()
                .map(transformingMethod)
                .toList();
    }
}
