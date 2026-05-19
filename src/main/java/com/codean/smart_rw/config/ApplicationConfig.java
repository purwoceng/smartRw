package com.codean.smart_rw.config;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.RolesMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.model.pojo.RolesPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UsersMapper usersMapper;

    private final RolesMapper rolesMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UsersPojo user = usersMapper.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            List<RolesPojo> roles =
                    rolesMapper.findRolesByUserId(user.getUserId());

            user.setRoles(roles);

            return new CustomUsersDetails(user, roles);
        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
