package com.github.fernandoteixxeira.roles.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationIdORM;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAssociationRepository extends JpaRepository<RoleAssociationORM, RoleAssociationIdORM> {
}
