package com.sasho.hibernate.repos;

import com.sasho.hibernate.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    Set<Authority> findAllByAuthorityIn(Set<String> authorityNames);
}
