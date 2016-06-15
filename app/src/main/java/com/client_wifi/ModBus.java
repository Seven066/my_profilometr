package com.client_wifi;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fourp on 23.10.2015.
 * E-mail: 065@t-sk.ru
 */
public class ModBus {

    public static int PROFILE_SIZE_BYTE = 410;
    public static int PROFILE_SIZE = 200;
    public byte profile_data[] = new byte[PROFILE_SIZE_BYTE];
    double ReceiveProfile[] = new double[PROFILE_SIZE];

    //CONST
    private static final double RAD_PER_TICK = 0.012345;
    private static final double RAD_ROTATE = 0.50;
    private static final double RADIUS = 60;

    private byte[] func_03 = new byte[12];
    private byte[] func_06 = new byte[12];

    public static int id = 0;

    public byte[] getFunc_03() {
        return func_03;
    }

    public byte[] getFunc_06() {
        return func_06;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void ByteToDouble() {
        int temp = 0;
        for (int i = 0; i < 200; i++) {
            temp = ((profile_data[2 * i] & 0xFF) << 8) + (profile_data[2 * i + 1] & 0xFF);
            ReceiveProfile[i] = temp / 100.0;
        }

    }

    public byte[] getProfilePartOne() {
        return getFrameFunction03((byte) 0xD0, (byte) 0x00, (byte) 0x00, (byte) 0x78);
    }

    public byte[] getProfilePartTwo() {
        return getFrameFunction03((byte) 0xD0, (byte) 0x78, (byte) 0x00, (byte) 0x50);
    }

    public byte[] getID() {
        byte bufID[] = new byte[2];
        bufID[0] = (byte) ((id >> 8) & 0xFF);
        bufID[1] = (byte) (id & 0xFF);
        return bufID;
    }


    public byte[] getFrameFunction03(byte Adress_H, byte Adress_L,
                                     byte Number_of_registers_H, byte Number_of_registers_L) {
        id++;
        //MBAP:

        func_03[0] = (byte) ((id >> 8) & 0xFF);
        func_03[1] = (byte) (id & 0xFF);
        func_03[2] = (byte) 0x00;
        func_03[3] = (byte) 0x00;
        func_03[4] = (byte) 0x00; // Data amount H
        func_03[5] = (byte) 0x06; // Data amount L
        func_03[6] = (byte) 0x01; //DEVICE_ID

        //PDU:
        func_03[7] = 3;             //Number function
        func_03[8] = Adress_H;
        func_03[9] = Adress_L;
        func_03[10] = Number_of_registers_H;
        func_03[11] = Number_of_registers_L;


        return func_03;
    }

    public byte[] getFrameFunction06(byte Adress_H, byte Adress_L, byte Value_H,
                                     byte Value_L) {
        id++;
        //MBAP:

        func_06[0] = (byte) ((id >> 8) & 0xFF);
        func_06[1] = (byte) (id & 0xFF);
        func_06[2] = (byte) 0x00;
        func_06[3] = (byte) 0x00;
        func_06[4] = (byte) 0x00;
        func_06[5] = (byte) 0x06;
        func_06[6] = (byte) 0x01; //DEVICE_ID

        //PDU:
        func_06[7] = 6;
        func_06[8] = Adress_H;
        func_06[9] = Adress_L;
        func_06[10] = Value_H;
        func_06[11] = Value_L;


        return func_06;
    }

    static void sleep(int i) {
        try {
            TimeUnit.MILLISECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
