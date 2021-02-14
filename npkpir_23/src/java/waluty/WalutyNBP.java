/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waluty;

import dao.TabelanbpDAO;
import data.Data;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.BufferedReader;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.joda.time.DateTime;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import xmlnbp.ArrayOfExchangeRatesTable;
import xmlnbp.ExchangeRatesTable;
import xmlnbp.Rate;

/**
 *
 * @author Osito
 */
@Named
public class WalutyNBP implements Serializable {
    
    public List<Tabelanbp> pobierzjedenpliknbp(String datawstepna, String datakoncowa, Waluty w, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp wiersz = tabelanbpDAO.findOstatniaTabela(w.getSymbolwaluty());
        if (wiersz == null) {
            datawstepna = "2019-12-30";
        } else {
            datawstepna = zmiendate(wiersz.getDatatabeli());
        }
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        try {
            wierszepobranezNBP.addAll(pobierzpliknbp(datawstepna, datakoncowa, w));
        } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
            //mail.Mail.nadajMailWystapilBlad(E.e(e), null, sMTPSettingsDAO.findSprawaByDef());

        }
        return wierszepobranezNBP;
    }
    private static String zmiendate(String przekazanadata) {
        DateTime staradata = new DateTime(przekazanadata);
        DateTime nowadata = staradata.plusDays(1);
        String nowydzienformat = nowadata.toString("yyyy-MM-dd");
        return nowydzienformat;
    }
    

    public List<Tabelanbp> pobierzpliknbp(String datapoczatkowa, String datakoncowa, Waluty waluta) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, ParseException {
        List<Tabelanbp> wynik = Collections.synchronizedList(new ArrayList<>());
        InputStream inputStream = null;
        try {
            String nazwapliku = "http://api.nbp.pl/api/exchangerates/tables/a/"+datapoczatkowa+"/"+datakoncowa+"/?format=xml";
            URL url = new URL(nazwapliku);
            inputStream = url.openStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfExchangeRatesTable.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ArrayOfExchangeRatesTable tabela =  (ArrayOfExchangeRatesTable) jaxbUnmarshaller.unmarshal(reader);
            if (tabela!=null) {
                for (ExchangeRatesTable p : tabela.getExchangeRatesTable()) {
                    for (Rate r : p.getRates().getRate()) {
                        if (r.getCode().equals(waluta.getSymbolwaluty())) {
                            wynik.add(new Tabelanbp(p.getNo(), waluta, Data.calendarToString(p.getEffectiveDate()), Z.z4(r.getMid())));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        return wynik;
    }

//    public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
//        Integer numer = 244;
//        String data = "2019-12-18";
//        List<Tabelanbp> wynik = Collections.synchronizedList(new ArrayList<>());
//        while (czydataPrzedDniemDzisiejszym(data)) {
//            InputStream inputStream = null;
//            while (inputStream == null && czydataPrzedDniemDzisiejszym(data)) {
//                try {
//                    String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli(numer) + "z" + sformatujdate(data) + ".xml";
//                    URL url = new URL(nazwapliku);
//                    inputStream = url.openStream();
//                } catch (Exception e) {
//                    data = zmiendate(data);
//                }
//            }
//            if (inputStream != null) {
//                Reader reader = new InputStreamReader(inputStream, "ISO-8859-2");
//                InputSource is = new InputSource(reader);
//                is.setEncoding("UTF-8");
//                SAXParserFactory factory = SAXParserFactory.newInstance();
//                SAXParser saxParser = factory.newSAXParser();
//                WalutyParseHandler handler = new WalutyParseHandler();
//                saxParser.parse(is, handler);
//                List<Tabelanbp> wyniktmp = handler.getElementy();
//                for (Tabelanbp p : wyniktmp) {
//                    if (p.getWaluta().getSymbolwaluty().equals("NOK")) {
//                        wynik.add(p);
//                    }
//                }
//                numer++;
//            }
//        }
//    }
    
    public static void main(String[] args) {
        try {
            //String nazwapliku = "http://api.nbp.pl/api/exchangerates/tables/a?format=xml";
            String nazwapliku = "http://api.nbp.pl/api/exchangerates/tables/a/2012-01-01/2012-01-31/?format=xml";
            URL url = new URL(nazwapliku);
//            BufferedReader in = new BufferedReader(
//            new InputStreamReader(url.openStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null)
//                System.out.println(inputLine);
//            in.close();
            InputStream inputStream = url.openStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "ISO-8859-2");
            JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfExchangeRatesTable.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ArrayOfExchangeRatesTable tabela =  (ArrayOfExchangeRatesTable) jaxbUnmarshaller.unmarshal(reader);
        } catch (Exception ex) {
            Logger.getLogger(WalutyNBP.class.getName()).log(Level.SEVERE, null, ex);
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
