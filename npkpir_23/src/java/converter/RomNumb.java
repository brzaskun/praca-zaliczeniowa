/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class RomNumb implements Serializable{
    
    public static String num2rom(int number) {
        switch (number) {
            case 1 : return "I";
            case 2 : return "II";
            case 3 : return "III";
            case 4 : return "IV";
            case 5 : return "V";
            case 6 : return "VI";
            case 7 : return "VII";
            case 8 : return "VIII";
            case 9 : return "IX";
            case 10 : return "X";
            case 11 : return "XI";
            case 12 : return "XII";
            case 13 : return "XIII";
            case 14 : return "XIV";
            case 15 : return "XV";
            case 16 : return "XVI";
            case 17 : return "XVII";
            case 18 : return "XVIII";
            case 19 : return "XIX";
            case 20 : return "XX";
        }
        return "out of range";
    }
    
    public static int rom2num(String roman) {
        switch (roman) {
            case "I" : return 1;
            case "II" : return 2;
            case "III" : return 3;
            case "IV" : return 4;
            case "V" : return 5;
            case "VI" : return 6;
            case "VII" : return 7;
            case "VIII" : return 8;
            case "IX" : return 9;
            case "X" : return 10;
            case "XI" : return 11;
            case "XII" : return 12;
            case "XIII" : return 13;
            case "XIV" : return 14;
            case "XV" : return 15;
            case "XVI" : return 16;
            case "XVII" : return 17;
            case "XVIII" : return 18;
            case "XIX" : return 19;
            case "XX" : return 20;
        }
        return 0;
    }
    
    public static String romInc(String roman) {
        int number = rom2num(roman);
        return num2rom(++number);
    }
    
    public static String romIncBack(String roman) {
        int number = rom2num(roman);
        return num2rom(--number);
    }
    
    public static String numbInc(String numb) {
        int number = Integer.parseInt(numb);
        return String.valueOf(++number);
    }
    
    public static String numbIncBack(String numb) {
        int number = Integer.parseInt(numb);
        return String.valueOf(--number);
    }
    
    public static String alfaInc(String alfa) {
        int charValue = alfa.charAt(0);
        return String.valueOf( (char) (charValue + 1));
    }
    
    public static String alfaIncBack(String alfa) {
        int charValue = alfa.charAt(0);
        return String.valueOf( (char) (charValue - 1));
    }
    
    public static String otherSign(String othersign) {
        switch (othersign) {
            case "-(1)" : return "-(2)";
            case "-(2)" : return "-(3)";
            case "-(3)" : return "-(4)";
            case "-(4)" : return "-(5)";
            case "-(5)" : return "-(6)";
            case "-(6)" : return "-(7)";
            case "-(7)" : return "-(8)";
            case "-(8)" : return "-(9)";
            case "-(9)" : return "-(10)";
            case "-(10)" : return "-(11)";
            case "-(11)" : return "-(12)";
        }
        return "-(x)";
    }
    
    public static String otherSignBack(String othersign) {
        switch (othersign) {
            case "-(1)" : return "-(x)";
            case "-(2)" : return "-(1)";
            case "-(3)" : return "-(2)";
            case "-(4)" : return "-(3)";
            case "-(5)" : return "-(4)";
            case "-(6)" : return "-(5)";
            case "-(7)" : return "-(6)";
            case "-(8)" : return "-(7)";
            case "-(9)" : return "-(8)";
            case "-(10)" : return "-(9)";
            case "-(11)" : return "-(10)";
        }
        return "-(x)";
    }
}
