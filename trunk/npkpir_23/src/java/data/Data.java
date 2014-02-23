/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Data implements Serializable {

    /**
     *
     * @param rok
     * @param mc
     * @return
     */
    public static String datapk(String rok, String mc) {
        switch (mc) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                return rok + "-" + mc + "-31";
            case "02":
                return rok + "-" + mc + "-28";
            default:
                return rok + "-" + mc + "-30";
        }
    }

    public static int compare(int rok1P, int mc1P, int rok2W, int mc2W) {
        int rokO1 = rok1P;
        int rokO2 = rok2W;
        int mcO1 = mc1P;
        int mcO2 = mc2W;
        if (rokO1 < rokO2) {
            return -1;
        } else if (rokO1 > rokO2) {
            return 1;
        } else if (rokO1 == rokO2) {
            if (mcO1 == mcO2) {
                return 0;
            } else if (mcO1 < mcO2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
    
     /**
     * Porównywanie dwóch dat. Data późniejsza i data wcześniejsza.
     * Data w formacie rrrr-MM-dd
     * 
     * @param datapozniejsza data późniejsza do porównywania
     * @param datawczesniejsza data wcześniejsza do porównywania
     * @return    <code>true</code> jeżeli data póżniejsza jest późniejsza jest wcześniejszej 
     *            <code>0</code> jeżeli daty są równe
     *            <code>false</code> jeżeli wcześniejsza jest jednak późniejsza
     */
    public static int compare(String datapozniejsza, String datawczesniejsza) {
        int rokO1 = Integer.parseInt(datapozniejsza.substring(0,4));
        int rokO2 = Integer.parseInt(datawczesniejsza.substring(0, 4));
        int mcO1 = Integer.parseInt(datapozniejsza.substring(5, 7));
        int mcO2 = Integer.parseInt(datawczesniejsza.substring(5, 7));
        if (rokO1 < rokO2) {
            return -1;
        } else if (rokO1 > rokO2) {
            return 1;
        } else if (rokO1 == rokO2) {
            if (mcO1 == mcO2) {
                return 0;
            } else if (mcO1 < mcO2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
    
}
