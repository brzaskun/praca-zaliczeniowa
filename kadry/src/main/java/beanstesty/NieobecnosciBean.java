/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Nieobecnosc;

/**
 *
 * @author Osito
 */
public class NieobecnosciBean {
    
    public static Nieobecnosc choroba;
    public static Nieobecnosc choroba2;
    public static Nieobecnosc korektakalendarzagora;
    public static Nieobecnosc korektakalendarzadol;
    public static Nieobecnosc urlop;
    public static Nieobecnosc urlopbezplatny;
    
    public static Nieobecnosc createChoroba() {
        if (choroba==null) {
           choroba = new Nieobecnosc();
           choroba.setDataod("2020-12-03");
           choroba.setDatado("2020-12-05");
           choroba.setNieobecnosckodzus(NieobecnosckodzusBean.createChoroba());
        }
        return choroba;
    }
    
    public static Nieobecnosc createKorektakalendarzaGora() {
        if (korektakalendarzagora==null) {
           korektakalendarzagora = new Nieobecnosc();
           korektakalendarzagora.setDataod("2020-12-01");
           korektakalendarzagora.setDatado("2020-12-02");
           korektakalendarzagora.setNieobecnosckodzus(NieobecnosckodzusBean.createKorektakalendarza());
        }
        return korektakalendarzagora;
    }
    
    public static Nieobecnosc createKorektakalendarzaDol() {
        if (korektakalendarzadol==null) {
           korektakalendarzadol = new Nieobecnosc();
           korektakalendarzadol.setDataod("2020-12-30");
           korektakalendarzadol.setDatado("2020-12-31");
           korektakalendarzadol.setNieobecnosckodzus(NieobecnosckodzusBean.createKorektakalendarza());
        }
        return korektakalendarzadol;
    }
    
    public static Nieobecnosc createChoroba2() {
        if (choroba2==null) {
           choroba2 = new Nieobecnosc();
           choroba2.setDataod("2020-12-15");
           choroba2.setDatado("2020-12-17");
           choroba2.setNieobecnosckodzus(NieobecnosckodzusBean.createChoroba());
        }
        return choroba2;
    }
    
    public static Nieobecnosc createUrlop() {
        if (urlop==null) {
           urlop = new Nieobecnosc();
           urlop.setDataod("2020-12-18");
           urlop.setDatado("2020-12-22");
           urlop.setDnikalendarzowe(4);
           urlop.setDnirobocze(3);
           urlop.setNieobecnosckodzus(NieobecnosckodzusBean.createUrlop());
        }
        return urlop;
    }
    
     public static Nieobecnosc createUrlopBezplatny() {
        if (urlopbezplatny==null) {
           urlopbezplatny = new Nieobecnosc();
           urlopbezplatny.setDataod("2020-12-28");
           urlopbezplatny.setDatado("2020-12-29");
           urlopbezplatny.setNieobecnosckodzus(NieobecnosckodzusBean.createUrlopBezplatny());
        }
        return urlopbezplatny;
    }
}
