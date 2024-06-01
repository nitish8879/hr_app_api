package com.hr.hr_management.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.TeamsEntities;

public interface TeamRepo extends JpaRepository<TeamsEntities, UUID> {

}
