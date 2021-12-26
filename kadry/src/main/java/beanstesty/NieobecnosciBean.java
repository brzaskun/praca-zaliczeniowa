/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.KalendarzmiesiacFacade;
import dao.NieobecnoscFacade;
import data.Data;
import embeddable.Mce;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Umowa;
import error.E;
import msg.Msg;

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
           choroba.setSwiadczeniekodzus(NieobecnosckodzusBean.createChoroba());
        }
        return choroba;
    }
     public static Nieobecnosc createChoroba2() {
        if (choroba2==null) {
           choroba2 = new Nieobecnosc();
           choroba2.setDataod("2020-12-15");
           choroba2.setDatado("2020-12-17");
           choroba2.setSwiadczeniekodzus(NieobecnosckodzusBean.createChoroba());
        }
        return choroba2;
    }
    public static Nieobecnosc createKorektakalendarzaGora() {
        if (korektakalendarzagora==null) {
           korektakalendarzagora = new Nieobecnosc();
           korektakalendarzagora.setDataod("2020-12-01");
           korektakalendarzagora.setDatado("2020-12-02");
           korektakalendarzagora.setSwiadczeniekodzus(NieobecnosckodzusBean.createKorektakalendarza());
        }
        return korektakalendarzagora;
    }
    
    public static Nieobecnosc createKorektakalendarzaDol() {
        if (korektakalendarzadol==null) {
           korektakalendarzadol = new Nieobecnosc();
           korektakalendarzadol.setDataod("2020-12-30");
           korektakalendarzadol.setDatado("2020-12-31");
           korektakalendarzadol.setSwiadczeniekodzus(NieobecnosckodzusBean.createKorektakalendarza());
        }
        return korektakalendarzadol;
    }
    
   
    
    public static Nieobecnosc createUrlop() {
        if (urlop==null) {
           urlop = new Nieobecnosc();
           urlop.setDataod("2020-12-07");
           urlop.setDatado("2020-12-09");
           urlop.setDnikalendarzowe(3);
           urlop.setDniroboczenieobecnosci(3);
           urlop.setSwiadczeniekodzus(NieobecnosckodzusBean.createUrlop());
        }
        return urlop;
    }
    
     public static Nieobecnosc createUrlopBezplatny() {
        if (urlopbezplatny==null) {
           urlopbezplatny = new Nieobecnosc();
           urlopbezplatny.setDataod("2020-12-28");
           urlopbezplatny.setDatado("2020-12-29");
           urlopbezplatny.setSwiadczeniekodzus(NieobecnosckodzusBean.createUrlopBezplatny());
        }
        return urlopbezplatny;
    }
     
     public static boolean nanies(Nieobecnosc nieobecnosc, Umowa umowa, NieobecnoscFacade nieobecnoscFacade, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
        boolean czynaniesiono = false;
        if (nieobecnosc.isNaniesiona() == false) {
            try {
                //bo nanosimy tylko na 2021
                String rokbiezacy = "2020";
                if (nieobecnosc.getRokod().equals(rokbiezacy) || nieobecnosc.getRokdo().equals(rokbiezacy)) {
                    String mcod = nieobecnosc.getMcod();
                    if (nieobecnosc.getRokod().equals("2019")) {
                        mcod = "01";
                    }
                    String mcdo = nieobecnosc.getMcdo();
                    for (String mc : Mce.getMceListS()) {
                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcUmowa(umowa, rokbiezacy, mc);
                            if (znaleziony != null) {
                                if (nieobecnosc.getRokod().equals(rokbiezacy) || nieobecnosc.getRokdo().equals(rokbiezacy)) {
                                    znaleziony.naniesnieobecnosc(nieobecnosc);
                                }
                                nieobecnoscFacade.edit(nieobecnosc);
                                kalendarzmiesiacFacade.edit(znaleziony);
                                czynaniesiono = true;
                            } else {
                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
                            }
                        }
                    }
                }
                rokbiezacy = "2021";
                if (nieobecnosc.getRokod().equals(rokbiezacy)) {
                    String mcod = nieobecnosc.getMcod();
                    String mcdo = nieobecnosc.getMcdo();
                    for (String mc : Mce.getMceListS()) {
                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcUmowa(umowa, rokbiezacy, mc);
                            if (znaleziony != null) {
                                if (nieobecnosc.getRokod().equals(rokbiezacy) || nieobecnosc.getRokdo().equals(rokbiezacy)) {
                                    znaleziony.naniesnieobecnosc(nieobecnosc);
                                }
                                nieobecnoscFacade.edit(nieobecnosc);
                                kalendarzmiesiacFacade.edit(znaleziony);
                                czynaniesiono = true;
                            } else {
                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
            }
        }
        return czynaniesiono;
    }
}
