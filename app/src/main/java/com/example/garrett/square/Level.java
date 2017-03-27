package com.example.garrett.square;

import java.util.Vector;

/**
 * Created by Garrett on 11/26/2016.
 */

public class Level {
    int dummy;
    //time chunks = 120/10 = 12
    //expected number of spawns over 10 seconds
    double[][][] spawn_rates = {
            //level 1
            {
                {5, 5, 5, 5, 20, 15, 8, 8, 8, 12, 12, 12},    //JunkIce
                {5, 5, 5, 5, 20, 15, 8, 8, 8, 12, 12, 12},    //JunkIron

                {2, 4, 5, 5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5},    //PlanetCold
                {2, 4, 5, 5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5},    //PlanetEarth
                {0, 0, 0, 0, 0, 0, 2, 3, 3, 3, 3, 3},      //PlanetGreenPink
                {1, 1, 1, 2, 2, 2, 2.5, 3, 3.5, 3.5, 4, 4},      //PlanetJupiter

                {1, 1, 1, 1.5, 2, 2.5, 3, 3, 3, 3, 3, 3},    //SunBasic
                {1, 1, 1, 1.5, 2, 2.5, 3, 3, 3, 3, 3, 3},    //SunBlue
                {1, 1, 1, 1.5, 2, 2.5, 3, 3, 3, 3, 3, 3},    //SunRed
                {1, 1, 1, 1.5, 2, 2.5, 3, 3, 3, 3, 3, 3},    //SunYellow
            },
            //level 2
            {
                {5, 5, 5, 5, 20, 15, 8, 8, 8, 12, 12, 12},    //JunkIce
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //JunkIron

                {5, 8, 10, 10, 5, 5, 5, 5, 5, 5, 5, 5},    //PlanetCold
                {5, 8, 10, 10, 5, 5, 5, 5, 5, 5, 5, 5},    //PlanetEarth
                {0, 0, 0, 0, 0, 0, 3, 5, 5, 5, 5, 5},      //PlanetGreenPink
                {3, 3, 3, 3, 3, 3, 5, 6, 7, 7, 8, 8},      //PlanetJupiter

                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunBasic
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunBlue
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunRed
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunYellow
            },
            //level 3
            {
                {5, 5, 5, 5, 20, 15, 8, 8, 8, 12, 12, 12},    //JunkIce
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //JunkIron

                {5, 8, 10, 10, 5, 5, 5, 5, 5, 5, 5, 5},    //PlanetCold
                {5, 8, 10, 10, 5, 5, 5, 5, 5, 5, 5, 5},    //PlanetEarth
                {0, 0, 0, 0, 0, 0, 3, 5, 5, 5, 5, 5},      //PlanetGreenPink
                {3, 3, 3, 3, 3, 3, 5, 6, 7, 7, 8, 8},      //PlanetJupiter

                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunBasic
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunBlue
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunRed
                {2, 2, 2, 3, 3.5, 4, 4.5, 5, 5, 5, 5, 5},    //SunYellow
            },
    };
    Level(){
        dummy = 0;
        //do nothing
    }
    Level(int i){
        //load background
    }


}
