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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
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
    
    @Inject
    private WalutyParseHandler walutyParseHandler; 

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

    private static int skorygujNumerTabeliZmianaRoku(String przekazanadata, int numer) {
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

    private static String zmiendate(String przekazanadata) {
        DateTime staradata = new DateTime(przekazanadata);
        DateTime nowadata = staradata.plusDays(1);
        String nowydzienformat = nowadata.toString("yyyy-MM-dd");
        return nowydzienformat;
    }
    
        private static String sformatujdate(String przekazanadata) {
        DateTime data = new DateTime(przekazanadata);
        return data.toString("yyMMdd");
    }

    private static boolean czydataPrzedDniemDzisiejszym(String data) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(data);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            datao2date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(WalutyNBP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datao1date.before(datao2date);
    }
    
    public List<Tabelanbp> pobierzjedenpliknbp(String data, int numerTabeliNBP, String waluta) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, ParseException {
        List<Tabelanbp> wynik = Collections.synchronizedList(new ArrayList<>());
        InputStream inputStream = null;
        try {
            String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli(numerTabeliNBP) + "z" + sformatujdate(data) + ".xml";
            URL url = new URL(nazwapliku);
            inputStream = url.openStream();
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream, "ISO-8859-2");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                walutyParseHandler.setElementy(new ArrayList<Tabelanbp>());
                saxParser.parse(is, walutyParseHandler);
                List<Tabelanbp> wyniktmp = walutyParseHandler.getElementy();
                for (Tabelanbp p : wyniktmp) {
                    if (p.getWaluta().getSymbolwaluty().equals(waluta)) {
                        wynik.add(p);
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return wynik;
    }

    public List<Tabelanbp> pobierzpliknbp(String data, int numerTabeliNBP, String waluta) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, ParseException {
        List<Tabelanbp> wynik = Collections.synchronizedList(new ArrayList<>());
        while (czydataPrzedDniemDzisiejszym(data)) {
            numerTabeliNBP = skorygujNumerTabeliZmianaRoku(data, numerTabeliNBP);
            InputStream inputStream = null;
            while (inputStream == null && czydataPrzedDniemDzisiejszym(data)) {
                try {
                    String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli(numerTabeliNBP) + "z" + sformatujdate(data) + ".xml";
                    URL url = new URL(nazwapliku);
                    inputStream = url.openStream();
                } catch (Exception e) {
                    data = zmiendate(data);
                    numerTabeliNBP = skorygujNumerTabeliZmianaRoku(data, numerTabeliNBP);
                }
            }
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream, "ISO-8859-2");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                walutyParseHandler.setElementy(new ArrayList<Tabelanbp>());
                saxParser.parse(is, walutyParseHandler);
                List<Tabelanbp> wyniktmp = walutyParseHandler.getElementy();
                for (Tabelanbp p : wyniktmp) {
                    if (p.getWaluta().getSymbolwaluty().equals(waluta)) {
                        wynik.add(p);
                        break;
                    }
                }
                //System.out.print(wynik.toString());
                numerTabeliNBP++;
            }
        }
        return wynik;
    }

    public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        Integer numer = 252;
        String data = "2018-12-31";
        List<Tabelanbp> wynik = Collections.synchronizedList(new ArrayList<>());
        while (czydataPrzedDniemDzisiejszym(data)) {
            InputStream inputStream = null;
            while (inputStream == null && czydataPrzedDniemDzisiejszym(data)) {
                try {
                    String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli(numer) + "z" + sformatujdate(data) + ".xml";
                    URL url = new URL(nazwapliku);
                    inputStream = url.openStream();
                } catch (Exception e) {
                    data = zmiendate(data);
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
                List<Tabelanbp> wyniktmp = handler.getElementy();
                for (Tabelanbp p : wyniktmp) {
                    if (p.getWaluta().getSymbolwaluty().equals("NOK")) {
                        wynik.add(p);
                    }
                }
                numer++;
            }
        }
    }
    
//    public static void main(String[] args) {
//        DateFormat formatter;
//        formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date datao1date = null;
//        Date datao2date = null;
//        try {
//            datao1date = formatter.parse("2016-02-03");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
//            datao2date = sdf.parse(sdf.format(new Date()));
//        } catch (ParseException ex) {
//            Logger.getLogger(WalutyNBP.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
