package com.client_wifi;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import jxl.write.WriteException;


public class MainActivity extends AppCompatActivity {

    private static final double SCALE_TEXT = 0.02;
    private static final double SCALE_PROFLE_WIDTH = 0.92;
    private static final double PROFLE_WIDTH_HEIGHT_RATIO = 0.6;
    private static final int SERVERPORT = 1234;
    private static final String SERVER_IP = "172.80.20.1";
    private static final String LOG_TAG = "debug";
    private SparseBooleanArray sbArray;
    FrameLayout profile_layout;
    ProfileView profileview;
    TextView battery_indicator;
    TextView connection_status;
    TextView textmeasure;
    ListView lvMain;
    TextView txlocation;
    Button measuring;
    ProgressBar p;
    Descriptor descriptor;
    ModBus modBus = new ModBus();
    DataOutputStream DOS = null;
    DataInputStream DIS = null;
    Thread receiveThread;
    NetworkInfo mWifi;
    boolean receiverun = false;
    int progress = 0;
    private Socket socket;
    //private static final String SERVER_IP = "192.168.173.1";
    private Handler updateConversationHandler;
    private LocationManager locationManager;
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            //showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            //checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }
    };
    public static FragmentManager fragmentManager = null;

    ArrayAdapter<String> adapter;

    Handler updateGUI = new Handler() {
        int i = 1;

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Log.d(LOG_TAG, "Add profile in the view");
                    measuring.setClickable(true);
                    p.setVisibility(View.INVISIBLE);

                    Mathematics mathematics = new Mathematics();
                    mathematics.calc(modBus.ReceiveProfile);

                    //profileview.addProfile(new Profile(modBus.XYProfile, modBus.PROFILE_SIZE, "Измерение " + i++, params));

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000 * 10, 10, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                            locationListener);
                    showLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

                    Profile profile = new Profile(mathematics.getProfile_xy(), mathematics.getProfileSize(), "Измерение " + i++, Calendar.getInstance().getTime(), mathematics.getParams());
                    profile.location = txlocation.getText().toString();

                    if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                        profile.setCoordinates(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
                                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
                    }

                    profile.drawable = true;
                    profileview.addProfile(profile);

                    double[] v = profile.params;
                    DecimalFormat df = new DecimalFormat("0.00");
                    textmeasure.setText(
                            "Hv\t=\t" + df.format(v[0]) + "\tmm" + "\n" +
                                    "Hh\t=\t" + df.format(v[1]) + "\tmm" + "\n" +
                                    "Hr\t=\t" + df.format(v[2]) + "\tmm" + "\n" +
                                    "Hпр\t=\t" + df.format(v[3]) + "\tmm" + "\n" +
                                    "Hпр45\t=\t" + df.format(v[4]) + "\tmm" + "\n" +
                                    "S1\t=\t" + df.format(v[5]) + "\tmm" + "\n" +
                                    "S2\t=\t" + df.format(v[6]) + "\tmm" + "\n");
                    adapter.notifyDataSetChanged();
                    lvMain.setItemChecked((profileview.profiles_titles.size() - 1), true);
                    lvMain.smoothScrollToPosition(profileview.profiles_titles.size()-1);
                    profileview.invalidate();
                    break;

                case 2:
                    Log.d(LOG_TAG, "No data profile");
                    measuring.setClickable(true);
                    p.setVisibility(View.INVISIBLE);
                    break;

                case 1:
                    if (socket != null) {
                        connection_status.setTextColor(Color.BLUE);
                        connection_status.setText("Подключен");
                        Log.d(LOG_TAG, "Set text connection status is Connect");
                    }
                    break;
                case 5:
                    battery_indicator.setTextColor(Color.BLUE);
                    battery_indicator.setText("Заряд: Высокий");
                    break;

                case 6:
                    battery_indicator.setTextColor(Color.RED);
                    battery_indicator.setText("Заряд: Низкий");
                    break;

                case 3:
                    Toast.makeText(getApplicationContext(), R.string.laserError, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    byte device_info[] = new byte[80];
    private String e_mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        fragmentManager = getFragmentManager();

        updateConversationHandler = new Handler();
        descriptor = new Descriptor();

        profile_layout = (FrameLayout) findViewById(R.id.profile_layout);
        battery_indicator = (TextView) findViewById(R.id.battery_indicator);
        measuring = (Button) findViewById(R.id.measuring);
        p = (ProgressBar) findViewById(R.id.progress_measuring);
        p.setVisibility(View.INVISIBLE);
        connection_status = (TextView) findViewById(R.id.connection_status);
        connection_status.setText("Отключен");
        txlocation = (TextView) findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        connection_status.setTextColor(Color.RED);
        textmeasure = (TextView) findViewById(R.id.textmeasure);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        //int Screen_unit_x = (int) (size.x / outMetrics.density);
        int Screen_unit_y = (int) (size.y / outMetrics.density);
        //int height_text = (int) (Screen_unit_y * SCALE_TEXT);

        //TODO delete this part?
        int profil_width, LayoutWidth;
        final int profil_height;
        LayoutWidth = profile_layout.getLayoutParams().width;
        profil_width = (int) (size.x * SCALE_PROFLE_WIDTH);
        profil_height = (int) (profil_width * PROFLE_WIDTH_HEIGHT_RATIO);

        profileview = new ProfileView(this, profile_layout.getLayoutParams().width, profile_layout.getLayoutParams().height);
        profile_layout.addView(profileview, profileview.layoutParams);

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, profileview.profiles_titles);
        lvMain.setAdapter(adapter);

        final DecimalFormat df = new DecimalFormat("0.00");
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                double[] v = profileview.profiles.get(position).params;
                textmeasure.setText(
                        "Hv\t=\t" + df.format(v[0]) + "\tmm" + "\n" +
                                "Hh\t=\t" + df.format(v[1]) + "\tmm" + "\n" +
                                "Hr\t=\t" + df.format(v[2]) + "\tmm" + "\n" +
                                "H45\t=\t" + df.format(v[3]) + "\tmm" + "\n" +
                                "R45\t=\t" + df.format(v[4]) + "\tmm" + "\n" +
                                "S1\t=\t" + df.format(v[5]) + "\tmm" + "\n" +
                                "S2\t=\t" + df.format(v[6]) + "\tmm" + "\n");

                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);

                if (profileview.profiles.get(position).drawable) {
                    profileview.profiles.get(position).drawable = false;
                } else {
                    profileview.profiles.get(position).drawable = true;
                }

                profileview.invalidate();
            }
        });
        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                ProfileDialog profileDialog = new ProfileDialog(profileview, id, adapter, sbArray);
                profileDialog.show(MainActivity.fragmentManager, "profileDialog");
                Log.v("long clicked", "pos: " + position);
                return true;
            }
        });
        double[] v = new double[5];
        Profile profile = new Profile(Mathematics.GOST_Profile1, Mathematics.GOST_Profile1.length, "ГОСТ", Calendar.getInstance().getTime(), v);
        profile.setCoordinates(0,0);
        profile.drawable = true;

        profileview.addProfile(profile);
        lvMain.setItemChecked((profileview.profiles_titles.size() - 1), true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Connect(null);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean deviceSound = prefs.getBoolean("notifications_circuit_sound", false);
        char ch = prefs.getString("wifi_name", "J").charAt(0);
        e_mail = prefs.getString("email_address", "cahek4293@mail.ru");
        changeByteWifiSSID(ch);
        try {
            if (deviceSound) {
                DOS.write(modBus.getFrameFunction06((byte) 0x20, (byte) 0x08, (byte) 0x00, (byte) 0x01));

                Log.d(LOG_TAG, "Send bit mute");
            } else {
                DOS.write(modBus.getFrameFunction06((byte) 0x20, (byte) 0x08, (byte) 0x00, (byte) 0x00));
                Log.d(LOG_TAG, "Send bit unmute");
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error send mute/unmute command");
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);

    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            txlocation.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "lat = %1$.3f, lon = %2$.3f, time = %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
        Disconnect(null);
    }

    public void Disconnect(View view) {
        Log.d(LOG_TAG, "Try disconnect");
        try {
            if (socket != null) {
                socket.close();
                Log.d(LOG_TAG, "Disconnect");
                receiverun = false;
                connection_status.setTextColor(Color.RED);
                connection_status.setText("Отключен");
                Log.d(LOG_TAG, "Set text connection status is Connect");
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Connect(View view) {
        Log.d(LOG_TAG, "Try connect");
        if (socket == null && mWifi.isConnected()) {
            receiverun = true;
            receiveThread = new Thread(new ClientThread());
            receiveThread.start();
        } else {
            Log.d(LOG_TAG, "Socket is not null or wifi not connected");
        }
    }

    public void measuring(final View view) {

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        showLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

        try {
            DOS.write(modBus.getFrameFunction06((byte) 0x20, (byte) 0x04, (byte) 0x00, (byte) 0x01));
            Log.d(LOG_TAG, "Send bit start measuring");
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error connection");
            return;
        }
        progress = 0;
        view.setClickable(false);
        p.setVisibility(View.VISIBLE);
        Runnable myThread = new Runnable() {
            @Override
            public void run() {
                try {
                    while (progress < 60) {
                        if (progress == 0) {
                            progress++;
                            DOS.write(modBus.getFrameFunction03((byte) 0x10, (byte) 0x04, (byte) 0x00, (byte) 0x1));
                            Log.d(LOG_TAG, "Check bit battery");
                        } else {
                            progress++;
                            DOS.write(modBus.getFrameFunction03((byte) 0x10, (byte) 0x03, (byte) 0x00, (byte) 0x1));
                            Log.d(LOG_TAG, "Check bit finish measuring");
                        }
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                }
                updateGUI.sendEmptyMessage(2);
            }
        };
        new Thread(myThread).start();
    }

    public int demoMeasuring_i;

    public void demoMeasuring(final View view) {

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        showLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

        Mathematics mathematics = new Mathematics();
        double demoProfileR[] = new double[]{17.78, 17.74, 17.74, 17.75, 17.78, 17.76, 17.57, 17.4, 17.26, 17.08, 16.97, 16.87, 16.74, 16.54, 16.37, 16.17, 15.95, 15.76, 15.63, 15.55, 15.51, 15.43, 15.27, 15.15, 15.13, 15.15, 15.04, 14.97, 14.96, 14.99, 15.02, 15.08, 15.15, 15.2, 15.26, 15.38, 15.53, 15.66, 15.74, 15.88, 16.11, 16.18, 16.2, 16.22, 16.36, 16.47, 16.53, 16.66, 16.77, 16.96, 17.06, 17.09, 17.28, 17.43, 17.56, 17.69, 17.67, 17.65, 17.69, 17.73, 17.83, 17.94, 18.01, 18.1, 18.2, 18.25, 18.29, 18.35, 18.4, 18.44, 18.5, 18.51, 18.48, 18.5, 18.53, 18.52, 18.53, 18.56, 18.61, 18.69, 18.72, 18.67, 18.66, 18.65, 18.64, 18.63, 18.57, 18.48, 18.45, 18.39, 18.32, 18.29, 18.27, 18.23, 18.17, 18.16, 18.06, 17.94, 17.88, 17.78, 17.71, 17.67, 17.55, 17.45, 17.39, 17.24, 17.03, 16.93, 16.81, 16.61, 16.45, 16.31, 16.12, 15.92, 15.75, 15.65, 15.54, 15.32, 15.11, 14.93, 14.72, 14.5, 14.25, 14.02, 13.78, 13.61, 13.4, 13.13, 12.81, 12.44, 12.08, 11.82, 11.61, 11.32, 11.0, 10.59, 10.28, 9.99, 9.7, 9.37, 8.99, 8.5, 8.0, 7.59, 7.26, 6.81, 6.34, 5.89, 5.42, 5.01, 4.64, 4.44, 4.34, 4.4, 4.54, 4.72, 4.85, 4.93, 5.1, 5.26, 5.4, 5.6, 5.8, 5.99, 6.24, 6.52, 6.7, 6.88, 7.08, 7.29, 7.5, 7.75, 8.08, 8.43, 8.7, 8.9, 9.13, 9.4, 9.62, 9.88, 10.05, 10.27, 10.46, 10.63, 10.81, 11.02, 11.25, 11.5, 11.69, 11.76, 11.86, 12.01, 12.19, 12.32, 12.46, 12.48, 12.5, 12.52, 12.58, 12.68,};
        mathematics.calc(demoProfileR);
        Profile profile = new Profile(mathematics.getProfile_xy(), mathematics.getProfileSize(), "случайное измерение " + demoMeasuring_i++, Calendar.getInstance().getTime(), mathematics.getParams(), profileview.profiles.get(profileview.profiles.size() - 1).railwayNumber, profileview.profiles.get(profileview.profiles.size() - 1).railwaySide);
        profile.location = txlocation.getText().toString();

//Todo добавить проверку есть ли lm
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            profile.setCoordinates(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
        }
        else profile.setCoordinates(0,0);

        profile.drawable = true;

        double iznos = 0;
        for (int i = 155; i >= 0; i--) {
            iznos += Math.random() * 0.05;
            profile.double_[i][1] -= iznos;
        }
        profileview.addProfile(profile);

        double[] v = profile.params;
        DecimalFormat df = new DecimalFormat("0.00");
        textmeasure.setText(
                "Hv\t=\t" + df.format(v[0]) + "\tmm" + "\n" +
                        "Hh\t=\t" + df.format(v[1]) + "\tmm" + "\n" +
                        "Hr\t=\t" + df.format(v[2]) + "\tmm" + "\n" +
                        "H45\t=\t" + df.format(v[3]) + "\tmm" + "\n" +
                        "R45\t=\t" + df.format(v[4]) + "\tmm" + "\n" +
                        "S1\t=\t" + df.format(v[5]) + "\tmm" + "\n" +
                        "S2\t=\t" + df.format(v[6]) + "\tmm" + "\n");
        adapter.notifyDataSetChanged();
        lvMain.setItemChecked((profileview.profiles_titles.size() - 1), true);
        lvMain.smoothScrollToPosition(profileview.profiles_titles.size() - 1);
        profileview.invalidate();
    }

    public void GetProfile(final View view) {
        try {
            DOS.write(modBus.getProfilePartOne());
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error sending query");
        }
    }

    public void DeleteProfile(final View view) {
        int i = profileview.profiles.size();
        if (i > 0) {
            profileview.profiles.remove(i - 1);
            profileview.invalidate();
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {


            if ((socket == null) && (mWifi.isConnected())) {
                InetAddress serverAddr = null;
                try {
                    serverAddr = InetAddress.getByName(SERVER_IP);
                } catch (UnknownHostException e) {
                    Log.d(LOG_TAG, "UnknownHostException");
                    e.printStackTrace();
                }
                try {
                    socket = new Socket(serverAddr, SERVERPORT);
                    Log.d(LOG_TAG, "Connect");
                    updateGUI.sendEmptyMessage(1);
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Error create socket");
                    e.printStackTrace();
                }
                try {
                    DOS = new DataOutputStream(socket.getOutputStream());
                    DIS = new DataInputStream(socket.getInputStream());
                } catch (Exception e) {
                    Log.d(LOG_TAG, "Error create DataOutputStream or DataInputStream");
                    //e.printStackTrace();
                }

                try {
                    Thread.sleep(500);
                    DOS.write(modBus.getFrameFunction03((byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x28));
                    Log.d(LOG_TAG, "Sent query for get device info");
                } catch (Exception e) {
                    Log.d(LOG_TAG, "Error sent query for get device info");
                    e.printStackTrace();
                }

            }

            byte buf[] = new byte[260];

            while (receiverun) {
                try {
                    int r = DIS.read(buf);
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Error read input stream");
                }
                String debug = modBus.bytesToHex(buf);
                // Если пришла 3 функция посллать запрос на остальные данные
                byte temp[] = new byte[2];
                temp = modBus.getID();
                if (temp[0] == buf[0] && temp[1] == buf[1]) {
                    if (buf[7] == 0x03) {  //if Function number 3
                        if (buf[8] == (byte) 0xF0) { //if receive 0xF0 bytes
                            System.arraycopy(buf, 9, modBus.profile_data, 0, 0xF0);
                            Log.d(LOG_TAG, "Take profile part one");
                            try {
                                DOS.write(modBus.getProfilePartTwo()); //answer other
                            } catch (IOException e) {
                                Log.d(LOG_TAG, "Error sent ");
                                e.printStackTrace();
                            }
                        }
                        if (buf[8] == (byte) 0xA0) {
                            System.arraycopy(buf, 9, modBus.profile_data, 240, 0xA0);

                            Log.d(LOG_TAG, "Take profile part two");
                            try {
                                DOS.write(modBus.getFrameFunction06((byte) 0x20, (byte) 0x07, (byte) 0x00, (byte) 0x01));
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(LOG_TAG, "Error sent ");
                            }

                            Log.d(LOG_TAG, "Set the bit in the received");
                            modBus.ByteToDouble();
                            updateGUI.sendEmptyMessage(0);
                        }
                        if (modBus.getFunc_03()[8] == 0x10 && modBus.getFunc_03()[9] == 0x03) {
                            if (buf[10] == 0x01) {
                                Log.d(LOG_TAG, "Sent query for get profile");
                                progress = 60;
                                GetProfile(null);
                            }
                            if (buf[10] == 0x02) {
                                Log.d(LOG_TAG, "Error measuring");
                                progress = 60;
                            }
                            if (buf[10] == 0x03) {
                                Log.d(LOG_TAG, "Laser disconnected");
                                updateGUI.sendEmptyMessage(3);
                                progress = 60;
                            }
                        }
                        if (modBus.getFunc_03()[8] == 0x10 && modBus.getFunc_03()[9] == 0x04) {
                            if (buf[10] != 0x00) { // low charge acc
                                Log.d(LOG_TAG, "Low charge acc");
                                updateGUI.sendEmptyMessage(6);
                            } else {
                                Log.d(LOG_TAG, "High charge acc");
                                updateGUI.sendEmptyMessage(5);
                            }
                        }
                        if (buf[8] == (byte) 0x50) {
                            System.arraycopy(buf, 9, device_info, 0, 0x30);

                        }
                    }
                    if (buf[7] == 0x06) { //if Function number 6

                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Resources res = getResources();
        Drawable foldericon = res.getDrawable(R.drawable.folder);
        Drawable fileicon = res.getDrawable(R.drawable.file);

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.save_file:
                SaveFileDialog savefileDialog = new SaveFileDialog(this)
                        .setFolderIcon(foldericon)
                        .setFileIcon(fileicon)
                        .setAccessDeniedMessage("Access Denied")

                        .setFilter(".*\\.xml")
                        .setSaveDialogListener(
                                new SaveFileDialog.SaveDialogListener() {
                                    @Override
                                    public void OnSelectedFile(String fileName) {
                                        Toast.makeText(getApplicationContext(), "Save: " +
                                                fileName, Toast.LENGTH_LONG).show();

                                        File file = new File(fileName);

                                        try {
                                            file.createNewFile();

                                            FileWriter f = new FileWriter(fileName);
                                            XMLManager manager = new XMLManager();

                                            BufferedWriter bw = new BufferedWriter(f);
                                            manager.SaveScheme(bw, profileview, fileName);

                                            DialogFragment clearDialog = new ClearDialog(profileview, adapter);
                                            clearDialog.show(getFragmentManager(), "clearDialog");

                                            f.flush();
                                            f.close();


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                savefileDialog.show();

                break;

            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("device_info", device_info);
                startActivity(intent);
                break;

            case R.id.send_file:
                OpenFileDialog saveDialog = new OpenFileDialog(this);
                saveDialog.setOpenDialogListener(saveListener);
                saveDialog.show();
                break;

            case R.id.open_file:
                OpenFileDialog openDialog = new OpenFileDialog(this);
                openDialog.setOpenDialogListener(openListener);
                openDialog.show();
                break;

            case R.id.menu_clearAll:
                DialogFragment clearDialog = new ClearDialog(profileview, adapter);
                clearDialog.show(getFragmentManager(), "clearDialog");
                break;
            //TODO find another solve. foreach?
            case R.id.menu_selectAll:
                for (int i = 0; i < profileview.profiles.size(); i++) {
                    profileview.profiles.get(i).drawable = true;
                    adapter.notifyDataSetChanged();
                    lvMain.setItemChecked((i), true);
                    profileview.invalidate();
                }
                break;

            case R.id.menu_deselectAll:
                for (int i = 0; i < profileview.profiles.size(); i++) {
                    profileview.profiles.get(i).drawable = false;
                    adapter.notifyDataSetChanged();
                    lvMain.setItemChecked((i), false);
                    profileview.invalidate();
                }
                break;

            case R.id.menu_inverseSelection:
                for (int i = 0; i < profileview.profiles.size(); i++) {
                    if (lvMain.isItemChecked(i) == true) {
                        profileview.profiles.get(i).drawable = false;
                        lvMain.setItemChecked((i), false);
                    } else {
                        profileview.profiles.get(i).drawable = true;
                        lvMain.setItemChecked((i), true);
                    }
                    adapter.notifyDataSetChanged();
                    profileview.invalidate();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private OpenFileDialog.OpenDialogListener saveListener = new OpenFileDialog.OpenDialogListener() {
        @Override
        public void OnSelectedFile(String fileName) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{e_mail});
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Message body");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:" + fileName));
            emailIntent.setType("text/plain");
            startActivity(Intent.createChooser(emailIntent, "Send mail to:"));
            //startActivity(emailIntent);
        }
    };

    private OpenFileDialog.OpenDialogListener openListener = new OpenFileDialog.OpenDialogListener() {
        @Override
        public void OnSelectedFile(String fileName) {
            profileview.profiles.clear();
            profileview.profiles_titles.clear();
            adapter.notifyDataSetChanged();
            profileview.invalidate();
            try {
                FileReader f = new FileReader(fileName);
                XMLManager manager = new XMLManager();
                BufferedReader br = new BufferedReader(f);
                manager.OpenScheme(br, profileview, lvMain);

                f.close();
            } catch (Exception e) {

            }
        }
    };


    public void changeByteWifiSSID(char ch) {
        if (mWifi.isConnected()) {
            try {
                DOS.write(modBus.getFrameFunction06((byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) ch));
                Log.d(LOG_TAG, "Send bit ");
            } catch (Exception e) {
                Log.d(LOG_TAG, "Error connection");
            }
        }
    }

    public void testXls(final View view) throws IOException, WriteException {
        profileview.WriteXLS();
    }
}
