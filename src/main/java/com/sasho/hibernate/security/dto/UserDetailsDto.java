package com.sasho.hibernate.security.dto;

import java.util.Set;

public record UserDetailsDto(String username, Set<String> authorities) {
}
