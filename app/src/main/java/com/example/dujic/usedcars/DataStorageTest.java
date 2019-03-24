package com.example.dujic.usedcars;

import android.util.Log;

import java.util.ArrayList;
import java.util.StringJoiner;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Dujic on 12/23/2017.
 */

public class DataStorageTest {
    public static ArrayList<Integer> listViewData = new ArrayList<Integer>();

    public static ArrayList<CarTest> carTestList = new ArrayList<CarTest>();

    public static ArrayList<Ad> adArrayList = new ArrayList<Ad>();

    public static ArrayList<Ad> userAdsList = new ArrayList<Ad>();

    public static  ArrayList<CarTest> userCarList = new ArrayList<CarTest>();

    public static void fillData(Integer n) {
        for (Integer i = 1; i <= n; i++)
            listViewData.add(i);
    }


    public static void fillCarList() {
        for (int i = 0; i < marks.length; i++) {
            CarTest car = new CarTest(i, i, marks[i], models[i], prices[i], years[i], powers[i], potrosnja[i],
                    kilometers[i], fuels[i], despritions[i], images[i]);

            carTestList.add(car);
        }
    }

    public static void fillUserCarList(String mail){
        Log.i("ADDDDDDDDDD", mail);
        for (int i = 0; i < adArrayList.size(); i++){
            Log.i("UPetlji", adArrayList.get(i).userId);
            if (adArrayList.get(i).userId.equals(mail)){
                userAdsList.add(adArrayList.get(i));

            }
        }

        Log.i("Velicina", String.valueOf(userAdsList.size()));

        for (int i = 0; i < carTestList.size(); i++){
            CarTest car = carTestList.get(i);
            for (int j = 0; j < userAdsList.size(); i++){
                Ad ad = userAdsList.get(j);
                Log.i("Usporedba", String.valueOf(ad.adId) + " " + String.valueOf(car.adId));
                if (ad.adId == car.adId){
                    userCarList.add(car);
                }
            }
        }
    }

    public static  void fillAdList(){
        for(int i = 0; i < userIds.length; i++){
            Ad ad = new Ad(i, i, userIds[i], telefons[i], loactions[i]);

            adArrayList.add(ad);
        }
    }

    private static String[] marks = {"Audi", "Bmw", "Mercedes"};

    private static String[] models = {"s5", "m3", "c250"};

    private static double[] prices = {200000, 250000, 150000};

    private static int[] years = {2012, 2010, 2012};

    private static int[] powers = {130, 200, 120};

    private static int[] potrosnja = {8, 12, 7};

    private static String[] fuels = {"dizel", "benzin", "dizel"};

    private static int[] kilometers = {50000, 60000, 80000};

    private static String[] despritions = {"Ovo je Audi s5, Klimatizacija je dvozonska automatska", "Ovo je Bmw m3", "Ovo je Mercedes c250"};

    private static String[][] images = {{"http://dagelic.com/vjezbe/android/carssmall/audis5.jpg", "http://st.motortrend.com/uploads/sites/10/2015/09/2012-Audi-S5-Cabriolet-engine.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3pQURK50tTSZMUUwb8h6QcUQTCJ0TPVzXOtZEk2V5Kcl68mkyiA"},
            {"https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/BMW_M3_Lime_Rock_Park_Edition_%2814497472213%29.jpg/220px-BMW_M3_Lime_Rock_Park_Edition_%2814497472213%29.jpg"}
            , {"http://www.awzqt.com/wp-content/uploads/2017/04/Stunning-Mercedes-Benz-C250-on-Small-Car-Decoration-Ideas-with-Mercedes-Benz-C250.jpg"}};


    private static int[] carIds = {1, 2, 3};

    private static String[] userIds = {"jdujic87@gmail.com", "sbukva87@gmail.com", "jdujic87@gmail.com"};

    private static String[] telefons = {"0955819879", "0981234567", "0955819879"};

    private static String[] loactions = {"Split", "Zagreb", "Split"};
}