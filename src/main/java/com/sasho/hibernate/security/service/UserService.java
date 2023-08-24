package com.sasho.hibernate.security.service;

import com.sasho.hibernate.domain.DomainUser;
import com.sasho.hibernate.repos.AuthorityRepo;
import com.sasho.hibernate.repos.UserRepo;
import com.sasho.hibernate.security.JWTService;
import com.sasho.hibernate.security.dto.UserFormRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepo authorityRepo;
    private final JWTService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepo.findByUsername(username);
    }

    public UserDetails registerUser(UserFormRequest userDto) {
        var pwd = this.passwordEncoder.encode(userDto.password());
        var allByAuthorityIn = this.authorityRepo.findAllByAuthorityIn(Set.of("ROLE_USER", "ROLE_ADMIN"));
        var domainUser = DomainUser.builder()
                .username(userDto.username())
                .password(pwd)
                .authorities(allByAuthorityIn)
                .build();
        return this.userRepo.save(domainUser);
    }

    public String verifyLogin(UserFormRequest userDto) {
        DomainUser userDetails = (DomainUser) this.loadUserByUsername(userDto.username());
        boolean passwordsMatch = passwordEncoder.matches(userDto.password(), userDetails.getPassword());
        if (passwordsMatch) {
            return "Bearer %s".formatted(jwtService.generateToken(userDetails));
        }
        throw new IllegalArgumentException("aa");
    }
}
