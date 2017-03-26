/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class CharakterCechy implements Serializable {
    
    private static final long serialVersionUID = 1L;
    public static final int NIEOKRESLONA = 0;
    public static final int NEUTRALNA = 0;
    public static final int PRZYCHOD = 1;
    public static final int KOSZT = 2;

    public CharakterCechy() {
    }
    
    public static String numtoString(int num) {
        String zwrot = "nieokreślona";
        switch(num) {
            case 1:
                zwrot = "przychód";
                break;
            case 2:
                zwrot = "koszt";
                break;
            default:
                zwrot = "nieokreślona";
                    
        }
        return zwrot;
    }
    
}
