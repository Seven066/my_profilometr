package com.client_wifi;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fourp on 14.07.2015.
 * E-mail: 065@t-sk.ru
 */

//Наименование измеренного профиля
//Дата регистрации
//Код оператора
//Наименование ЖД
//Дистанция пути
//Номер пути
//План пути
//Сторона рельса
//Путевая координата
//Комментарии

public class Profile {
    public double[][] double_;
    public String title;
    public  int size;
    public Date date;
    public double [] params;
    public String railwayNumber;
    public String location;
    public boolean railwaySide;
    public boolean drawable = false;
    public double [] coordinates;
    public boolean isDrawed = false;
    //Наименование измеренного профиля
    //Дата регистрации
    //Код оператора
    public String operatorCode;
    //Наименование ЖД
    public String ZDName;
    //Дистанция пути
    public String railwayDistance;
    //План пути
    public String railwayPlan;
    //Сторона рельса
    //Путевая координата
    public String railwayCoordinate;
    //Комментарии
    public String comment;

    public void setCoordinates(double lat, double lon) {
        this.coordinates = new double[2];
        coordinates[0] = lat;
        coordinates[1] = lon;
    }





    public Profile(double[][] mas, int size_, String title, Date date_, double[] params, String railwayNumber, boolean railwaySide) {
        this.params = new double[7];
        System.arraycopy(params, 0, this.params, 0, params.length);
        this.title = title;
        size = size_;
        date = date_;
        double_ = new double[size][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size; j++) {
                double_[j][i] = mas[j][i];
            }
        }
        this.railwayNumber = railwayNumber;
        this.railwaySide = railwaySide;
        operatorCode = "1";
        ZDName = "1";
        //Дистанция пути
        railwayDistance = "1";
        //План пути
        railwayPlan = "1";
        //Путевая координата
        railwayCoordinate = "1";
        comment = "1";
        location = "1";
    }


    public Profile(double[][] mas, int size_, String title,Date date_, double[] params) {
        this.params = new double[7];
        System.arraycopy(params, 0, this.params, 0, params.length);
        this.title = title;
        size = size_;
        date = date_;
        double_ = new double[size][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size; j++) {
                double_[j][i] = mas[j][i];
            }
        }
        //TODO change it to = profile[i-1] params;
        railwayNumber = "1";
        railwaySide = false;
        operatorCode = "1";
        ZDName = "1";
        //Дистанция пути
        railwayDistance = "1";
        //План пути
        railwayPlan = "1";
        //Путевая координата
        railwayCoordinate = "1";
        comment = "1";
        location = "1";
    }

    public void setInfo(String operatorCode, String ZDName, String railwayDistance, String railwayNumber, String railwayPlan, boolean railwaySide, String railwayCoordinate, String location, String comment){
        this.operatorCode = operatorCode;
        this.ZDName = ZDName;
        this.railwayDistance = railwayDistance;
        this.railwayNumber = railwayNumber;
        this.railwayPlan = railwayPlan;
        this.railwaySide = railwaySide;
        this.railwayCoordinate = railwayCoordinate;
        this.location = location;
        this.comment = comment;
    }
}
