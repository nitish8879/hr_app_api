package com.hr.hr_management.entities;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hr.hr_management.utils.enums.WrokingDays;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class CompanyEntities {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "company_holidays", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "holiday_id"))
    private List<HolidayEntity> holidays;

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
