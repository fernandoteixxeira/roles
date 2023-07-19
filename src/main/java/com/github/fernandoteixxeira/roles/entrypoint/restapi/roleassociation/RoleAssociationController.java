package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociationSaverUseCase;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos.LinkResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@Validated
public class RoleAssociationController {

    private final RoleAssociationSaverUseCase roleAssociationSaverUseCase;
    private final UserLinkResponseFactory userLinkResponseFactory;
    private final TeamLinkResponseFactory teamLinkResponseFactory;

    @Transactional
    @PostMapping("/roles/{roleId}/associations")
    @ResponseStatus(CREATED)
    public RoleAssociationResponse addRoleAssociationsByRoleId(
            @NotBlank @PathVariable final String roleId,
            @Valid @RequestBody final RoleAssociationRequest roleAssociationRequest
    ) {
        val roleAssociation = RoleAssociationFromRoleAssociationRequestAdapter.of(roleId, roleAssociationRequest).adapt();
        val roleAssociationSaved = roleAssociationSaverUseCase.save(roleAssociation);
        final List<Function<UserIdGetter, LinkResponse>> linksCreator = List.of(userLinkResponseFactory::create, teamLinkResponseFactory::create);
        return RoleAssociationResponseFromRoleAssociationAdapter.of(roleAssociationSaved, linksCreator).adapt();
    }
}
