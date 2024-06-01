package com.hr.hr_management.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    private UUID id;

    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    private String teamName;

    @ManyToMany
    @JoinTable(name = "team_user", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private List<UserEntities> members = new ArrayList<>();


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntities company;
}
