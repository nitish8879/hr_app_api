package com.hr.hr_management.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.CompanyEntities;
import com.hr.hr_management.entities.UserActivityEntities;
import com.hr.hr_management.entities.UserEntities;

public interface UserActivitiesRepo extends JpaRepository<UserActivityEntities, UUID> {

    Optional<UserActivityEntities> findByIdAndCreatedAt(UUID id, LocalDate localDate);

    Optional<UserActivityEntities> findByUserAndCompanyAndCreatedAt(UserEntities user, CompanyEntities company,
            LocalDate createdAt);

    List<UserActivityEntities> findByCompany_IdAndCreatedAt(UUID companyId, LocalDate createdAt);

}
