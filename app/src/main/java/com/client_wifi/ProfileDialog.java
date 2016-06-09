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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private EditText currentLocationText;
    private Switch railwaySide;

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

        editTextName = (EditText) view.findViewById(R.id.profilename);
        editTextName.setText(String.valueOf(profile.title));
        currentLocationText = (EditText) view.findViewById(R.id.currentLocation);
        currentLocationText.setText(profile.location);
        dateTextName = (TextView) view.findViewById(R.id.profileDate);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(profile.date.getTime());
        dateTextName.setText(calendar.getTime().toString());
        //datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        //timePicker.setIs24HourView(true);
        railwayNumber = (EditText) view.findViewById(R.id.railwayNumber);
        railwaySide = (Switch)  view.findViewById(R.id.railwaySide);
        railwayNumber.setText(profile.railwayNumber);
        railwaySide.setChecked(profile.railwaySide);
        return view;
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sumdialogOk:
                String s = editTextName.getText().toString();
                profile.title = s;
                strings.set(id, s);
                profile.railwayNumber = String.valueOf(railwayNumber.getText());
                profile.railwaySide = railwaySide.isChecked();

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
