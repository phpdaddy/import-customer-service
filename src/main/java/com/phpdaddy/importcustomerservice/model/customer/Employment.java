package com.phpdaddy.importcustomerservice.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phpdaddy.importcustomerservice.csv.IntFromFloatWithCommaConversion;
import com.phpdaddy.importcustomerservice.csv.IntFromFloatWithPointConversion;
import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;

import java.util.Date;

public class Employment {
    @Parsed(field = "Position")
    private String position;
    @Parsed(field = "Employeed since")
    @Format(formats = {"dd.mm.YYYY"})
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date sinceDate;
    @Parsed(field = "Employeed to")
    @Format(formats = {"dd.mm.YYYY"})
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date toDate;
    @Parsed(field = "Weekly hours of working")
    @Convert(conversionClass = IntFromFloatWithPointConversion.class)
    private Integer weeklyHours;
    @Parsed(field = "Weekly days of woeking")
    @Convert(conversionClass = IntFromFloatWithCommaConversion.class)
    private Integer weeklyDays;
    @Parsed(field = "Vacation days")
    @Convert(conversionClass = IntFromFloatWithCommaConversion.class)
    private Integer vacationDays;
    @Parsed(field = "Working place")
    private String workingPlace;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Integer getWeeklyDays() {
        return weeklyDays;
    }

    public void setWeeklyDays(Integer weeklyDays) {
        this.weeklyDays = weeklyDays;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }
}
