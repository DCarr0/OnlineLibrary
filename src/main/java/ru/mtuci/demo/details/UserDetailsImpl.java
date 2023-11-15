package ru.mtuci.demo.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mtuci.demo.models.User;

import java.util.Collections;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromApplicationUser(User user) {
        var authorities = user.getRoles().stream()
                .map(role -> role.getName().grantedAuthority())
                .flatMap(Set::stream)
                .toList();

        if (authorities.isEmpty()) {
            System.out.println("No roles found for user " + user.getName());
            authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        }
        System.out.println("Roles for user " + user.getName() + ": " + authorities);
        return new UserDetailsImpl (user.getName(),
                user.getPassword(),
                authorities,
                !user.getBan());
    }
}
