/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waluty;

import entityfk.Tabelanbp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Osito
 */
public class WalutyNBP implements Serializable{
    // convert InputStream to String

    
    public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        int numertabeli = 217;
        String dzien = "131108";
        String nazwapliku = "http://www.nbp.pl/kursy/xml/a" + numertabeli + "z" + dzien + ".xml";
        URL url = new URL(nazwapliku);
        InputStream inputStream = url.openStream();
        Reader reader = new InputStreamReader(inputStream, "ISO-8859-2");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        WalutyParseHandler handler = new WalutyParseHandler();
        saxParser.parse(is, handler);
        List<Tabelanbp> wynik = WalutyParseHandler.getElementy();
        System.out.print(wynik.toString());

    }
}
