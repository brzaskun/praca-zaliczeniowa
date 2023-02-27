/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.KalendarzmiesiacRMNormalcomparator;
import dao.KalendarzmiesiacFacade;
import dao.NieobecnoscFacade;
import data.Data;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import error.E;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
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
     
//     public static boolean nanies(Nieobecnosc nieobecnosc, NieobecnoscFacade nieobecnoscFacade, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
//        boolean czynaniesiono = false;
//        if (nieobecnosc.isNaniesiona() == false) {
//            List<Okres> okresy = OkresBean.generujokresy(nieobecnosc);
//            try {
//                //bo nanosimy tylko na 2021
//                int rokgraniczny = 2020;
//                if (Integer.parseInt(nieobecnosc.getRokod())>=rokgraniczny) {
//                    for (Okres okr : okresy) {
//                        Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(nieobecnosc.getAngaz(), okr.getRok(), okr.getMc());
//                        if (znaleziony != null) {
//                            int dniroboczenieobecnosci = znaleziony.naniesnieobecnosc(nieobecnosc);
//                            if (dniroboczenieobecnosci>0) {
//                                nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+dniroboczenieobecnosci);
//                            }
//                            nieobecnoscFacade.edit(nieobecnosc);
//                            kalendarzmiesiacFacade.edit(znaleziony);
//                            czynaniesiono = true;
//                        } else {
//                            Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy "+okr.toString()+". Nie można nanieść nieobecności!");
//                        }
//                    }
//                }
//
//            } catch (Exception e) {
//                E.e(e);
//                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//            }
//        }
//        return czynaniesiono;
//    }
     //<editor-fold defaultstate="collapsed" desc="comment">
     
//      public static boolean nanies(Nieobecnosc nieobecnosc, Umowa umowa, NieobecnoscFacade nieobecnoscFacade, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
//        boolean czynaniesiono = false;
//        if (nieobecnosc.isNaniesiona() == false) {
//            List<Okres> okresy = OkresBean.generujokresy(nieobecnosc);
//            try {
//                //bo nanosimy tylko na 2021
//                String rokbiezacy = "2020";
//                if (nieobecnosc.getRokod().equals(rokbiezacy) || nieobecnosc.getRokdo().equals(rokbiezacy)) {
//                    String mcod = nieobecnosc.getMcod();
//                    if (nieobecnosc.getRokod().equals("2019")) {
//                        mcod = "01";
//                    }
//                    String mcdo = nieobecnosc.getMcdo();
//                    for (String mc : Mce.getMceListS()) {
//                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(umowa, rokbiezacy, mc);
//                            if (znaleziony != null) {
//                                if (nieobecnosc.getRokod().equals(rokbiezacy) || nieobecnosc.getRokdo().equals(rokbiezacy)) {
//                                    znaleziony.naniesnieobecnosc(nieobecnosc);
//                                }
//                                nieobecnoscFacade.edit(nieobecnosc);
//                                kalendarzmiesiacFacade.edit(znaleziony);
//                                czynaniesiono = true;
//                            } else {
//                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                            }
//                        }
//                    }
//                }
//                rokbiezacy = "2021";
//                if (nieobecnosc.getRokod().equals(rokbiezacy)) {
//                    String mcod = nieobecnosc.getMcod();
//                    String mcdo = nieobecnosc.getMcdo();
//                    for (String mc : Mce.getMceListS()) {
//                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(umowa, rokbiezacy, mc);
//                            if (znaleziony != null) {
//                                if (nieobecnosc.getRokod().equals(rokbiezacy) || nieobecnosc.getRokdo().equals(rokbiezacy)) {
//                                    znaleziony.naniesnieobecnosc(nieobecnosc);
//                                }
//                                nieobecnoscFacade.edit(nieobecnosc);
//                                kalendarzmiesiacFacade.edit(znaleziony);
//                                czynaniesiono = true;
//                            } else {
//                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                E.e(e);
//                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//            }
//        }
//        return czynaniesiono;
//    }
//</editor-fold>
     
