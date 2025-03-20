package com.auth.userAuth.utils;

import com.auth.userAuth.entities.enums.Permissions;
import com.auth.userAuth.entities.enums.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.auth.userAuth.entities.enums.Permissions.*;
import static com.auth.userAuth.entities.enums.Roles.*;

public class RolePermissionMapping {

    public static final Map<Roles, Set<Permissions>> rolePermissionMap = Map.of(
            USER, Set.of(USER_READ),
            ADMIN, Set.of(USER_CREATE, USER_DELETE, USER_UPDATE),
            CREATE, Set.of(USER_CREATE)
    );

    public static Set<SimpleGrantedAuthority> getGrantedAuthorities(Roles role){
        return rolePermissionMap.get(role).stream()
                .map(permission-> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }


}
