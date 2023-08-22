package com.sasho.hibernate.repos;

import com.sasho.hibernate.domain.DomainUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<DomainUser, Long> {

    @EntityGraph(attributePaths = {"address", "cars", "books"})
    @Query("SELECT d FROM DomainUser d WHERE d.id = :id")
    DomainUser findByIdWithAllRelations(@Param("id") Long id);
}
