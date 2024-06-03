package com.hr.hr_management.services;

import java.util.UUID;

import com.hr.hr_management.dao.req.CreateTeamReq;
import com.hr.hr_management.dao.req.MemberAddReq;

public interface TeamsService {

    public String createTeam(CreateTeamReq req);

    public String addMember(MemberAddReq req);

    public Object fetchTeamsAndMembers(UUID companyID, UUID userID);

}
