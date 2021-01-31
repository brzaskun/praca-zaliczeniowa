/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.DokDAOfk;
import dao.EVatwpis1DAO;
import dao.EVatwpisDedraDAO;
import dao.EVatwpisFKDAO;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturaDodPozycjaKontrahentDAO;
import dao.FakturaRozrachunkiDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import dao.PodatnikEwidencjaDokDAO;
import dao.SchemaEwidencjaDAO;
import entity.Dok;
import entity.Faktura;
import entity.FakturaDodPozycjaKontrahent;
import entity.FakturaRozrachunki;
import entity.Klienci;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class RewolucjaPodatnikView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade dokFacade;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private PodatnikEwidencjaDokDAO podatnikEwidencjaDokDAO;
    @Inject
    private EVatwpis1DAO eVatwpis1DAO;
    @Inject
    private EVatwpisFKDAO eVatwpisFKDAO;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
   
    
//     public void edycjadok() {
//        List<Dok> dokumenty= sessionFacade.findAll(Dok.class);
//        error.E.s("Pobralem dok");
//        List<Podatnik> podatnik= sessionFacade.findAll(Podatnik.class);
//        error.E.s("Pobralem podatnicy");
//        int i = 1;
//        for (Iterator<Dok> it = dokumenty.iterator(); it.hasNext();) {
//            Dok w = it.next();
//            if (w.getTypdokumentu()!= null) {
//                w.setPoddid(odnajdzdok(podatnik, w.getPodatnik().trim()));
//                printprogres(i);
//            }
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(dokumenty);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+dokumenty.size());
//    }
//    
//    public void edycjadokrodzaj() {
//        List<Dok> dokumenty= sessionFacade.findAll(Dok.class);
//        error.E.s("Pobralem dok");
//        List<Rodzajedok> rodzajedok= sessionFacade.findAll(Rodzajedok.class);
//        error.E.s("Pobralem rodzajedok");
//        int i = 1;
//        for (Iterator<Dok> it = dokumenty.iterator(); it.hasNext();) {
//            Dok w = it.next();
//            if (w.getTypdokumentu()!= null) {
//                w.setRodzajedok(odnajdzrodzajdok(rodzajedok, w.getTypdokumentu(), w.getPoddid()));
//                printprogres(i);
//            }
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(dokumenty);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+dokumenty.size());
//    }
    
//    public void przenumeruj()  {
//        List<Dokfk> wiersze= sessionFacade.findAll(Dokfk.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Dokfk> it = wiersze.iterator(); it.hasNext();) {
//            Dokfk w = it.next();
//            if (w.getRodzajedok()!= null) {
//                w.setRodzajdok(w.getRodzajedok().getId());
//            }
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
    
