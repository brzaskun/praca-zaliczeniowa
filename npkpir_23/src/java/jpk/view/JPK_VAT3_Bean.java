/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
import entityfk.EVatwpisFK;
import error.E;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import jpk201801.JPK;
import jpk201801.JPK.Podmiot1;
import jpk201801.TKodFormularza;
import jpk201801.TNaglowek;


/**
 *
 * @author Osito
 */
public class JPK_VAT3_Bean {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        try {
//            JPK jpk = new JPK();
//            jpk.setNaglowek(naglowek("2016-09-01", "2016-09-30","4444"));
//            jpk.setPodmiot1(podmiot1());
//            dodajWierszeSprzedazy(jpk);
//            jpk.setSprzedazCtrl(obliczsprzedazCtrl(jpk));
//            dodajWierszeZakupy(jpk);
//            jpk.setZakupCtrl(obliczzakupCtrl(jpk));
//            JAXBContext context = JAXBContext.newInstance(JPK.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(jpk, System.out);
//            marshaller.marshal(jpk, new FileWriter("james.xml"));
//            Wysylka.zipfile("james.xml");
//            Wysylka.encryptAES("james.xml.zip");
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            JPK person2 = (JPK) unmarshaller.unmarshal(new File("james.xml"));
//            //System.out.println(person2);
////            System.out.println(person2.getNazwisko());
////            System.out.println(person2.getAdres());
//
////          marshaller.marshal(person, new FileWriter("edyta.xml"));
////          marshaller.marshal(person, System.out);
//        } catch (Exception ex) {
//            Logger.getLogger(JPK_VAT2_Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static TNaglowek naglowek(String dataod, String datado) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 3;
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
            n.setNazwaSystemu("Taxman");
        } catch (Exception ex) {

        }
        return n;
    }

    public static XMLGregorianCalendar databiezaca() throws DatatypeConfigurationException {
        GregorianCalendar gcal = new GregorianCalendar();
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal).normalize();
    }

    public static XMLGregorianCalendar dataoddo(String data) throws DatatypeConfigurationException {
        String f = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(f);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(data));
    }

    public static Podmiot1 podmiot1(Podatnik wv) {
        Podmiot1 p = new Podmiot1();
        p.setNIP(wv.getNip());
        p.setPelnaNazwa(wv.getNazwapelna());
        p.setEmail("us@taxman.biz.pl");
        return p;
    }
    
    public static Podmiot1 podmiot1() {
        Podmiot1 p = new Podmiot1();
        p.setNIP("8511005007");
        p.setPelnaNazwa("Kotek Motek");
        p.setEmail("us@taxman.biz.pl");
        return p;
    }

          
    public static void dodajWierszeSprzedazy(JPK jpk) {
        jpk.getSprzedazWiersz().add(dodajwierszsprzedazy());
    }
    
    public static JPK.SprzedazWiersz dodajwierszsprzedazy(EVatwpis1 ev, BigInteger lp, JPK.SprzedazCtrl sprzedazCtrl) {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(dataoddo(ev.getDok().getDataSprz()));
            w.setDataWystawienia(dataoddo(ev.getDok().getDataWyst()));
            w.setNrKontrahenta(ev.getDok().getKontr1().getNip());
            w.setNazwaKontrahenta(ev.getDok().getKontr1().getNpelna());
            w.setAdresKontrahenta(ev.getDok().getKontr1().getAdres());
            w.setDowodSprzedazy(ev.getDok().getNrWlDk());
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    public static JPK.SprzedazWiersz dodajwierszsprzedazy(EVatwpisDedra ev, BigInteger lp, JPK.SprzedazCtrl sprzedazCtrl) {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(dataoddo(ev.getDataoperacji()));
            w.setDataWystawienia(dataoddo(ev.getDatadokumentu()));
            w.setNrKontrahenta("brak");
            w.setNazwaKontrahenta(ev.getImienazwisko());
            w.setAdresKontrahenta(ev.getAdres());
            w.setDowodSprzedazy(ev.getFaktura());
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    
    
    public static JPK.SprzedazWiersz dodajwierszsprzedazyFK(EVatwpisFK ev, BigInteger lp, JPK.SprzedazCtrl sprzedazCtrl) {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(dataoddo(ev.getDokfk().getDataoperacji()));
            w.setDataWystawienia(dataoddo(ev.getDokfk().getDatawystawienia()));
            w.setNrKontrahenta(ev.getDokfk().getKontr().getNip());
            w.setNazwaKontrahenta(ev.getDokfk().getKontr().getNpelna());
            w.setAdresKontrahenta(ev.getDokfk().getKontr().getAdres());
            w.setDowodSprzedazy(ev.getDokfk().getNumerwlasnydokfk());
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl);
        } catch (Exception ex) {

        }
        return w;
    }

    public static JPK.SprzedazWiersz dodajwierszsprzedazy() {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(BigInteger.ONE);
            w.setDataSprzedazy(dataoddo("2016-01-01"));
            w.setDataWystawienia(dataoddo("2016-01-02"));
            w.setNrKontrahenta("nrkonrahenta");
            w.setNazwaKontrahenta("nazwakontrahenta");
            w.setAdresKontrahenta("adreskontrahenta");
            w.setDowodSprzedazy("dowodsprzedazy");
            w.setK19(BigDecimal.valueOf(100));
            w.setK20(BigDecimal.valueOf(23));
        } catch (Exception ex) {

        }
        return w;
    }

    public static JPK.SprzedazCtrl obliczsprzedazCtrl(JPK jpk) {
        List<JPK.SprzedazWiersz> l = jpk.getSprzedazWiersz();
        JPK.SprzedazCtrl s = new JPK.SprzedazCtrl();
        for (JPK.SprzedazWiersz r : l) {
            sumujsprzedaz(r, s);
        }
        return s;
    }

    public static void sumujsprzedaz(JPK.SprzedazWiersz r, JPK.SprzedazCtrl s) {
        BigInteger b = s.getLiczbaWierszySprzedazy();
        if (b == null) {
            s.setLiczbaWierszySprzedazy(BigInteger.ONE);
        } else {
            s.setLiczbaWierszySprzedazy(s.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
        }
        BigDecimal podnal = s.getPodatekNalezny();
        if (b == null) {
            s.setPodatekNalezny(r.getK20());
        } else {
            s.setPodatekNalezny(podnal.add(r.getK20()));
        }
    }


    
    public static void dodajWierszeZakupy(JPK jpk) {
        jpk.getZakupWiersz().add(dodajwierszzakupu());
    }

    public static JPK.ZakupWiersz dodajwierszzakupu(EVatwpis1 ev, BigInteger lp, JPK.ZakupCtrl zakupCtrl) {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDataZakupu(dataoddo(ev.getDok().getDataSprz()));
            w.setDataWplywu(dataoddo(ev.getDok().getDataWyst()));
            w.setNazwaDostawcy(ev.getDok().getKontr1().getNpelna());
            w.setNrDostawcy(ev.getDok().getKontr1().getNip());
            w.setAdresDostawcy(ev.getDok().getKontr1().getAdres());
            w.setDowodZakupu(ev.getDok().getNrWlDk());
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    
    public static JPK.ZakupWiersz dodajwierszzakupu(EVatwpisFK ev, BigInteger lp, JPK.ZakupCtrl zakupCtrl) {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDataZakupu(dataoddo(ev.getDokfk().getDataoperacji()));
            w.setDataWplywu(dataoddo(ev.getDokfk().getDatawystawienia()));
            w.setNazwaDostawcy(ev.getDokfk().getKontr().getNpelna());
            w.setNrDostawcy(ev.getDokfk().getKontr().getNip());
            w.setAdresDostawcy(ev.getDokfk().getKontr().getAdres());
            w.setDowodZakupu(ev.getDokfk().getNumerwlasnydokfk());
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl);
        } catch (Exception ex) {

        }
        return w;
    }
    public static JPK.ZakupWiersz dodajwierszzakupu() {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(BigInteger.ONE);
            w.setDataZakupu(dataoddo("2016-01-01"));
            w.setDataWplywu(dataoddo("2016-01-02"));
            w.setNrDostawcy("nrdostawcy");
            w.setNazwaDostawcy("nazwadostawcy");
            w.setAdresDostawcy("adresdostawcy");
            w.setDowodZakupu("dowodzakupu");
            w.setK45(BigDecimal.valueOf(1000));
            w.setK46(BigDecimal.valueOf(230));
        } catch (Exception ex) {

        }
        return w;
    }

    public static JPK.ZakupCtrl obliczzakupCtrl(JPK jpk) {
        List<JPK.ZakupWiersz> l = jpk.getZakupWiersz();
        JPK.ZakupCtrl s = new JPK.ZakupCtrl();
        for (JPK.ZakupWiersz r : l) {
            sumujzakup(r, s);
        }
        return s;
    }

    public static void sumujzakup(JPK.ZakupWiersz r, JPK.ZakupCtrl s) {
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

    private static void dodajkwotydowierszaSprzedazy(JPK.SprzedazWiersz w, EVatwpisSuper ev, JPK.SprzedazCtrl sprzedazCtrl) {
        try {
            String netto = ev.getEwidencja().getPolejpk_netto_sprzedaz().replace("_", "");
            String vat = ev.getEwidencja().getPolejpk_vat_sprzedaz() != null ? ev.getEwidencja().getPolejpk_vat_sprzedaz().replace("_", "") : null;
            String nettosuma = ev.getEwidencja().getPolejpk_netto_sprzedaz_suma() != null ? ev.getEwidencja().getPolejpk_netto_sprzedaz_suma().replace("_", "") : null;
            String vatsuma = ev.getEwidencja().getPolejpk_vat_sprzedaz_suma() != null ? ev.getEwidencja().getPolejpk_vat_sprzedaz_suma().replace("_", "") : null;
            if (netto != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getNetto()));
            }
            if (nettosuma != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),nettosuma),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getNetto()));
            }
            if (vat != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getVat()));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())));
            }
            if (vatsuma != null) {
                Method method = JPK.SprzedazWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vatsuma),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getVat()));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())));
            }
            if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                sprzedazCtrl.setLiczbaWierszySprzedazy(sprzedazCtrl.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    private static void dodajkwotydowierszaZakupu(JPK.ZakupWiersz w, EVatwpisSuper ev, JPK.ZakupCtrl zakupCtrl) {
        try {
            String netto = ev.getEwidencja().getPolejpk_netto_zakup().replace("_", "");
            String vat = ev.getEwidencja().getPolejpk_vat_zakup() != null ? ev.getEwidencja().getPolejpk_vat_zakup().replace("_", "") : null;
            if (netto != null) {
                Method method = JPK.ZakupWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getNetto()));
            }
            if (vat != null) {
                Method method = JPK.ZakupWiersz.class.getMethod(zwrocpolejpk(ev.getEwidencja(),vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(ev.getVat()));
                zakupCtrl.setPodatekNaliczony(zakupCtrl.getPodatekNaliczony().add(BigDecimal.valueOf(ev.getVat())));
            }
             if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                zakupCtrl.setLiczbaWierszyZakupow(zakupCtrl.getLiczbaWierszyZakupow().add(BigInteger.ONE));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static String zwrocpolejpk(Evewidencja p, String pole) {
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(pole);
        return sb.toString();
    }

   public static void main(String[] args) {
        try {
            JPK.SprzedazWiersz jpk = new JPK.SprzedazWiersz();
            Method method = JPK.SprzedazWiersz.class.getMethod("setK25",BigDecimal.class);
            method.invoke(jpk, BigDecimal.TEN);
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(JPK_VAT3_Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    
}
