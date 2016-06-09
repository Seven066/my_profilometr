package com.client_wifi;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Axel on 30.11.2015.
 */
public class ClearDialog extends DialogFragment implements OnClickListener{

    private ProfileView profileView;
    private ArrayList<String> strings;
    private ArrayAdapter adapter;
    private Profile profile;
    SparseBooleanArray sbArray;

    public ClearDialog(ProfileView profileview, ArrayAdapter adapter) {
        this.profileView = profileview;
        this.strings = profileview.profiles_titles;
        this.adapter = adapter;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.clearlDialogTitle)
                .setPositiveButton(R.string.yes, this)   // заменить на r.string
                .setNegativeButton(R.string.no, this)
                .setMessage(R.string.clearDialogMessage);
        return alertDialogBuilder.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                i = R.string.yes;
                profile = profileView.profiles.get(0);
                profileView.profiles.clear();
                strings.clear();
                adapter.notifyDataSetChanged();
                profileView.addProfile(profile);
                profileView.invalidate();
                break;
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.no;
                break;
        }
        if (i > 0)
            Log.d("MyLog", "Dialog 2: " + getResources().getString(i));
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("MyLog", "Dialog 2: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("MyLog", "Dialog 2: onCancel");
    }
}
