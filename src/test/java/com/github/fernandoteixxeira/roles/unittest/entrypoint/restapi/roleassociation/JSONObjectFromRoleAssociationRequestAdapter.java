package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleRequest;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation.RoleAssociationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.json.JSONObject;

@RequiredArgsConstructor(staticName = "of")
public class JSONObjectFromRoleAssociationRequestAdapter {
    private final RoleAssociationRequest role;

    @SneakyThrows
    public JSONObject adapt() {
        val json = new JSONObject();
        json.put("teamId", role.getTeamId());
        json.put("userId", role.getUserId());
        return json;
    }
}
