package com.github.fernandoteixxeira.roles.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.ListOfRoleAssociations;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByRoleId;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaver;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RoleAssociationDataProvider implements RoleAssociationSaver, RoleAssociationGetterByRoleId {
    private final RoleAssociationRepository roleAssociationRepository;
    @Override
    public RoleAssociation save(RoleAssociation roleAssociation) {
        val roleAssociationORM = RoleAssociationORMFromRoleAdapter.of(roleAssociation).adapt();
        val savedRoleAssociationORM = roleAssociationRepository.save(roleAssociationORM);
        return RoleAssociationFromRoleAssociationORMAdapter.of(savedRoleAssociationORM).adapt();
    }

    @Override
    public List<RoleAssociation> getByRoleId(String roleId) {
        val roleAssociationsORM = roleAssociationRepository.findByRoleId(roleId);
        return roleAssociationsORM.stream()
                .map(roleAssociationORM -> RoleAssociationFromRoleAssociationORMAdapter.of(roleAssociationORM).adapt())
                .toList();
    }
}
