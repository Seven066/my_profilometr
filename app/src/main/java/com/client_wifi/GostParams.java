package com.client_wifi;

/**
 * Created by Axel on 23.07.2016.
 */
public class GostParams {

    double shift_fi;
    double shift_x;//подбор
    double shift_y;
    double middle_x;
    double middle_y;

    public GostParams(double shift_fi, double shift_x, double shift_y, double middle_x, double middle_y) {
        this.shift_fi = shift_fi;
        this.shift_x = shift_x;
        this.shift_y = shift_y;
        this.middle_x = middle_x;
        this.middle_y = middle_y;
    }

    public void setParams(double shift_fi, double shift_x, double shift_y, double middle_x, double middle_y) {
        this.shift_fi = shift_fi;
        this.shift_x = shift_x;
        this.shift_y = shift_y;
        this.middle_x = middle_x;
        this.middle_y = middle_y;
    }


}

