/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import data.Data;
import entity.EVatwpis1;
import entity.Evewidencja;
import entityfk.EVatwpisFK;
import error.E;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import jpk201701.JPK;
import static jpk.view.JPK_VAT2_Bean.*;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class JPK_VAT2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    public void przygotujXML(List<EVatwpis1> wiersze) {
        List<JPK.SprzedazWiersz> listas = utworzwierszjpkSprzedaz(wiersze, EVatwpis1.class);
        List<JPK.ZakupWiersz> listaz = utworzwierszjpkZakup(wiersze, EVatwpis1.class);
        generujXML(listas, listaz);
    }
    
    public void przygotujXMLFK(List<EVatwpisFK> wiersze) {
        List<JPK.SprzedazWiersz> listas = utworzwierszjpkSprzedaz(wiersze, EVatwpisFK.class);
        List<JPK.ZakupWiersz> listaz = utworzwierszjpkZakup(wiersze, EVatwpisFK.class);
        generujXML(listas, listaz);
    }
    
    
    public void generujXML(List<JPK.SprzedazWiersz> listas, List<JPK.ZakupWiersz> listaz) {
        try {
            JPK jpk = new JPK();
            jpk.setNaglowek(naglowek(Data.dzienpierwszy(wpisView), Data.dzienostatni(wpisView),wpisView.getPodatnikObiekt().getUrzadskarbowy()));
            jpk.setPodmiot1(podmiot1(wpisView));
            jpk.getSprzedazWiersz().addAll(listas);
            jpk.setSprzedazCtrl(obliczsprzedazCtrl(jpk));
            jpk.getZakupWiersz().addAll(listaz);
            jpk.setZakupCtrl(obliczzakupCtrl(jpk));
            Msg.dP();
        } catch(Exception e) {
            Msg.dPe();
            E.e(e);
        }
    }
    
    public static void main(String[] args) {
        try {
            JPK jpk = new JPK();
            jpk.setNaglowek(naglowek("2016-09-01", "2016-09-30","2002"));
            jpk.setPodmiot1(podmiot1());
            dodajWierszeSprzedazy(jpk);
            jpk.setSprzedazCtrl(obliczsprzedazCtrl(jpk));
            dodajWierszeZakupy(jpk);
            jpk.setZakupCtrl(obliczzakupCtrl(jpk));
            JAXBContext context = JAXBContext.newInstance(JPK.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(jpk, System.out);
            marshaller.marshal(jpk, new FileWriter("james.xml"));
            Wysylka.zipfile("james.xml");
            Wysylka.encryptAES("james.xml.zip");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JPK person2 = (JPK) unmarshaller.unmarshal(new File("james.xml"));
            //System.out.println(person2);
//            System.out.println(person2.getNazwisko());
//            System.out.println(person2.getAdres());

//          marshaller.marshal(person, new FileWriter("edyta.xml"));
//          marshaller.marshal(person, System.out);
        } catch (Exception ex) {
            E.e(ex);
        }
    }


    private List<JPK.SprzedazWiersz> utworzwierszjpkSprzedaz(List wiersze, Class c) {
        List<JPK.SprzedazWiersz> lista = new ArrayList<>();
        if (c.getName().equals("EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z")) {
                    lista.add(dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++)));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("z")) {
                    lista.add(dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++)));
                }
            }
        }
        return lista;
    }
    
    private List<JPK.ZakupWiersz> utworzwierszjpkZakup(List wiersze, Class c) {
        List<JPK.ZakupWiersz> lista = new ArrayList<>();
        if (c.getName().equals("EVatwpis1")) {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpis1 wiersz = (EVatwpis1) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s")) {
                    lista.add(dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++)));
                }
            }
        } else {
            int lp = 1;
            for (Object p : wiersze) {
                EVatwpisFK wiersz = (EVatwpisFK) p;
                if (!wiersz.getEwidencja().getTypewidencji().equals("s")) {
                    lista.add(dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++)));
                }
            }
        }
        return lista;
    }

    
     
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
}
