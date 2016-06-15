package com.client_wifi;
import android.graphics.PointF;

import java.util.Vector;

public class Mathematics {

    double radius = 52;
    double radPerTick = 0.012345;

    double[][] profile_xy;
    int number_params = 7;
    double[] params = new double[number_params];

    static final double[][] GOST_Profile1 = new double[][]{
            {17.24301075, -3.76344086},
            {12.03010753, -2.634408602},
            {10.82150538, -1.88172043},
            {8.012903226, -1.129032258},
            {5.208602151, -0.752688172},
            {3.2, 0},
            {1.595698925, 0.376344086},
            {0.787096774, 1.129032258},
            {0.378494624, 1.88172043},
            {0.361290323, 3.387096774},
            {0.352688172, 4.139784946},
            {0.339784946, 5.268817204},
            {0.72688172, 6.397849462},
            {0.713978495, 7.52688172},
            {0.701075269, 8.655913979},
            {0.692473118, 9.408602151},
            {0.679569892, 10.53763441},
            {0.670967742, 11.29032258},
            {0.658064516, 12.41935484},
            {1.040860215, 13.92473118},
            {1.027956989, 15.05376344},
            {1.402150538, 17.31182796},
            {1.389247312, 18.44086022},
            {1.380645161, 19.19354839},
            {1.36344086, 20.69892473},
            {1.350537634, 21.82795699},
            {1.733333333, 23.33333333},
            {2.516129032, 24.83870968},
            {2.903225807, 25.96774194},
            {4.08172043, 27.84946237},
            {4.87311828, 28.60215054},
            {5.660215054, 29.7311828},
            {7.251612903, 30.48387097},
            {8.443010753, 31.23655914},
            {9.238709677, 31.61290323},
            {10.03010753, 32.3655914},
            {12.02150538, 33.11827957},
            {14.01290323, 33.87096774},
            {15.60860215, 34.24731183},
            {16.80860215, 34.24731183},
            {18.40860215, 34.24731183},
            {20.8, 35},
            {24.4, 35},
            {30.8, 35},
            {32, 35},
            {34, 35},
            {37.6, 35},
            {41.2, 35},
            {44.8, 35},
            {48.4, 35},
            {52.4, 35},
            {55.2, 35},
            {58.80430108, 34.62365591},
            {61.20860215, 34.24731183},
            {63.6172043, 33.49462366},
            {66.03010753, 32.3655914},
            {64.82150538, 33.11827957},
            {69.24731183, 30.86021505},
            {69.5, 30.65},
            {70.05591398, 30.10752688},
            {70.46021505, 29.7311828},
            {70.86451613, 29.35483871},
            {72.07741935, 28.22580645},
            {73.29462366, 26.72043011},
            {73.70752688, 25.59139785},
            {74.52043011, 24.46236559},
            {75.34193548, 22.58064516},
            {75.35483871, 21.4516129},
            {75.37204301, 19.94623656},
            {75.79784946, 17.68817204},
            {75.80645161, 16.93548387},
            {75.82795699, 15.05376344},
            {76.23655914, 14.30107527},
            {76.24946237, 13.17204301},
            {76.26666667, 11.66666667},
            {76.70107527, 8.655913979},
            {76.72688172, 6.397849462},
            {77.14408602, 4.892473118},
            {77.1655914, 3.010752688},
            {77.18709677, 1.129032258},
            {76.39569892, 0.376344086},
            {73.60430108, -0.376344086},
            {70.81290323, -1.129032258},
            {68.8172043, -1.505376344},
            {65.62580645, -2.258064516},
            {64.43010753, -2.634408602},
            {61.23870968, -3.387096774},
            {59.64301075, -3.76344086}};

