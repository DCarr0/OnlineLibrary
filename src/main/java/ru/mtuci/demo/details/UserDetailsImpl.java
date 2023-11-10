package ru.mtuci.demo.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mtuci.demo.models.User;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

   @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static UserDetails fromApplicationUser(User user) {
        var authorities = user.getRoles().stream()
                .map(role -> role.getName().grantedAuthority())
                .flatMap(Set::stream)
                .toList();
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
