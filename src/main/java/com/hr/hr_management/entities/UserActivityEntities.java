package com.hr.hr_management.entities;

import java.sql.Time;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserActivityEntities {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int userID;

    @Column(nullable = false)
    private int companyID;

    @Column(nullable = false)
    private Time inTime;

    @Column(nullable = true)
    private Time outTime;

    @Column(nullable = true)
    private Time breakInTime;
    @Column(nullable = true)
    private Time breakOutTime;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    public UserActivityEntities(int userID, int companyID, Time inTime) {
        this.userID = userID;
        this.companyID = companyID;
        this.inTime = inTime;
    }

    public UserActivityEntities() {
    }

}
