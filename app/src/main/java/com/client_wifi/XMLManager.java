package com.client_wifi;

import android.util.Log;
import android.util.Xml;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Fourp on 08.07.2015.
 * E-mail: 065@t-sk.ru
 */
public class XMLManager {
    //Сохранение результатов измерения в структуированный xml файл
    void SaveScheme(BufferedWriter bw, ProfileView prof, String filename) {
        try {
            //Создание xml файла
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("Params");
            rootElement.setAttribute("Client_wifi", filename);
            document.appendChild(rootElement);

            WriteCurrentTime(document, rootElement);
            prof.WriteXML(document, rootElement);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Properties outFormat = new Properties();
            outFormat.setProperty(OutputKeys.INDENT, "yes");
            outFormat.setProperty(OutputKeys.METHOD, "xml");
            outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            outFormat.setProperty(OutputKeys.VERSION, "1.0");
            outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperties(outFormat);
            DOMSource domSource = new DOMSource(document.getDocumentElement());
            OutputStream output = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(output);

            transformer.transform(domSource, result);

            String xmlString = output.toString();

            try {
                bw.write(xmlString);
                bw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParserConfigurationException e) {
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }
    }

    private void WriteCurrentTime(Document document, Element element) {
        Element time = document.createElement("Date");
        element.appendChild(time);
        Calendar rightNow = Calendar.getInstance();
        time.setAttribute("Time", rightNow.getTime().toString());
    }

    void OpenScheme(BufferedReader br, ProfileView prof,ListView lv) {

        try {
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xpp.setInput(br);
            xpp.nextTag();
            readFeed(xpp, prof,lv);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //открытие xml разбор по тегам
    private void readFeed(XmlPullParser parser, ProfileView profileView, ListView lv) throws XmlPullParserException, IOException {
        int idx = 0,prof_idx = 0;
        //todo BAD size in double[205][2]. Заменить 205 на переменную.
        double[][] double_ = new double[205][2];
        String title_ = "";
        String operatorCode= "";
        String ZDName= "";
        String railwayDistance= "";
        String railwayPlan= "";
        String railwayCoordinate= "";
        String railwayNumber = "";
        String railwaySide = "";
        String comment = "";
        String location = "";
        int size_ = 0;
        double[] params_ = new double[7];
        Profile profile;
        while (parser.next() != XmlPullParser.END_DOCUMENT) {

            if (parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();
                Log.d("PARSER", name);
                if (name.startsWith("Profile_")) {
                    title_ = parser.getAttributeValue("", "name");
                    operatorCode = parser.getAttributeValue("","operator_code");
                    ZDName = parser.getAttributeValue("","ZDName");
                    railwayDistance = parser.getAttributeValue("","Distance");
                    railwayNumber = parser.getAttributeValue("","rw_number");
                    railwayPlan = parser.getAttributeValue("","rw_plan");
                    railwaySide = parser.getAttributeValue("","rw_side");
                    railwayCoordinate = parser.getAttributeValue("","rw_coord");
                    location = parser.getAttributeValue("","gps");
                    comment = parser.getAttributeValue("","comment");
                }
                if (name.startsWith("Points")) {
                   // size_ = Integer.valueOf(parser.getAttributeValue("", "size"));
                    //double_ = new double[size_][2];

                }
                if (name.startsWith("Point_")) {
                    double_[idx][0] = Double.valueOf(parser.getAttributeValue("", "X"));
                    double_[idx][1] = Double.valueOf(parser.getAttributeValue("", "Y"));
                    idx++;
                }
                if (name.startsWith("Parameter_")) {
                    params_[idx] = Double.valueOf(parser.getAttributeValue("", "Val"));
                    idx++;
                }
            } else if (parser.getEventType() == XmlPullParser.END_TAG) {
                String name = parser.getName();
                Log.d("Parser_end", name);

                if (name.equals("Points")) {
                    Log.d("Parser_end", String.valueOf(idx));
                    size_ = idx;
                    idx = 0;
                }
                if (name.equals("Parameters")) {
                    Log.d("Parser_end", String.valueOf(idx));
                    idx = 0;
                }
                if (name.toString().startsWith("Profile_")) {
                    //TODO download DATE from file
                    profile = new Profile(double_, size_, title_, Calendar.getInstance().getTime(), params_);

                    profile.setInfo(operatorCode,ZDName,railwayDistance,railwayNumber,
                            railwayPlan,(railwaySide.equals("right")),railwayCoordinate,location,comment);

                    profile.drawable = true;
                    lv.setItemChecked(prof_idx,true);
                    prof_idx++;
                    profileView.addProfile(profile);
                }
            } else continue;

        }
        profileView.invalidate();

    }
}