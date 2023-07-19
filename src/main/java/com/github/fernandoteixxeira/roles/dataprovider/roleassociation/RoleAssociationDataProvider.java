package com.github.fernandoteixxeira.roles.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaver;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RoleAssociationDataProvider implements RoleAssociationSaver {
    private final RoleAssociationRepository roleAssociationRepository;
    @Override
    public RoleAssociation save(RoleAssociation roleAssociation) {
        val roleOrm = RoleAssociationORMFromRoleAdapter.of(roleAssociation).adapt();
        val roleSaved = roleAssociationRepository.save(roleOrm);
        return RoleAssociationFromRoleAssociationORMAdapter.of(roleSaved).adapt();
    }
}
