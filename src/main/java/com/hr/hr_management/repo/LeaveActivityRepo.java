package com.hr.hr_management.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.LeaveAcitivityEntities;

public interface LeaveActivityRepo extends JpaRepository<LeaveAcitivityEntities, UUID> {

    List<LeaveAcitivityEntities> findByCompanyID(UUID id);

    List<LeaveAcitivityEntities> findByUserID(UUID userID);

    List<LeaveAcitivityEntities> findByUserIDAndCompanyID(UUID userID, UUID companyID);
}
