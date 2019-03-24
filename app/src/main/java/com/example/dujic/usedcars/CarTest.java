package com.example.dujic.usedcars;

/**
 * Created by Dujic on 12/23/2017.
 */

public class CarTest {
    public int id;
    public int adId;
    public String mark;
    public String model;
    public double price;
    public int year;
    public int enginePower;
    public int consummation; //potrošnja
    public int kilometersOfTravel;
    public String fuel; //benzin, dizel
    public String description;
    public String[] images;

    public CarTest (int id, int adId, String mark, String model, double price, int year, int enginePower
    , int consumption, int kilometersOfTravel, String fuel, String description, String[] images ){
        this.id = id;
        this.adId = adId;
        this.mark = mark;
        this.model = model;
        this.price = price;
        this.year = year;
        this.enginePower = enginePower;
        this.consummation = consumption;
        this.kilometersOfTravel = kilometersOfTravel;
        this.fuel = fuel;
        this.description = description;
        this.images = images;

    }
}
