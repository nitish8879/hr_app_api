package com.hr.hr_management.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.CreateTeamReq;
import com.hr.hr_management.dao.req.MemberAddReq;
import com.hr.hr_management.entities.TeamsEntities;
import com.hr.hr_management.entities.UserEntities;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.TeamRepo;
import com.hr.hr_management.repo.UserRepo;
import com.hr.hr_management.services.TeamsService;

@Service
public class TeamsServiceImpl implements TeamsService {

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    ValidationUserService validationUserService;

    @Override
    public String createTeam(CreateTeamReq req) {
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        TeamsEntities teamsEntities = new TeamsEntities();
        teamsEntities.setTeamName(req.getTeamName());
        teamsEntities.setCompany(companyRepo.findById(req.getCompanyID()).get());
        teamRepo.save(teamsEntities);
        return "Team Created";
    }

    @Override
    public String addMember(MemberAddReq req) {
        validationUserService.isUserValid(req.getCreatingUserID(), req.getCompanyID());
        var team = teamRepo.findById(req.getTeamID());
        var userExit = userRepo.findByUserName(req.getUserName());
        if (!team.isPresent()) {
            throw new RuntimeException("Team not Found");
        } else if (team.get().getCompany().getId() != req.getCompanyID()) {
            throw new RuntimeException("Team is not belongs to this company");
        } else if (userExit.isPresent()) {
            throw new RuntimeException("User Already exits");
        } else {
            UserEntities newUser = new UserEntities();
            newUser.setUserName(req.getUserName());
            newUser.setPassword(req.getUserName());
            newUser.setFullName(req.getFullName());
            newUser.setCompany(userExit.get().getCompany());
            newUser.setCreatedBy(req.getCreatingUserID());
            newUser.setEmployeApproved(true);
            userRepo.save(newUser);
        }
        return "Member Added";
    }

    @Override
    public Object fetchTeamsAndMembers(UUID companyID, UUID userID) {
        validationUserService.isUserValid(userID, companyID);
        var companyExit = companyRepo.findById(companyID);
        if (!companyExit.isPresent()) {
            throw new RuntimeException("Team not found");
        } else {
            return companyExit.get().getTeams();
        }
    }

}
