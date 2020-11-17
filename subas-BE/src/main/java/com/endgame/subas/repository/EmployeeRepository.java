package com.endgame.subas.repository;

import com.endgame.subas.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT u FROM employees u WHERE u.username = ?1")
    Optional<Employee> findEmployeeByUserName(String username);
}
