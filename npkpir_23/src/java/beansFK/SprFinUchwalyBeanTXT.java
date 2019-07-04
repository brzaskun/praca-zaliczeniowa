/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import entity.PodatnikUdzialy;
import entityfk.SprFinKwotyInfDod;
import java.util.List;
import pdffk.PdfMain;

/**
 *
 * @author Osito
 */
public class SprFinUchwalyBeanTXT {

    static void naglowekglowny(Document document, String rok, String datasporzadzenia, String nazwa, String siedziba, String dataod, String datado, String nrkrs) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Uchwała nr 1/"+data.Data.getMc(datasporzadzenia)+"/"+data.Data.getRok(datasporzadzenia), Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "powzięta za Zebraniu Wspólników", Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Spółki "+nazwa, Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisu(document, "nr KRS "+nrkrs, Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "z siedzibą w "+siedziba, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "w sprawie zatwierdzenia sprawozdania finansowego za rok "+rok, Element.ALIGN_CENTER);
    }

    static void podnaglowek1(String formaprawna, Document document, String datasporzadzenia,  List<PodatnikUdzialy> podatnikUdzialy) {
        PdfMain.dodajLinieOpisu(document, "Dnia "+datasporzadzenia+" wspólnicy Spółki w osobach:", Element.ALIGN_LEFT);
        double sumaliczbaudzialow = 0.0;
        double sumawartoscudzialow = 0.0;
        for (PodatnikUdzialy p : podatnikUdzialy) {
            sumaliczbaudzialow += p.getLiczbaudzialow();
            sumawartoscudzialow += p.getWartoscnominalna();
        }
        int i =1;
        if (formaprawna.equals("SPOLKA_KOMANDYTOWA")) {
            for (PodatnikUdzialy p : podatnikUdzialy) {
                String tresc = i+". "+p.getNazwiskoimie();
                PdfMain.dodajLinieOpisu(document, tresc, Element.ALIGN_LEFT);
                i++;
            }
            PdfMain.dodajLinieOpisu(document, "Obecni na zebraniu reprezentują 100% udziałów spółki.", Element.ALIGN_LEFT);
        } else {
            for (PodatnikUdzialy p : podatnikUdzialy) {
                String tresc = i+". "+p.getNazwiskoimie()+" - "+p.getLiczbaudzialow()+" udziałów o wartości nominalnej "+p.getWartoscnominalna();
                PdfMain.dodajLinieOpisu(document, tresc, Element.ALIGN_LEFT);
                i++;
            }
            PdfMain.dodajLinieOpisu(document, "Razem reprezentowane udziały "+sumaliczbaudzialow+" z ogólnej liczby "+sumaliczbaudzialow+" o łącznej wartości nominalnej "+sumawartoscudzialow, Element.ALIGN_LEFT);
        }
        
        PdfMain.dodajLinieOpisu(document, "Podjęli jednogłośnie następującą uchwałę ", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek2(Document document, String rok, double zyskstrata, double sumabilansowa) {
        PdfMain.dodajLinieOpisu(document, "§ 1", Element.ALIGN_CENTER);
        if (zyskstrata>0) {
            PdfMain.dodajLinieOpisu(document, "Wspólnicy jednogłośnie zatwierdzają przedłożone sprawozdanie finansowe za rok  "+rok+" z sumą bilansową "+format.F.curr(sumabilansowa)+" i zyskiem "+format.F.curr(zyskstrata), Element.ALIGN_JUSTIFIED);
        } else {
            PdfMain.dodajLinieOpisu(document, "Wspólnicy jednogłośnie zatwierdzają przedłożone sprawozdanie finansowe za rok  "+rok+" z sumą bilansową "+format.F.curr(sumabilansowa)+" i stratą "+format.F.curr(zyskstrata), Element.ALIGN_JUSTIFIED);
        }
    }

        
    static void naglowekglowny1(Document document, String rok, String datasporzadzenia, String nazwa, String siedziba, String dataod, String datado, double zyskstrata, String nrkrs) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Uchwała nr 2/"+data.Data.getMc(datasporzadzenia)+"/"+data.Data.getRok(datasporzadzenia), Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "powzięta za Zebraniu Wspólników Spółki", Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, nazwa, Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisu(document, "nr KRS "+nrkrs, Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "z siedzibą w "+siedziba, Element.ALIGN_CENTER);
        if (zyskstrata>0.0) {
            PdfMain.dodajLinieOpisu(document, "w sprawie przeznaczenia zysku za rok  "+rok, Element.ALIGN_CENTER);
        } else {
            PdfMain.dodajLinieOpisu(document, "w sprawie pokrycia straty za rok "+rok, Element.ALIGN_CENTER);
        }
    }
    
    static void podnaglowek11(String formaprawna, Document document, String datasporzadzenia,  List<PodatnikUdzialy> podatnikUdzialy) {
        PdfMain.dodajLinieOpisu(document, "Dnia "+datasporzadzenia+" wspólnicy Spółki w osobach:", Element.ALIGN_LEFT);
        double sumaliczbaudzialow = 0.0;
        double sumawartoscudzialow = 0.0;
        for (PodatnikUdzialy p : podatnikUdzialy) {
            sumaliczbaudzialow += p.getLiczbaudzialow();
            sumawartoscudzialow += p.getWartoscnominalna();
        }
        int i =1;
        if (formaprawna.equals("SPOLKA_KOMANDYTOWA")) {
            for (PodatnikUdzialy p : podatnikUdzialy) {
                String tresc = i+". "+p.getNazwiskoimie();
                PdfMain.dodajLinieOpisu(document, tresc, Element.ALIGN_LEFT);
                i++;
            }
            PdfMain.dodajLinieOpisu(document, "Obecni na zebraniu reprezentują 100% udziałów spółki.", Element.ALIGN_LEFT);
        } else {
            for (PodatnikUdzialy p : podatnikUdzialy) {
                String tresc = i+". "+p.getNazwiskoimie()+" - "+p.getLiczbaudzialow()+" udziałów o wartości nominalnej "+p.getWartoscnominalna();
                PdfMain.dodajLinieOpisu(document, tresc, Element.ALIGN_LEFT);
                i++;
            }
            PdfMain.dodajLinieOpisu(document, "Razem reprezentowane udziały "+sumaliczbaudzialow+" z ogólnej liczby "+sumaliczbaudzialow+" o łącznej wartości nominalnej "+sumawartoscudzialow, Element.ALIGN_LEFT);
        }
        PdfMain.dodajLinieOpisu(document, "Podjęli jednogłośnie następującą uchwałę ", Element.ALIGN_LEFT);
    }
    
     static void podnaglowek21(Document document, SprFinKwotyInfDod sprFinKwotyInfDod, String rok, double zyskstrata, double sumabilansowa) {
        PdfMain.dodajLinieOpisu(document, "§ 1", Element.ALIGN_CENTER);
        if (zyskstrata>0.0) {
            PdfMain.dodajLinieOpisu(document, "Wspólnicy jednogłośnie ustalają, że udziałowcy zysk za rok "+rok+" w wysokości "+format.F.curr(zyskstrata)+" przeznaczają na: ", Element.ALIGN_JUSTIFIED);
            if (sprFinKwotyInfDod.getDopodzialu()>0.0) {
               PdfMain.dodajLinieOpisu(document, "- do podziału między wspólników w wysokości "+format.F.curr(sprFinKwotyInfDod.getDopodzialu()), Element.ALIGN_JUSTIFIED); 
            }
            if (sprFinKwotyInfDod.getKapitalrezerwowy()>0.0) {
               PdfMain.dodajLinieOpisu(document, "- do zatrzymania w spółce jako kapitał zapasowy w wysokości "+format.F.curr(sprFinKwotyInfDod.getKapitalrezerwowy()), Element.ALIGN_JUSTIFIED); 
            }
            if (sprFinKwotyInfDod.getStratazlatubieglych()>0.0) {
               PdfMain.dodajLinieOpisu(document, "- do pokrycia strat z lat ubiegłych w wysokości "+format.F.curr(sprFinKwotyInfDod.getStratazlatubieglych()), Element.ALIGN_JUSTIFIED); 
            }
        } else {
            PdfMain.dodajLinieOpisu(document, "Wspólnicy jednogłośnie ustalają, że udziałowcy stratę za rok "+rok+" w wysokości "+format.F.curr(zyskstrata)+" pokryją z zysku lat przyszłych.", Element.ALIGN_JUSTIFIED);
        }
    }
     
     static void podnaglowek7(Document document) {
         PdfMain.dodajLinieOpisu(document, "", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, ".............................", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "Za zarząd", Element.ALIGN_LEFT);
    }
}
