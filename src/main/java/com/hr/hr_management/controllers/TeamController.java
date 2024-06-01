package com.hr.hr_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.CreateTeamReq;
import com.hr.hr_management.dao.req.MemberAddReq;
import com.hr.hr_management.services.TeamsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamsService teamsService;

    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamReq req) {
        return ResponseEntity.ok(teamsService.createTeam(req));
    }

    @PostMapping("/add/member")
    public ResponseEntity<?> addMember(@RequestBody @Valid MemberAddReq req) {
        return ResponseEntity.ok(teamsService.addMember(req));
    }
}
