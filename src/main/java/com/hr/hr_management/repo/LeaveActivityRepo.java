package com.hr.hr_management.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.LeaveAcitivityEntities;

public interface LeaveActivityRepo extends JpaRepository<LeaveAcitivityEntities, UUID> {
    List<LeaveAcitivityEntities> findByCompany_IdAndApplyDate(UUID companyId, LocalDate applyDate);
}
