/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import dao.DokDAOfk;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class EwidencjaVatCheck1View implements Serializable {

    
    @Inject
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    
   public void wykryjbledy() {
       List<Dokfk> dokumenty = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
       List<Dokfk> dokumentyfilter = sprawdzikontoewidencja(dokumenty);
       
   }

    private List<Dokfk> sprawdzikontoewidencja(List<Dokfk> dokumenty) {
        List<Dokfk> nieok = new ArrayList<>();
        for (Dokfk p : dokumenty) {
            double sumavat221_12 = 0.0;
            double sumavat221_34 = 0.0;
            List<StronaWiersza> stronyWierszy = p.getStronyWierszy();
            boolean jestvat = false;
            for (StronaWiersza s : stronyWierszy) {
                if (s.getKonto().getPelnynumer().equals("221-1")||s.getKonto().getPelnynumer().equals("221-2")) {
                    sumavat221_12 = sumavat221_12 + s.getKwotaPLN();
                    jestvat = true;
                } else if ((s.getKonto().getPelnynumer().equals("221-3")||s.getKonto().getPelnynumer().equals("221-4"))&&!jestvat) {
                    sumavat221_34 = sumavat221_34 + s.getKwotaPLN();

                }
            }
            List<Wiersz> listawierszy = p.getListawierszy();
            List<EVatwpisFK> ewidencjaVATwiersz = new ArrayList<>();
            for (Wiersz w : listawierszy) {
                EVatwpisFK eVatwpisFK = w.geteVatwpisFK();
                if (eVatwpisFK != null) {
                    ewidencjaVATwiersz.add(eVatwpisFK);
                }
            }
            double sumavat = 0.0;
            List<EVatwpisFK> ewidencjaVAT = p.getEwidencjaVAT();
            ewidencjaVAT.addAll(ewidencjaVATwiersz);
            for (EVatwpisFK e : ewidencjaVAT) {
                sumavat = sumavat + e.getVat();
            }
            if (Z.z(sumavat221_12+sumavat221_34)!=Z.z(sumavat)) {
                nieok.add(p);
            }
        }
        return nieok;
    }


}