    static final double[][] GOST_Profile2 = new double[][]{
            {-1.638, 1.7937486},
            {-1.2428342, 2.3111089},
            {-0.84766834, 2.9146958},
            {-0.45250251, 4.6554906},
            {-0.057336683, 6.3844144},
            {0.33782915, 10.10244},
            {0.73299497, 14.519168},
            {1.1281608, 19.000942},
            {1.5233266, 22.381155},
            {1.9184925, 24.01566},
            {2.3136583, 25.056371},
            {2.7088241, 25.832708},
            {3.1039899, 26.544001},
            {3.4991558, 26.93217},
            {3.8943216, 27.326633},
            {4.2894874, 28.10297},
            {4.6846533, 28.514219},
            {5.0798191, 28.902387},
            {5.4749849, 29.290556},
            {5.8701508, 29.678724},
            {6.2653166, 29.819304},
            {6.6604824, 30.037518},
            {7.0556482, 30.425687},
            {7.4508141, 30.654391},
            {7.8459799, 30.78448},
            {8.2411457, 31.071935},
            {8.6363116, 31.143274},
            {9.0314774, 31.489478},
            {9.4266432, 31.502068},
            {9.821809, 31.890236},
            {10.216975, 31.907022},
            {10.612141, 32.24903},
            {11.007307, 32.324565},
            {11.402472, 32.324565},
            {11.797638, 32.578448},
            {12.192804, 32.742109},
            {12.58797, 32.742109},
            {12.983136, 32.907867},
            {13.378302, 33.159652},
            {13.773467, 33.159652},
            {14.168633, 33.159652},
            {14.563799, 33.207911},
            {14.958965, 33.577196},
            {15.354131, 33.577196},
            {15.749296, 33.577196},
            {16.144462, 33.577196},
            {16.539628, 33.896123},
            {16.934794, 33.994739},
            {17.32996, 33.994739},
            {17.725126, 33.994739},
            {18.120291, 33.994739},
            {18.515457, 33.994739},
            {18.910623, 33.994739},
            {19.305789, 33.994739},
            {19.700955, 33.994739},
            {20.096121, 34.049293},
            {20.491286, 34.412283},
            {20.886452, 34.412283},
            {21.281618, 34.412283},
            {21.676784, 34.412283},
            {22.07195, 34.412283},
            {22.467116, 34.412283},
            {22.862281, 34.412283},
            {23.257447, 34.412283},
            {23.652613, 34.412283},
            {24.047779, 34.412283},
            {24.442945, 34.412283},
            {24.838111, 34.412283},
            {25.233276, 34.412283},
            {25.628442, 34.412283},
            {26.023608, 34.412283},
            {26.418774, 34.410184},
            {26.81394, 34.022016},
            {27.209106, 33.994739},
            {27.604271, 34.326256},
            {27.999437, 34.412283},
            {28.394603, 34.412283},
            {28.789769, 34.412283},
            {29.184935, 34.412283},
            {29.580101, 34.412283},
            {29.975266, 34.412283},
            {30.370432, 34.412283},
            {30.765598, 34.412283},
            {31.160764, 34.412283},
            {31.55593, 34.412283},
            {31.951095, 34.412283},
            {32.346261, 34.412283},
            {32.741427, 34.412283},
            {33.136593, 34.412283},
            {33.531759, 34.412283},
            {33.926925, 34.412283},
            {34.32209, 34.412283},
            {34.717256, 34.412283},
            {35.112422, 34.412283},
            {35.507588, 34.412283},
            {35.902754, 34.412283},
            {36.29792, 34.412283},
            {36.693085, 34.412283},
            {37.088251, 34.412283},
            {37.483417, 34.412283},
            {37.878583, 34.412283},
            {38.273749, 34.412283},
            {38.668915, 34.412283},
            {39.06408, 34.412283},
            {39.459246, 34.412283},
            {39.854412, 34.412283},
            {40.249578, 34.412283},
            {40.644744, 34.412283},
            {41.03991, 34.412283},
            {41.435075, 34.412283},
            {41.830241, 34.412283},
            {42.225407, 34.412283},
            {42.620573, 34.412283},
            {43.015739, 34.412283},
            {43.410905, 34.412283},
            {43.80607, 34.412283},
            {44.201236, 34.412283},
            {44.596402, 34.412283},
            {44.991568, 34.412283},
            {45.386734, 34.412283},
            {45.781899, 34.412283},
            {46.177065, 34.208756},
            {46.572231, 34.16889},
            {46.967397, 34.412283},
            {47.362563, 34.412283},
            {47.757729, 34.412283},
            {48.152894, 34.468934},
            {48.54806, 34.829826},
            {48.943226, 34.829826},
            {49.338392, 34.829826},
            {49.733558, 34.829826},
            {50.128724, 34.502506},
            {50.523889, 34.412283},
            {50.919055, 34.412283},
            {51.314221, 34.412283},
            {51.709387, 34.412283},
            {52.104553, 34.412283},
            {52.499719, 34.412283},
            {52.894884, 34.412283},
            {53.29005, 34.412283},
            {53.685216, 34.412283},
            {54.080382, 34.412283},
            {54.475548, 34.408086},
            {54.870714, 34.019918},
            {55.265879, 33.994739},
            {55.661045, 33.994739},
            {56.056211, 33.994739},
            {56.451377, 33.994739},
            {56.846543, 33.994739},
            {57.241709, 33.994739},
            {57.636874, 33.807999},
            {58.03204, 33.577196},
            {58.427206, 33.577196},
            {58.822372, 33.577196},
            {59.217538, 33.577196},
            {59.612704, 33.53733},
            {60.007869, 33.159652},
            {60.403035, 33.159652},
            {60.798201, 32.790367},
            {61.193367, 32.742109},
            {61.588533, 32.742109},
            {61.983698, 32.742109},
            {62.378864, 32.490324},
            {62.77403, 32.324565},
            {63.169196, 32.13153},
            {63.564362, 31.907022},
            {63.959528, 31.907022},
            {64.354693, 31.802111},
            {64.749859, 31.489478},
            {65.145025, 31.443318},
            {65.540191, 31.055149},
            {65.935357, 30.666981},
            {66.330523, 30.654391},
            {66.725688, 30.308187},
            {67.120854, 29.920018},
            {67.51602, 29.819304},
            {67.911186, 29.561225},
            {68.306352, 29.173056},
            {68.701518, 28.784888},
            {69.096683, 28.396719},
            {69.491849, 28.00855},
            {69.887015, 27.509177},
            {70.282181, 26.81467},
            {70.677347, 26.374046},
            {71.072513, 25.597709},
            {71.467678, 24.821372},
            {71.862844, 24.045035},
            {72.25801, 22.916199},
            {72.653176, 21.105445},
            {73.048342, 18.577104},
            {73.443508, 12.687432},
            {73.838673, 7.8972226},
            {74.233839, 5.6143719},
            {74.629005, 3.5329491},
            {75.024171, 1.8438913},
            {75.419337, 1.8438913},
            {75.814503, 1.8438913},
            {76.209668, 0},
            {76.604834, 0}
    };
    private int profileSize;

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setRadPerTick(double radPerTick) {
        this.radPerTick = radPerTick;
    }

