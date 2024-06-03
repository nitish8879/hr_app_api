package com.hr.hr_management.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class TeamsEntities {

    public TeamsEntities() {
    }

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    @JsonIgnore
    private UUID id;

    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    private String teamName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntities company;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntities manager;

    @ManyToMany(mappedBy = "teams", fetch = FetchType.LAZY)
    private List<UserEntities> users = new ArrayList<>();
}
