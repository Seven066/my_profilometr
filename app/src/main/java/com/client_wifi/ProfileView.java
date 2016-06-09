package com.client_wifi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;

/**
 * Created by Fourp on 29.06.2015.
 * E-mail: 065@t-sk.ru
 */
public class ProfileView extends ImageView {

    public int width;
    public int height;
    public LinearLayout.LayoutParams layoutParams;

    public ArrayList<Profile> profiles = new ArrayList<>();
    public ArrayList<String> profiles_titles = new ArrayList<>();
    static private double WIDTH_KOEF = 0.01;
    static private double HEIGHT_KOEF = 0.02;
    public double shift_X = 10.0;
    public double shift_Y = 5.0;
    public ProfileView(Context context) {
        super(context);
    }

    public ProfileView(Context context, int width_, int height_) {
        super(context);
        width = width_;
        height = height_;
        layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.gravity = Gravity.CENTER;
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
        profiles_titles.add(profile.title);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawLine(width / 2, 0, width / 2, height, paint);
        //canvas.drawLine(0, height / 2, width, height / 2, paint);
        //paint.setStrokeWidth(height / 250);
        canvas.drawRect(0, 0, width - 1, height - 1, paint);

        paint.setColor(Color.BLUE);

        int[] color = {
                Color.BLACK, Color.BLUE, Color.RED,
        };

        if (profiles != null) {
            for (int j = 0; j < profiles.size(); j++) {
                if (profiles.get(j).drawable) {
                    paint.setColor(color[j % 3]);
                    for (int i = 1; i < profiles.get(j).size; i++) {
                        Profile profile = profiles.get(j);
                        canvas.drawLine((float) (WIDTH_KOEF * width * (profile.double_[i - 1][0] + shift_X)),
                                (float) (height - (HEIGHT_KOEF * height * (profile.double_[i - 1][1] + shift_Y))),
                                (float) (WIDTH_KOEF * width * (profile.double_[i][0] + shift_X)),
                                (float) (height - (HEIGHT_KOEF * height * (profile.double_[i][1] + shift_Y))), paint);
                    }
                }
            }
        }
    }

    public void WriteXML(Document document, Element element) {
        //TODO добавить запись координат
        int profile_i=0;
        for (Profile profile : profiles) {

            //String title = "Profile" + String.V.title;
            Element prof = document.createElement("Profile_" + profile_i++);
            element.appendChild(prof);
            prof.setAttribute("name",profile.title);
            Element points = document.createElement("Points");
            prof.appendChild(points);
            Element params = document.createElement("Parameters");
            prof.appendChild(params);
            //Оформляем точки
            Element s = document.createElement("Size");
            points.setAttribute("size",String.valueOf(profile.size));
            for (int i = 0; i < profile.size; i++) {
                Element p = document.createElement("Point_" + i);
                points.appendChild(p);
                p.setAttribute("X", String.valueOf(profile.double_[i][0]));
                p.setAttribute("Y", String.valueOf(profile.double_[i][1]));
            }
            for (int i = 0; i < 7; i++) {
                Element par = document.createElement("Parameter_" + i);
                params.appendChild(par);
                par.setAttribute("Val", String.valueOf(profile.params[i]));
            }
            //TODO добавить запись координат
        }

    }

    public void WriteXLS() throws IOException, WriteException {
        //TODO добавить запись координат
        WriteExcel we = new WriteExcel(profiles);
        String fileName;
        Calendar calendar;

        calendar = Calendar.getInstance();
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName = basePath + "/test/profile" + calendar.getTime().toString() + ".xls";
        we.setOutputFile(fileName);
        we.setDate(calendar.getTime().toString());
        we.write();
    }
}
