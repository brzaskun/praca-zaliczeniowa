/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.TabelanbpFacade;
import dao.WalutyFacade;
import data.Data;
import entity.Tabelanbp;
import entity.Waluty;
import error.E;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import xmlnbp.ArrayOfExchangeRatesTable;
import xmlnbp.ExchangeRatesTable;
import xmlnbp.Rate;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class WalutyFKBean {

    @Inject
    private TabelanbpFacade tabelanbpFacade;
    @Inject
    private WalutyFacade walutyFacade;
    
    
    
    @Schedule(hour = "14,20", persistent = false)
    public void pobierzkursy() {
        String datawstepna;
        List<Waluty> pobranewaluty = walutyFacade.findAll();
        Iterator<Waluty> it = pobranewaluty.iterator();
        while(it.hasNext()) {
            Waluty w = it.next();
            if(w.getSymbolwaluty().equals("PLN")) {
                it.remove();
                break;
            }
        }
        String datakoncowa = Data.calendarToString(Data.databiezaca());
        for (Waluty w : pobranewaluty) {
            Tabelanbp wiersz = tabelanbpFacade.findOstatniaTabela(w.getSymbolwaluty());
            if (wiersz == null) {
                datawstepna = "2022-01-01";
            } else {
                datawstepna = Data.dodajdzien(wiersz.getDatatabeli(),1);
            }
            List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
            try {
                wierszepobranezNBP.addAll(pobierzpliknbp(datawstepna, datakoncowa, w));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //mail.Mail.nadajMailWystapilBlad(E.e(e), null, sMTPSettingsDAO.findSprawaByDef());
                
            }
            for (Tabelanbp p : wierszepobranezNBP) {
               tabelanbpFacade.create(p);
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
       
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
   
    
}
