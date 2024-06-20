package com.hr.hr_management.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.UserEntities;

public interface UserRepo extends JpaRepository<UserEntities, UUID> {

    Optional<UserEntities> findByUserNameAndPassword(String userName, String password);

    Optional<UserEntities> findByUserName(String userName);

}
