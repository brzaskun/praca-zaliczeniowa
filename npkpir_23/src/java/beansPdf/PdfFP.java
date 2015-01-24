/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.FakturaXXLKolumnaDAO;
import dao.FakturaelementygraficzneDAO;
import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.FakturaXXLKolumna;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import entity.Pozycjenafakturze;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.inject.Named;
import slownie.Slownie;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PdfFP {

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

    public static void dolaczpozycjedofaktury(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiaryGora, List<Pozycjenafakturze> lista, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : lista) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:data":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "data");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:data") - 5, 140, 15);
                    absText(writer, selected.getMiejscewystawienia() + " dnia: " + selected.getDatawystawienia(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:data"), 8);
                    break;
                case "akordeon:formwzor:datasprzedazy":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "datasprzedazy");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:datasprzedazy") - 5, 110, 15);
                    absText(writer, "Data sprzedaży: " + selected.getDatasprzedazy(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:datasprzedazy"), 8);
                    break;
                case "akordeon:formwzor:fakturanumer":
                    //Dane do modulu fakturanumer
                    pobrane = zwrocpozycje(lista, "fakturanumer");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:fakturanumer") - 5, 190, 20);
                    absText(writer, "Faktura nr " + selected.getFakturaPK().getNumerkolejny(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:fakturanumer"), 10);
                    break;
                case "akordeon:formwzor:wystawca":
                    //Dane do modulu sprzedawca
                    pobrane = zwrocpozycje(lista, "wystawca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:wystawca") - 65, 250, 80);
                    absText(writer, "Sprzedawca:", (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca"), 10);
                    absText(writer, selected.getWystawca().getNazwadlafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca") - 20, 8);
                    adres = selected.getWystawca().getAdresdlafaktury();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca") - 40, 8);
                    absText(writer, "NIP: " + selected.getWystawca().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wystawca") - 60, 8);
                    break;
                case "akordeon:formwzor:odbiorca":
                    //Dane do modulu odbiorca
                    pobrane = zwrocpozycje(lista, "odbiorca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:odbiorca") - 65, 250, 80);
                    absText(writer, "Nabywca:", (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca"), 10);
                    absText(writer, selected.getKontrahent().getNpelna(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca") - 20, 8);
                    adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca") - 40, 8);
                    absText(writer, "NIP: " + selected.getKontrahent().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:odbiorca") - 60, 8);
                    break;
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocpozycje(lista, "towary");
                    PdfPTable table = null;
                    PdfPTable tablekorekta = null;
                    if (selected.getPozycjepokorekcie() != null) {
                        table = wygenerujtablice(selected.getPozycjenafakturze(), selected);
                    }
                    if (selected.isFakturaxxl()) {
                        table = wygenerujtablicexxl(selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView);
                    } else {
                        table = wygenerujtablice(selected.getPozycjenafakturze(), selected);
                    }
                    // write the table to an absolute position
                    table.writeSelectedRows(0, table.getRows().size(), (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:towary"), writer.getDirectContent());
                    break;
                case "akordeon:formwzor:logo":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("logo", elementydod)) {
                        pobrane = zwrocpozycje(lista, "logo");
                        Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
                        String nazwaplikuzbazy = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/" + element.getFakturaelementygraficznePK().getNazwaelementu();
                        Image logo = Image.getInstance(nazwaplikuzbazy);
                        // Set the position of image
                        logo.scaleToFit(90f, 90f);
                        logo.setAbsolutePosition((pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:logo") - 50); //e
                        // Add paragraph to PDF document.
                        document.add(logo);
                    }
                    break;
                case "akordeon:formwzor:nrzamowienia":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("nr zamówienia", elementydod)) {
                        pobrane = zwrocpozycje(lista, "nrzamowienia");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:nrzamowienia") - 5, 180, 15);
                        absText(writer, "nr zamówienia: " + selected.getNumerzamowienia(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:nrzamowienia"), 8);
                    }
                    break;
                case "akordeon:formwzor:przewłaszczenie":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("przewłaszczenie", elementydod)) {
                        pobrane = zwrocpozycje(lista, "przewłaszczenie");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:przewłaszczenie") - 5, 230, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("przewłaszczenie", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:przewłaszczenie"), 8);
                    }
                    break;
                case "akordeon:formwzor:warunkidostawy":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("warunki dostawy", elementydod)) {
                        pobrane = zwrocpozycje(lista, "warunkidostawy");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:warunkidostawy") - 5, 360, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("warunki dostawy", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:warunkidostawy"), 8);
                    }
                    break;
                case "akordeon:formwzor:wezwaniedozapłaty":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("wezwanie do zapłaty", elementydod)) {
                        pobrane = zwrocpozycje(lista, "wezwaniedozapłaty");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:wezwaniedozapłaty") - 5, 230, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("wezwanie do zapłaty", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                    }
                    break;
                case "akordeon:formwzor:platnosc":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "platnosc");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:platnosc") - 25, 250, 35);
                    absText(writer, "Sposób zapłaty: " + selected.getSposobzaplaty(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Termin płatności: " + selected.getTerminzaplaty(), (int) (pobrane.getLewy() / dzielnik) + 100, wymiaryGora.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Nr konta bankowego: " + selected.getNrkontabankowego(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:platnosc") - 20, 8);
                    break;
                case "akordeon:formwzor:dozaplaty":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "dozaplaty");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiaryGora.get("akordeon:formwzor:dozaplaty") - 25, 350, 35);
                    absText(writer, "Do zapłaty: " + przerobkwote(selected.getBrutto()) + " " + selected.getWalutafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:dozaplaty"), 8);
                    absText(writer, "Słownie: " + Slownie.slownie(String.valueOf(selected.getBrutto())), (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:dozaplaty") - 20, 8);
                    break;
                case "akordeon:formwzor:podpis":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "podpis");
                    String podpis = selected.getPodpis() == null ? "" : selected.getPodpis();
                    absText(writer, podpis, (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:podpis"), 8);
                    absText(writer, "..........................................", (int) (pobrane.getLewy() / dzielnik), wymiaryGora.get("akordeon:formwzor:podpis") - 20, 8);
                    absText(writer, "wystawca faktury", (int) (pobrane.getLewy() / dzielnik) + 15, wymiaryGora.get("akordeon:formwzor:podpis") - 40, 8);
                    break;
            }
        }
    }
    
    public static void dolaczpozycjedofakturydlugacz1(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> lista, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : lista) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:data":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "data");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:data") - 5, 140, 15);
                    absText(writer, selected.getMiejscewystawienia() + " dnia: " + selected.getDatawystawienia(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:data"), 8);
                    break;
                case "akordeon:formwzor:datasprzedazy":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "datasprzedazy");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:datasprzedazy") - 5, 110, 15);
                    absText(writer, "Data sprzedaży: " + selected.getDatasprzedazy(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:datasprzedazy"), 8);
                    break;
                case "akordeon:formwzor:fakturanumer":
                    //Dane do modulu fakturanumer
                    pobrane = zwrocpozycje(lista, "fakturanumer");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:fakturanumer") - 5, 190, 20);
                    absText(writer, "Faktura nr " + selected.getFakturaPK().getNumerkolejny(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:fakturanumer"), 10);
                    break;
                case "akordeon:formwzor:wystawca":
                    //Dane do modulu sprzedawca
                    pobrane = zwrocpozycje(lista, "wystawca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wystawca") - 65, 250, 80);
                    absText(writer, "Sprzedawca:", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca"), 10);
                    absText(writer, selected.getWystawca().getNazwadlafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 20, 8);
                    adres = selected.getWystawca().getAdresdlafaktury();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 40, 8);
                    absText(writer, "NIP: " + selected.getWystawca().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 60, 8);
                    break;
                case "akordeon:formwzor:odbiorca":
                    //Dane do modulu odbiorca
                    pobrane = zwrocpozycje(lista, "odbiorca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:odbiorca") - 65, 250, 80);
                    absText(writer, "Nabywca:", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca"), 10);
                    absText(writer, selected.getKontrahent().getNpelna(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 20, 8);
                    adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 40, 8);
                    absText(writer, "NIP: " + selected.getKontrahent().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 60, 8);
                    break;
                case "akordeon:formwzor:logo":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("logo", elementydod)) {
                        pobrane = zwrocpozycje(lista, "logo");
                        Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
                        String nazwaplikuzbazy = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/" + element.getFakturaelementygraficznePK().getNazwaelementu();
                        Image logo = Image.getInstance(nazwaplikuzbazy);
                        // Set the position of image
                        logo.scaleToFit(90f, 90f);
                        logo.setAbsolutePosition((pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:logo") - 50); //e
                        // Add paragraph to PDF document.
                        document.add(logo);
                    }
                    break;
                case "akordeon:formwzor:nrzamowienia":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("nr zamówienia", elementydod)) {
                        pobrane = zwrocpozycje(lista, "nrzamowienia");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:nrzamowienia") - 5, 180, 15);
                        absText(writer, "nr zamówienia: " + selected.getNumerzamowienia(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:nrzamowienia"), 8);
                    }
                    break;
                case "akordeon:formwzor:przewłaszczenie":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("przewłaszczenie", elementydod)) {
                        pobrane = zwrocpozycje(lista, "przewłaszczenie");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:przewłaszczenie") - 5, 230, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("przewłaszczenie", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:przewłaszczenie"), 8);
                    }
                    break;
                case "akordeon:formwzor:warunkidostawy":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("warunki dostawy", elementydod)) {
                        pobrane = zwrocpozycje(lista, "warunkidostawy");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:warunkidostawy") - 5, 360, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("warunki dostawy", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:warunkidostawy"), 8);
                    }
                    break;
                case "akordeon:formwzor:wezwaniedozapłaty":
                    //Dane do modulu przewłaszczenie
                    if (PdfFP.czydodatkowyelementjestAktywny("wezwanie do zapłaty", elementydod)) {
                        pobrane = zwrocpozycje(lista, "wezwaniedozapłaty");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wezwaniedozapłaty") - 5, 230, 15);
                        absText(writer, PdfFP.pobierzelementdodatkowy("wezwanie do zapłaty", elementydod), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                    }
                    break;
                default:
                    break;
               
            }
        }
    }
    
    public static PdfPTable dolaczpozycjedofakturydlugacz2(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> lista, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : lista) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:towary":
                    //Dane do tablicy z wierszami
                    pobrane = zwrocpozycje(lista, "towary");
                    return wygenerujtablicexxl(selected.getPozycjenafakturze(), selected, fakturaXXLKolumnaDAO, wpisView);
                default:
                    break;
               
            }
        }
        return null;
    }
    
    public static void dolaczpozycjedofakturydlugacz3(FakturaelementygraficzneDAO fakturaelementygraficzneDAO, PdfWriter writer, Faktura selected, Map<String, Integer> wymiary, List<Pozycjenafakturze> lista, WpisView wpisView, Document document, List<Fakturadodelementy> elementydod, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO) throws DocumentException, IOException {
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : lista) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:platnosc":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "platnosc");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:platnosc") - 25, 250, 35);
                    absText(writer, "Sposób zapłaty: " + selected.getSposobzaplaty(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Termin płatności: " + selected.getTerminzaplaty(), (int) (pobrane.getLewy() / dzielnik) + 100, wymiary.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Nr konta bankowego: " + selected.getNrkontabankowego(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:platnosc") - 20, 8);
                    break;
                case "akordeon:formwzor:dozaplaty":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "dozaplaty");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:dozaplaty") - 25, 350, 35);
                    absText(writer, "Do zapłaty: " + przerobkwote(selected.getBrutto()) + " " + selected.getWalutafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:dozaplaty"), 8);
                    absText(writer, "Słownie: " + Slownie.slownie(String.valueOf(selected.getBrutto())), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:dozaplaty") - 20, 8);
                    break;
                case "akordeon:formwzor:podpis":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "podpis");
                    String podpis = selected.getPodpis() == null ? "" : selected.getPodpis();
                    absText(writer, podpis, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:podpis"), 8);
                    absText(writer, "..........................................", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:podpis") - 20, 8);
                    absText(writer, "wystawca faktury", (int) (pobrane.getLewy() / dzielnik) + 15, wymiary.get("akordeon:formwzor:podpis") - 40, 8);
                    break;
                default:
                    break;
            }
        }
    }

    private static Pozycjenafakturze zwrocpozycje(List<Pozycjenafakturze> lista, String data) {
        for (Pozycjenafakturze p : lista) {
            if (p.getPozycjenafakturzePK().getNazwa().contains(data)) {
                return p;
            }
        }
        return null;
    }

    private static PdfPTable wygenerujtablice(List<Pozycjenafakturzebazadanych> poz, Faktura selected) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        PdfPTable table = new PdfPTable(11);
        table.setTotalWidth(new float[]{20, 100, 40, 40, 40, 50, 60, 50, 60, 60, 30});
        // set the total width of the table
        table.setTotalWidth(500);
        table.addCell(ustawfrazeAlign("lp", "center", 8));
        table.addCell(ustawfrazeAlign("opis", "center", 8));
        table.addCell(ustawfrazeAlign("PKWiU", "center", 8));
        table.addCell(ustawfrazeAlign("ilość", "center", 8));
        table.addCell(ustawfrazeAlign("jedn.m.", "center", 8));
        table.addCell(ustawfrazeAlign("cena netto", "center", 8));
        table.addCell(ustawfrazeAlign("wartość netto", "center", 8));
        table.addCell(ustawfrazeAlign("stawka vat", "center", 8));
        table.addCell(ustawfrazeAlign("kwota vat", "center", 8));
        table.addCell(ustawfrazeAlign("wartość brutto", "center", 8));
        table.addCell(ustawfrazeAlign("uwagi", "center", 8));
        table.setHeaderRows(1);
        int lp = 1;
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 8));
            table.addCell(ustawfrazeAlign(pozycje.getPKWiU(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCena())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf((int) pozycje.getPodatek()) + "%", "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getPodatekkwota())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getBrutto())), "right", 8));
            table.addCell(ustawfrazeAlign("", "center", 8));
        }
        table.addCell(ustawfraze("Razem", 6, 0));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 8));
        table.addCell(ustawfrazeAlign("*", "center", 8));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVat())), "right", 8));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBrutto())), "right", 8));
        table.completeRow();
        table.addCell(ustawfraze("w tym wg stawek vat", 6, 0));
        List<EVatwpis> ewidencja = selected.getEwidencjavat();
        int ilerow = 0;
        if (ewidencja != null) {
            for (EVatwpis p : ewidencja) {
                if (ilerow > 0) {
                    table.addCell(ustawfraze(" ", 6, 0));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto() + p.getVat())), "right", 8));
                table.completeRow();
                ilerow++;
            }
        }
        // complete the table

        return table;
    }

    private static PdfPTable wygenerujtablicexxl(List<Pozycjenafakturzebazadanych> poz, Faktura selected, FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO, WpisView wpisView) throws DocumentException, IOException {
        FakturaXXLKolumna fakturaXXLKolumna = pobierzfakturaxxlkolumna(fakturaXXLKolumnaDAO, wpisView);
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        PdfPTable table = new PdfPTable(15);
        table.setTotalWidth(new float[]{20, 150, 50, 30, 30, 70, 70, 70, 70, 70, 70, 90, 40, 90, 90});
        table.setTotalWidth(560);
        table.addCell(ustawfrazeAlign("lp", "center", 7));
        table.addCell(ustawfrazeAlign("opis", "center", 7));
        table.addCell(ustawfrazeAlign("PKWiU", "center", 7));
        table.addCell(ustawfrazeAlign("ilość", "center", 7));
        table.addCell(ustawfrazeAlign("jedn.m.", "center", 7));
        table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis0(), "center", 7));
        table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis1(), "center", 7));
        table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis2(), "center", 7));
        table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis3(), "center", 7));
        table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis4(), "center", 7));
        table.addCell(ustawfrazeAlign(fakturaXXLKolumna.getNettoopis5(), "center", 7));
        table.addCell(ustawfrazeAlign("wartość netto", "center", 7));
        table.addCell(ustawfrazeAlign("stawka vat", "center", 7));
        table.addCell(ustawfrazeAlign("kwota vat", "center", 7));
        table.addCell(ustawfrazeAlign("wartość brutto", "center", 7));
        table.setHeaderRows(1);
        int lp = 1;
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 7));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 7));
            table.addCell(ustawfrazeAlign(pozycje.getPKWiU(), "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 7));
            table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCena())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCenajedn1())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCenajedn2())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCenajedn3())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCenajedn4())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCenajedn5())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf((int) pozycje.getPodatek()) + "%", "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getPodatekkwota())), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getBrutto())), "right", 7));
        }
        table.addCell(ustawfraze("Razem", 11, 0));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 7));
        table.addCell(ustawfrazeAlign("*", "center", 7));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVat())), "right", 7));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBrutto())), "right", 7));
        table.completeRow();
        table.addCell(ustawfraze("w tym wg stawek vat", 11, 0));
        List<EVatwpis> ewidencja = selected.getEwidencjavat();
        int ilerow = 0;
        if (ewidencja != null) {
            for (EVatwpis p : ewidencja) {
                if (ilerow > 0) {
                    table.addCell(ustawfraze(" ", 10, 0));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto() + p.getVat())), "right", 8));
                table.completeRow();
                ilerow++;
            }
        }
        // complete the table

        return table;
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

}
