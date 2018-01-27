/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.JPKSprzedazWierszcomparator;
import comparator.JPKZakupWierszcomparator;
import embeddable.TKodUS;
import entity.UPO;
import entity.Uz;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import jpk201701.JPK;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PdfUPO extends Pdf implements Serializable {

   public static void drukuj(UPO upo, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"JPK";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (upo != null) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Plik JPK zestawienie", wpisView.getPodatnikObiekt(),wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            List<JPK.SprzedazWiersz> sprzedazWiersz = ((JPK)upo.getJpk()).getSprzedazWiersz();
            Collections.sort(sprzedazWiersz, new JPKSprzedazWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOS(sprzedazWiersz),100, 0);
            JPK.SprzedazCtrl sprzedazCtrl = ((JPK)upo.getJpk()).getSprzedazCtrl();
            if (sprzedazCtrl!=null) {
                String opis = "Ilość faktur "+sprzedazCtrl.getLiczbaWierszySprzedazy().intValue()+". Podatek należny "+F.curr(sprzedazCtrl.getPodatekNalezny().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów sprzedaży");
            }
            List<JPK.ZakupWiersz> zakupWiersz = ((JPK)upo.getJpk()).getZakupWiersz();
            Collections.sort(zakupWiersz, new JPKZakupWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOZ(zakupWiersz),100,0);
            JPK.ZakupCtrl zakupCtrl = ((JPK)upo.getJpk()).getZakupCtrl();
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
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }
   
   public static void drukujJPK(JPK jpk, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"JPK";
        if (jpk != null) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Plik JPK zestawienie", wpisView.getPodatnikObiekt(),wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            List<JPK.SprzedazWiersz> sprzedazWiersz = jpk.getSprzedazWiersz();
            Collections.sort(sprzedazWiersz, new JPKSprzedazWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOS(sprzedazWiersz),100, 0);
            JPK.SprzedazCtrl sprzedazCtrl = jpk.getSprzedazCtrl();
            if (sprzedazCtrl!=null) {
                String opis = "Ilość faktur "+sprzedazCtrl.getLiczbaWierszySprzedazy().intValue()+". Podatek należny "+F.curr(sprzedazCtrl.getPodatekNalezny().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów sprzedaży");
            }
            List<JPK.ZakupWiersz> zakupWiersz = jpk.getZakupWiersz();
            Collections.sort(zakupWiersz, new JPKZakupWierszcomparator());
            dodajTabele(document, testobjects.testobjects.getTabelaUPOZ(zakupWiersz),100,0);
            JPK.ZakupCtrl zakupCtrl = jpk.getZakupCtrl();
            if (zakupCtrl!=null) {
                String opis = "Ilość faktur "+zakupCtrl.getLiczbaWierszyZakupow().intValue()+". Podatek naliczony "+F.curr(zakupCtrl.getPodatekNaliczony().doubleValue());
                dodajLinieOpisu(document, opis);
            } else {
                dodajLinieOpisu(document, "Brak dokumentów zakupu");
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }

    private static void getSprzedazWiersz() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
