/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Umowa;
import java.util.ArrayList;

/**
 *
 * @author Osito
 */
public class UmowaBean {
    
    public static Umowa umowa;
    
    public static Umowa create() {
        if (umowa ==null) {
            umowa = new Umowa();
            umowa.setAngaz(AngazBean.create());
            umowa.setChorobowe(Boolean.TRUE);
            umowa.setChorobowedobrowolne(Boolean.FALSE);
            umowa.setDatado("2020-12-31");
            umowa.setDatanfz("2020-12-01");
            umowa.setDataod("2020-12-01");
            umowa.setDataspoleczne("2020-12-01");
            umowa.setDatazawarcia("2020-12-01");
            umowa.setDatazdrowotne("2020-12-01");
            umowa.setEmerytalne(Boolean.TRUE);
            umowa.setKodubezpieczenia("01000");
            umowa.setKodzawodu("5041");
            umowa.setKosztyuzyskania(250.0);
            umowa.setNfz("16");
            umowa.setNieliczFGSP(Boolean.FALSE);
            umowa.setNieliczFP(Boolean.FALSE);
            umowa.setOdliczaculgepodatkowa(Boolean.TRUE);
            umowa.setRentowe(Boolean.TRUE);
            umowa.setRodzajumowy(RodzajumowyBean.create());
            umowa.setWypadkowe(Boolean.TRUE);
            umowa.setZdrowotne(Boolean.TRUE);
            umowa.setSkladnikwynagrodzeniaList(new ArrayList<>());
            umowa.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createWynagrodzenie());
            umowa.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
            umowa.setSkladnikpotraceniaList(new ArrayList<>());
            umowa.getSkladnikpotraceniaList().add(SkladnikpotraceniaBean.create());
        }
        return umowa;
    }
        
        public static void main (String[] args) {
            Umowa zwrot = create();
            System.out.println("");
        }
}