//    public void przenumeruj() {
//       proc1();
//       error.E.s("Proc 1");
//       proc2();
//       error.E.s("Proc 2");
//       proc3();
//       error.E.s("Proc 3");
//       proc4();
//       error.E.s("Proc 4");
//       proc5();
//       error.E.s("Proc 5");
//       proc6();
//       error.E.s("Proc 6");
//       proc7();
//       error.E.s("Proc 7");
//       proc8();
//       error.E.s("Proc 8");
//       proc9();
//       error.E.s("Proc 9");
//       proc10();
//       error.E.s("Proc 10");
//       proc11();
//       error.E.s("Proc 11");
//       proc12();
//       error.E.s("Proc 12");
//       proc13();
//       error.E.s("Proc 13");
//       proc14();
//       error.E.s("Proc 14");
//       proc15();
//       error.E.s("Proc 15");
//       proc16();
//       error.E.s("Proc 16");
//       proc17();
//       error.E.s("Proc 17");
//       proc18();
//       error.E.s("Proc 18");
//       proc19();
//       error.E.s("Proc 19");
//       proc20();
//       error.E.s("Proc 20");
//       proc21();
//       error.E.s("Proc 21");
//       proc22();
//       error.E.s("Proc 22");
//       proc23();
//       error.E.s("Proc 23");
//       proc24();
//       error.E.s("Proc 24");
//       proc25();
//       error.E.s("Proc 25");
//       proc26();
//       error.E.s("Proc 26");
//       proc27();
//       error.E.s("Proc 27");
//       proc28();
//       error.E.s("Proc 28");
//       proc29();
//       error.E.s("Proc 29");
//       proc30();
//       error.E.s("Proc 30");
//       proc31();
//       error.E.s("Proc 31");
//       error.E.s("*************************");
//       error.E.s("Skonczylem bez bledu");
//        
//    }
//    
//    private void proc1() {
//        List<Podatnik> wiersze = sessionFacade.findAll(Podatnik.class);
//        error.E.s("Pobralem proc1");
//        int j = 0;
//        for (int i = 1; i < wiersze.size()+1; i++) {
//            wiersze.get(j++).setId(i);
//        }
//        error.E.s("Zachowuje proc1");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem proc1");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    private void proc2() {
//        List<Cechazapisu> wiersze= sessionFacade.findAll(Cechazapisu.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Cechazapisu> it = wiersze.iterator(); it.hasNext();) {
//            Cechazapisu w = it.next();
//            if (w.getPodatnik()!= null) {
//                w.setPodid(w.getPodatnik().getId());
//                printprogres(i);
//            }
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//     public void proc3() {
//        List<Delegacja> wiersze= sessionFacade.findAll(Delegacja.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Delegacja> it = wiersze.iterator(); it.hasNext();) {
//            Delegacja w = it.next();
//            if (w.getPodatnikObj()!= null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//     public void proc4() {
//        List<Dokfk> wiersze= sessionFacade.findAll(Dokfk.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Dokfk> it = wiersze.iterator(); it.hasNext();) {
//            Dokfk w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//     public void proc5() {
//        List<EVatDeklaracjaPlik> wiersze= sessionFacade.findAll(EVatDeklaracjaPlik.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<EVatDeklaracjaPlik> it = wiersze.iterator(); it.hasNext();) {
//            EVatDeklaracjaPlik w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//     public void proc6() {
//        List<EVatwpisDedra> wiersze= sessionFacade.findAll(EVatwpisDedra.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<EVatwpisDedra> it = wiersze.iterator(); it.hasNext();) {
//            EVatwpisDedra w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//      public void proc7() {
//        List<MiejscePrzychodow> wiersze= sessionFacade.findAll(MiejscePrzychodow.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<MiejscePrzychodow> it = wiersze.iterator(); it.hasNext();) {
//            MiejscePrzychodow w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//      public void proc8() {
//        List<MiejsceKosztow> wiersze= sessionFacade.findAll(MiejsceKosztow.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<MiejsceKosztow> it = wiersze.iterator(); it.hasNext();) {
//            MiejsceKosztow w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//      public void proc9() {
//        List<Pojazdy> wiersze= sessionFacade.findAll(Pojazdy.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Pojazdy> it = wiersze.iterator(); it.hasNext();) {
//            Pojazdy w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//      public void proc10() {
//        List<SprawozdanieFinansowe> wiersze= sessionFacade.findAll(SprawozdanieFinansowe.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<SprawozdanieFinansowe> it = wiersze.iterator(); it.hasNext();) {
//            SprawozdanieFinansowe w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//      public void proc11() {
//        List<WierszBO> wiersze= sessionFacade.findAll(WierszBO.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<WierszBO> it = wiersze.iterator(); it.hasNext();) {
//            WierszBO w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//      
//    public void proc12() {
//        List<WynikFKRokMc> wiersze= sessionFacade.findAll(WynikFKRokMc.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<WynikFKRokMc> it = wiersze.iterator(); it.hasNext();) {
//            WynikFKRokMc w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc13() {
//        List<Faktura> wiersze= sessionFacade.findAll(Faktura.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Faktura> it = wiersze.iterator(); it.hasNext();) {
//            Faktura w = it.next();
//            if (w.getWystawca() != null) {
//                w.setPodid(w.getWystawca().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc14() {
//        List<FakturaStopkaNiemiecka> wiersze= sessionFacade.findAll(FakturaStopkaNiemiecka.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaStopkaNiemiecka> it = wiersze.iterator(); it.hasNext();) {
//            FakturaStopkaNiemiecka w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc15() {
//        List<FakturaWalutaKonto> wiersze= sessionFacade.findAll(FakturaWalutaKonto.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaWalutaKonto> it = wiersze.iterator(); it.hasNext();) {
//            FakturaWalutaKonto w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc16() {
//        List<FakturaXXLKolumna> wiersze= sessionFacade.findAll(FakturaXXLKolumna.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaXXLKolumna> it = wiersze.iterator(); it.hasNext();) {
//            FakturaXXLKolumna w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc17() {
//        List<Logofaktura> wiersze= sessionFacade.findAll(Logofaktura.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Logofaktura> it = wiersze.iterator(); it.hasNext();) {
//            Logofaktura w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc18() {
//        List<MultiuserSettings> wiersze= sessionFacade.findAll(MultiuserSettings.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<MultiuserSettings> it = wiersze.iterator(); it.hasNext();) {
//            MultiuserSettings w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    
//    public void proc19() {
//        List<ParamCzworkiPiatki> wiersze= sessionFacade.findAll(ParamCzworkiPiatki.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<ParamCzworkiPiatki> it = wiersze.iterator(); it.hasNext();) {
//            ParamCzworkiPiatki w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc20() {
//        List<ParamVatUE> wiersze= sessionFacade.findAll(ParamVatUE.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<ParamVatUE> it = wiersze.iterator(); it.hasNext();) {
//            ParamVatUE w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//        
//    
//    public void proc21() {
//        List<PodatnikOpodatkowanieD> wiersze= sessionFacade.findAll(PodatnikOpodatkowanieD.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<PodatnikOpodatkowanieD> it = wiersze.iterator(); it.hasNext();) {
//            PodatnikOpodatkowanieD w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc22() {
//        List<PodatnikUdzialy> wiersze= sessionFacade.findAll(PodatnikUdzialy.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<PodatnikUdzialy> it = wiersze.iterator(); it.hasNext();) {
//            PodatnikUdzialy w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc23() {
//        List<Rodzajedok> wiersze= sessionFacade.findAll(Rodzajedok.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Rodzajedok> it = wiersze.iterator(); it.hasNext();) {
//            Rodzajedok w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc24() {
//        List<SkladkaStowarzyszenie> wiersze= sessionFacade.findAll(SkladkaStowarzyszenie.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<SkladkaStowarzyszenie> it = wiersze.iterator(); it.hasNext();) {
//            SkladkaStowarzyszenie w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc25() {
//        List<Sprawa> wiersze= sessionFacade.findAll(Sprawa.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Sprawa> it = wiersze.iterator(); it.hasNext();) {
//            Sprawa w = it.next();
//            if (w.getKlient() != null) {
//                w.setPodid(w.getKlient().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc26() {
//        List<Statystyka> wiersze= sessionFacade.findAll(Statystyka.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Statystyka> it = wiersze.iterator(); it.hasNext();) {
//            Statystyka w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc27() {
//        List<Strata> wiersze= sessionFacade.findAll(Strata.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Strata> it = wiersze.iterator(); it.hasNext();) {
//            Strata w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc28() {
//        List<Vies> wiersze= sessionFacade.findAll(Vies.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Vies> it = wiersze.iterator(); it.hasNext();) {
//            Vies w = it.next();
//            if (w.getPodatnik() != null) {
//                w.setPodid(w.getPodatnik().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc29() {
//        List<ZamkniecieRokuRozliczenie> wiersze= sessionFacade.findAll(ZamkniecieRokuRozliczenie.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<ZamkniecieRokuRozliczenie> it = wiersze.iterator(); it.hasNext();) {
//            ZamkniecieRokuRozliczenie w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    
//    public void proc30() {
//        List<Rodzajedok> wiersze= sessionFacade.findAll(Rodzajedok.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Rodzajedok> it = wiersze.iterator(); it.hasNext();) {
//            Rodzajedok w = it.next();
//            if (w.getPodatnikObj() != null) {
//                w.setPodid(w.getPodatnikObj().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//    public void proc31() {
//        List<FakturaRozrachunki> wiersze= sessionFacade.findAll(FakturaRozrachunki.class);
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<FakturaRozrachunki> it = wiersze.iterator(); it.hasNext();) {
//            FakturaRozrachunki w = it.next();
//            if (w.getWystawca() != null) {
//                w.setPodid(w.getWystawca().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        sessionFacade.edit(wiersze);
//        error.E.s("Skonczylem");
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
//            error.E.s("even");
//        } else {
//            error.E.s("not even");
//        }
//    }
    
//        public void procKonto() {
//            List<Podatnik> podatnicy = podatnikDAO.findAllManager();
//            List<Konto> wiersze= sessionFacade.findAll(Konto.class);
//            error.E.s("Pobralem");
//            int i = 1;
//            for (Iterator<Konto> it = wiersze.iterator(); it.hasNext();) {
//                Konto w = it.next();
//                if (w.getPodatnik()!= null) {
//                    w.setPoddid(zwrocpodatnika(podatnicy,w.getPodatnik()));
//                    printprogres(i++);
//                }
//            }
//            error.E.s("Zachowuje");
//            sessionFacade.edit(wiersze);
//            error.E.s("Skonczylem");
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
//        error.E.s("start wiersze");
//        try {
//            List<Wiersz> wiersze = wierszDAO.findAll();
//            error.E.s("pobrano wiersze");
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
//                    error.E.s("i "+i);
//                    lim = lim+1000;
//                }
//            }
//            wierszDAO.editListLateFlush(wiersze);
//            error.E.s("stop wiersze");
//            msg.Msg.dP();
//        } catch (Exception e) {
//            error.E.s("blad wiersze");
//            msg.Msg.dPe();
//        }
//        
//    }
    
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private DokDAO dokDAO;
    
    public void przebudowawiersze() {
        error.E.s("start dokfk");
        try {
            List<Dokfk> lista = dokDAOfk.findAll();
            if (!lista.isEmpty()) {
                error.E.s("pobrano wiersze "+lista.size());
                int i = 0;
                int lim = 2000;
                for (Dokfk p : lista) {
                    if (p.getRodzajedok().getSkrotNazwyDok().equals("BO")) {
                        zrobwiersze(p);
                        i++;
                        if (i>lim) {
                            error.E.s("i "+i);
                            lim = lim+2000;
                         }
                    }
                }
                //dokDAOfk.editListLateFlush(lista);
                error.E.s("stop wiersze");
            } else {
                error.E.s("pusty rok");
            }
        }catch (Exception e) {
            E.e(e);
            error.E.s("blad wiersze");
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
                //dokFacade.remove(p);
             } catch (Exception e) {
                 E.e(e);
             }
         }
//        List<Wiersz> wiersze1 = sessionFacade.getEntityManager().createQuery("SELECT k FROM Wiersz k GROUP BY k.idporzadkowy, k.dokfk HAVING COUNT(k) > 1").getResultList();
         error.E.s("koniec");
     }
     
