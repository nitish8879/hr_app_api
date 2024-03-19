package com.hr.hr_management.repo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.UserActivityEntities;
import java.util.List;


public interface UserActivitiesRepo extends JpaRepository<UserActivityEntities, Integer> {

    Optional<UserActivityEntities> findByIdAndCreatedAt(Integer id, LocalDate localDate);

    Optional<UserActivityEntities> findByUserIDAndCompanyIDAndCreatedAt(int userID, int companyID, LocalDate createdAt);
}
