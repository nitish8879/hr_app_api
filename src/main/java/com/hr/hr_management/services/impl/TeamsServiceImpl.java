package com.hr.hr_management.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.hr.hr_management.utils.enums.UserRoleType;

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
        var company = companyRepo.findById(req.getCompanyID());
        TeamsEntities teamsEntities = new TeamsEntities(req.getTeamName(), company.get());
        teamRepo.save(teamsEntities);
        return "Team Created";
    }

    @Override
    public List<UserEntities> fetchAllAdminManagerByCompany(UUID companyId) {
        var company = companyRepo.findById(companyId);
        List<UserEntities> user = new ArrayList<>();
        if (!company.isPresent()) {
            throw new RuntimeException("Company not Found");
        } else {
            for (var e : company.get().getTeams()) {
                user.add(e.getManager());
            }
            return user;
        }

    }

    @Override
    public String addMember(MemberAddReq req) {
        validationUserService.isUserValid(req.getCreatingUserID(), req.getCompanyID());
        var team = teamRepo.findById(req.getTeamID());
        var userExit = userRepo.findByUserName(req.getUserName());
        if (!team.isPresent()) {
            throw new RuntimeException("Team not Found");
        } else if (!team.get().getCompany().getId().toString().equals(req.getCompanyID().toString())) {
            throw new RuntimeException("Team is not belongs to this company");
        } else if (userExit.isPresent()) {
            throw new RuntimeException("User Already exits");
        } else {
            var company = companyRepo.findById(req.getCompanyID());
            UserEntities newUser = new UserEntities();
            newUser.setUserName(req.getUserName());
            newUser.setPassword(req.getUserName());
            newUser.setFullName(req.getFullName());
            newUser.setCompany(company.get());
            newUser.setCreatedBy(req.getCreatingUserID());
            newUser.setRoleType(req.getRoleType());
            newUser.setEmployeApproved(true);
            var savedUser = userRepo.save(newUser);

            if (req.getRoleType() == UserRoleType.ADMIN || req.getRoleType() == UserRoleType.MANAGER) {
                team.get().setManager(newUser);
            }
            var userTeam = team.get().getUsers();
            userTeam.add(savedUser);
            team.get().setUsers(userTeam);
            teamRepo.save(team.get());
            userRepo.save(savedUser);

            // var userTeams = savedUser.getTeams();
            // userTeams.add(team.get());
            // savedUser.setTeams(userTeams);
            // userRepo.save(savedUser);

        }
        return "Member Added";
    }

    @Override
    public Object fetchTeams(UUID companyID, UUID userID, UserRoleType roleType) {
        validationUserService.isUserValid(userID, companyID);
        var userExit = userRepo.findById(userID);
        if (!userExit.isPresent()) {
            throw new RuntimeException("Company not found");
        } else if (roleType == UserRoleType.ADMIN || roleType == UserRoleType.SUPERADMIN) {
            return userExit.get().getCompany().getTeams();
        } else {
            List<TeamsEntities> resp = new ArrayList<>();
            for (var e : userExit.get().getCompany().getTeams()) {
                for (var w : e.getUsers()) {
                    if (w.getId().equals(userID)) {
                        resp.add(e);
                    }
                }
            }
            return resp;
        }
    }

    @Override
    public Object fetchMembers(UUID companyID, UUID userID, UUID teamID) {
        validationUserService.isUserValid(userID, companyID);
        var foundTeam = teamRepo.findById(teamID);
        if (!foundTeam.isPresent()) {
            throw new RuntimeException("Team not found");
        } else {
            var data = new HashMap<>();
            data.put("manager", foundTeam.get().getManager());
            data.put("members", foundTeam.get().getUsers());
            return data;
        }
    }

}
