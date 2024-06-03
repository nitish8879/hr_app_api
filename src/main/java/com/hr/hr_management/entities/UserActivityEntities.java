package com.hr.hr_management.entities;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.hr_management.utils.TimeListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class UserActivityEntities {

    public UserActivityEntities(UserEntities user, Time inTime) {
        this.user = user;
        this.inTime = inTime;
    }

    public UserActivityEntities() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private UUID id;

    @Convert(converter = TimeListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Time> breakInTimes;

    @Convert(converter = TimeListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Time> breakOutTimes;

    @Column(nullable = true)
    private Time inTime;

    @Column(nullable = true)
    private Time outTime;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntities user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntities company;
}
