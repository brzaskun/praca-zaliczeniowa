/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package waluty;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class Z implements Serializable {
    public static double z(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return m;
    }
}
