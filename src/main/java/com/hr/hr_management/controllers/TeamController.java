package com.hr.hr_management.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.CreateTeamReq;
import com.hr.hr_management.dao.req.MemberAddReq;
import com.hr.hr_management.services.TeamsService;
import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamsService teamsService;

    @PostMapping("/add")
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamReq req) {
        return ResponseEntity.ok(teamsService.createTeam(req));
    }

    @PostMapping("/add/member")
    public ResponseEntity<?> addMember(@RequestBody @Valid MemberAddReq req) {
        return ResponseEntity.ok(teamsService.addMember(req));
    }

    @GetMapping("/fetchTeams")
    public ResponseEntity<?> fetchTeams(@RequestParam("userID") UUID userID,
            @RequestParam("companyID") UUID companyID,@RequestParam("roleType") UserRoleType roleType) {
        return ResponseEntity.ok(teamsService.fetchTeams(companyID, userID, roleType));
    }

    @GetMapping("/fetchMembers")
    public ResponseEntity<?> fetchMembers(@RequestParam("userID") UUID userID,
            @RequestParam("companyID") UUID companyID, @RequestParam("teamID") UUID teamID) {
        return ResponseEntity.ok(teamsService.fetchMembers(companyID, userID, teamID));
    }

}
