/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entityfk.MiejscePrzychodow;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import viewfk.StowRozrachCzlonkView;

/**
 *
 * @author Osito
 */
public class PDFStowRozrachCzlonk {
     public static void drukuj(List<StowRozrachCzlonkView.Pozycja> wykaz, MiejscePrzychodow wybranyczlonek, String rokWpisuSt) {
        String nazwa = Data.aktualnaData()+wybranyczlonek.getId()+"_stowrozrachczlonk";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wykaz != null && wykaz.size() > 0) {
            Document document = inicjacjaA4Landscape();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajLinieOpisu(document, "Zestawienie naliczonych i zapłaconych kwot w roku kalendarzowym "+wybranyczlonek.getOpismiejsca()+" rok "+rokWpisuSt);
            dodajTabele(document, testobjects.testobjects.getStowRozrachCzlonk(wykaz),95,0,8);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Błąd podczas wydruku");
        }
    }
     
     public static void drukuj(Map<MiejscePrzychodow, List<StowRozrachCzlonkView.Pozycja>> czlonkowie, String rokWpisuSt, String nip) {
        String nazwa = Data.aktualnaData()+nip+"zbiorcze_stowrozrachczlonk";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (czlonkowie != null && czlonkowie.size() > 0) {
            Document document = inicjacjaA4Landscape();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            Collection<List<StowRozrachCzlonkView.Pozycja>> tabele = czlonkowie.values();
            List<MiejscePrzychodow> mp = new ArrayList<>();
            mp.addAll(czlonkowie.keySet());
            int i = 0;
            for (List<StowRozrachCzlonkView.Pozycja> p : tabele) {
                dodajLinieOpisu(document, "Zestawienie naliczonych i zapłaconych kwot w roku kalendarzowym "+mp.get(i++)+" rok "+rokWpisuSt);
                dodajTabele(document, testobjects.testobjects.getStowRozrachCzlonk(p),95,0,8);
                dodajLinieOpisu(document, "");
            };
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Błąd podczas wydruku");
        }
    }

   
}
