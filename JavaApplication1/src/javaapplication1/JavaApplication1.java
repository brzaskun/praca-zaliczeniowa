/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TKodKraju;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.JPK;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.JPK.Podmiot1;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.TAdresJPK;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.TKodFormularza;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.TNaglowek;

/**
 *
 * @author Osito
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JPK jpk = new JPK();
            jpk.setNaglowek(naglowek("2016-09-01", "2016-09-30"));
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
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static TNaglowek naglowek(String dataod, String datado) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 2;
            n.setCelZlozenia(p);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.JPK_VAT);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setDataWytworzeniaJPK(databiezaca());
            n.setDataOd(dataoddo(dataod));
            n.setDataDo(dataoddo(datado));
            n.setKodUrzedu("0202");
            n.setDomyslnyKodWaluty(CurrCodeType.PLN);
        } catch (Exception ex) {

        }
        return n;
    }

    private static XMLGregorianCalendar databiezaca() throws DatatypeConfigurationException {
        GregorianCalendar gcal = new GregorianCalendar();
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal).normalize();
    }

    private static XMLGregorianCalendar dataoddo(String data) throws DatatypeConfigurationException {
        String f = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(f);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(data));
    }

    private static Podmiot1 podmiot1() {
        Podmiot1 p = new Podmiot1();
        p.setIdentyfikatorPodmiotu(indetyfikator());
        p.setAdresPodmiotu(adrespodmiotu());
        return p;
    }

    private static TIdentyfikatorOsobyNiefizycznej indetyfikator() {
        TIdentyfikatorOsobyNiefizycznej i = new TIdentyfikatorOsobyNiefizycznej();
        i.setNIP("1111111111");
        i.setREGON("123456789");
        i.setPelnaNazwa("Pełna Nazwa");
        return i;
    }

    private static TAdresJPK adrespodmiotu() {
        TAdresJPK t = new TAdresJPK();
        t.setKodKraju(TKodKraju.PL);
        t.setWojewodztwo("Zachodniopomorskie");
        t.setPowiat("powiat");
        t.setGmina("gmina");
        t.setUlica("ulica");
        t.setNrDomu("nrdomu");
        t.setNrLokalu("nrlokalu");
        t.setMiejscowosc("miejscowosc");
        t.setKodPocztowy("70-100");
        t.setPoczta("poczta");
        return t;
    }

    private static void dodajWierszeSprzedazy(JPK jpk) {
        jpk.getSprzedazWiersz().add(dodajwierszsprzedazy());
    }

    private static JPK.SprzedazWiersz dodajwierszsprzedazy() {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(BigInteger.ONE);
            w.setDataSprzedazy(dataoddo("2016-01-01"));
            w.setDataWystawienia(dataoddo("2016-01-02"));
            w.setNrKontrahenta("nrkonrahenta");
            w.setNazwaKontrahenta("nazwakontrahenta");
            w.setAdresKontrahenta("adreskontrahenta");
            w.setDowodSprzedazy("dowodsprzedazy");
            w.setTyp("G");
            w.setK19(BigDecimal.valueOf(100));
            w.setK20(BigDecimal.valueOf(23));
        } catch (Exception ex) {

        }
        return w;
    }

    private static JPK.SprzedazCtrl obliczsprzedazCtrl(JPK jpk) {
        List<JPK.SprzedazWiersz> l = jpk.getSprzedazWiersz();
        JPK.SprzedazCtrl s = new JPK.SprzedazCtrl();
        for (JPK.SprzedazWiersz r : l) {
            sumujsprzedaz(r, s);
        }
        return s;
    }

    private static void sumujsprzedaz(JPK.SprzedazWiersz r, JPK.SprzedazCtrl s) {
        BigInteger b = s.getLiczbaWierszySprzedazy();
        if (b == null) {
            s.setLiczbaWierszySprzedazy(BigInteger.ONE);
        } else {
            s.setLiczbaWierszySprzedazy(s.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
        }
        BigDecimal b1 = s.getPodatekNalezny();
        if (b == null) {
            s.setPodatekNalezny(r.getK20());
        } else {
            s.setPodatekNalezny(s.getPodatekNalezny().add(r.getK20()));
        }
    }

    private static void dodajWierszeZakupy(JPK jpk) {
        jpk.getZakupWiersz().add(dodajwierszzakupu());
    }

    private static JPK.ZakupWiersz dodajwierszzakupu() {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(BigInteger.ONE);
            w.setDataZakupu(dataoddo("2016-01-01"));
            w.setDataWplywu(dataoddo("2016-01-02"));
            w.setNrDostawcy("nrdostawcy");
            w.setNazwaDostawcy("nazwadostawcy");
            w.setAdresDostawcy("adresdostawcy");
            w.setDowodZakupu("dowodzakupu");
            w.setTyp("G");
            w.setK45(BigDecimal.valueOf(1000));
            w.setK46(BigDecimal.valueOf(230));
        } catch (Exception ex) {

        }
        return w;
    }

    private static JPK.ZakupCtrl obliczzakupCtrl(JPK jpk) {
        List<JPK.ZakupWiersz> l = jpk.getZakupWiersz();
        JPK.ZakupCtrl s = new JPK.ZakupCtrl();
        for (JPK.ZakupWiersz r : l) {
            sumujzakup(r, s);
        }
        return s;
    }

    private static void sumujzakup(JPK.ZakupWiersz r, JPK.ZakupCtrl s) {
        BigInteger b = s.getLiczbaWierszyZakupow();
        if (b == null) {
            s.setLiczbaWierszyZakupow(BigInteger.ONE);
        } else {
            s.setLiczbaWierszyZakupow(s.getLiczbaWierszyZakupow().add(BigInteger.ONE));
        }
        BigDecimal b1 = s.getPodatekNaliczony();
        if (b == null) {
            s.setPodatekNaliczony(r.getK46());
        } else {
            s.setPodatekNaliczony(s.getPodatekNaliczony().add(r.getK46()));
        }
    }

   
}
