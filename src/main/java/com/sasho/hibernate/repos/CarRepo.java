package com.sasho.hibernate.repos;

import com.sasho.hibernate.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    List<Car> findAllByIdIn(Set<Long> ids);
}
