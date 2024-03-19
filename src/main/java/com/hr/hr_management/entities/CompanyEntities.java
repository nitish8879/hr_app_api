package com.hr.hr_management.entities;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hr.hr_management.utils.enums.WrokingDays;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CompanyEntities {

    @Id
    @GeneratedValue
    private int id;// companyID

    @Column(nullable = false)
    private String companyName;

    @Column(unique = true, nullable = false, updatable = false)
    private int ownerID;

    @Column(nullable = false)
    private Time inTime;

    @Column(nullable = false)
    private Time outTime;

    @Convert(converter = WrokingDays.class)
    private List<String> workingDays;

    @ElementCollection
    @Column(nullable = true)
    private Collection<Integer> allEmployesID = new ArrayList<Integer>();

    public CompanyEntities() {
    }

    public CompanyEntities(String companyName, int ownerID, Time inTime, Time outTime, List<String> workingDays) {
        this.companyName = companyName;
        this.ownerID = ownerID;
        this.inTime = inTime;
        this.outTime = outTime;
        this.workingDays = workingDays;
    }

}
