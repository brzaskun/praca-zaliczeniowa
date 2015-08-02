/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansVAT;

import dao.EvewidencjaDAO;
import data.Data;
import embeddable.EVatViewPola;
import embeddable.EVatwpisSuma;
import embeddable.EwidencjaAddwiad;
import embeddable.VatKorektaDok;
import entity.Evewidencja;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import view.EwidencjaVatView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class EwidencjaVATSporzadzanie {
    
    
    
    public static void transferujDokdoEVatwpis(List<VatKorektaDok> listadokvat, List<EVatViewPola>listadokvatprzetworzona, String rok, String mc, EvewidencjaDAO evewidencjaDAO) {
         for (VatKorektaDok dokumentkorekty : listadokvat) {
                if (dokumentkorekty.getEwidencjaVAT() != null) {
                    List<EwidencjaAddwiad> ewidencja = new ArrayList<>();
                    ewidencja.addAll(dokumentkorekty.getEwidencjaVAT());
                    int lp = 0;
                    for (EwidencjaAddwiad ewidwiersz : ewidencja) {
                        if (ewidwiersz.getVat() != 0 || ewidwiersz.getNetto() != 0) {
                            try {
                                EVatViewPola wiersz = new EVatViewPola();
                                wiersz.setId(lp++);
                                wiersz.setDataSprz(Data.ostatniDzien(rok, mc));
                                wiersz.setDataWyst(Data.ostatniDzien(rok, mc));
                                wiersz.setKontr(null);
                                wiersz.setNrWlDk(dokumentkorekty.getNrwłasny());
                                wiersz.setOpis(dokumentkorekty.getOpisDokumnetu());
                                Evewidencja nowaEv = evewidencjaDAO.znajdzponazwie(ewidwiersz.getOpis());
                                wiersz.setNazwaewidencji(nowaEv);
                                wiersz.setNrpolanetto(nowaEv.getNrpolanetto());
                                wiersz.setNrpolavat(nowaEv.getNrpolavat());
                                wiersz.setNetto(ewidwiersz.getNetto());
                                wiersz.setVat(ewidwiersz.getVat());
                                wiersz.setOpizw(ewidwiersz.getOpzw());
                                listadokvatprzetworzona.add(wiersz);
                            } catch (Exception ex) {
                                Logger.getLogger(EwidencjaVATSporzadzanie.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }   
                    }
                }
            }
    }
    
    public static void rozdzielEVatwpisNaEwidencje(List<EVatViewPola>listadokvatprzetworzona, HashMap<String, List<EVatViewPola>> listaewidencji, HashMap<String, EVatwpisSuma> sumaewidencji,  EvewidencjaDAO evewidencjaDAO) {
        for (EVatViewPola wierszogolny : listadokvatprzetworzona) {
                ArrayList<EVatViewPola> listatmp = new ArrayList<>();
                //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
                String nazwaewidencji = wierszogolny.getNazwaewidencji().getNazwa();
                try {
                    Collection c = listaewidencji.get(nazwaewidencji);
                    listatmp.addAll(c);
                } catch (Exception e) {
                    try {
                        listaewidencji.put(nazwaewidencji, new ArrayList<EVatViewPola>());
                        Evewidencja nowaEv = evewidencjaDAO.znajdzponazwie(nazwaewidencji);
                        sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, wierszogolny.getOpizw()));
                    } catch (Exception ex) {
                        Logger.getLogger(EwidencjaVatView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                listatmp.add(wierszogolny);
                EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
                BigDecimal sumanetto = ew.getNetto().add(BigDecimal.valueOf(wierszogolny.getNetto()).setScale(0, RoundingMode.HALF_EVEN));
                ew.setNetto(sumanetto);
                BigDecimal sumavat = ew.getVat().add(BigDecimal.valueOf(wierszogolny.getVat()).setScale(0, RoundingMode.HALF_EVEN));
                ew.setVat(sumavat);
                sumaewidencji.put(nazwaewidencji, ew);
                listaewidencji.put(nazwaewidencji, listatmp);
            }
    }
    
    public static void dodajDoEwidencjiPozycjeKorekt(HashMap<String, EVatwpisSuma> sumaewidencji, HashMap<String, EVatwpisSuma> sumaewidencjikorekta,  EvewidencjaDAO evewidencjaDAO) {
        ArrayList<String> sumaewidencjiKeys = new ArrayList<>();
        sumaewidencjiKeys.addAll(sumaewidencji.keySet());
        for (String p : sumaewidencjikorekta.keySet()) {
            if (sumaewidencjiKeys.contains(p)) {
                BigDecimal netto = sumaewidencji.get(p).getNetto().add(sumaewidencjikorekta.get(p).getNetto());
                sumaewidencji.get(p).setNetto(netto);
                BigDecimal vat = sumaewidencji.get(p).getVat().add(sumaewidencjikorekta.get(p).getVat());
                sumaewidencji.get(p).setVat(vat);
                
            } else {
                try {
                    Evewidencja nowaEv = evewidencjaDAO.znajdzponazwie(p);
                    sumaewidencji.put(p, new EVatwpisSuma(nowaEv, sumaewidencjikorekta.get(p).getNetto(), sumaewidencjikorekta.get(p).getVat(), sumaewidencjikorekta.get(p).getEwidencja().getRodzajzakupu()));
                } catch (Exception ex) {
                    Logger.getLogger(EwidencjaVATSporzadzanie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    
    
   
}
