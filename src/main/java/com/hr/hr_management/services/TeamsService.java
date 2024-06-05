package com.hr.hr_management.services;

import java.util.UUID;

import java.util.List;

import com.hr.hr_management.dao.req.CreateTeamReq;
import com.hr.hr_management.dao.req.MemberAddReq;
import com.hr.hr_management.entities.UserEntities;
import com.hr.hr_management.utils.enums.UserRoleType;

public interface TeamsService {

    public String createTeam(CreateTeamReq req);

    public String addMember(MemberAddReq req);

    public Object fetchTeams(UUID companyID, UUID userID,UserRoleType roleType);

    public Object fetchMembers(UUID companyID, UUID userID, UUID teamID);

    public List<UserEntities> fetchAllAdminManagerByCompany(UUID companyId,UUID userId);

}
