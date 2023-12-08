package ru.mtuci.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER(Set.of(Permission.READ)),
    REDACTOR(Set.of(Permission.READ,Permission.CREATE)),
    MODERATOR(Set.of(Permission.READ, Permission.CREATE,Permission.MODIFICATION)),
    ADMIN(Set.of(Permission.READ, Permission.CREATE, Permission.MODIFICATION, Permission.DELETE));
    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> grantedAuthority() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        return authorities;
    }
}
