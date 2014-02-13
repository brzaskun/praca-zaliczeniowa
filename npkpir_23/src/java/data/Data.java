/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import entity.Amodok;
import entity.AmodokPK;
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Data implements Serializable {

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

    public static int compare(int rok1, int mc1, int rok2, int mc2) {
        int rokO1 = rok1;
        int rokO2 = rok2;
        int mcO1 = mc1;
        int mcO2 = mc2;
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
