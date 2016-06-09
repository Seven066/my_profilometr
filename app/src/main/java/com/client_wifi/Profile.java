package com.client_wifi;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fourp on 14.07.2015.
 * E-mail: 065@t-sk.ru
 */
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
        railwayNumber = "";
        railwaySide = false;
    }
}
