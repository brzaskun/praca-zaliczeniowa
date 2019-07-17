/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entityfk.SprFinKwotyInfDod;
import view.WpisView;
import java.io.File;
import pdffk.PdfMain;
import static pdffk.PdfMain.*;
import com.itextpdf.text.pdf.PdfPTable;
import embeddablefk.SaldoKonto;
import entity.PodatnikUdzialy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.PrimeFaces;
import plik.Plik;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SprFinInfDodBean {
    
    public static void drukujInformacjeDodatkowa(WpisView wpisView, SprFinKwotyInfDod sprFinKwotyInfDod, List<SaldoKonto> listaSaldoKonto) {
        String nazwa = null;
        nazwa = wpisView.getPodatnikObiekt().getNip()+"InfDod"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Portrait(60,40);
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        SprFinInfDodBeanTXT.naglowekglowny(document, wpisView.getRokWpisuSt());
        dodajSzczegoly(document, sprFinKwotyInfDod, listaSaldoKonto);;
        PdfMain.dodajpodpis(document, wpisView.getFormaprawna().toString());
        finalizacjaDokumentuQR(document,nazwa);
        String f = null;
        f = "pokazwydruk('"+nazwa+"');";
        PrimeFaces.current().executeScript(f);
        
    }
    
    public static void main(String[] args) {
        String nazwa = null;
        nazwa = "8511005008"+"InfDod"+"2019";
        Document document = inicjacjaA4Portrait(60,40);
        PdfWriter writer = inicjacjaWriteraTmp(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        SprFinInfDodBeanTXT.naglowekglowny(document, null);
        dodajSzczegoly(document, null, null);
        PdfMain.dodajpodpis(document, "SP. Z O.O.");
        finalizacjaDokumentuQR(document,nazwa);
    }

    private static void dodajSzczegoly(Document document, SprFinKwotyInfDod sprFinKwotyInfDod, List<SaldoKonto> listaSaldoKonto) {
        SprFinInfDodBeanTXT.podnaglowek1(document);
        ustawtabela2k1(2, 10, document, SprFinInfDodBeanTXT.wierszeTab1(sprFinKwotyInfDod));
        SprFinInfDodBeanTXT.podnaglowek2(document);
        SprFinInfDodBeanTXT.zasadyrachunkowosci(document);
        SprFinInfDodBeanTXT.podnaglowek3(document);
        ustawtabela5k(document, SprFinInfDodBeanTXT.wierszeTab2(listaSaldoKonto));
        ustawtabela2k(7, 3, document, SprFinInfDodBeanTXT.wierszeTab3(listaSaldoKonto));
        SprFinInfDodBeanTXT.podnaglowek4(document);
        ustawtabela4k(7, 3, 3, 3, document, SprFinInfDodBeanTXT.wierszeTab4(listaSaldoKonto));
        SprFinInfDodBeanTXT.podnaglowek5(document);
        ustawtabela4k(7, 3, 3, 3, document, SprFinInfDodBeanTXT.wierszeTab5(listaSaldoKonto));
        SprFinInfDodBeanTXT.podnaglowek6(document);
        ustawtabela2k(7, 3, document, SprFinInfDodBeanTXT.wierszeTab6(listaSaldoKonto, sprFinKwotyInfDod));
        SprFinInfDodBeanTXT.podnaglowek7(document);
        ustawtabela4k(7, 3, 3, 3, document, SprFinInfDodBeanTXT.wierszeTab7(listaSaldoKonto));
        SprFinInfDodBeanTXT.podnaglowek8(document);
        ustawtabela5k(document, SprFinInfDodBeanTXT.wierszeTab8(listaSaldoKonto));
        SprFinInfDodBeanTXT.podnaglowek9(document);
        ustawtabela4k(7, 3, 3, 3, document, SprFinInfDodBeanTXT.wierszeTab9(listaSaldoKonto));
        SprFinInfDodBeanTXT.podnaglowek10(document);
        ustawtabela4k(7, 3, 3, 3, document, SprFinInfDodBeanTXT.wierszeTab10(sprFinKwotyInfDod));
        SprFinInfDodBeanTXT.podnaglowek11(document);
        ustawtabela2k(7, 3, document, SprFinInfDodBeanTXT.wierszeTab11(sprFinKwotyInfDod));
    }


    private static void ustawwiersz(PdfPTable table, List<String> wiersze, String[] rozstaw) {
        int i = 0;
        int r = rozstaw.length-1;
        for (String p : wiersze) {
            table.addCell(ustawfrazeAlign(p, rozstaw[i], 8));
            i++;
            if (i>r) {
                i=0;
            }
        }
    }
    
    private static void ustawheader(PdfPTable table, List<String> wiersze) {
        for (String p : wiersze) {
            table.addCell(ustawfrazeAlign(p, "center", 8));
        }
    }
    
    private static void ustawtabela2k1(int k1, int k2, Document document, List<String> wiersze) {
        try {
            int[] col = {k1,k2};
            PdfPTable table = przygotujtabele(col.length,col, 92, 2f, 3f);
            String[] rozstaw = {"left", "left"};
            ustawwiersz(table,wiersze, rozstaw);
            document.add(table);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(SprFinInfDodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void ustawtabela2k(int k1, int k2, Document document, List<String> wiersze) {
        try {
            int[] col = {k1,k2};
            PdfPTable table = przygotujtabele(col.length,col, 92, 2f, 3f);
            String[] rozstaw = {"left", "right"};
            ustawwiersz(table,wiersze, rozstaw);
            document.add(table);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(SprFinInfDodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static void ustawtabela5k(Document document, List<String> wiersze) {
        try {
            int[] col = {4,2,2,2,2};
            PdfPTable table = przygotujtabele(col.length,col, 100, 2f, 3f);
            ustawheader(table,wiersze.subList(0, col.length));
            String[] rozstaw = {"left", "right", "right", "right", "right"};
            ustawwiersz(table,wiersze.subList(col.length, wiersze.size()), rozstaw);
            document.add(table);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(SprFinInfDodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ustawtabela4k(int i, int i0, int i1, int i2, Document document, List<String> wiersze) {
        try {
            int[] col = {i,i0,i1, i2};
            PdfPTable table = przygotujtabele(col.length,col, 92, 2f, 3f);
            ustawheader(table,wiersze.subList(0, col.length));
            String[] rozstaw = {"left", "right", "right", "right"};
            ustawwiersz(table,wiersze.subList(col.length, wiersze.size()), rozstaw);
            document.add(table);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(SprFinInfDodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void drukujSprawozdanieZarzadu(WpisView wpisView, SprFinKwotyInfDod sprFinKwotyInfDod) {
        String nazwa = null;
        nazwa = wpisView.getPodatnikObiekt().getNip()+"SprawZarzadu"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Portrait(60,40);
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        SprFinSprawZarzaduBeanTXT.naglowekglowny(document, sprFinKwotyInfDod, wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getMiejscowosc(), wpisView.getPodatnikObiekt().getImie());
        dodajSzczegolySprZarz(document, sprFinKwotyInfDod);;
        finalizacjaDokumentuQR(document,nazwa);
        String f = null;
        f = "pokazwydruk('"+nazwa+"');";
        PrimeFaces.current().executeScript(f);
    }
    
    private static void dodajSzczegolySprZarz(Document document, SprFinKwotyInfDod sprFinKwotyInfDod) {
        SprFinSprawZarzaduBeanTXT.podnaglowek1(document, sprFinKwotyInfDod.getPpdzialalnosci());
        SprFinSprawZarzaduBeanTXT.podnaglowek2(document, sprFinKwotyInfDod.getRok(), sprFinKwotyInfDod.getZyskstratanetto(), sprFinKwotyInfDod.getSumabilansowa(), sprFinKwotyInfDod.getDatado());
        SprFinSprawZarzaduBeanTXT.podnaglowek3(document);
        SprFinSprawZarzaduBeanTXT.podnaglowek4(document);
        SprFinSprawZarzaduBeanTXT.podnaglowek5(document);
        SprFinSprawZarzaduBeanTXT.podnaglowek6(document);
        SprFinSprawZarzaduBeanTXT.podnaglowek7(document);
        
    }
    
    public static void drukujUchwaly1(WpisView wpisView, SprFinKwotyInfDod sprFinKwotyInfDod, List<PodatnikUdzialy> podatnikUdzialy) {
        String nazwa = null;
        nazwa = wpisView.getPodatnikObiekt().getNip()+"Uchwaly1"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Portrait(60,40);
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        SprFinUchwalyBeanTXT.naglowekglowny(document, sprFinKwotyInfDod.getRok(), sprFinKwotyInfDod.getDatauchwal(), wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getMiejscowosc(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado(), wpisView.getPodatnikObiekt().getImie());
        dodajSzczegolyUchwaly(wpisView.getFormaprawna(), document, sprFinKwotyInfDod, podatnikUdzialy);;
        finalizacjaDokumentuQR(document,nazwa);
        String f = null;
        f = "pokazwydruk('"+nazwa+"');";
        PrimeFaces.current().executeScript(f);
    }
    
    private static void dodajSzczegolyUchwaly(String formaprawna, Document document, SprFinKwotyInfDod sprFinKwotyInfDod, List<PodatnikUdzialy> podatnikUdzialy) {
        SprFinUchwalyBeanTXT.podnaglowek1(formaprawna, document, sprFinKwotyInfDod.getDatauchwal(), podatnikUdzialy);
        SprFinUchwalyBeanTXT.podnaglowek2(document, sprFinKwotyInfDod.getRok(), sprFinKwotyInfDod.getZyskstratanetto(), sprFinKwotyInfDod.getSumabilansowa());
        SprFinUchwalyBeanTXT.podnaglowek7(document);
    }
    
    public static void drukujUchwaly2(WpisView wpisView, SprFinKwotyInfDod sprFinKwotyInfDod, List<PodatnikUdzialy> podatnikUdzialy) {
        String nazwa = null;
        nazwa = wpisView.getPodatnikObiekt().getNip()+"Uchwaly2"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Portrait(60,40);
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        if (Z.z(sprFinKwotyInfDod.getZyskstratanetto())==0.0) {
            SprFinUchwalyBeanTXT.naglowekglowny1a(document, sprFinKwotyInfDod.getRok(), sprFinKwotyInfDod.getDatauchwal(), wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getMiejscowosc(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado(), sprFinKwotyInfDod.getZyskstratanetto(), wpisView.getPodatnikObiekt().getImie());
            SprFinUchwalyBeanTXT.podnaglowek7(document);
        } else {
            SprFinUchwalyBeanTXT.naglowekglowny1(document, sprFinKwotyInfDod.getRok(), sprFinKwotyInfDod.getDatauchwal(), wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getMiejscowosc(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado(), sprFinKwotyInfDod.getZyskstratanetto(), wpisView.getPodatnikObiekt().getImie());
            dodajSzczegolyUchwaly1(wpisView.getFormaprawna(), document, sprFinKwotyInfDod, podatnikUdzialy);;
        }
        finalizacjaDokumentuQR(document,nazwa);
        String f = null;
        f = "pokazwydruk('"+nazwa+"');";
        PrimeFaces.current().executeScript(f);
    }
    
    private static void dodajSzczegolyUchwaly1(String formaprawna, Document document, SprFinKwotyInfDod sprFinKwotyInfDod, List<PodatnikUdzialy> podatnikUdzialy) {
        SprFinUchwalyBeanTXT.podnaglowek11(formaprawna, document, sprFinKwotyInfDod.getDatauchwal(), podatnikUdzialy);
        SprFinUchwalyBeanTXT.podnaglowek21(document, sprFinKwotyInfDod,  sprFinKwotyInfDod.getRok(), sprFinKwotyInfDod.getZyskstratanetto(), sprFinKwotyInfDod.getSumabilansowa());
        SprFinUchwalyBeanTXT.podnaglowek7(document);
    }
}
