package com.codean.smart_rw.config;

import com.codean.smart_rw.model.pojo.RolesPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@RequiredArgsConstructor
public class CustomUsersDetails implements UserDetails {
    private final UsersPojo usersPojo;
    private final List<RolesPojo> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNamaRole()))
                .toList();
    }


    public String getUserId(){
        return usersPojo.getUserId();
    }

    @Override
    public String getUsername(){
        return usersPojo.getEmail();
    }

    @Override
    public String getPassword(){
        return usersPojo.getPassword();
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
