package com.github.fernandoteixxeira.roles.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationIdORM;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface RoleAssociationRepository extends JpaRepository<RoleAssociationORM, RoleAssociationIdORM> {
    @Query("SELECT ra FROM RoleAssociationORM ra WHERE ra.id.roleId = ?1")
    List<RoleAssociationORM> findByRoleId(final String roleId);
}
