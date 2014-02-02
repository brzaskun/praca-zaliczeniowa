/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waluty;

import entityfk.Tabelanbp;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.joda.time.DateTime;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Osito
 */
@Named
public class WalutyNBP implements Serializable {

    private static int zmianaroku = 0;

    private static String numertabeli(int numer) {
        if (zmianaroku == 1) {
            zmianaroku = 0;
            return "001";
        } else {
            Integer numertabeli = numer;
            String numertabeliStr;
            if (numertabeli.toString().length() == 1) {
                numertabeliStr = "00" + numertabeli;
            } else if (numertabeli.toString().length() == 2) {
                numertabeliStr = "0" + numertabeli;
            } else {
                numertabeliStr = numertabeli.toString();
            }
            return numertabeliStr;
        }
    }

    private static int sprawdzzmianeroku(String przekazanadata, int numer) {
        DateTime staradata = new DateTime(przekazanadata);
        int staryrok = staradata.getYear();
        DateTime nowadata = staradata.plusDays(1);
        int nowyrok = nowadata.getYear();
        if (staryrok < nowyrok) {
            zmianaroku = 1;
            return 1;
        }
        return numer;
    }

    private static String zmiendate(String przekazanadata, int ktora) {
        DateTime staradata = new DateTime(przekazanadata);
        DateTime nowadata = staradata.plusDays(1);
        String nowydzien = nowadata.toString("yyMMdd");
        String nowydzienformat = nowadata.toString("yyyy-MM-dd");
        if (ktora == 2) {
            return nowydzien;
        } else {
            return nowydzienformat;
        }
    }

    private static boolean porownajdaty(String data) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = (Date) formatter.parse(data);
            datao2date = new Date();
        } catch (ParseException ex) {
            Logger.getLogger(WalutyNBP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datao1date.before(datao2date);
    }

    public static List<Tabelanbp> pobierzpliknbp(String data, int numer, String waluta) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, ParseException {
        List<Tabelanbp> wynik = new ArrayList<>();
        numer = sprawdzzmianeroku(data, numer);
        while (porownajdaty(data)) {
            InputStream inputStream = null;
            while (inputStream == null && porownajdaty(data)) {
                try {
                    String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli(numer) + "z" + zmiendate(data, 2) + ".xml";
                    URL url = new URL(nazwapliku);
                    inputStream = url.openStream();
                } catch (Exception e) {
                    data = zmiendate(data, 1);
                }
            }
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream, "ISO-8859-2");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                WalutyParseHandler handler = new WalutyParseHandler();
                saxParser.parse(is, handler);
                List<Tabelanbp> wyniktmp = WalutyParseHandler.getElementy();
                for (Tabelanbp p : wyniktmp) {
                    if (p.getTabelanbpPK().getSymbolwaluty().equals(waluta)) {
                        wynik.add(p);
                    }
                }
                //System.out.print(wynik.toString());
                numer++;
            }
        }
        return wynik;
    }

    public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        Integer numer = 1;
        String data = "2013-11-10";
        List<Tabelanbp> wynik = new ArrayList<>();
        while (porownajdaty(data)) {
            InputStream inputStream = null;
            while (inputStream == null && porownajdaty(data)) {
                try {
                    String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli(numer) + "z" + zmiendate(data, 2) + ".xml";
                    URL url = new URL(nazwapliku);
                    inputStream = url.openStream();
                } catch (Exception e) {
                    data = zmiendate(data, 1);
                }
            }
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream, "ISO-8859-2");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                WalutyParseHandler handler = new WalutyParseHandler();
                saxParser.parse(is, handler);
                List<Tabelanbp> wyniktmp = WalutyParseHandler.getElementy();
                for (Tabelanbp p : wyniktmp) {
                    if (p.getTabelanbpPK().getSymbolwaluty().equals("EUR")) {
                        wynik.add(p);
                    }
                }
                System.out.print(wynik.toString());
                numer++;
            }
        }
    }
}
