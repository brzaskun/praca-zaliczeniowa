/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAF;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlignNOBorder;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.FakturaXXLKolumnaDAO;
import dao.FakturaelementygraficzneDAO;
import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.FakturaDuplikat;
import entity.FakturaStopkaNiemiecka;
import entity.FakturaXXLKolumna;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import entity.Pozycjenafakturze;
import error.E;
import format.F;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import msg.B;
import org.apache.commons.lang3.ArrayUtils;
import pdf.PdfFaktura;
import slownie.Slownie;
import slownie.SlownieDE;
import view.DeklaracjaVatSchemaView;
import view.FakturaView;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class PdfFP {

    public static void dodajnaglowek(PdfWriter writer, List<Fakturadodelementy> elementydod) {
        if (czydodatkowyelementjestAktywny("nagłówek", elementydod)) {
            //naglowek
            absText(writer, pobierzelementdodatkowy("nagłówek", elementydod), 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560, 10);
        }
    }

    public static void dodajnaglowekstopka(PdfWriter writer, List<Fakturadodelementy> elementydod) {
        if (czydodatkowyelementjestAktywny("nagłówek", elementydod)) {
            //naglowek
            absText(writer, pobierzelementdodatkowy("nagłówek", elementydod), 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560, 10);
        }
        if (czydodatkowyelementjestAktywny("stopka", elementydod)) {
            //stopka
            absText(writer, pobierzelementdodatkowy("stopka", elementydod), 15, 26, 6);
            prost(writer.getDirectContent(), 12, 15, 560, 20);
        }
    }

    public static void dodajoznaczenieduplikat(PdfWriter writer, FakturaDuplikat duplikatobj) {
        //naglowek
        absText(writer, "DUPLIKAT wystawiony dnia " + duplikatobj.getDatawystawienia(), 300, 780, 12);
        //prost(writer.getDirectContent(), 295, 777, 280, 30);
    }

    public static boolean czydodatkowyelementjestAktywny(String element, List<Fakturadodelementy> fdod) {
        for (Fakturadodelementy p : fdod) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getAktywny();
            }
        }
        return false;
    }

    public static String pobierzelementdodatkowy(String element, List<Fakturadodelementy> fdod) {
        for (Fakturadodelementy p : fdod) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getTrescelementu();
            }
        }
        return "nie odnaleziono";
    }

    private static void absText(PdfWriter writer, String text, int x, int y, int font) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, font);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    private static void absText(PdfContentByte cb, String text, int x, int y, int font) {
        try {
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, font);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    public static void dodajopisdok(Document document) {
        document.addTitle("Faktura");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk faktury w formacie pdf");
        document.addKeywords("Faktura, PDF");
        document.addCreator("Grzegorz Grzelczyk");
    }

    public static Map<String, Integer> pobierzwymiarGora(List<Pozycjenafakturze> lista) {
        Map<String, Integer> wymiary = new HashMap<>();
        int gornylimit = 836;
        for (Pozycjenafakturze p : lista) {
            int wymiargora = (p.getGora() / 2);
            wymiary.put(p.getPozycjenafakturzePK().getNazwa(), gornylimit - wymiargora);
        }
        return wymiary;
    }

    public static void dolaczpozycjedofaktury(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiaryGora, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        int wierszewtabelach = PdfFP.obliczwierszewtabelach(selected);
        Pozycjenafakturze pozycja = new Pozycjenafakturze();
        String adres = "";
        String text = null;
        float dzielnik = 2;
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:data":
                    //Dane do moudlu data
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "data");
                    prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:data") - 5, 140, 15);
                    text = selected.getMiejscewystawienia() + " "+B.b("dnia")+": " + selected.getDatawystawienia();
                    absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:data"), 8);
                    break;
                case "akordeon:formwzor:datasprzedazy":
                    //Dane do moudlu data
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "datasprzedazy");
                    prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:datasprzedazy") - 5, 190, 15);
                    text = B.b("datasprzedazy")+": " + selected.getDatasprzedazy();
                    absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:datasprzedazy"), 8);
                    break;
                case "akordeon:formwzor:fakturanumer":
                    //Dane do modulu fakturanumer
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "fakturanumer");
                    prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:fakturanumer") - 5, 190, 20);
                    if (selected.isGutschrift()) {
                        text = "Gutschrift Nr "+ selected.getFakturaPK().getNumerkolejny();
                    } else {
                        text = B.b("fakturanr")+" "+ selected.getFakturaPK().getNumerkolejny();
                    }
                    absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:fakturanumer"), 10);
                    break;
                case "akordeon:formwzor:wystawca":
                    //Dane do modulu sprzedawca
                    if (!czydodatkowyelementjestAktywny("stopka niemiecka", elementydod)) {
                        pozycja = zwrocPolozenieElementu(skladnikifaktury, "wystawca");
                        prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:wystawca") - 65, 250, 80);
                        absText(writer, B.b("sprzedawca")+": ", (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca"), 10);
                        absText(writer, selected.getWystawca().getNazwadlafaktury(), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca") - 20, 8);
                        adres = selected.getWystawca().getAdresdlafaktury();
                        absText(writer, adres, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca") - 40, 8);
                        String nip = selected.getWystawca().getNipdlafaktury() != null ? selected.getWystawca().getNipdlafaktury() : selected.getWystawca().getNip();
                        absText(writer, B.b("NIP")+": " + selected.getWystawca().getNipdlafaktury(), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca") - 60, 8);
                    }
                    break;
                case "akordeon:formwzor:odbiorca":
                    //Dane do modulu odbiorca
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "odbiorca");
                    prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:odbiorca") - 65, 250, 80);
                    absText(writer, B.b("nabywca")+": ", (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca"), 10);
                    absText(writer, selected.getKontrahent().getNpelna(), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca") - 20, 8);
                    if (selected.getKontrahent().getLokal() != null && !selected.getKontrahent().getLokal().equals("-") && !selected.getKontrahent().getLokal().equals("")) {
                        adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom() + "/" + selected.getKontrahent().getLokal();
                    } else {
                        adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom();
                    }
                    absText(writer, adres, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca") - 40, 8);
                    text = B.b("NIP")+": " + selected.getKontrahent().getNip();
                    absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca") - 60, 8);
                    break;
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    PdfPTable table = null;
                    PdfPTable tablekorekta = null;
                    if (selected.getPozycjepokorekcie() != null && selected.isFakturavatmarza() == true) {
                        table = wygenerujtablicevatmarza(false, selected.getPozycjenafakturze(), selected);
                        tablekorekta = wygenerujtablicevatmarza(true, selected.getPozycjepokorekcie(), selected);
                    } else if (selected.getPozycjepokorekcie() != null && selected.isFakturaxxl() == false) {
                        table = wygenerujtablice(false, selected.getPozycjenafakturze(), selected);
                        tablekorekta = wygenerujtablice(true, selected.getPozycjepokorekcie(), selected);
                    } else if (selected.getPozycjepokorekcie() == null && selected.isFakturaNormalna()) {
                        table = wygenerujtablice(false, selected.getPozycjenafakturze(), selected);
                    } else if (selected.getPozycjepokorekcie() == null && selected.isFakturavatmarza()) {
                        table = wygenerujtablicevatmarza(false, selected.getPozycjenafakturze(), selected);
                    } else if (selected.getPozycjepokorekcie() == null && selected.isFakturaniemiecka13b()) {
                        table = wygenerujtabliceNiemiecka13b(false, selected.getPozycjenafakturze(), selected);
                    } else if (selected.getPozycjepokorekcie() != null && selected.isFakturaxxl() == true) {
                        if (wierszewtabelach > 12) {
                            table = wygenerujtablicexxl(false, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, true);
                            tablekorekta = wygenerujtablicexxl(true, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, true);
                        } else {
                            //false to znaczy ze odleglosci maja byc inne
                            table = wygenerujtablicexxl(false, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, false);
                            tablekorekta = wygenerujtablicexxl(true, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, false);
                        }
                    } else if (selected.getPozycjepokorekcie() == null && selected.isFakturaxxl() == true) {
                        if (wierszewtabelach > 12) {
                            table = wygenerujtablicexxl(false, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, true);
                        } else {
                            table = wygenerujtablicexxl(false, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, false);
                        }
                    }
                    // write the table to an absolute position
                    table.writeSelectedRows(0, table.getRows().size(), (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:towary"), writer.getDirectContent());
                    if (selected.getPozycjepokorekcie() != null) {
                        int odstep = wymiaryGora.get("akordeon:formwzor:towary") - (table.getRows().size() * 17);
                        tablekorekta.writeSelectedRows(0, tablekorekta.getRows().size(), (pozycja.getLewy() / dzielnik), odstep, writer.getDirectContent());
                        int odstep1 = wymiaryGora.get("akordeon:formwzor:towary") - (table.getRows().size() * 17) - (tablekorekta.getRows().size() * 17);
                        text = B.b("przyczynakorekty")+": " + selected.getPrzyczynakorekty();
                        absText(writer, text, (int) (pozycja.getLewy() / dzielnik), odstep1, 8);
                    }
                    break;
                case "akordeon:formwzor:logo":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("logo", elementydod)) {
                        try {
                            pozycja = zwrocPolozenieElementu(skladnikifaktury, "logo");
                            Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
                            String nazwaplikuzbazy = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/" + element.getFakturaelementygraficznePK().getNazwaelementu();
                            File f = new File(nazwaplikuzbazy);
                            if (f.exists()) {
                                Image logo = Image.getInstance(nazwaplikuzbazy);
                                // Set the position of image
                                float wysokosc = zamienStringnaFloat(element.getWysokosc());
                                float szerokosc = zamienStringnaFloat(element.getSzerokosc());
                                logo.scaleToFit(szerokosc, wysokosc);
                                logo.setAbsolutePosition((pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:logo") - wysokosc * .85f); //e
                                // Add paragraph to PDF document.
                                document.add(logo);
                            }
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                    break;
                case "akordeon:formwzor:nrzamowienia":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("nr zamówienia", elementydod)) {
                        pozycja = zwrocPolozenieElementu(skladnikifaktury, "nrzamowienia");
                        prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:nrzamowienia") - 5, 180, 15);
                        text = B.b("nrzamowienia")+": " + selected.getNumerzamowienia();
                        absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:nrzamowienia"), 8);
                    }
                    break;
                case "akordeon:formwzor:przewłaszczenie":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("przewłaszczenie", elementydod)) {
                        pozycja = zwrocPolozenieElementu(skladnikifaktury, "przewłaszczenie");
                        prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:przewłaszczenie") - 5, 230, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("przewłaszczenie", elementydod), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:przewłaszczenie"), 8);
                    }
                    break;
                case "akordeon:formwzor:warunkidostawy":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("warunki dostawy", elementydod)) {
                        pozycja = zwrocPolozenieElementu(skladnikifaktury, "warunkidostawy");
                        prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:warunkidostawy") - 5, 400, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("warunki dostawy", elementydod), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:warunkidostawy"), 8);
                    }
                    break;
                case "akordeon:formwzor:wezwaniedozapłaty":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("wezwanie do zapłaty", elementydod)) {
                        pozycja = zwrocPolozenieElementu(skladnikifaktury, "wezwaniedozapłaty");
                        prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:wezwaniedozapłaty") - 5, 230, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("wezwanie do zapłaty", elementydod), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                    }
                    break;
                case "akordeon:formwzor:platnosc":
                    //Dane do modulu platnosc
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "platnosc");
                    prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:platnosc") - 25, 250, 35);
                    text = B.b("sposobzaplaty")+": " + B.b(selected.getSposobzaplaty());
                    absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:platnosc"), 8);
                    text = B.b("terminplatnosci")+": " + selected.getTerminzaplaty();
                    absText(writer, text, (int) (pozycja.getLewy() / dzielnik) + 100, wymiaryGora.get("akordeon:formwzor:platnosc"), 8);
                    text = B.b("nrkontabankowego")+": " + selected.getNrkontabankowego();
                    if (selected.getSposobzaplaty().equals("przelew") && selected.getNrkontabankowego() != null) {
                        absText(writer, text, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:platnosc") - 20, 8);
                    }
                    break;
                case "akordeon:formwzor:dozaplaty":
                    //Dane do modulu platnosc
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "dozaplaty");
                    prost(writer.getDirectContent(), (int) (pozycja.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:dozaplaty") - 25, 350, 35);
                    double wynik = 0;
                    if (selected.getPozycjepokorekcie() != null) {
                        wynik = Z.z((selected.getNettopk() + selected.getVatpk()) - (selected.getNetto() + selected.getVat()));
                    } else {
                        wynik = Z.z(selected.getNetto() + selected.getVat());
                    }
                    if (wynik > 0) {
                        absText(writer, B.b("dozaplaty")+": " + przerobkwote(wynik) + " " + selected.getWalutafaktury(), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:dozaplaty"), 8);
                    } else {
                        absText(writer, B.b("dozwrotu")+": " + przerobkwote(wynik) + " " + selected.getWalutafaktury(), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:dozaplaty"), 8);
                    }
                    if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage().equals("pl")) {
                        absText(writer, B.b("slownie")+": "  + Slownie.slownie(String.valueOf(wynik),selected.getWalutafaktury()), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:dozaplaty") - 20, 8);
                    } else {
                        absText(writer, B.b("slownie")+": "  + SlownieDE.slownie(String.valueOf(wynik),selected.getWalutafaktury()), (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:dozaplaty") - 20, 8);
                    }
                    break;
                case "akordeon:formwzor:podpis":
                    //Dane do modulu platnosc
                    pozycja = zwrocPolozenieElementu(skladnikifaktury, "podpis");
                    String podpis = selected.getPodpis() == null ? "" : selected.getPodpis();
                    absText(writer, podpis, (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:podpis"), 8);
                    absText(writer, "..........................................", (int) (pozycja.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:podpis") - 20, 8);
                    absText(writer, B.b("wystawcafaktury"), (int) (pozycja.getLewy() / dzielnik) + 15, wymiaryGora.get("akordeon:formwzor:podpis") - 40, 8);
                    break;
            }
        }
    }

    public static Image dolaczpozycjedofakturydlugaczlogo(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pozycja = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:logo":
                    //Dane do modulu przewłaszczenie
                    try {
                        if (PdfFP.czydodatkowyelementjestAktywny("logo", elementydod)) {
                            pozycja = zwrocPolozenieElementu(skladnikifaktury, "logo");
                            Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
                            String nazwaplikuzbazy = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/" + element.getFakturaelementygraficznePK().getNazwaelementu();
                            File f = new File(nazwaplikuzbazy);
                            if (f.exists()) {
                                Image logo = Image.getInstance(nazwaplikuzbazy);
                                // Set the position of image
                                float wysokosc = zamienStringnaFloat(element.getWysokosc());
                                float szerokosc = zamienStringnaFloat(element.getSzerokosc());
                                logo.scaleToFit(szerokosc, wysokosc);
                                logo.setAbsolutePosition((pozycja.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:logo") - wysokosc * .85f); //e
                                // Add paragraph to PDF document.
                                return logo;
                            }
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                    break;
                default:
                    break;

            }
        }
        return null;
    }

    private static float zamienStringnaFloat(String w) {
        String obciete = w.substring(0, w.length() - 2);
        return Float.parseFloat(obciete) / 2f;
    }

    public static void dolaczpozycjedofakturydlugacz1(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfContentByte canvas, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:data":
                    //Dane do moudlu data
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "data");
                    prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:data") - 5, 140, 15);
                    absText(canvas, selected.getMiejscewystawienia() + " "+B.b("dnia")+" " + selected.getDatawystawienia(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:data"), 8);
                    break;
                case "akordeon:formwzor:fakturanumer":
                    //Dane do modulu fakturanumer
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "fakturanumer");
                    prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:fakturanumer") - 5, 190, 20);
                    absText(canvas, B.b("fakturanr")+" " + selected.getFakturaPK().getNumerkolejny(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:fakturanumer"), 10);
                    break;
                case "akordeon:formwzor:wystawca":
                    //Dane do modulu sprzedawca
                    if (!czydodatkowyelementjestAktywny("stopka niemiecka", elementydod)) {
                        pobrane = zwrocPolozenieElementu(skladnikifaktury, "wystawca");
                        prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wystawca") - 65, 250, 80);
                        absText(canvas, B.b("sprzedawca")+":", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca"), 10);
                        absText(canvas, selected.getWystawca().getNazwadlafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 20, 8);
                        adres = selected.getWystawca().getAdresdlafaktury();
                        absText(canvas, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 40, 8);
                        absText(canvas, B.b("NIP")+": " + selected.getWystawca().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 60, 8);
                    }
                    break;
                case "akordeon:formwzor:odbiorca":
                    //Dane do modulu odbiorca
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "odbiorca");
                    prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:odbiorca") - 65, 250, 80);
                    absText(canvas, B.b("nabywca")+":", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca"), 10);
                    absText(canvas, selected.getKontrahent().getNpelna(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 20, 8);
                    adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom();
                    absText(canvas, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 40, 8);
                    absText(canvas, B.b("NIP")+": " + selected.getKontrahent().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 60, 8);
                    break;
                case "akordeon:formwzor:przewłaszczenie":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("przewłaszczenie", elementydod)) {
                        pobrane = zwrocPolozenieElementu(skladnikifaktury, "przewłaszczenie");
                        prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:przewłaszczenie") - 5, 230, 15);
                        absText(canvas, PdfFP.pobierzelementdodatkowy("przewłaszczenie", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:przewłaszczenie"), 8);
                    }
                    break;
                case "akordeon:formwzor:warunkidostawy":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("warunki dostawy", elementydod)) {
                        pobrane = zwrocPolozenieElementu(skladnikifaktury, "warunkidostawy");
                        prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:warunkidostawy") - 5, 400, 15);
                        absText(canvas, PdfFP.pobierzelementdodatkowy("warunki dostawy", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:warunkidostawy"), 8);
                    }
                    break;
                case "akordeon:formwzor:wezwaniedozapłaty":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("wezwanie do zapłaty", elementydod)) {
                        pobrane = zwrocPolozenieElementu(skladnikifaktury, "wezwaniedozapłaty");
                        prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wezwaniedozapłaty") - 5, 230, 15);
                        absText(canvas, PdfFP.pobierzelementdodatkowy("wezwanie do zapłaty", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                    }
                    break;
                default:
                    break;

            }
        }
    }

    public static PdfPTable dolaczpozycjedofakturydlugacz2(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    return wygenerujtablicexxl(false, selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView, true);
                default:
                    break;

            }
        }
        return null;
    }

    public static PdfPTable dolaczpozycjedofaktury2normal(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    return wygenerujtablice(false, selected.getPozycjenafakturze(), selected);
                default:
                    break;
            }
        }
        return null;
    }
    
    public static PdfPTable dolaczpozycjedofakturyvatmarza(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    return wygenerujtablicevatmarza(false, selected.getPozycjenafakturze(), selected);
                default:
                    break;
            }
        }
        return null;
    }

    public static PdfPTable dolaczpozycjedofaktury2normalkorekta(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    return wygenerujtablice(true, selected.getPozycjepokorekcie(), selected);
                default:
                    break;
            }
        }
        return null;
    }
    
    public static PdfPTable dolaczpozycjedofakturyvatmarzakorekta(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    return wygenerujtablicevatmarza(true, selected.getPozycjepokorekcie(), selected);
                default:
                    break;
            }
        }
        return null;
    }

    public static PdfPTable dolaczpozycjedofakturydlugacz2korekta(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "towary");
                    return wygenerujtablicexxl(true, selected.getPozycjepokorekcie(), selected, fakturaXXLKolumnaDAO, wpisView, true);
                default:
                    break;
            }
        }
        return null;
    }

    public static void dolaczpozycjedofakturydlugacz3(boolean nowastrona, FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfContentByte canvas, Faktura selected, double gora, List<Pozycjenafakturze> skladnikifaktury, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        int wymiar = (int) gora - 20;
        pobrane = zwrocPolozenieElementu(skladnikifaktury, "platnosc");
        if (selected.getPozycjepokorekcie() != null) {
            absText(canvas, "Przyczyna korekty: " + selected.getPrzyczynakorekty(), (int) (pobrane.getLewy() / dzielnik), wymiar, 8);
        }
        for (Pozycjenafakturze p : skladnikifaktury) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:datasprzedazy":
                    //Dane do moudlu data
                    wymiar = (int) gora - 45;
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "datasprzedazy");
                    prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiar - 5, 110, 15);
                    absText(canvas, "Data sprzedaży: " + selected.getDatasprzedazy(), (int) (pobrane.getLewy() / dzielnik), wymiar, 8);
                    break;
                case "akordeon:formwzor:platnosc":
                    //Dane do modulu platnosc
                    wymiar = (int) gora - 85;
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "platnosc");
                    prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiar - 25, 250, 35);
                    absText(canvas, "Sposób zapłaty: " + selected.getSposobzaplaty(), (int) (pobrane.getLewy() / dzielnik), wymiar, 8);
                    absText(canvas, "Termin płatności: " + selected.getTerminzaplaty(), (int) (pobrane.getLewy() / dzielnik) + 100, wymiar, 8);
                    absText(canvas, "Nr konta bankowego: " + selected.getNrkontabankowego(), (int) (pobrane.getLewy() / dzielnik), wymiar - 20, 8);
                    break;
                case "akordeon:formwzor:nrzamowienia":
                    //Dane do modulu przewłaszczenie
                    wymiar = (int) gora - 125;
                    if (PdfFP.czydodatkowyelementjestAktywny("nr zamówienia", elementydod)) {
                        pobrane = zwrocPolozenieElementu(skladnikifaktury, "nrzamowienia");
                        prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiar - 5, 180, 15);
                        absText(canvas, "nr zamówienia: " + selected.getNumerzamowienia(), (int) (pobrane.getLewy() / dzielnik), wymiar, 8);
                    }
                    break;
                case "akordeon:formwzor:dozaplaty":
                    //Dane do modulu platnosc
                    wymiar = (int) gora - 150;
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "dozaplaty");
                    prost(canvas, (int) (pobrane.getLewy() / dzielnik) - 5, wymiar - 25, 350, 35);
                    double wynik = 0;
                    if (selected.getPozycjepokorekcie() != null) {
                        wynik = Z.z((selected.getNettopk() + selected.getVatpk()) - (selected.getNetto() + selected.getVat()));
                    } else {
                        wynik = Z.z(selected.getNetto() + selected.getVat());
                    }
                    if (wynik > 0) {
                        absText(canvas, "Do zapłaty: " + przerobkwote(wynik) + " " + selected.getWalutafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiar, 8);
                    } else {
                        absText(canvas, "Do zwrotu: " + przerobkwote(wynik) + " " + selected.getWalutafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiar, 8);
                    }
                    absText(canvas, "Słownie: " + Slownie.slownie(String.valueOf(wynik)), (int) (pobrane.getLewy() / dzielnik), wymiar - 20, 8);
                    break;
                case "akordeon:formwzor:podpis":
                    //Dane do modulu platnosc
                    wymiar = (int) gora - 205;
                    pobrane = zwrocPolozenieElementu(skladnikifaktury, "podpis");
                    String podpis = selected.getPodpis() == null ? "" : selected.getPodpis();
                    absText(canvas, podpis, (int) (pobrane.getLewy() / dzielnik), wymiar - 25, 8);
                    absText(canvas, "..........................................", (int) (pobrane.getLewy() / dzielnik), wymiar - 20, 8);
                    absText(canvas, "wystawca faktury", (int) (pobrane.getLewy() / dzielnik) + 15, wymiar - 40, 8);
                    break;
                default:
                    break;
            }
        }
    }

    private static Pozycjenafakturze zwrocPolozenieElementu(List<Pozycjenafakturze> lista, String data) {
        for (Pozycjenafakturze p : lista) {
            if (p.getPozycjenafakturzePK().getNazwa().contains(data)) {
                return p;
            }
        }
        return null;
    }

    private static PdfPTable wygenerujtablice(boolean korekta, List<Pozycjenafakturzebazadanych> poz, Faktura selected) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        PdfPTable table = new PdfPTable(11);
        table.setTotalWidth(new float[]{20, 180, 40, 40, 40, 50, 60, 50, 60, 60, 30});
        // set the total width of the table
        table.setTotalWidth(540);
        if (selected.getPozycjepokorekcie() != null) {
            if (korekta) {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("po korekcie", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
            } else {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("przed korektą", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
            }
        }
        table.addCell(ustawfrazeAlign(B.b("lp"), "center", 8));
        String opis = selected.getNazwa() != null ? selected.getNazwa() : B.b("opis");
        table.addCell(ustawfrazeAlign(opis, "center", 8));
        table.addCell(ustawfrazeAlign(B.b("pkwiu"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("ilosc"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("jednostkamiary"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("cenanetto"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("wartoscnetto"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("stawkavat"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("kwotavat"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("wartoscbrutto"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("uwagi"), "center", 8));
        if (selected.getPozycjepokorekcie() != null) {
            table.setHeaderRows(2);
        } else {
            table.setHeaderRows(1);
        }
        int lp = 1;
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 8));
            table.addCell(ustawfrazeAlign(pozycje.getPKWiU(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCena())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 8));
            if (pozycje.getPodatek() == -1) {
                table.addCell(ustawfrazeAlign(B.b("niepodlega"), "center", 8));
                table.addCell(ustawfrazeAlign("-", "right", 8));
            } else {
                table.addCell(ustawfrazeAlign(String.valueOf((int) pozycje.getPodatek()) + "%", "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getPodatekkwota())), "right", 8));
            }
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getBrutto())), "right", 8));
            table.addCell(ustawfrazeAlign("", "center", 8));
        }
        if (korekta) {
            table.addCell(ustawfraze(B.b("razem"), 6, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk())), "right", 8));
            table.addCell(ustawfrazeAlign("*", "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVatpk())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk() + selected.getVatpk())), "right", 8));
            table.addCell(ustawfrazeAlign("", "center", 8));
        } else {
            table.addCell(ustawfraze(B.b("razem"), 6, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 8));
            table.addCell(ustawfrazeAlign("*", "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVat())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto() + selected.getVat())), "right", 8));
            table.addCell(ustawfrazeAlign("", "center", 8));
        }
        table.addCell(ustawfraze(B.b("wgstawekvat"), 6, 0));
        List<EVatwpis> ewidencja = null;
        if (korekta) {
            ewidencja = selected.getEwidencjavatpk();
        } else {
            ewidencja = selected.getEwidencjavat();
        }
        int ilerow = 0;
        if (ewidencja != null) {
            for (EVatwpis p : ewidencja) {
                if (ilerow > 0) {
                    table.addCell(ustawfraze(" ", 6, 0));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
                if (p.getEstawka().equals("-1.0")) {
                    table.addCell(ustawfrazeAlign(B.b("niepodlega"), "center", 8));
                    table.addCell(ustawfrazeAlign("-", "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 8));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto() + p.getVat())), "right", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                ilerow++;
            }
            
        }
        if (korekta) {
            wierszroznicy(selected, table);
        }
        // complete the table
        table.completeRow();
        return table;
    }
    
    private static PdfPTable wygenerujtablicevatmarza(boolean korekta, List<Pozycjenafakturzebazadanych> poz, Faktura selected) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        PdfPTable table = new PdfPTable(7);
        table.setTotalWidth(new float[]{20, 180, 40, 40, 40, 50, 60});
        // set the total width of the table
        table.setTotalWidth(450);
        if (selected.getPozycjepokorekcie() != null) {
            if (korekta) {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("po korekcie", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
            } else {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("przed korektą", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
            }
        }
        table.addCell(ustawfrazeAlign(B.b("lp"), "center", 8));
        String opis = selected.getNazwa() != null ? selected.getNazwa() : B.b("opis");
        table.addCell(ustawfrazeAlign(opis, "center", 8));
        table.addCell(ustawfrazeAlign(B.b("pkwiu"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("ilosc"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("jednostkamiary"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("cenabrutto"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("wartoscbrutto"), "center", 8));
        if (selected.getPozycjepokorekcie() != null) {
            table.setHeaderRows(2);
        } else {
            table.setHeaderRows(1);
        }
        int lp = 1;
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 8));
            table.addCell(ustawfrazeAlign(pozycje.getPKWiU(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCena())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 8));
        }
        if (korekta) {
            table.addCell(ustawfraze(B.b("razem"), 6, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk())), "right", 8));
        } else {
            table.addCell(ustawfraze(B.b("razem"), 6, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 8));
        }
        if (korekta) {
            wierszroznicyvatmarza(selected, table);
        }
        // complete the table
        table.completeRow();
        return table;
    }
    
    private static PdfPTable wygenerujtabliceNiemiecka13b(boolean korekta, List<Pozycjenafakturzebazadanych> poz, Faktura selected) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        PdfPTable table = new PdfPTable(6);
        table.setTotalWidth(new float[]{20, 180, 40, 40, 50, 60});
        // set the total width of the table
        table.setTotalWidth(450);
        if (selected.getPozycjepokorekcie() != null) {
            if (korekta) {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("po korekcie", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));

            } else {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("przed korektą", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("", "center", 8));
            }
        }
        table.addCell(ustawfrazeAlign(B.b("lp"), "center", 8));
        String opis = selected.getNazwa() != null ? selected.getNazwa() : B.b("opis");
        table.addCell(ustawfrazeAlign(opis, "center", 8));
        table.addCell(ustawfrazeAlign(B.b("ilosc"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("jednostkamiary"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("cenanetto"), "center", 8));
        table.addCell(ustawfrazeAlign(B.b("wartoscnetto"), "center", 8));
        if (selected.getPozycjepokorekcie() != null) {
            table.setHeaderRows(2);
        } else {
            table.setHeaderRows(1);
        }
        int lp = 1;
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(Z.z(pozycje.getNetto()/pozycje.getIlosc()))), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 8));
        }
        if (korekta) {
            table.addCell(ustawfraze(B.b("razem"), 5, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk())), "right", 8));
        } else {
            table.addCell(ustawfraze(B.b("razem"), 5, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 8));
        }
        List<EVatwpis> ewidencja = null;
        if (korekta) {
            ewidencja = selected.getEwidencjavatpk();
        } else {
            ewidencja = selected.getEwidencjavat();
        }
        int ilerow = 0;
        if (ewidencja != null) {
            for (EVatwpis p : ewidencja) {
                if (ilerow > 0) {
                    table.addCell(ustawfraze(" ", 6, 0));
                }
                if (p.getEstawka().equals("-1.0")) {
                    table.addCell(ustawfraze(" ", 6, 0));
                } else {
                    table.addCell(ustawfrazeAF(B.b("kwotavatwgstawek"), 4, 0, Element.ALIGN_RIGHT, 8));
                    table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 8));
                }
                ilerow++;
            }
            table.addCell(ustawfrazeAF(B.b("wartoscbrutto"), 4, 0, Element.ALIGN_RIGHT, 8));
            table.addCell(ustawfrazeAlign("*", "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBrutto())), "right", 8));
        }
        if (korekta) {
            wierszroznicy(selected, table);
        }
        // complete the table
        table.completeRow();
        return table;
    }

    private static PdfPTable wygenerujtablicexxl(boolean korekta, List<Pozycjenafakturzebazadanych> poz, Faktura selected, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO, WpisView wpisView, boolean maladuza) throws DocumentException, IOException {
        FakturaXXLKolumna fakturaXXLKolumna = pobierzfakturaxxlkolumna(fakturaXXLKolumnaDAO, wpisView);
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        PdfPTable table = ustawTable(fakturaXXLKolumna, maladuza);
        int wielkoscXXL = oblicziloscXXLkolumn(fakturaXXLKolumna) + 4;
        if (selected.getPozycjepokorekcie() != null) {
            if (korekta) {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("po korekcie", "center", 8));
                for (int i = 0; i < wielkoscXXL; i++) {
                    table.addCell(ustawfrazeAlign("", "center", 8));
                }
            } else {
                table.addCell(ustawfrazeAlign("", "center", 8));
                table.addCell(ustawfrazeAlign("przed korektą", "center", 8));
                for (int i = 0; i < wielkoscXXL; i++) {
                    table.addCell(ustawfrazeAlign("", "center", 8));
                }
            }
        }
        table.addCell(ustawfrazeAlign("lp", "center", 7));
        String opis = selected.getNazwa() != null ? selected.getNazwa() : "opis";
        table.addCell(ustawfrazeAlign(opis, "center", 7));
        if (fakturaXXLKolumna.isPkwiu()) {
            table.addCell(ustawfrazeAlign("PKWiU", "center", 7));
        }
        if (fakturaXXLKolumna.isIlosc()) {
            table.addCell(ustawfrazeAlign("ilość", "center", 7));
        }
        if (fakturaXXLKolumna.isJednostka()) {
            table.addCell(ustawfrazeAlign("jedn.m.", "center", 7));
        }
        if (fakturaXXLKolumna.isCena()) {
            table.addCell(ustawfrazeAlign("cena jedn.", "center", 7));
        }
        if (!fakturaXXLKolumna.getNettoopis0().equals("")) {
            table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis0(), "center", 7));
        }
        if (!fakturaXXLKolumna.getNettoopis1().equals("")) {
            table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis1(), "center", 7));
        }
        if (!fakturaXXLKolumna.getNettoopis2().equals("")) {
            table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis2(), "center", 7));
        }
        if (!fakturaXXLKolumna.getNettoopis3().equals("")) {
            table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis3(), "center", 7));
        }
        if (!fakturaXXLKolumna.getNettoopis4().equals("")) {
            table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis4(), "center", 7));
        }
        if (!fakturaXXLKolumna.getNettoopis5().equals("")) {
            table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis5(), "center", 7));
        }
        table.addCell(ustawfrazeAlign("wartość netto", "center", 7));
        table.addCell(ustawfrazeAlign("stawka vat", "center", 7));
        table.addCell(ustawfrazeAlign("kwota vat", "center", 7));
        table.addCell(ustawfrazeAlign("wartość brutto", "center", 7));
        if (selected.getPozycjepokorekcie() != null) {
            table.setHeaderRows(2);
        } else {
            table.setHeaderRows(1);
        }
        int lp = 1;
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 7));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 7));
            if (fakturaXXLKolumna.isPkwiu()) {
                table.addCell(ustawfrazeAlign(pozycje.getPKWiU(), "center", 7));
            }
            if (fakturaXXLKolumna.isIlosc()) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 7));
            }
            if (fakturaXXLKolumna.isJednostka()) {
                table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 7));
            }
            if (fakturaXXLKolumna.isCena()) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCena() == 0.0 ? "" : formatter.format(pozycje.getCena())), "right", 7));
            }
            if (!fakturaXXLKolumna.getNettoopis0().equals("")) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCenajedn0() == 0.0 ? "" : formatter.format(pozycje.getCenajedn0())), "right", 7));
            }
            if (!fakturaXXLKolumna.getNettoopis1().equals("")) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCenajedn1() == 0.0 ? "" : formatter.format(pozycje.getCenajedn1())), "right", 7));
            }
            if (!fakturaXXLKolumna.getNettoopis2().equals("")) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCenajedn2() == 0.0 ? "" : formatter.format(pozycje.getCenajedn2())), "right", 7));
            }
            if (!fakturaXXLKolumna.getNettoopis3().equals("")) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCenajedn3() == 0.0 ? "" : formatter.format(pozycje.getCenajedn3())), "right", 7));
            }
            if (!fakturaXXLKolumna.getNettoopis4().equals("")) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCenajedn4() == 0.0 ? "" : formatter.format(pozycje.getCenajedn4())), "right", 7));
            }
            if (!fakturaXXLKolumna.getNettoopis5().equals("")) {
                table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getCenajedn5() == 0.0 ? "" : formatter.format(pozycje.getCenajedn5())), "right", 7));
            }
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf((int) pozycje.getPodatek()) + "%", "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getPodatekkwota())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getBrutto())), "right", 7));
        }
        int l = 2 + oblicziloscXXLkolumn(fakturaXXLKolumna);
        if (korekta) {
            table.addCell(ustawfraze("Razem", l, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk())), "right", 7));
            table.addCell(ustawfrazeAlign("*", "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVatpk())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk() + selected.getVatpk())), "right", 7));
        } else {
            table.addCell(ustawfraze("Razem", l, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 7));
            table.addCell(ustawfrazeAlign("*", "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVat())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto() + selected.getVat())), "right", 7));
        }
        table.addCell(ustawfraze("w tym wg stawek vat", l, 0));
        List<EVatwpis> ewidencja = null;
        if (korekta) {
            ewidencja = selected.getEwidencjavatpk();
        } else {
            ewidencja = selected.getEwidencjavat();
        }
        int ilerow = 0;
        if (ewidencja != null) {
            for (EVatwpis p : ewidencja) {
                if (ilerow > 0) {
                    table.addCell(ustawfraze(" ", l, 0));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto() + p.getVat())), "right", 7));
                ilerow++;
            }
        }
        if (korekta) {
            wierszroznicyxxl(selected, table, wielkoscXXL - 4);
        }
        // complete the table
        table.completeRow();
        return table;
    }

    private static PdfPTable ustawTable(FakturaXXLKolumna fakturaXXLKolumna, boolean maladuza) {
        try {
            int wielkoscXXL = oblicziloscXXLkolumn(fakturaXXLKolumna);
            PdfPTable table = new PdfPTable(6 + wielkoscXXL);
            List<Float> szerokosci = new ArrayList<>();
            setszerokosci(fakturaXXLKolumna, szerokosci, maladuza);
            float[] floatArray = ArrayUtils.toPrimitive(szerokosci.toArray(new Float[0]), 0.0F);
            table.setTotalWidth(floatArray);
            if (maladuza == false) {
                table.setTotalWidth(550);
            }
            table.setWidthPercentage(95);
            return table;
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    private static void setszerokosci(FakturaXXLKolumna fakturaXXLKolumna, List<Float> szerokosci, boolean maladuza) {
        if (maladuza == false) {
            szerokosci.add(15f);
            szerokosci.add(75f);
            dodajSzerokosciMala(fakturaXXLKolumna, szerokosci);
            szerokosci.add(45f);
            szerokosci.add(20f);
            szerokosci.add(45f);
            szerokosci.add(45f);
        } else {
            szerokosci.add(30f);
            szerokosci.add(150f);
            dodajSzerokosciDuza(fakturaXXLKolumna, szerokosci);
            szerokosci.add(90f);
            szerokosci.add(40f);
            szerokosci.add(90f);
            szerokosci.add(90f);
        }
    }

    private static int oblicziloscXXLkolumn(FakturaXXLKolumna fakturaXXLKolumna) {
        int liczba = 0;
        if (fakturaXXLKolumna.isCena() == true) {
            liczba++;
        }
        if (fakturaXXLKolumna.isIlosc() == true) {
            liczba++;
        }
        if (fakturaXXLKolumna.isJednostka() == true) {
            liczba++;
        }
        if (fakturaXXLKolumna.isPkwiu() == true) {
            liczba++;
        }
        if (!fakturaXXLKolumna.getNettoopis0().equals("")) {
            liczba++;
        }
        if (!fakturaXXLKolumna.getNettoopis1().equals("")) {
            liczba++;
        }
        if (!fakturaXXLKolumna.getNettoopis2().equals("")) {
            liczba++;
        }
        if (!fakturaXXLKolumna.getNettoopis3().equals("")) {
            liczba++;
        }
        if (!fakturaXXLKolumna.getNettoopis4().equals("")) {
            liczba++;
        }
        if (!fakturaXXLKolumna.getNettoopis5().equals("")) {
            liczba++;
        }
        return liczba;
    }

    private static void dodajSzerokosciMala(FakturaXXLKolumna fakturaXXLKolumna, List<Float> szerokosci) {
        if (fakturaXXLKolumna.isPkwiu() == true) {
            szerokosci.add(25f);
        }
        if (fakturaXXLKolumna.isIlosc() == true) {
            szerokosci.add(30f);
        }
        if (fakturaXXLKolumna.isJednostka() == true) {
            szerokosci.add(15f);
        }
        if (fakturaXXLKolumna.isCena() == true) {
            szerokosci.add(25f);
        }
        if (!fakturaXXLKolumna.getNettoopis0().equals("")) {
            szerokosci.add(32f);
        }
        if (!fakturaXXLKolumna.getNettoopis1().equals("")) {
            szerokosci.add(32f);
        }
        if (!fakturaXXLKolumna.getNettoopis2().equals("")) {
            szerokosci.add(32f);
        }
        if (!fakturaXXLKolumna.getNettoopis3().equals("")) {
            szerokosci.add(32f);
        }
        if (!fakturaXXLKolumna.getNettoopis4().equals("")) {
            szerokosci.add(32f);
        }
        if (!fakturaXXLKolumna.getNettoopis5().equals("")) {
            szerokosci.add(32f);
        }
    }

    private static void dodajSzerokosciDuza(FakturaXXLKolumna fakturaXXLKolumna, List<Float> szerokosci) {
        if (fakturaXXLKolumna.isPkwiu() == true) {
            szerokosci.add(50f);
        }
        if (fakturaXXLKolumna.isIlosc() == true) {
            szerokosci.add(40f);
        }
        if (fakturaXXLKolumna.isJednostka() == true) {
            szerokosci.add(35f);
        }
        if (fakturaXXLKolumna.isCena() == true) {
            szerokosci.add(50f);
        }
        if (!fakturaXXLKolumna.getNettoopis0().equals("")) {
            szerokosci.add(65f);
        }
        if (!fakturaXXLKolumna.getNettoopis1().equals("")) {
            szerokosci.add(65f);
        }
        if (!fakturaXXLKolumna.getNettoopis2().equals("")) {
            szerokosci.add(65f);
        }
        if (!fakturaXXLKolumna.getNettoopis3().equals("")) {
            szerokosci.add(65f);
        }
        if (!fakturaXXLKolumna.getNettoopis4().equals("")) {
            szerokosci.add(65f);
        }
        if (!fakturaXXLKolumna.getNettoopis5().equals("")) {
            szerokosci.add(65f);
        }
    }
    
    private static void wierszroznicyvatmarza(Faktura selected, PdfPTable table) throws DocumentException, IOException {
        table.addCell(ustawfraze("Różnica", 6, 0));
        table.addCell(ustawfrazeAlign(String.valueOf(F.number(selected.getNettopk() - selected.getNetto())), "right", 8));
    }

    private static void wierszroznicy(Faktura selected, PdfPTable table) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        table.addCell(ustawfraze("Różnica", 6, 0));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk() - selected.getNetto())), "right", 8));
        table.addCell(ustawfrazeAlign("*", "center", 8));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVatpk() - selected.getVat())), "right", 8));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBruttopk() - selected.getBrutto())), "right", 8));
        table.addCell(ustawfrazeAlign("", "center", 8));
        table.addCell(ustawfraze("w tym wg stawek vat", 6, 0));
        List<EVatwpis> ewidencja = selected.getEwidencjavat();
        List<EVatwpis> ewidencjapk = selected.getEwidencjavatpk();
        int ilerow = 0;
        if (ewidencja != null) {
            for (int i = 0; i < ewidencja.size(); i++) {
                if (ilerow > 0) {
                    table.addCell(ustawfraze(" ", 6, 0));
                }
                EVatwpis ewpier = ewidencja.get(i);
                double nettoPK = 0.0;
                double vatPK = 0.0;
                double bruttoPK = 0.0;
                for (EVatwpis l : ewidencjapk) {
                    int rozmiarpier = ewidencjapk.size();
                    if (l.getEwidencja().equals(ewpier.getEwidencja())) {
                        nettoPK = l.getNetto();
                        vatPK = l.getVat();
                        bruttoPK = Z.z(nettoPK + vatPK);
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(nettoPK - ewpier.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(ewpier.getEstawka())) + "%", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(vatPK - ewpier.getVat())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(bruttoPK - (ewpier.getNetto() + ewpier.getVat()))), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    } else if (!l.getEwidencja().equals(ewpier.getEwidencja()) && rozmiarpier == 1) {
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(-ewpier.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(ewpier.getEstawka())) + "%", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(-ewpier.getVat())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(-(ewpier.getNetto() + ewpier.getVat()))), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    rozmiarpier--;
                }
                ilerow++;
            }
        }
    }

    private static void wierszroznicyxxl(Faktura selected, PdfPTable table, int wielkoscXXL) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        table.addCell(ustawfraze("Różnica", 2 + wielkoscXXL, 0));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNettopk() - selected.getNetto())), "right", 7));
        table.addCell(ustawfrazeAlign("*", "center", 7));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVatpk() - selected.getVat())), "right", 7));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBruttopk() - selected.getBrutto())), "right", 7));
        table.addCell(ustawfraze("w tym wg stawek vat", 2 + wielkoscXXL, 0));
        List<EVatwpis> ewidencja = selected.getEwidencjavat();
        List<EVatwpis> ewidencjapk = selected.getEwidencjavatpk();
        if (ewidencja != null) {
            for (int i = 0; i < ewidencja.size(); i++) {
                if (i > 0) {
                    table.addCell(ustawfraze(" ", 2 + wielkoscXXL, 0));
                }
//                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(ewidencjapk.get(i).getNetto()-ewidencja.get(i).getNetto())), "right", 7));
//                table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(ewidencja.get(i).getEstawka())) + "%", "center", 7));
//                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(ewidencjapk.get(i).getVat()-ewidencja.get(i).getVat())), "right", 7));
//                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format((ewidencjapk.get(i).getNetto() + ewidencjapk.get(i).getVat())-(ewidencja.get(i).getNetto() + ewidencja.get(i).getVat()))), "right", 7));
                EVatwpis ewpier = ewidencja.get(i);
                double nettoPK = 0.0;
                double vatPK = 0.0;
                double bruttoPK = 0.0;
                for (EVatwpis l : ewidencjapk) {
                    int rozmiarpier = ewidencjapk.size();
                    if (l.getEwidencja().equals(ewpier.getEwidencja())) {
                        nettoPK = l.getNetto();
                        vatPK = l.getVat();
                        bruttoPK = Z.z(nettoPK + vatPK);
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(nettoPK - ewpier.getNetto())), "right", 7));
                        table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(ewpier.getEstawka())) + "%", "center", 7));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(vatPK - ewpier.getVat())), "right", 7));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(bruttoPK - (ewpier.getNetto() + ewpier.getVat()))), "right", 7));

                    } else if (!l.getEwidencja().equals(ewpier.getEwidencja()) && rozmiarpier == 1) {
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(-ewpier.getNetto())), "right", 7));
                        table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(ewpier.getEstawka())) + "%", "center", 7));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(-ewpier.getVat())), "right", 7));
                        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(-(ewpier.getNetto() + ewpier.getVat()))), "right", 7));

                    }
                    rozmiarpier--;
                }
            }
        }
    }

    private static FakturaXXLKolumna pobierzfakturaxxlkolumna(FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO, WpisView wpisView) {
        FakturaXXLKolumna f = null;
        try {
            f = fakturaXXLKolumnaDAO.findXXLByPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) {

        }
        if (f == null) {
            f = new FakturaXXLKolumna();
            f.setNettoopis0("cena jedn.netto");
            f.setNettoopis1("cena jedn.netto");
            f.setNettoopis2("cena jedn.netto");
            f.setNettoopis3("cena jedn.netto");
            f.setNettoopis4("cena jedn.netto");
            f.setNettoopis5("cena jedn.netto");
        }
        return f;
    }

    private static String przerobkwote(double kwota) {
        String tmp = String.valueOf(kwota);
        String poczatek = null;
        String koniec = null;
        if (tmp.contains(".")) {
            String[] tab = tmp.split("\\.");
            poczatek = tab[0];
            koniec = tab[1];
        } else {
            poczatek = tmp;
            koniec = "00";
        }
        int dlugosc = poczatek.length();
        String czesc1 = null;
        String czesc2 = null;
        String czesc3 = null;
        switch (dlugosc) {
            case 1:
                czesc3 = poczatek;
                break;
            case 2:
                czesc3 = poczatek;
                break;
            case 3:
                czesc3 = poczatek;
                break;
            case 4:
                czesc2 = poczatek.substring(0, 1);
                czesc3 = poczatek.substring(1, 4);
                break;
            case 5:
                czesc2 = poczatek.substring(0, 2);
                czesc3 = poczatek.substring(2, 5);
                break;
            case 6:
                czesc2 = poczatek.substring(0, 3);
                czesc3 = poczatek.substring(3, 6);
                break;
            case 7:
                czesc1 = poczatek.substring(0, 1);
                czesc2 = poczatek.substring(1, 4);
                czesc3 = poczatek.substring(4, 7);
                break;
        }
        if (czesc1 != null) {
            poczatek = czesc1 + " " + czesc2 + " " + czesc3;
        } else if (czesc2 != null) {
            poczatek = czesc2 + " " + czesc3;
        } else {
            poczatek = czesc3;
        }
        if (koniec.equals("0")) {
            koniec = "00";
        } else if (koniec.length() == 1) {
            koniec = koniec.concat("0");
        }
        return poczatek + "," + koniec;

    }

    public static int obliczwierszewtabelach(Faktura selected) {
        int iloscwierszy = 0;
        if (selected.getPozycjenafakturze() != null) {
            iloscwierszy += selected.getPozycjenafakturze().size();
        }
        if (selected.getPozycjepokorekcie() != null) {
            iloscwierszy += selected.getPozycjepokorekcie().size();
        }
        return iloscwierszy;
    }

    public static void usunplik(String nazwa) {
        try {
            File file = new File(nazwa);
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {

        }
    }
    
    public static void stopkaniemiecka(PdfWriter writer, Document document, FakturaStopkaNiemiecka f) {
        PdfPTable table = null;
        try {
            table = new PdfPTable(4);
            table.setTotalWidth(new float[]{165,120,120,165});
            table.setWidthPercentage(95);
            table.getDefaultCell().setBorderColor(BaseColor.WHITE);
            table.addCell(frazaNoBorderkolor(f.getNazwafirmy()));
            table.addCell(frazaNoBorderkolor("Mobil: "+f.getKomorka()));
            table.addCell(frazaNoBorderkolor("Amstgericht "+f.getKrs()));
            table.addCell(frazaNoBorderkolor(f.getBank()));
            table.addCell(frazaNoBorderkolor("Geschäftsführer "+f.getPrezes()));
            table.addCell(frazaNoBorderkolor("Büro: "+f.getTelefon()));
            table.addCell(frazaNoBorderkolor("HRB "+f.getKrs()));
            table.addCell(frazaNoBorderkolor("IBAN "+f.getIban()));
            table.addCell(frazaNoBorderkolor(f.getUlica()));
            table.addCell(frazaNoBorderkolor(""));
            table.addCell(frazaNoBorderkolor("Finanzamt "+f.getUrzadskarbowy()));
            table.addCell(frazaNoBorderkolor("BIC "+f.getBic()));
            table.addCell(frazaNoBorderkolor(f.getMiejscowosc()));
            table.addCell(frazaNoBorderkolor("EMail "+f.getEmail()));
            table.addCell(frazaNoBorderkolor("Steuernummer "+f.getNip()));
            table.addCell(frazaNoBorderkolor("BLZ "+f.getBlz()+" | KTO-NR "+f.getKtonr()));
            table.completeRow();
            table.writeSelectedRows(0, -1,
            document.left(document.leftMargin()+15f),
            table.getTotalHeight() + document.bottom(document.bottomMargin()-15f), 
            writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFaktura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPCell frazaNoBorderkolor(String f) {
        PdfPCell cell = ustawfrazeAlignNOBorder(f, "left", 8);
        //cell.setBackgroundColor(new BaseColor(170, 170, 170, 90));
        return cell;
    }

    public static void sprzedazsamochoduimportowanego(PdfWriter writer, Document document, Faktura selected) {
         PdfPTable table = null;
        try {
            table = new PdfPTable(2);
            table.setTotalWidth(new float[]{400,140});
            StringBuilder nb = new StringBuilder();
            nb.append("Sprzedawca oświadcza, że jestem bezpośredniom importerem pojazdu marki: ");
            nb.append(selected.getMarkapojazdu());
            nb.append(", o numerze nadwozia (VIN) ");
            nb.append(selected.getVIN());
            nb.append(", który zakupiłem w roku ");
            nb.append(selected.getDatazakupusamochodu());
            table.addCell(ustawfrazeAlign(nb.toString(), "left",8, 30f));
            table.addCell(ustawfrazeAlign(".............................","center",9));
            nb = new StringBuilder();
            if (selected.isSamochodbeztablic()) {
                nb.append("Sprzedawca oświadcza, że pojazd marki ");
                nb.append(selected.getMarkapojazdu());
                nb.append(", o numerze nadwozia (VIN) ");
                nb.append(selected.getVIN());
                nb.append(" został sprowadzony bez tablic rejestracyjnych ");
            } else {
                nb.append("Sprzedawca oświadcza, że tablice rejestracyjne pojazdu marki ");
                nb.append(selected.getMarkapojazdu());
                nb.append(", o numerze nadwozia (VIN) ");
                nb.append(selected.getVIN());
                nb.append(" zostały zwrócone do organu rejestrującego państwa, z którego pojazd został sprowadzony");
            }
            table.addCell(ustawfrazeAlign(nb.toString(), "left",8, 30f));
            table.addCell(ustawfrazeAlign(".............................","center",9));
            table.completeRow();
            table.writeSelectedRows(0, -1,
            document.left(document.leftMargin()+25f),
            table.getTotalHeight() + document.bottom(document.bottomMargin()-25f), 
            writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFaktura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
