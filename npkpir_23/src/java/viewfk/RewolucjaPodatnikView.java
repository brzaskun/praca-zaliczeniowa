/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.DAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import daoFK.WierszDAO;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class RewolucjaPodatnikView extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade dokFacade;
    @Inject
    private PodatnikDAO podatnikDAO;
   
    
//     public void edycjadok() {
//        List<Dok> dokumenty= sessionFacade.findAll(Dok.class);
//        System.out.println("Pobralem dok");
//        List<Podatnik> podatnik= sessionFacade.findAll(Podatnik.class);
//        System.out.println("Pobralem podatnicy");
//        int i = 1;
//        for (Iterator<Dok> it = dokumenty.iterator(); it.hasNext();) {
//            Dok w = it.next();
//            if (w.getTypdokumentu()!= null) {
//                w.setPoddid(odnajdzdok(podatnik, w.getPodatnik().trim()));
//                printprogres(i);
//            }
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(dokumenty);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+dokumenty.size());
//    }
//    
//    public void edycjadokrodzaj() {
//        List<Dok> dokumenty= sessionFacade.findAll(Dok.class);
//        System.out.println("Pobralem dok");
//        List<Rodzajedok> rodzajedok= sessionFacade.findAll(Rodzajedok.class);
//        System.out.println("Pobralem rodzajedok");
//        int i = 1;
//        for (Iterator<Dok> it = dokumenty.iterator(); it.hasNext();) {
//            Dok w = it.next();
//            if (w.getTypdokumentu()!= null) {
//                w.setRodzajedok(odnajdzrodzajdok(rodzajedok, w.getTypdokumentu(), w.getPoddid()));
//                printprogres(i);
//            }
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(dokumenty);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+dokumenty.size());
//    }
    
//    public void przenumeruj()  {
//        List<Dokfk> wiersze= sessionFacade.findAll(Dokfk.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Dokfk> it = wiersze.iterator(); it.hasNext();) {
//            Dokfk w = it.next();
//            if (w.getRodzajedok()!= null) {
//                w.setRodzajdok(w.getRodzajedok().getId());
//            }
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
    