//     public static boolean nanies(Nieobecnosc nieobecnosc, String rokwpisu, String rokuprzedni, KalendarzmiesiacFacade kalendarzmiesiacFacade, NieobecnoscFacade nieobecnoscFacade) {
//        boolean czynaniesiono = false;
//        if (nieobecnosc.isNaniesiona() == false) {
//            try {
//                if (nieobecnosc.getRokod().equals(rokwpisu) || nieobecnosc.getRokdo().equals(rokwpisu)) {
//                    String mcod = nieobecnosc.getMcod();
//                    if (nieobecnosc.getRokod().equals(rokuprzedni)) {
//                        mcod = "01";
//                    }
//                    String mcdo = nieobecnosc.getMcdo();
//                    if (nieobecnosc.getRokdo().equals(Data.roknastepny(rokwpisu))) {
//                        mcdo = "13";
//                    }
//                    for (String mc : Mce.getMceListS()) {
//                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(nieobecnosc.getAngaz(), rokwpisu, mc);
//                            if (znaleziony != null) {
//                                if (nieobecnosc.getRokod().equals(rokwpisu) || nieobecnosc.getRokdo().equals(rokwpisu)) {
//                                    int dniroboczenieobecnosci = znaleziony.naniesnieobecnosc(nieobecnosc);
//                                    if (dniroboczenieobecnosci>0) {
//                                        nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+dniroboczenieobecnosci);
//                                    }
//                                }
//                                nieobecnoscFacade.edit(nieobecnosc);
//                                kalendarzmiesiacFacade.edit(znaleziony);
//                                czynaniesiono = true;
//                            } else {
//                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                            }
//                        }
//                    }
//                }
//                if (nieobecnosc.getRokdo().equals(Data.roknastepny(rokwpisu))) {
//                      String mcod = "01";
//                      String mcdo = nieobecnosc.getMcdo();
//                    String roknastepny = Data.roknastepny(rokwpisu);
//                    for (String mc : Mce.getMceListS()) {
//                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(nieobecnosc.getAngaz(), roknastepny, mc);
//                            if (znaleziony != null) {
//                                if (nieobecnosc.getRokod().equals(roknastepny) || nieobecnosc.getRokdo().equals(roknastepny)) {
//                                    int dniroboczenieobecnosci = znaleziony.naniesnieobecnosc(nieobecnosc);
//                                    if (dniroboczenieobecnosci>0) {
//                                        nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+dniroboczenieobecnosci);
//                                    }
//                                }
//                                nieobecnoscFacade.edit(nieobecnosc);
//                                kalendarzmiesiacFacade.edit(znaleziony);
//                                czynaniesiono = true;
//                            } else {
//                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                E.e(e);
//                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//            }
//        }
//        return czynaniesiono;
//    }
     
    public static boolean nanies(Nieobecnosc nieobecnosc, KalendarzmiesiacFacade kalendarzmiesiacFacade, NieobecnoscFacade nieobecnoscFacade) {
        boolean czynaniesiono = false;
        if (nieobecnosc.isNaniesiona() == false) {
            try {
                String rokod = Data.getRok(nieobecnosc.getDataod());
                String rokdo = Data.getRok(nieobecnosc.getDatado());
                String mcod = nieobecnosc.getMcod();
                String mcdo = nieobecnosc.getMcdo();
                boolean start = false;
                boolean stop = false;
                List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByAngaz(nieobecnosc.getAngaz());
                Collections.sort(kalendarze, new KalendarzmiesiacRMNormalcomparator());
                nieobecnosc.setDniroboczenieobecnosci(0.0);
                nieobecnosc.setGodzinyroboczenieobecnosc(0.0);
                for (Kalendarzmiesiac kal : kalendarze) {
                    boolean pierwszymc = false;
                    boolean ostatnimc = false;
                    String rokkalendarza = kal.getRok();
                    String mckalendarza = kal.getMc();
                    if (rokkalendarza.equals(rokod)) {
                        if (mckalendarza.equals(mcod)) {
                            start = true;
                            pierwszymc = true;
                        }
                    }
                    if (rokkalendarza.equals(rokdo)) {
                        if (mckalendarza.equals(mcdo)) {
                            stop = true;
                            ostatnimc = true;
                        }
                    }
                    if (start) {
                        //tu zmieniłem 2023-02-04 nieobecnosci nanoszenie dni i dodalem godziny
                         int[] dnigodziny = kal.naniesnieobecnosc(nieobecnosc, pierwszymc, ostatnimc);
                         nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+dnigodziny[0]);
                         nieobecnosc.setGodzinyroboczenieobecnosc(nieobecnosc.getGodzinyroboczenieobecnosc()+dnigodziny[1]);
                         nieobecnoscFacade.edit(nieobecnosc);
                         kalendarzmiesiacFacade.edit(kal);
                         czynaniesiono = true;
                     }
                     if (stop) {
                         break;
                     }
                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
            }
        }
        return czynaniesiono;
    }
     
    public static void main(String[] args) {
        LocalDate monty = LocalDate.parse("2023-02-01").with(TemporalAdjusters.lastDayOfMonth());
        int wynik = monty.getDayOfMonth();
        System.out.println("ostatni "+wynik);
    }
     
}
