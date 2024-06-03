package com.hr.hr_management.repo;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.UserActivityEntities;

public interface UserActivitiesRepo extends JpaRepository<UserActivityEntities, UUID> {

    Optional<UserActivityEntities> findByIdAndCreatedAt(UUID id, LocalDate localDate);

    Optional<UserActivityEntities> findByUserIDAndCompanyIDAndCreatedAt(UUID userID, UUID companyID,
            LocalDate createdAt);
}