//     public void ewidencjabyid() {
//         System.out.println("start");
//         List<SchemaEwidencja> schemy = schemaEwidencjaDAO.findAll();
//         for (SchemaEwidencja s : schemy) {
//             s.setEvewidencjaID(s.getEvewidencja());
//         }
//         schemaEwidencjaDAO.editList(schemy);
//         System.out.println("koniec");
//         System.out.println("start");
//         List<PodatnikEwidencjaDok> podatnikEwidencjaDok = podatnikEwidencjaDokDAO.findAll();
//         for (PodatnikEwidencjaDok s : podatnikEwidencjaDok) {
//             s.setEvewidencjaID(s.getEwidencja());
//         }
//         podatnikEwidencjaDokDAO.editList(podatnikEwidencjaDok);
//         System.out.println("koniec");
//            System.out.println("start");
//            List<EVatwpis1> vat1 = eVatwpis1DAO.zwrocNULL();
//            for (EVatwpis1 s : vat1) {
//                s.setEwidencjaID(s.getEwidencja());
//            }
//            eVatwpis1DAO.editList(vat1);
//            System.out.println("koniec rok");
//             System.out.println("start");
//            List<EVatwpisFK> vat2 = eVatwpisFKDAO.zwrocNULL();
//            for (EVatwpisFK s : vat2) {
//                s.setEwidencjaID(s.getEwidencja());
//            }
//            eVatwpisFKDAO.editList(vat2);
//            System.out.println("koniec fk FK");
//         System.out.println("start");
//         for (int i = 2013; i<2021; i++) {
//            for (String mc : Mce.getMceListS()) {
//                List<EVatwpisDedra> vat3 = eVatwpisDedraDAO.zwrocRokMc(String.valueOf(i), mc);
//                for (EVatwpisDedra s : vat3) {
//                    s.setEwidencjaID(s.getEwidencja());
//                }
//                eVatwpisFKDAO.editList(vat3);
//                System.out.println("koniec fk rok"+i+" mc"+mc);
//            }
//         }
//         msg.Msg.dP();
//     }

     @Inject
     private KlienciDAO klienciDAO;

     @Inject
     private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
     @Inject
     private FakturaDAO fakturaDAO;
     @Inject
     private FakturaDodPozycjaKontrahentDAO fakturaDodPozycjaKontrahentDAO;
               
     public void klienciporzadek() {
        List<Klienci> doplery = klienciDAO.findDoplery(1);
        for (Klienci t : doplery) {
            try {
                List<Klienci> doplery2  = klienciDAO.findKlienciByNip(t.getNip());
                for (Klienci s : doplery2) {
                    try {
                        klienciDAO.remove(s);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception ex) {
            }
        }
        doplery = klienciDAO.findKlienciNipSpacja();
        for (Klienci t : doplery) {
            try {
                List<Dok> dokumenty = dokDAO.findByKontr(t);
                for (Dok d : dokumenty) {
                    Klienci kli = klienciDAO.findKlientByNip(d.getPodatnik().getNip());
                    d.setKontr1(kli);
                    dokDAO.edit(d);
                }
                klienciDAO.remove(t);
            } catch (Exception e) {
//                String zwrot = E.e(e);
//                System.out.println(zwrot);
            }
        }
        doplery = klienciDAO.findKlienciByNip("0000000000");
        List<Klienci> doplery2 = klienciDAO.findKlienciNipSpacja();
        doplery.addAll(doplery2);
        String nip = "XX000000";
        int licznik = 2316;
        for (Klienci t : doplery) {
            String nowynip = nip+licznik;
            t.setNip(nowynip);
            licznik++;
            klienciDAO.edit(t);
//            System.out.println(t.getId());
//            System.out.println(t.toString2());
        }
          
        doplery = klienciDAO.findDoplery(1);
        for (Klienci t : doplery) {
            try {
                doplery2  = klienciDAO.findKlienciByNip(t.getNip());
                if (doplery2!=null) {
                    Klienci nowy = doplery2.get(0);
                    doplery2.remove(nowy);
                    znajdzdaneregonAutomat(nowy);
                    for (Klienci s : doplery2) {
                        try {
                            List<Dok> dokumenty = dokDAO.findByKontr(t);
                            for (Dok d : dokumenty) {
                                d.setKontr1(nowy);
                                try {
                                    dokDAO.edit(d);
                                } catch (EJBException e) {
                                    String numer = d.getNrWlDk();
                                    numer = "DUPLIKAT/"+numer;
                                    d.setNrWlDk(numer);
                                    dokDAO.edit(d);
                                }
                            }
                            List<Dokfk> dokumentyfk = dokDAOfk.findByKontr(t);
                            for (Dokfk d : dokumentyfk) {
                                d.setKontr(nowy);
                                try {
                                    dokDAOfk.edit(d);
                                } catch (EJBException e) {
                                    String numer = d.getNumerwlasnydokfk();
                                    numer = "DUPLIKAT/"+numer;
                                    d.setNumerwlasnydokfk(numer);
                                    dokDAOfk.edit(d);
                                }
                            }
                            List<Faktura> faktury = fakturaDAO.findbyKontrahent(t);
                            for (Faktura d : faktury) {
                                d.setKontrahent(nowy);
                                //uzyc znacznik1 aby onzaczyc duplikat :)
                                    fakturaDAO.edit(d);
                            }
                            List<FakturaRozrachunki> fakturyr = fakturaRozrachunkiDAO.findbyKontrahent(t);
                            for (FakturaRozrachunki d : fakturyr) {
                                d.setKontrahent(nowy);
                                fakturaRozrachunkiDAO.edit(d);
                            }
                            List<EVatwpisFK> vat = eVatwpisFKDAO.findbyKontrahent(t);
                            for (EVatwpisFK d : vat) {
                                d.setKlient(nowy);
                                eVatwpisFKDAO.edit(d);
                            }
                            List<FakturaDodPozycjaKontrahent> fakpoz = fakturaDodPozycjaKontrahentDAO.findbyKontrahent(t);
                            for (FakturaDodPozycjaKontrahent d : fakpoz) {
                                d.setKontrahent(nowy);
                                fakturaDodPozycjaKontrahentDAO.edit(d);
                            }
                            klienciDAO.remove(s);
                        } catch (Exception e) {
                            String zwrot = E.e(e);
                            System.out.println(zwrot);

                        }
                    }
                }
            } catch (Exception ex) {
            }
            System.out.println("dopler "+t.getNpelna());
        }
        List<Dok> dokumenty = dokDAO.znajdzKontr1NullOdDo();
        for (Dok d : dokumenty) {
            Klienci kont = klienciDAO.findKlientByNip(d.getPodatnik().getNip());
            d.setKontr1(kont);
            try {
                dokDAO.edit(d);
            } catch (EJBException e) {
            }
        }
         System.out.println("KONIEC**************");
     }
     
      public void znajdzdaneregonAutomat(Klienci klientfaktura) {
        try {
            Klienci aktualizuj = SzukajDaneBean.znajdzdaneregonAutomat(klientfaktura.getNip());
            if (aktualizuj!=null) {
                klientfaktura.setNpelna(aktualizuj.getNpelna());
                klientfaktura.setMiejscowosc(aktualizuj.getMiejscowosc());
                klientfaktura.setUlica(aktualizuj.getUlica());
                klientfaktura.setDom(aktualizuj.getDom());
                klientfaktura.setLokal(aktualizuj.getLokal());
                klientfaktura.setKodpocztowy(aktualizuj.getKodpocztowy());
                klientfaktura.setNskrocona(aktualizuj.getNpelna());
                klienciDAO.edit(klientfaktura);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }


 
      
     
}   