//    public void przenumeruj() {
//       proc1();
//       System.out.println("Proc 1");
//       proc2();
//       System.out.println("Proc 2");
//       proc3();
//       System.out.println("Proc 3");
//       proc4();
//       System.out.println("Proc 4");
//       proc5();
//       System.out.println("Proc 5");
//       proc6();
//       System.out.println("Proc 6");
//       proc7();
//       System.out.println("Proc 7");
//       proc8();
//       System.out.println("Proc 8");
//       proc9();
//       System.out.println("Proc 9");
//       proc10();
//       System.out.println("Proc 10");
//       proc11();
//       System.out.println("Proc 11");
//       proc12();
//       System.out.println("Proc 12");
//       proc13();
//       System.out.println("Proc 13");
//       proc14();
//       System.out.println("Proc 14");
//       proc15();
//       System.out.println("Proc 15");
//       proc16();
//       System.out.println("Proc 16");
//       proc17();
//       System.out.println("Proc 17");
//       proc18();
//       System.out.println("Proc 18");
//       proc19();
//       System.out.println("Proc 19");
//       proc20();
//       System.out.println("Proc 20");
//       proc21();
//       System.out.println("Proc 21");
//       proc22();
//       System.out.println("Proc 22");
//       proc23();
//       System.out.println("Proc 23");
//       proc24();
//       System.out.println("Proc 24");
//       proc25();
//       System.out.println("Proc 25");
//       proc26();
//       System.out.println("Proc 26");
//       proc27();
//       System.out.println("Proc 27");
//       proc28();
//       System.out.println("Proc 28");
//       proc29();
//       System.out.println("Proc 29");
//       proc30();
//       System.out.println("Proc 30");
//       proc31();
//       System.out.println("Proc 31");
//       System.out.println("*************************");
//       System.out.println("Skonczylem bez bledu");
//        
//    }
//    
//    private void proc1() {
//        List<Podatnik> wiersze = sessionFacade.findAll(Podatnik.class);
//        System.out.println("Pobralem proc1");
//        int j = 0;
//        for (int i = 1; i < wiersze.size()+1; i++) {
//            wiersze.get(j++).setId(i);
//        }
//        System.out.println("Zachowuje proc1");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem proc1");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    private void proc2() {
//        List<Cechazapisu> wiersze= sessionFacade.findAll(Cechazapisu.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Cechazapisu> it = wiersze.iterator(); it.hasNext();) {
//            Cechazapisu w = it.next();
//            if (w.getPodatnik()!= null) {
//                w.setPodid(w.getPodatnik().getId());
//                printprogres(i);
//            }
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//     public void proc3() {
//        List<Delegacja> wiersze= sessionFacade.findAll(Delegacja.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Delegacja> it = wiersze.iterator(); it.hasNext();) {
//            Delegacja w = it.next();
//            if (w.getPodatnikObj()!= null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//     public void proc4() {
//        List<Dokfk> wiersze= sessionFacade.findAll(Dokfk.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Dokfk> it = wiersze.iterator(); it.hasNext();) {
//            Dokfk w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//     public void proc5() {
//        List<EVatDeklaracjaPlik> wiersze= sessionFacade.findAll(EVatDeklaracjaPlik.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<EVatDeklaracjaPlik> it = wiersze.iterator(); it.hasNext();) {
//            EVatDeklaracjaPlik w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//     public void proc6() {
//        List<EVatwpisDedra> wiersze= sessionFacade.findAll(EVatwpisDedra.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<EVatwpisDedra> it = wiersze.iterator(); it.hasNext();) {
//            EVatwpisDedra w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//      public void proc7() {
//        List<MiejscePrzychodow> wiersze= sessionFacade.findAll(MiejscePrzychodow.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<MiejscePrzychodow> it = wiersze.iterator(); it.hasNext();) {
//            MiejscePrzychodow w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//      public void proc8() {
//        List<MiejsceKosztow> wiersze= sessionFacade.findAll(MiejsceKosztow.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<MiejsceKosztow> it = wiersze.iterator(); it.hasNext();) {
//            MiejsceKosztow w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//      public void proc9() {
//        List<Pojazdy> wiersze= sessionFacade.findAll(Pojazdy.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Pojazdy> it = wiersze.iterator(); it.hasNext();) {
//            Pojazdy w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//      public void proc10() {
//        List<SprawozdanieFinansowe> wiersze= sessionFacade.findAll(SprawozdanieFinansowe.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<SprawozdanieFinansowe> it = wiersze.iterator(); it.hasNext();) {
//            SprawozdanieFinansowe w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//      public void proc11() {
//        List<WierszBO> wiersze= sessionFacade.findAll(WierszBO.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<WierszBO> it = wiersze.iterator(); it.hasNext();) {
//            WierszBO w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//    public void proc12() {
//        List<WynikFKRokMc> wiersze= sessionFacade.findAll(WynikFKRokMc.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<WynikFKRokMc> it = wiersze.iterator(); it.hasNext();) {
//            WynikFKRokMc w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc13() {
//        List<Faktura> wiersze= sessionFacade.findAll(Faktura.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Faktura> it = wiersze.iterator(); it.hasNext();) {
//            Faktura w = it.next();
//            if (w.getWystawca() != null) {
//                w.setPodid(w.getWystawca().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc14() {
//        List<FakturaStopkaNiemiecka> wiersze= sessionFacade.findAll(FakturaStopkaNiemiecka.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaStopkaNiemiecka> it = wiersze.iterator(); it.hasNext();) {
//            FakturaStopkaNiemiecka w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc15() {
//        List<FakturaWalutaKonto> wiersze= sessionFacade.findAll(FakturaWalutaKonto.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaWalutaKonto> it = wiersze.iterator(); it.hasNext();) {
//            FakturaWalutaKonto w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc16() {
//        List<FakturaXXLKolumna> wiersze= sessionFacade.findAll(FakturaXXLKolumna.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaXXLKolumna> it = wiersze.iterator(); it.hasNext();) {
//            FakturaXXLKolumna w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc17() {
//        List<Logofaktura> wiersze= sessionFacade.findAll(Logofaktura.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Logofaktura> it = wiersze.iterator(); it.hasNext();) {
//            Logofaktura w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc18() {
//        List<MultiuserSettings> wiersze= sessionFacade.findAll(MultiuserSettings.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<MultiuserSettings> it = wiersze.iterator(); it.hasNext();) {
//            MultiuserSettings w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    
//    public void proc19() {
//        List<ParamCzworkiPiatki> wiersze= sessionFacade.findAll(ParamCzworkiPiatki.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<ParamCzworkiPiatki> it = wiersze.iterator(); it.hasNext();) {
//            ParamCzworkiPiatki w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc20() {
//        List<ParamVatUE> wiersze= sessionFacade.findAll(ParamVatUE.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<ParamVatUE> it = wiersze.iterator(); it.hasNext();) {
//            ParamVatUE w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//        
//    
//    public void proc21() {
//        List<PodatnikOpodatkowanieD> wiersze= sessionFacade.findAll(PodatnikOpodatkowanieD.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<PodatnikOpodatkowanieD> it = wiersze.iterator(); it.hasNext();) {
//            PodatnikOpodatkowanieD w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc22() {
//        List<PodatnikUdzialy> wiersze= sessionFacade.findAll(PodatnikUdzialy.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<PodatnikUdzialy> it = wiersze.iterator(); it.hasNext();) {
//            PodatnikUdzialy w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc23() {
//        List<Rodzajedok> wiersze= sessionFacade.findAll(Rodzajedok.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Rodzajedok> it = wiersze.iterator(); it.hasNext();) {
//            Rodzajedok w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc24() {
//        List<SkladkaStowarzyszenie> wiersze= sessionFacade.findAll(SkladkaStowarzyszenie.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<SkladkaStowarzyszenie> it = wiersze.iterator(); it.hasNext();) {
//            SkladkaStowarzyszenie w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc25() {
//        List<Sprawa> wiersze= sessionFacade.findAll(Sprawa.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Sprawa> it = wiersze.iterator(); it.hasNext();) {
//            Sprawa w = it.next();
//            if (w.getKlient() != null) {
//                w.setPodid(w.getKlient().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc26() {
//        List<Statystyka> wiersze= sessionFacade.findAll(Statystyka.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Statystyka> it = wiersze.iterator(); it.hasNext();) {
//            Statystyka w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc27() {
//        List<Strata> wiersze= sessionFacade.findAll(Strata.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Strata> it = wiersze.iterator(); it.hasNext();) {
//            Strata w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc28() {
//        List<Vies> wiersze= sessionFacade.findAll(Vies.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Vies> it = wiersze.iterator(); it.hasNext();) {
//            Vies w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc29() {
//        List<ZamkniecieRokuRozliczenie> wiersze= sessionFacade.findAll(ZamkniecieRokuRozliczenie.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<ZamkniecieRokuRozliczenie> it = wiersze.iterator(); it.hasNext();) {
//            ZamkniecieRokuRozliczenie w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    
//    public void proc30() {
//        List<Rodzajedok> wiersze= sessionFacade.findAll(Rodzajedok.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<Rodzajedok> it = wiersze.iterator(); it.hasNext();) {
//            Rodzajedok w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc31() {
//        List<FakturaRozrachunki> wiersze= sessionFacade.findAll(FakturaRozrachunki.class);
//        System.out.println("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaRozrachunki> it = wiersze.iterator(); it.hasNext();) {
//            FakturaRozrachunki w = it.next();
//            if (w.getWystawca() != null) {
//                w.setPodid(w.getWystawca().getId());
//            }
//            printprogres(i);
//        }
//        System.out.println("Zachowuje");
//        sessionFacade.edit(wiersze);
//        System.out.println("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
    private void printprogres(int val) {
        if ( (val % 5000) == 0) {
        }
    }
