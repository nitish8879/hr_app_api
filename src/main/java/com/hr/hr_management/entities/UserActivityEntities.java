package com.hr.hr_management.entities;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.CreationTimestamp;

import com.hr.hr_management.utils.TimeListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

    @Convert(converter = TimeListConverter.class)
    private List<Time> breakInTimes = new ArrayList<>();

    @Convert(converter = TimeListConverter.class)
    private List<Time> breakOutTimes =  new ArrayList<>();

    @Column(nullable = true)
    private Time inTime;

    @Column(nullable = true)
    private Time outTime;

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
