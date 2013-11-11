/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waluty;

import entityfk.Tabelanbp;
import entityfk.TabelanbpPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Osito
 */
public class WalutyParseHandler extends DefaultHandler implements Serializable {

    private static List<Tabelanbp> elementy;
    private Tabelanbp wiersztabeli;
    private static String numer_tabeli;
    private static String data_publikacji;
    private String startelement;
    
    boolean bnumer_tabeli;
    boolean bdata_publikacji;

    public WalutyParseHandler() {
        elementy = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("numer_tabeli")) {
            bnumer_tabeli = true;
        }
        if (qName.equals("data_publikacji")) {
            bdata_publikacji = true;
        }
        if (qName.equals("pozycja")) {
            wiersztabeli = new Tabelanbp();
            TabelanbpPK tabelanbpPK = new TabelanbpPK();
            tabelanbpPK.setNrtabeli(numer_tabeli);
            wiersztabeli.setTabelanbpPK(tabelanbpPK);
            wiersztabeli.setDatatabeli(data_publikacji);
        }
        startelement = qName;
        System.out.print("Start Element :" + qName + " ");
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        String qName = new String(ch, start, length);
        if (bnumer_tabeli) {
            bnumer_tabeli = false;
            numer_tabeli = qName;
        }
        if (bdata_publikacji) {
            bdata_publikacji = false;
            data_publikacji = qName;
        }
        if (startelement.equals("kod_waluty")) {
            wiersztabeli.getTabelanbpPK().setSymbolwaluty(qName);
            startelement = "";
        }
        if (startelement.equals("kurs_sredni")) {
            String replace = qName.replace(",",".");
            wiersztabeli.setKurssredni(Double.parseDouble(replace));
            startelement = "";
        }
        System.out.println(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName,
            String qName)
            throws SAXException {
        if (qName.equals("pozycja")) {
            elementy.add(wiersztabeli);
        }
        System.out.println(" End Element :" + qName);
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public static List<Tabelanbp> getElementy() {
        return elementy;
    }

    public static void setElementy(List<Tabelanbp> elementy) {
        WalutyParseHandler.elementy = elementy;
    }
    //</editor-fold>
}
