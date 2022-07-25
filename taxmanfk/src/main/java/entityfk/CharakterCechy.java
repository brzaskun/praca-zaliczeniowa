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


    public CharakterCechy() {
    }
    
    public static String numtoString(int num) {
        String zwrot = "nieokreślona";
        switch(num) {
            case 1:
                zwrot = "neutralna";
                break;
            case 2:
                zwrot = "przychód";
                break;
            case 3:
                zwrot = "koszt";
                break;
            default:
                zwrot = "nieokreślona";
                    
        }
        return zwrot;
    }
    
    public static String przesuniecie(int num) {
        String zwrot = "bez przesunięcia";
        if (num > 0) {
            zwrot = " do przodu o "+num+" mc";
        } else if (num < 0) {
            zwrot = " do tyłu o "+num+" mc";
        }
        return zwrot;
    }
    
}
