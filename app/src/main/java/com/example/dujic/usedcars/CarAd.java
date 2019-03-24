package com.example.dujic.usedcars;

/**
 * Created by Dujic on 12/27/2017.
 */

public class CarAd {
    public String carAdID;
    public String mark;
    public String model;
    public double price;
    public int year;
    public int enginePower;
    public double consummation; //potrošnja
    public int kilometersOfTravel;
    public String fuel; //benzin, dizel
    public String description;
    public String userID;
    public String telefon;
    public String location;

    public CarAd(String carAdID, String mark, String model, double price, int year, int enginePower, double consummation,
                 int kilometersOfTravel, String fuel, String description, String userID, String telefon, String location){
        this.carAdID = carAdID;
        this.mark = mark;
        this.model = model;
        this.price = price;
        this.year = year;
        this.enginePower = enginePower;
        this.consummation = consummation;
        this.kilometersOfTravel = kilometersOfTravel;
        this.fuel = fuel;
        this.description = description;
        this.userID = userID;
        this.telefon = telefon;
        this.location = location;
    }

    public CarAd(){

    }

}
