package com.hr.hr_management.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.hr_management.entities.UserEntities;
import java.util.List;


public interface UserRepo extends JpaRepository<UserEntities, Integer> {

    Optional<UserEntities> findByUserNameAndPassword(String userName, String password);

    Optional<UserEntities> findByUserName(String userName);

    List<UserEntities> findByCompanyID(int companyID);

}
