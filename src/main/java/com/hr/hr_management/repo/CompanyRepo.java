package com.hr.hr_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.CompanyEntities;

public interface CompanyRepo extends JpaRepository<CompanyEntities, Integer> {

}
