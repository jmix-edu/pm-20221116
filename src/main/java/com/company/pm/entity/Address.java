package com.company.pm.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@JmixEntity
@Embeddable
public class Address {
    @Column(name = "ZIP")
    private String zip;

    @Column(name = "STREET")
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}