package com.example.dujic.usedcars;

/**
 * Created by Dujic on 12/26/2017.
 */

public class Ad {
    public int adId;
    public int carId;
    public String userId;
    public String telefon;
    public String location;

    public Ad(int adId, int carId, String userId, String telefon, String location){
        this.adId = adId;
        this.carId = carId;
        this.userId = userId;
        this.telefon = telefon;
        this.location = location;
    }
}
