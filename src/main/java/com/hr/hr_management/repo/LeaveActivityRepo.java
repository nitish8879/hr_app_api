package com.hr.hr_management.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.LeaveAcitivityEntities;

public interface LeaveActivityRepo extends JpaRepository<LeaveAcitivityEntities, UUID> {

    List<LeaveAcitivityEntities> findByCompanyID(Integer id);

    List<LeaveAcitivityEntities> findByUserID(int userID);

    List<LeaveAcitivityEntities> findByUserIDAndCompanyID(int userID,int companyID);
}
