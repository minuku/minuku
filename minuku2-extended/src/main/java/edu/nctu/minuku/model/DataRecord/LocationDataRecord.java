/*
 * Copyright (c) 2016.
 *
 * DReflect and Minuku Libraries by Shriti Raj (shritir@umich.edu) and Neeraj Kumar(neerajk@uci.edu) is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/Shriti-UCI/Minuku-2.
 *
 *
 * You are free to (only if you meet the terms mentioned below) :
 *
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 *
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * Under the following terms:
 *
 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * NonCommercial — You may not use the material for commercial purposes.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 */

package edu.nctu.minuku.model.DataRecord;

import org.json.JSONObject;

import java.util.Date;

import edu.nctu.minukucore.model.DataRecord;
/**
 * Created by shriti on 7/15/16.
 */
public class LocationDataRecord implements DataRecord {

    private long creationTime;

    private float latitude;
    private float longitude;
    private float Accuracy;
    private float Altitude;
    private float Speed;
    private float Bearing;
    private String Provider;


    protected JSONObject jSONObject;


    public LocationDataRecord() {

    }

    public LocationDataRecord(float latitude, float longitude, float Accuracy, float Altitude, float Speed, float Bearing, String Provider) {
        this.creationTime = new Date().getTime();
        this.latitude = latitude;
        this.longitude = longitude;
        this.Accuracy = Accuracy;
        this.Altitude = Altitude;
        this.Speed = Speed;
        this.Bearing = Bearing;
        this.Provider = Provider;

    }

    public LocationDataRecord(float latitude, float longitude) {
        this.creationTime = new Date().getTime();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDataRecord(JSONObject jSONObject) {
        this.creationTime = new Date().getTime();
        this.jSONObject = jSONObject;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getAccuracy(){
        return Accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.Accuracy = accuracy;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public float getAltitude() {
        return Altitude;
    }

    public void setAltitude(float Altitude) {
        this.Altitude = Altitude;
    }

    public float getSpeed() {
        return Speed;
    }

    public void setSpeed(float Speed) {
        this.Speed = Speed;
    }

    public float getBearing() {
        return Bearing;
    }

    public void setBearing(float Bearing) {
        this.Bearing = Bearing;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String Provider) {
        this.Provider = Provider;
    }

    @Override
    public String toString() {
        return "Loc:" + this.latitude + ":" + this.longitude;
    }
}
