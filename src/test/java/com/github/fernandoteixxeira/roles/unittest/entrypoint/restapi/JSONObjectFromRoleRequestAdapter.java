package com.github.fernandoteixxeira.roles.unittest.entrypoint.restapi;

import com.github.fernandoteixxeira.roles.entrypoint.restapi.role.RoleRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.json.JSONObject;

@RequiredArgsConstructor(staticName = "of")
public class JSONObjectFromRoleRequestAdapter {
    private final RoleRequest role;

    @SneakyThrows
    public JSONObject adapt() {
        val json = new JSONObject();
        json.put("id", role.getId());
        json.put("description", role.getDescription());
        return json;
    }
}