//    
//    public static void main(String[] args) {
//        if ( (10002 % 10000) == 0) {
//            System.out.println("even");
//        } else {
//            System.out.println("not even");
//        }
//    }
    
//        public void procKonto() {
//            List<Podatnik> podatnicy = podatnikDAO.findAllManager();
//            List<Konto> wiersze= sessionFacade.findAll(Konto.class);
//            System.out.println("Pobralem");
//            int i = 1;
//            for (Iterator<Konto> it = wiersze.iterator(); it.hasNext();) {
//                Konto w = it.next();
//                if (w.getPodatnik()!= null) {
//                    w.setPoddid(zwrocpodatnika(podatnicy,w.getPodatnik()));
//                    printprogres(i++);
//                }
//            }
//            System.out.println("Zachowuje");
//            sessionFacade.edit(wiersze);
//            System.out.println("Skonczylem");
//            Msg.msg("Przenumerowane "+wiersze.size());
//        }
//        
//        private Podatnik zwrocpodatnika(List<Podatnik> podatnicy, String podatnik) {
//            Podatnik zwrot = null;
//            for (Podatnik p : podatnicy) {
//                if (p.getNazwapelna().equals(podatnik)) {
//                    zwrot = p;
//                }
//            }
//            return zwrot;
//        }

//    private Rodzajedok odnajdzrodzajdok(List<Rodzajedok> rodzajedok, String typdokumentu, Podatnik podid) {
//        Rodzajedok zwrot = null;
//        for (Rodzajedok r : rodzajedok) {
//            if (r.getPodatnikObj().equals(podid) && r.getSkrot().equals(typdokumentu)) {
//                zwrot = r;
//                break;
//            }
//        }
//        return zwrot;
//    }
//
//    private Podatnik odnajdzdok(List<Podatnik> podatnik, String podatnik0) {
//        Podatnik zwrot = null;
//        for (Podatnik r : podatnik) {
//            if (r.getNazwapelna().trim().equals(podatnik0)) {
//                zwrot = r;
//                break;
//            }
//        }
//        return zwrot;
//    }
   
