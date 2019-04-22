/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.JPK2SprzedazWierszcomparator;
import comparator.JPK2ZakupWierszcomparator;
import comparator.JPK3SprzedazWierszcomparator;
import comparator.JPK3ZakupWierszcomparator;
import embeddable.TKodUS;
import entity.JPKSuper;
import entity.Podatnik;
import entity.UPO;
import entity.Uz;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import msg.Msg;import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PdfUPO extends Pdf implements Serializable {

   public static void drukuj_JPK2(UPO upo, WpisView wpisView) {
        String nazwa = upo.getPodatnik().getNip()+"JPK";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (upo != null) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Plik JPK zestawienie", upo.getPodatnik(), upo.getMiesiac(), upo.getRok());
            JPKSuper jpk = upo.getJpk();
            List<jpk201701.JPK.SprzedazWiersz> sprzedazWiersz = jpk.getSprzedazWiersz();
            Collections.sort(sprzedazWiersz, new JPK2SprzedazWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOS(sprzedazWiersz),100, 0);
            jpk201701.JPK.SprzedazCtrl sprzedazCtrl = (jpk201701.JPK.SprzedazCtrl) jpk.getSprzedazCtrl();
            if (sprzedazCtrl!=null) {
                String opis = "Ilość faktur "+sprzedazCtrl.getLiczbaWierszySprzedazy().intValue()+". Podatek należny "+F.curr(sprzedazCtrl.getPodatekNalezny().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów sprzedaży");
            }
            List<jpk201701.JPK.ZakupWiersz> zakupWiersz = jpk.getZakupWiersz();
            Collections.sort(zakupWiersz, new JPK2ZakupWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOZ(zakupWiersz),100,0);
            jpk201701.JPK.ZakupCtrl zakupCtrl = (jpk201701.JPK.ZakupCtrl) jpk.getZakupCtrl();
            if (zakupCtrl!=null) {
                String opis = "Ilość faktur "+zakupCtrl.getLiczbaWierszyZakupow().intValue()+". Podatek naliczony "+F.curr(zakupCtrl.getPodatekNaliczony().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów zakupu");
            }
            String opis = "Nr wysyłki "+upo.getReferenceNumber();
            dodajLinieOpisuBezOdstepu(document, opis);
            opis = "Okres "+upo.getMiesiac()+"/"+upo.getRok();
            dodajLinieOpisuBezOdstepu(document, opis);
            opis = "Data sporządzenia "+data.Data.data_ddMMMMyyyy(upo.getDatajpk());
            dodajLinieOpisuBezOdstepu(document, opis);
            if (upo.getCode() != null) {
                opis = "Data upo "+data.Data.data_ddMMMMyyyy(upo.getDataupo());
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Kod upo "+upo.getCode();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Status "+upo.getDescription()+" "+upo.getDetails();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Wersja "+upo.getWersja();
                dodajLinieOpisuBezOdstepu(document, opis);
            }
            if (upo.getPotwierdzenie() != null) {
                opis = "Potwierdzenie "+upo.getPotwierdzenie().getNazwaPodmiotuPrzyjmujacego();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Skrot dokumentu: "+upo.getPotwierdzenie().getSkrotDokumentu() +"Nr ref: "+upo.getPotwierdzenie().getNumerReferencyjny();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Stempel czasu "+upo.getPotwierdzenie().getStempelCzasu();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Nazwa struktury logicznej "+upo.getPotwierdzenie().getNazwaStrukturyLogicznej();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Nazwa urzędu "+TKodUS.getNazwaUrzedu(upo.getPotwierdzenie().getKodUrzedu());
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Data wpłynęcia "+data.Data.data_ddMMMMyyyy(upo.getPotwierdzenie().getDataWplyniecia().toGregorianCalendar().getTime());
                dodajLinieOpisuBezOdstepu(document, opis);
            }
            opis = "Sporządził "+upo.getWprowadzil().getImieNazwisko();
            dodajLinieOpisuBezOdstepu(document, opis);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Pusty plik JPK");
        }
    }
   
   public static void drukuj_JPK3(UPO upo, WpisView wpisView) {
        String nazwa = upo.getPodatnik().getNip()+"JPK";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (upo != null) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Plik JPK zestawienie", upo.getPodatnik(), upo.getMiesiac(), upo.getRok());
            JPKSuper jpk = upo.getJpk();
            List<jpk201801.JPK.SprzedazWiersz> sprzedazWiersz = jpk.getSprzedazWiersz();
            Collections.sort(sprzedazWiersz, new JPK3SprzedazWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOS(sprzedazWiersz),100, 0);
            jpk201801.JPK.SprzedazCtrl sprzedazCtrl = (jpk201801.JPK.SprzedazCtrl) jpk.getSprzedazCtrl();
            if (sprzedazCtrl!=null) {
                String opis = "Ilość faktur "+sprzedazCtrl.getLiczbaWierszySprzedazy().intValue()+". Podatek należny "+F.curr(sprzedazCtrl.getPodatekNalezny().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów sprzedaży");
            }
            List<jpk201801.JPK.ZakupWiersz> zakupWiersz = jpk.getZakupWiersz();
            Collections.sort(zakupWiersz, new JPK3ZakupWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOZ(zakupWiersz),100,0);
            jpk201801.JPK.ZakupCtrl zakupCtrl = (jpk201801.JPK.ZakupCtrl) jpk.getZakupCtrl();
            if (zakupCtrl!=null) {
                String opis = "Ilość faktur "+zakupCtrl.getLiczbaWierszyZakupow().intValue()+". Podatek naliczony "+F.curr(zakupCtrl.getPodatekNaliczony().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów zakupu");
            }
            String opis = "Nr wysyłki "+upo.getReferenceNumber();
            dodajLinieOpisuBezOdstepu(document, opis);
            opis = "Okres "+upo.getMiesiac()+"/"+upo.getRok();
            dodajLinieOpisuBezOdstepu(document, opis);
            opis = "Data sporządzenia "+data.Data.data_ddMMMMyyyy(upo.getDatajpk());
            dodajLinieOpisuBezOdstepu(document, opis);
            if (upo.getCode() != null) {
                opis = "Data upo "+data.Data.data_ddMMMMyyyy(upo.getDataupo());
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Kod upo "+upo.getCode();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Status "+upo.getDescription()+" "+upo.getDetails();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Wersja "+upo.getWersja();
                dodajLinieOpisuBezOdstepu(document, opis);
            }
            if (upo.getPotwierdzenie() != null) {
                opis = "Potwierdzenie "+upo.getPotwierdzenie().getNazwaPodmiotuPrzyjmujacego();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Skrot dokumentu: "+upo.getPotwierdzenie().getSkrotDokumentu() +"Nr ref: "+upo.getPotwierdzenie().getNumerReferencyjny();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Stempel czasu "+upo.getPotwierdzenie().getStempelCzasu();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Nazwa struktury logicznej "+upo.getPotwierdzenie().getNazwaStrukturyLogicznej();
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Nazwa urzędu "+TKodUS.getNazwaUrzedu(upo.getPotwierdzenie().getKodUrzedu());
                dodajLinieOpisuBezOdstepu(document, opis);
                opis = "Data wpłynęcia "+data.Data.data_ddMMMMyyyy(upo.getPotwierdzenie().getDataWplyniecia().toGregorianCalendar().getTime());
                dodajLinieOpisuBezOdstepu(document, opis);
            }
            opis = "Sporządził "+upo.getWprowadzil().getImieNazwisko();
            dodajLinieOpisuBezOdstepu(document, opis);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Pusty plik JPK");
        }
    }
   
   public static void drukujJPK2(JPKSuper jpk, WpisView wpisView, Podatnik podatnik) {
        String nazwa = podatnik.getNip()+"JPK";
        if (jpk != null) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Plik JPK zestawienie", podatnik,wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            List<jpk201701.JPK.SprzedazWiersz> sprzedazWiersz = jpk.getSprzedazWiersz();
            Collections.sort(sprzedazWiersz, new JPK2SprzedazWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOS(sprzedazWiersz),100, 0);
            jpk201701.JPK.SprzedazCtrl sprzedazCtrl = (jpk201701.JPK.SprzedazCtrl) jpk.getSprzedazCtrl();
            if (sprzedazCtrl!=null) {
                String opis = "Ilość faktur "+sprzedazCtrl.getLiczbaWierszySprzedazy().intValue()+". Podatek należny "+F.curr(sprzedazCtrl.getPodatekNalezny().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów sprzedaży");
            }
            List<jpk201701.JPK.ZakupWiersz> zakupWiersz = jpk.getZakupWiersz();
            Collections.sort(zakupWiersz, new JPK2ZakupWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOZ(zakupWiersz),100,0);
            jpk201701.JPK.ZakupCtrl zakupCtrl = (jpk201701.JPK.ZakupCtrl) jpk.getZakupCtrl();
            if (zakupCtrl!=null) {
                String opis = "Ilość faktur "+zakupCtrl.getLiczbaWierszyZakupow().intValue()+". Podatek naliczony "+F.curr(zakupCtrl.getPodatekNaliczony().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów zakupu");
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Pusty plik JPK");
        }
    }
   
   public static void drukujJPK3(JPKSuper jpk, WpisView wpisView, Podatnik podatnik) {
        String nazwa = podatnik.getNip()+"JPK";
        if (jpk != null) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Plik JPK zestawienie", podatnik,wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            List<jpk201801.JPK.SprzedazWiersz> sprzedazWiersz = jpk.getSprzedazWiersz();
            Collections.sort(sprzedazWiersz, new JPK3SprzedazWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOS(sprzedazWiersz),100, 0);
            jpk201801.JPK.SprzedazCtrl sprzedazCtrl = (jpk201801.JPK.SprzedazCtrl) jpk.getSprzedazCtrl();
            if (sprzedazCtrl!=null) {
                String opis = "Ilość faktur "+sprzedazCtrl.getLiczbaWierszySprzedazy().intValue()+". Podatek należny "+F.curr(sprzedazCtrl.getPodatekNalezny().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów sprzedaży");
            }
            List<jpk201801.JPK.ZakupWiersz> zakupWiersz = jpk.getZakupWiersz();
            Collections.sort(zakupWiersz, new JPK3ZakupWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOZ(zakupWiersz),100,0);
            jpk201801.JPK.ZakupCtrl zakupCtrl = (jpk201801.JPK.ZakupCtrl) jpk.getZakupCtrl();
            if (zakupCtrl!=null) {
                String opis = "Ilość faktur "+zakupCtrl.getLiczbaWierszyZakupow().intValue()+". Podatek naliczony "+F.curr(zakupCtrl.getPodatekNaliczony().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów zakupu");
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Pusty plik JPK");
        }
    }

    private static void getSprzedazWiersz() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
