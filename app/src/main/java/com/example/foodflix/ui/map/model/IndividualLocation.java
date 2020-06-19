/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.map.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * POJO class for an individual location
 */
public class IndividualLocation {

    private String name;
    private String address;
    private String hours;
    private String phoneNum;
    private String distance;
    private LatLng location;

    /**
     * Instantiates a new Individual location.
     *
     * @param name     the name
     * @param address  the address
     * @param hours    the hours
     * @param phoneNum the phone num
     * @param location the location
     */
    public IndividualLocation(String name, String address, String hours, String phoneNum, LatLng location) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.phoneNum = phoneNum;
        this.location = location;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets hours.
     *
     * @return the hours
     */
    public String getHours() {
        return hours;
    }

    /**
     * Gets phone num.
     *
     * @return the phone num
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public LatLng getLocation() {
        return location;
    }
}
