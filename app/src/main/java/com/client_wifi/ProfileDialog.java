package com.client_wifi;

/**
 * Created by Fourp on 12.11.2015.
 * E-mail: 065@t-sk.ru
 * changed by alex on 27.11.2015
 */


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class ProfileDialog extends DialogFragment implements View.OnClickListener {

    private final Profile profile;
    private ArrayList<String> strings;
    private ProfileView profileView;
    private EditText editTextName;
    private Calendar calendar;
    private int id;
    private ArrayAdapter adapter;
    SparseBooleanArray sbArray;
    private TextView dateTextName;
    private EditText railwayNumber;
    private TextView currentLocationText;
    private EditText operatorCode;
    //Наименование жд
    private EditText ZDName;
    //Дистанция пути
    private EditText railwayDistance;
    //План пути
    private EditText railwayPlan;
    //Сторона рельса
    private Switch railwaySide;
    //Путевая координата
    private EditText railwayCoordinate;
    private EditText comment;

    public ProfileDialog(ProfileView profileview, long id, ArrayAdapter adapter, SparseBooleanArray sbArray) {
        this.sbArray = sbArray;
        this.profileView = profileview;
        this.strings = profileview.profiles_titles;
        this.profile = profileview.profiles.get((int)id);
        this.id = (int)id;
        this.adapter = adapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Профиль " + profile.title);
        View view = inflater.inflate(R.layout.profile_dialog, container);
        view.findViewById(R.id.sumdialogCancel).setOnClickListener(this);
        view.findViewById(R.id.sumdialogOk).setOnClickListener(this);
        view.findViewById(R.id.sumdialogDelete).setOnClickListener(this);

        //Наименование измеренного профиля
        editTextName = (EditText) view.findViewById(R.id.profilename);
        editTextName.setText(String.valueOf(profile.title));
        editTextName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Дата регистрации
        dateTextName = (TextView) view.findViewById(R.id.profileDate);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(profile.date.getTime());
        dateTextName.setText(calendar.getTime().toString());

        //Код оператора
        operatorCode = (EditText) view.findViewById(R.id.operatorCode);
        operatorCode.setText(String.valueOf(profile.operatorCode));
        //Наименование ЖД
        ZDName = (EditText) view.findViewById(R.id.ZDName);
        ZDName.setText(String.valueOf(profile.ZDName));
        //Дистанция пути
        railwayDistance = (EditText) view.findViewById(R.id.railwayDistance);
        railwayDistance.setText(String.valueOf(profile.railwayDistance));
        //Номер пути
        railwayNumber = (EditText) view.findViewById(R.id.railwayNumber);
        railwayNumber.setText(profile.railwayNumber);
        //План пути
        railwayPlan = (EditText) view.findViewById(R.id.railwayPlan);
        railwayPlan.setText(String.valueOf(profile.railwayPlan));
        //Сторона рельса
        railwaySide = (Switch)  view.findViewById(R.id.railwaySide);
        railwaySide.setChecked(profile.railwaySide);
        //Путевая координата]
        railwayCoordinate = (EditText) view.findViewById(R.id.railwayCoordinate);
        railwayCoordinate.setText(String.valueOf(profile.railwayCoordinate));
        //GPS
        currentLocationText = (TextView) view.findViewById(R.id.currentLocation);
        currentLocationText.setText(profile.location);
        //Комментарии
        comment = (EditText) view.findViewById(R.id.comment);
        comment.setText(profile.comment);
        //datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        //timePicker.setIs24HourView(true);


        return view;
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sumdialogOk:
                //Наименование измеренного профиля
                String s = editTextName.getText().toString();
                profile.title = s;
                strings.set(id, s);
                //Дата регистрации
                    //неизменна
                //Код оператора
                profile.operatorCode = String.valueOf(operatorCode.getText());
                //Наименование ЖД
                profile.ZDName = String.valueOf(ZDName.getText());
                //Дистанция пути
                profile.railwayDistance = String.valueOf(railwayDistance.getText());
                //Номер пути
                profile.railwayNumber = String.valueOf(railwayNumber.getText());
                //План пути
                profile.railwayPlan = String.valueOf(railwayPlan.getText());
                //Сторона рельса
                profile.railwaySide = railwaySide.isChecked();
                //Путевая координата
                profile.railwayCoordinate = String.valueOf(railwayCoordinate.getText());
                //Комментарии
                profile.comment = String.valueOf(comment.getText());

                //Уведомляем адаптер о изменении
                adapter.notifyDataSetChanged();
                dismiss();
                break;
            case R.id.sumdialogCancel:
                dismiss();
                break;
            case R.id.sumdialogDelete:
                profileView.profiles.remove(id);
                strings.remove(id);
                adapter.notifyDataSetChanged();
                sbArray.delete(id);
                dismiss();
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