    public void calc(double[] inmas) {  // inmas ReceiveProfile
        profileSize = inmas.length;
        double L1 = 13;
        double shift_x = 0, shift_y = 0, shift_fi = 0;
        shift_fi = 0.1186;
        shift_x = 40;
        shift_y = -4;
        int gost_anc_idx = 7;
        int profile_anc_idx = 199;

        profile_xy = new double[inmas.length][2];
        //сглаживание массива. нули рассчитываем как i+1 + i-1 /2 если справа не ноль
        for (int i = 1; i < inmas.length - 1; i++) {
            if (inmas[i] == 0) {
                inmas[i] = (inmas[i - 1] + inmas[i + 1]) / 2;
            }
        }

        //Расчет всего массива, переход из полярных в декартовы
        for (int i = 0; i < inmas.length; i++) {
            profile_xy[i][0] = (radius - inmas[i]) * Math.cos(i * radPerTick + shift_fi) + shift_x;
            profile_xy[i][1] = (radius - inmas[i]) * Math.sin(i * radPerTick + shift_fi) + shift_y;
        }
        //-------------------------------------------------------------------------------------
        //Рассчет Hv
        //http://habrahabr.ru/post/148325/
        //-------------------------------------------------------------------------------------
        double a, b, c, x1, x2, y1, y2;
        int Hv_idx = 0;
        double Hv = 0, Hv_x = 0, Hv_y = 0;
        //Hv_idx = (int)(((1.5707 - shift_fi)/radPerTick) + 0.5);

        Hv_x = 37;  // middle_x of gost было 38,5
        while ((Hv_x - profile_xy[Hv_idx][0]) < 0) {
            Hv_idx++;
        }

        y1 = profile_xy[Hv_idx][1];
        y2 = profile_xy[Hv_idx - 1][1];
        x1 = profile_xy[Hv_idx][0];
        x2 = profile_xy[Hv_idx - 1][0];

        a = y1 - y2;
        b = x2 - x1;
        c = x1 * y2 - x2 * y1;

        Hv_y = -((a * Hv_x + c) / b);
        Hv = 35 - Hv_y;  //35 - max high in gost

        //рассчет Hh
        double Hh = 0, Hh_x = 0, Hh_y = 0;

        Hh_y = Hv_y - L1;

        int Hh_idx = Hv_idx;

        while ((Hh_y - profile_xy[Hh_idx][1]) < 0) {
            Hh_idx--;
            //TODO Если все точки лежат ниже, то бред, надо обрабтать. (Прт нормальном рельсе не бывает)
            if (Hh_idx == 0)
                break;
        }

        y1 = profile_xy[Hh_idx][1];
        y2 = profile_xy[Hh_idx + 1][1];
        x1 = profile_xy[Hh_idx][0];
        x2 = profile_xy[Hh_idx + 1][0];

        a = y1 - y2;
        b = x2 - x1;
        c = x1 * y2 - x2 * y1;

        Hh_x = -((b * Hh_y + c) / a);

        int gost_Hh_idx = 47;  // 47 - middle of gost
        while ((Hh_y - GOST_Profile1[gost_Hh_idx][1]) < 0) {
            gost_Hh_idx++;
        }
        y1 = GOST_Profile1[gost_Hh_idx][1];
        y2 = GOST_Profile1[gost_Hh_idx - 1][1];
        x1 = GOST_Profile1[gost_Hh_idx][0];
        x2 = GOST_Profile1[gost_Hh_idx - 1][0];
        a = y1 - y2;
        b = x2 - x1;
        c = x1 * y2 - x2 * y1;
        double gost_Hh_x = -((b * Hh_y + c) / a);
        Hh = gost_Hh_x - Hh_x;

        //-------------------------------------------------------------------------------------
        //
        //Расчет износа под углом в 45гр
        //
        //-------------------------------------------------------------------------------------
        double H45 = 0, H45_x = 0, H45_y, gost_H45_x = 0, gost_H45_y = 0;
        int H45_idx = 0;
        boolean check;
        PointDouble Line45_1 = new PointDouble(36.5,0);
        PointDouble Line45_2 = new PointDouble(76.5,40);
        PointDouble P1 = new PointDouble(0,0);
        PointDouble P2 = new PointDouble(1,1);
        PointDouble ProfileInterceptPoint = new PointDouble(0,0);
        PointDouble GostInterceptPoint = new PointDouble();
        Interception interception = new Interception();

        //Поиск точки пересечения с измеренным профилем
        H45_idx = 0;
        do {
            P1.x = profile_xy[H45_idx][0];
            P1.y = profile_xy[H45_idx][1];

            P2.x = profile_xy[H45_idx + 1][0];
            P2.y = profile_xy[H45_idx + 1][1];
            H45_idx++;
        } while ((!interception.isHasIntercept(P1,P2,Line45_1,Line45_2)&(H45_idx<profileSize-2)));
        ProfileInterceptPoint = interception.interceptPoint(P1,P2,Line45_1,Line45_2);

        //Поиск точки пересечения с профилем по ГОСТ
        H45_idx = 0;
        do {
            P1.x = GOST_Profile1[H45_idx][0];
            P1.y = GOST_Profile1[H45_idx][1];

            P2.x = GOST_Profile1[H45_idx + 1][0];
            P2.y = GOST_Profile1[H45_idx + 1][1];
            H45_idx++;
        } while (!interception.isHasIntercept(P1,P2,Line45_1,Line45_2));
        GostInterceptPoint = interception.interceptPoint(P1,P2,Line45_1,Line45_2);


        H45 = Math.sqrt((ProfileInterceptPoint.x - GostInterceptPoint.x)*(ProfileInterceptPoint.x - GostInterceptPoint.x) +
                        (ProfileInterceptPoint.y - GostInterceptPoint.y)*(ProfileInterceptPoint.y - GostInterceptPoint.y));

        //поиск площадей
        double S, S1, S2;
        S1 = 0;
        S2 = 0;
        double center_point_x = 38.5,center_point_y = 0;

        for (int i = 0; i < profileSize - 1; i++) {
            S = 0.5*( (center_point_x - profile_xy[i+1][0])*(profile_xy[i][1] - profile_xy[i+1][1]) -
                      (profile_xy[i][0] - profile_xy[i+1][0])*(center_point_y - profile_xy[i+1][1]) );
            S2 += S;
        }
        //площадь госта. Убрать, забить константой
        for (int i = 0; i < 86; i++) {
            S = 0.5*( (center_point_x - GOST_Profile1[i+1][0])*(GOST_Profile1[i][1] - GOST_Profile1[i+1][1]) -
                    (GOST_Profile1[i][0] - GOST_Profile1[i+1][0])*(center_point_y - GOST_Profile1[i+1][1]) );
            S1 += S;
        }

        params[0] = Hv;
        params[1] = Hh;
        params[2] = H45; // H45==Hr
        params[3] = Hv + 0.5 * Hh;
        params[4] = Hv + 0.5 * H45;
        params[5] = Math.abs(S1);
        params[6] = Math.abs(S2);

    }