//    @Inject
//    private WierszDAO wierszDAO;
//    
//    public void przebudowawiersze() {
//        System.out.println("start wiersze");
//        try {
//            List<Wiersz> wiersze = wierszDAO.findAll();
//            System.out.println("pobrano wiersze");
//            int i = 0;
//            int lim = 1000;
//            for (Wiersz p : wiersze) {
//                if (!p.getStrona().isEmpty()) {
//                    if (p.getStronaWn()!=null) {
//                        p.setStrWn(p.getStronaWn());
//                    }
//                    if (p.getStronaMa()!=null) {
//                        p.setStrMa(p.getStronaMa());
//                    }
//                    i++;
//                }
//                if (i>lim) {
//                    System.out.println("i "+i);
//                    lim = lim+1000;
//                }
//            }
//            wierszDAO.editListLateFlush(wiersze);
//            System.out.println("stop wiersze");
//            msg.Msg.dP();
//        } catch (Exception e) {
//            System.out.println("blad wiersze");
//            msg.Msg.dPe();
//        }
//        
//    }
    
    @Inject
    private DokDAOfk dokDAOfk;
    public void przebudowawiersze() {
        System.out.println("start dokfk");
        try {
            List<Dokfk> lista = dokDAOfk.findAll();
            if (!lista.isEmpty()) {
                System.out.println("pobrano wiersze "+lista.size());
                int i = 0;
                int lim = 2000;
                for (Dokfk p : lista) {
                    if (p.getRodzajedok().getSkrotNazwyDok().equals("BO")) {
                        zrobwiersze(p);
                        i++;
                        if (i>lim) {
                            System.out.println("i "+i);
                            lim = lim+2000;
                         }
                    }
                }
                dokDAOfk.editListLateFlush(lista);
                System.out.println("stop wiersze");
            } else {
                System.out.println("pusty rok");
            }
        }catch (Exception e) {
            E.e(e);
            System.out.println("blad wiersze");
            msg.Msg.dPe();
        }
    }

    private void zrobwiersze(Dokfk p) {
        int i = 1;
        for (Wiersz w : p.getListawierszy()) {
            if (w.getIdporzadkowy()!=i) {
                w.setIdporzadkowy(i);
                i++;
            } else {
                i++;
            }
        }
    }
      
    
     public void usunpustewiersze() {
         List<Wiersz> wiersze = dokFacade.getEntityManager().createQuery("SELECT k FROM Wiersz k WHERE k.dokfk IS NULL").getResultList();
         for (Wiersz p : wiersze) {
             try {
                dokFacade.remove(p);
             } catch (Exception e) {
                 E.e(e);
             }
         }
//        List<Wiersz> wiersze1 = sessionFacade.getEntityManager().createQuery("SELECT k FROM Wiersz k GROUP BY k.idporzadkowy, k.dokfk HAVING COUNT(k) > 1").getResultList();
         System.out.println("koniec");
     }
}   
