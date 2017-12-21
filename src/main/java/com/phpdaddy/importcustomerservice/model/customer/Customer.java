package com.phpdaddy.importcustomerservice.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phpdaddy.importcustomerservice.model.customer.enums.AgeGroup;
import com.phpdaddy.importcustomerservice.model.customer.enums.Gender;
import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Nested;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.conversions.EnumSelector;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    private String id;

    @Parsed(field = "P-ID")
    private Integer pid;

    private String username;

    private String password;

    @Parsed(field = "Gender")
    @EnumOptions(customElement = "csvShortCode", selectors = {EnumSelector.CUSTOM_FIELD})
    private Gender gender;

    @Parsed(field = "Surname")
    private String firstName;

    @Parsed(field = "Family name")
    private String lastName;

    @Parsed(field = "Date of birth")
    @Format(formats = {"dd.mm.YYYY"})
    @JsonFormat(pattern = "dd.MM.yyyy")
    private java.util.Date birthDate;

    @Nested
    private Employment employment;
    @Nested
    private Address address;
    @Nested
    private Contact contact;
    @Nested
    private  Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public java.util.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.util.Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        if (this.birthDate == null) {
            return null;
        }
        ZonedDateTime birthDate = this.birthDate.toInstant().atZone(ZoneId.systemDefault());
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());

        return Period.between(birthday, today).getYears();
    }

    public AgeGroup getAgeGroup() {
        Integer age = getAge();
        if (age == null) {
            return null;
        }
        return AgeGroup.calc(age);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