    public double[] getParams() {
        return params;
    }

    public double[][] getProfile_xy() {
        return profile_xy;
    }

    public int getProfileSize() {
        return profileSize;
    }

    public class PointDouble{
        double x;
        double y;
        public PointDouble()
        {
            x=0;
            y=0;
        }

        public PointDouble(double x_, double y_)
        {
            x=x_;
            y=y_;
        }

    }

    public class Interception{
        private PointDouble P1 = new PointDouble();
        private PointDouble P2 = new PointDouble();
        private PointDouble M1 = new PointDouble();
        private PointDouble M2 = new PointDouble();

        public Interception(PointDouble P1,PointDouble P2, PointDouble M1, PointDouble M2){
            this.P1 = P1;
            this.P2 = P2;
            this.M1 = M1;
            this.M2 = M2;
        }
        public Interception(){
            P1.x = 0;
            P1.y = 0;
            P2.x = 0;
            P2.y = 0;
            M1.x = 0;
            M1.y = 0;
            M2.x = 0;
            M2.y = 0;
        }

        private double dotVector(PointDouble A1,PointDouble A2,PointDouble B1,PointDouble B2){
            PointDouble V1 = new PointDouble();
            PointDouble V2 = new PointDouble();

            V1.x = A2.x - A1.x;
            V1.y = A2.y - A1.y;
            V2.x = B2.x - B1.x;
            V2.y = B2.y - B1.y;

            return V1.x*V2.y-V2.x*V1.y;
        }

        private boolean isHasIntercept(PointDouble A1,PointDouble A2, PointDouble B1, PointDouble B2){
            if ((dotVector(A1,A2,A1,B2)*dotVector(A1,A2,A1,B1)<0)&&(dotVector(B1,B2,B1,A1)*dotVector(B1,B2,B1,A2)<0))
                return true;
            else
                return false;
        }

        public PointDouble interceptPoint(PointDouble P1,PointDouble P2, PointDouble P3, PointDouble P4){
            double Ua;
            PointDouble resPoint = new PointDouble(0,0);
            Ua = ( (P4.x-P3.x)*(P1.y-P3.y)-(P4.y-P3.y)*(P1.x-P3.x) )/( (P4.y-P3.y)*(P2.x-P1.x)-(P4.x-P3.x)*(P2.y-P1.y) );
            resPoint.x = P1.x + Ua*(P2.x-P1.x);
            resPoint.y = P1.y + Ua*(P2.y-P1.y);
            return resPoint;
        }

    }

}
