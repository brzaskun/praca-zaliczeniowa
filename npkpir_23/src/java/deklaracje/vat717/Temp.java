/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracje.vat717;

import java.math.BigDecimal;
import jaxb.Makexml;

/**
 *
 * @author Osito
 */
public class Temp {
    public static void main(String[] args) {
        Deklaracja.Zalaczniki zal = new Deklaracja.Zalaczniki();
        WniosekVATZZ wniosekVATZZ = new WniosekVATZZ();
        wniosekVATZZ.naglowek = new TNaglowekVATZZ();
        wniosekVATZZ.naglowek.setKodFormularza(wniosekVATZZ.naglowek.getKodFormularza());
        wniosekVATZZ.pozycjeSzczegolowe = new WniosekVATZZ.PozycjeSzczegolowe();
        byte b = 1;
        wniosekVATZZ.pozycjeSzczegolowe.p8 = b;
        wniosekVATZZ.pozycjeSzczegolowe.p9 = new BigDecimal(100);
        wniosekVATZZ.pozycjeSzczegolowe.p10 = new WniosekVATZZ.PozycjeSzczegolowe.P10();
        wniosekVATZZ.pozycjeSzczegolowe.p10.setValue("Bo tak");
        String s = Makexml.marszal(wniosekVATZZ, WniosekVATZZ.class);
        int s1 = s.lastIndexOf("VATZZ");
        int s2 = s.lastIndexOf("golowe>");
        System.out.println("koniec "+s.substring(s1+8,s2+7));
    }
}
