package org.projpi.jetSuite.rpg.chat.util;

import java.util.Random;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class MessageUtils
{
    private static Random random = new Random();

    public static String distort(String s, int dist, int max)
    {
        String distorted = "";
        for(char c : s.toCharArray())
        {
            distorted += (random.nextInt(max) > dist) ? c : (random.nextInt(50) > 20) ? '-' : ' ' ;
        }
        return distorted;
    }
}
