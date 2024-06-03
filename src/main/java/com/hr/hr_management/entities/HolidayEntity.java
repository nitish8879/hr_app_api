package com.hr.hr_management.entities;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class HolidayEntity {

    public HolidayEntity(CompanyEntities company){
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id")
    private UUID id;

    private Date holidayDate;

    private String label;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntities company;
}
