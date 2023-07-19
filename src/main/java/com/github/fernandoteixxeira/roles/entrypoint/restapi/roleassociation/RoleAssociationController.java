package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationGetterByRoleIdUseCase;
import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaverUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos.LinkResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1")
@Validated
public class RoleAssociationController {

    private final RoleAssociationSaverUseCase roleAssociationSaverUseCase;
    private final RoleAssociationGetterByRoleIdUseCase roleAssociationGetterByRoleIdUseCase;
    private final List<Function<IdGetter, LinkResponse>> linksCreator;

    public RoleAssociationController(
            final RoleAssociationSaverUseCase roleAssociationSaverUseCase,
            final RoleAssociationGetterByRoleIdUseCase roleAssociationGetterUseCase,
            final UserLinkResponseFactory userLinkResponseFactory,
            final TeamLinkResponseFactory teamLinkResponseFactory
    ) {
        this.roleAssociationSaverUseCase = roleAssociationSaverUseCase;
        this.roleAssociationGetterByRoleIdUseCase = roleAssociationGetterUseCase;
        this.linksCreator = List.of(userLinkResponseFactory::create, teamLinkResponseFactory::create);
    }

    @Transactional
    @PostMapping("/roles/{roleId}/associations")
    @ResponseStatus(CREATED)
    public RoleAssociationResponse addRoleAssociationsByRoleId(
            @NotBlank @PathVariable final String roleId,
            @Valid @RequestBody final RoleAssociationRequest roleAssociationRequest
    ) {
        val roleAssociation = RoleAssociationFromRoleAssociationRequestAdapter.of(roleId, roleAssociationRequest).adapt();
        val roleAssociationSaved = roleAssociationSaverUseCase.save(roleAssociation);
        return RoleAssociationResponseFromRoleAssociationAdapter.of(roleAssociationSaved, linksCreator).adapt();
    }

    @Transactional
    @GetMapping("/roles/{roleId}/associations")
    @ResponseStatus(OK)
    public ListOfRoleAssociationResponse getRoleAssociationsByRoleId(@NotBlank @PathVariable final String roleId) {
        val listOfRoleAssociations = roleAssociationGetterByRoleIdUseCase.getByRoleId(roleId);
        val roleAssociations = listOfRoleAssociations
                .transform(roleAssociation -> RoleAssociationResponseFromRoleAssociationAdapter.of(roleAssociation, linksCreator).adapt());
        return ListOfRoleAssociationResponse.builder()
                .associations(roleAssociations)
                .build();
    }
}
