package com.client_wifi;

public class Mathematics {

    double radius = 52;
    double radPerTick = 0.012345;

    double[][] profile_xy;
    int number_params = 7;
    double[] params = new double[number_params];
    static final double[][] GOST_Profile50 = new double[][]{
            {1.98984, -0.695556},
            {0.904379, -0.177222},
            {0.54256, 0.341111},
            {0, 1.205},
            {0, 2.58722},
            {0.05, 3.96944},
            {0.1, 6.04278},
            {0.15, 7.425},
            {0.180741, 8.63444},
            {0.26, 9.84389},
            {0.3, 10.8806},
            {0.361651, 13.4722},
            {0.5, 17.1006},
            {0.723469, 19.0011},
            {1.08529, 21.42},
            {1.80893, 23.1478},
            {2.71347, 24.7028},
            {3.61802, 26.085},
            {4.52257, 27.1217},
            {5.24621, 27.8128},
            {6.69348, 29.0222},
            {8.14076, 29.7133},
            {9.76894, 30.4044},
            {11.0353, 30.75},
            {12.6635, 31.0956},
            {14.1108, 31.4411},
            {16.2817, 31.7867},
            {18.4526, 31.7867},
            {19.8999, 32.1322},
            {21.1662, 32.1322},
            {23.1562, 32.4778},
            {25.3272, 32.4778},
            {27.1363, 32.6506},
            {29.3072, 32.8233},
            {32.3826, 32.8233},
            {35.639, 32.8233},
            {36, 33},
            {39.0763, 32.8233},
            {41.4281, 32.8233},
            {43.9608, 32.8233},
            {47.7599, 32.8233},
            {51.559, 32.4778},
            {53.549, 32.4778},
            {55.539, 32.305},
            {57.8909, 31.7867},
            {59.8809, 31.4411},
            {60.4236, 31.2683},
            {61.8709, 30.9228},
            {62.0518, 30.75},
            {63.8609, 29.8861},
            {65.67, 28.6767},
            {66.7554, 27.4672},
            {67.66, 26.7761},
            {68.5645, 25.7394},
            {69.1073, 24.8756},
            {69.65, 23.3206},
            {70.3736, 21.5928},
            {70.3736, 19.5194},
            {70.7354, 17.7917},
            {70.7354, 16.4094},
            {70.7354, 14.8544},
            {71.0973, 13.4722},
            {71.4591, 11.7444},
            {71.4591, 10.0167},
            {71.8209, 7.94333},
            {71.8209, 6.21556},
            {72.0018, 4.48778},
            {72.1827, 2.76},
            {72.0018, 1.72333},
            {71.2782, 0.513889},
            {70.1927, 0.168333},
            {68.9263, -0.35},
            {66.9363, -0.868333},
            {65.4891, -1.38667},
            {63.4991, -1.905}
    };
    static final double[][] GOST_Profile65 = new double[][]{
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
            {37, 35},
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
            {59.64301075, -3.76344086}
    };

    static final double[][] GOST_Profile75 = new double[][]{
            {17.24301075, -3.76344086},
            {12.03010753, -2.634408602},
            {10.82150538, -1.88172043},
            {8.012903226, -1.129032258},
            {5.208602151, -0.752688172},
            {3.2, 0},
            {1.595698925, 0.376344086},
            {0.787096774, 1.129032258},
            {0.378494624, 1.88172043},
            {0.361290323, 3.387096774}
    };

    public double[][] GOST_Profile = GOST_Profile50;
    public String GOST_ProfileType = "1";
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
        shift_x = 38.5;//подбор
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

        GostParams gost = new GostParams(0,0,0,0,0);
        //TODO исправить для 1 и 3
        if (GOST_ProfileType.equals("1"))
        {
            gost.setParams(0.1186,38.5,-4,36,33);
        }
        if (GOST_ProfileType.equals("2"))
        {
            gost.setParams(0.1186,38.5,-4,37,35);
        }
        if (GOST_ProfileType.equals("3"))
        {
            gost.setParams(0.1186,38.5,-4,37,35);
        }
        Hv_x = gost.middle_x;  // middle_x of gost было 37
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
        Hv = gost.middle_y - Hv_y;  //35 - max high in gost

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

        int gost_Hh_idx = GOST_Profile.length/2;  // 47 - middle of gost
        while ((Hh_y - GOST_Profile[gost_Hh_idx][1]) < 0) {
            gost_Hh_idx++;
        }
        y1 = GOST_Profile[gost_Hh_idx][1];
        y2 = GOST_Profile[gost_Hh_idx - 1][1];
        x1 = GOST_Profile[gost_Hh_idx][0];
        x2 = GOST_Profile[gost_Hh_idx - 1][0];
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
            P1.x = GOST_Profile[H45_idx][0];
            P1.y = GOST_Profile[H45_idx][1];

            P2.x = GOST_Profile[H45_idx + 1][0];
            P2.y = GOST_Profile[H45_idx + 1][1];
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
        for (int i = 0; i < GOST_Profile.length-1; i++) {
            S = 0.5*( (center_point_x - GOST_Profile[i+1][0])*(GOST_Profile[i][1] - GOST_Profile[i+1][1]) -
                    (GOST_Profile[i][0] - GOST_Profile[i+1][0])*(center_point_y - GOST_Profile[i+1][1]) );
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
