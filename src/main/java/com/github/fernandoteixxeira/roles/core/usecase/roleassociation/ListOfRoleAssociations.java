package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import lombok.Builder;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Builder
public class ListOfRoleAssociations {
    private List<RoleAssociation> roleAssociations;

    public <T> Collection<T> transform(final Function<RoleAssociation, T> transformingMethod) {
        return roleAssociations.stream()
                .map(transformingMethod)
                .toList();
    }

}
