package com.hr.hr_management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.LeaveAcitivityEntities;

public interface LeaveActivityRepo extends JpaRepository<LeaveAcitivityEntities, Integer> {

    List<LeaveAcitivityEntities> findByCompanyID(Integer id);
}
