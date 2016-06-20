package com.client_wifi;
/**
 * Created by Axel on 22.04.2016.
 */

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.drawing.Chart;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;


public class WriteExcel {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputFile;
    private ArrayList<Profile> profiles;
    private String Date;
    private AssetManager assetManager;
    public WriteExcel(ArrayList<Profile> profiles, AssetManager assetManager) {
        this.profiles = profiles;
        this.assetManager = assetManager;
    }
    public WriteExcel(){}


    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void write() throws IOException, WriteException {

        File file = new File(inputFile);    //Создание файла с результатами
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();


        //WorkbookSettings wbSettings = new WorkbookSettings();
        //wbSettings.setLocale(new Locale("en", "EN"));

        Workbook sampleWb = null;
        try {
            //sampleWb = Workbook.getWorkbook(new File(basePath + "/test/sample.xls"));
            InputStream is = assetManager.open("sample.xls");
            sampleWb = Workbook.getWorkbook(is);
        } catch (BiffException e) {
            e.printStackTrace();
        }

        WritableWorkbook workbook = Workbook.createWorkbook(file, sampleWb);
        //workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        createLabel(excelSheet);
        createContent(excelSheet);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers
        addCaption(sheet, 0, 0, "Отчет измерения от");
        addCaption(sheet, 1, 0, Date);
    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Write profiles
        int profile_i = 0;
        int firstCellLine = 2;
        for (Profile profile : profiles){

            //Head
            addLabel(sheet, 5*profile_i, firstCellLine, "Profile " + profile_i);
            addLabel(sheet, 5*profile_i+1, firstCellLine, profile.title);
            addLabel(sheet, 5*profile_i+2, firstCellLine, "Параметр");
            addLabel(sheet, 5*profile_i+3, firstCellLine, "Значение");

            //Параметры
            addLabel(sheet, 5*profile_i+2, firstCellLine+1, "Hv");
            addNumber(sheet, 5*profile_i+3, firstCellLine+1, profile.params[0]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+2, "Hh");
            addNumber(sheet, 5*profile_i+3, firstCellLine+2, profile.params[1]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+3, "Hr");
            addNumber(sheet, 5*profile_i+3, firstCellLine+3, profile.params[2]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+4, "H45");
            addNumber(sheet, 5*profile_i+3, firstCellLine+4, profile.params[3]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+5, "R45");
            addNumber(sheet, 5*profile_i+3, firstCellLine+5, profile.params[4]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+6, "S1");
            addNumber(sheet, 5*profile_i+3, firstCellLine+6, profile.params[5]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+7, "S2");
            addNumber(sheet, 5*profile_i+3, firstCellLine+7, profile.params[6]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+8, "Lat");
            addNumber(sheet, 5*profile_i+3, firstCellLine+8, profile.coordinates[0]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+9, "Lon");
            addNumber(sheet, 5*profile_i+3, firstCellLine+9, profile.coordinates[1]);
            addLabel(sheet, 5*profile_i+2, firstCellLine+10, "operator_code");
            addLabel(sheet, 5*profile_i+3, firstCellLine+10, profile.operatorCode);
            addLabel(sheet, 5*profile_i+2, firstCellLine+11, "ZDName");
            addLabel(sheet, 5*profile_i+3, firstCellLine+11, profile.ZDName);
            addLabel(sheet, 5*profile_i+2, firstCellLine+12, "Distance");
            addLabel(sheet, 5*profile_i+3, firstCellLine+12, profile.railwayDistance);
            addLabel(sheet, 5*profile_i+2, firstCellLine+13, "rw_number");
            addLabel(sheet, 5*profile_i+3, firstCellLine+13, profile.railwayNumber);
            addLabel(sheet, 5*profile_i+2, firstCellLine+14, "rw_plan");
            addLabel(sheet, 5*profile_i+3, firstCellLine+14, profile.railwayPlan);
            addLabel(sheet, 5*profile_i+2, firstCellLine+15, "rw_side");
            addLabel(sheet, 5*profile_i+3, firstCellLine+15, profile.railwaySide?"правый":"левый");
            addLabel(sheet, 5*profile_i+2, firstCellLine+16, "rw_coord");
            addLabel(sheet, 5*profile_i+3, firstCellLine+16, profile.railwayCoordinate);
            addLabel(sheet, 5*profile_i+2, firstCellLine+17, "gps");
            addLabel(sheet, 5*profile_i+3, firstCellLine+17, profile.location);
            addLabel(sheet, 5*profile_i+2, firstCellLine+18, "comment");
            addLabel(sheet, 5*profile_i+3, firstCellLine+18, profile.comment);
            //XY
            addLabel(sheet, 5*profile_i, firstCellLine+1, "X");
            addLabel(sheet, 5*profile_i+1, firstCellLine+1, "Y");

            for (int i=0; i<profile.size;i++){
                addNumber(sheet, 5*profile_i, firstCellLine+2+i, profile.double_[i][0]);
                addNumber(sheet, 5*profile_i+1, firstCellLine+2+i, profile.double_[i][1]);
            }


            profile_i++;
        }

        /*
        for (int i = 1; i < 10; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10);
            // Second column
            addNumber(sheet, 1, i, i * i);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

        // now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i);
            // Second column
            addLabel(sheet, 1, i, "Another text");
        }
        */
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
                           double db) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, db, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

}


