/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Data;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import jaxb.Makexml;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import pl.zus._2020.kedu_5_2.TDADRA;
import pl.zus._2020.kedu_5_2.TDIPL;
import pl.zus._2020.kedu_5_2.TDRA;
import pl.zus._2020.kedu_5_2.TDanePLId;
import pl.zus._2020.kedu_5_2.TINNI;
import pl.zus._2020.kedu_5_2.TIdentyfikatorDeklRap8;
import pl.zus._2020.kedu_5_2.TKEDU;
import pl.zus._2020.kedu_5_2.TNaglowekKEDU;
import pl.zus._2020.kedu_5_2.TOPLS;
import pl.zus._2020.kedu_5_2.TProgram;
import pl.zus._2020.kedu_5_2.TRIXDRA;
import pl.zus._2020.kedu_5_2.TStatusIdentyfikacji;
import pl.zus._2020.kedu_5_2.TZDRAV;
import pl.zus._2020.kedu_5_2.TZSDRA;
import pl.zus._2020.kedu_5_2.TZSDRAI;
import pl.zus._2020.kedu_5_2.TZWDRA;
import pl.zus._2020.kedu_5_2.TZestaw;


/**
 *
 * @author Osito
 */
public class KeduView {
    
    public static void main(String[] args) {
        pl.zus._2020.kedu_5_2.TKEDU kedu = new TKEDU();
        QName _KEDU_QNAME = new QName("http://www.zus.pl/2020/KEDU_5_2", "KEDU");
        kedu.setWersjaSchematu(kedu.getWersjaSchematu());
        kedu.setNaglowekKEDU(kedu_naglowek());
        kedu.getZUSDRAOrZUSRCAOrZUSRSA().add(zrob_DRA());
        String marszal = Makexml.marszal(new JAXBElement<TKEDU>(_KEDU_QNAME, TKEDU.class, null, kedu), pl.zus._2020.kedu_5_2.TKEDU.class);
        walidujkedu();
    }

    private static TNaglowekKEDU kedu_naglowek() {
        TNaglowekKEDU nag = new TNaglowekKEDU();
        nag.setDataUtworzeniaKEDU(Data.databiezaca());
        nag.setProgram(kedu_program());
        return nag;
    }

    private static TProgram kedu_program() {
        TProgram prog = new TProgram();
        prog.setProducent("Taxman");
        prog.setSymbol("Taxman Kadry");
        prog.setWersja("wersja 1.0");
        return prog;
    }


    private static JAXBElement  zrob_DRA() {
        TDRA dra = new TDRA();
        dra.setIdDokumentu(BigInteger.ONE);
        dra.setStatusKontroli("0");
        dra.setIdentyfikacjaPL(dra_identyfikacja());
        dra.setI(dra_tdadra());
        dra.setII(dra_tdipl());
        dra.setIII(dra_tinni());
        //spoleczne
        dra.setIV(dra_tzsdrai());
        //wyplacone swiadczenia
        dra.setV(dra_tzwdra());
        //zdrowotna
        dra.setVI(dra_tzsdra());
        //FP
        dra.setVII(dra_tzdrav());
        //kwota do wplaty
        dra.setIX(dra_trixdra(dra));
        //data
        dra.setXI(dra_topls());
        QName qName = new QName("http://www.zus.pl/2020/KEDU_5_2", "ZUSDRA");
        return new JAXBElement<TDRA>(qName, TDRA.class, TZestaw.class, dra);
    }

    private static TDanePLId dra_identyfikacja() {
        TDanePLId t = new TDanePLId();
        t.setIdPLZUSStatus(TStatusIdentyfikacji.B);
        return t;
    }

    private static TDADRA dra_tdadra() {
        TDADRA t = new TDADRA();
        t.setP1("3");
        t.setP2(dra_identdekl());
        return t;
    }

    private static TIdentyfikatorDeklRap8 dra_identdekl() {
        TIdentyfikatorDeklRap8 t = new TIdentyfikatorDeklRap8();
        t.setP1("01");
        t.setP2(Data.XMLGCinitRokMc("2020","08"));
        return t;
    }

    private static TDIPL dra_tdipl() {
        TDIPL t = new TDIPL();
        t.setP1(new BigInteger("8511005008"));
        t.setP2(new BigInteger("810649340"));
        t.setP3(new BigInteger("70052809810"));
        t.setP4("1");
        t.setP6("GRZEGORZ GRZELCZYK");
        t.setP7("GRZELCZYK");
        t.setP8("GRZEGORZ");
        t.setP9(Data.dataoddo("1970-05-28"));
        return t;
    }
    
    private static TINNI dra_tinni() {
        TINNI t = new TINNI();
        t.setP1(BigInteger.ONE);//liczba osób
        t.setP3(BigDecimal.valueOf(1.51).setScale(2));//stopa wypadkowa
        return t;
    }
    
     private static TZSDRAI dra_tzsdrai() {
        TZSDRAI t = new TZSDRAI();
        //sumaemeryt
        t.setP1(BigDecimal.valueOf(1.00).setScale(2));
        //sumarentowe
        t.setP2(BigDecimal.valueOf(1.00).setScale(2));
        t.setP3(t.getP1().add(t.getP2()));
        
        //emerytalne finansowane przez
        //ubezpieczonych
        t.setP4(BigDecimal.valueOf(1.00).setScale(2));
        //planika
        t.setP7(BigDecimal.valueOf(1.00).setScale(2));
        //rentowe finansowane przez
        //ubezpieczonych
        t.setP5(BigDecimal.valueOf(1.00).setScale(2));
        //planika
        t.setP8(BigDecimal.valueOf(1.00).setScale(2));
        t.setP6(t.getP4().add(t.getP5()));
        t.setP9(t.getP7().add(t.getP8()));
        
        //sumachorobowe
        t.setP19(BigDecimal.valueOf(1.00).setScale(2));
        //sumawypadkowe
        t.setP20(BigDecimal.valueOf(1.00).setScale(2));
        t.setP21(t.getP19().add(t.getP20()));
        
        //chorobowe finansowane przez
        //ubezpieczonych
        t.setP22(BigDecimal.valueOf(1.00).setScale(2));
        //planika
        t.setP25(BigDecimal.valueOf(1.00).setScale(2));
        
        //wypadkowe finansowane przez
        //ubezpieczonych
        t.setP23(BigDecimal.valueOf(1.00).setScale(2));
        //planika
        t.setP26(BigDecimal.valueOf(1.00).setScale(2));
        t.setP24(t.getP22().add(t.getP23()));
        t.setP27(t.getP25().add(t.getP26()));
        
        //zus51
        t.setP37(t.getP6().add(t.getP9()).add(t.getP24()).add(t.getP27()));
        return t;
    }

     
    private static TZWDRA dra_tzwdra() {
       TZWDRA t = new TZWDRA();
       //wyplacone zasilki chorobowe
       t.setP1(BigDecimal.valueOf(1.00).setScale(2));
       //wynagrodzenie
       t.setP2(BigDecimal.valueOf(1.00).setScale(2));
       //wyplacone zasilki wypadkowe
       t.setP3(BigDecimal.valueOf(1.00).setScale(2));
       //wynagrodzenie
       t.setP4(BigDecimal.valueOf(1.00).setScale(2));
       t.setP5(t.getP1().add(t.getP2()).add(t.getP3()).add(t.getP4()));
       return t;
    }
    
    private static TZSDRA dra_tzsdra() {
        TZSDRA t = new TZSDRA();
        //kwota finansowana przez platnika
        t.setP1(BigDecimal.valueOf(1.00).setScale(2));
        //kwota finansowana przez ubezpieczonych
        t.setP2(BigDecimal.valueOf(1.00).setScale(2));
        t.setP5(t.getP1().add(t.getP2()));
        //wynagrodzenie dla platynika
        t.setP6(BigDecimal.valueOf(1.00).setScale(2));
        t.setP7(t.getP5().add(t.getP6()));
        return t;
    }

    private static TZDRAV dra_tzdrav() {
        TZDRAV t = new TZDRAV();
        //FP
        t.setP1(BigDecimal.valueOf(1.00).setScale(2));
        //FGSP
        t.setP2(BigDecimal.valueOf(1.00).setScale(2));
        t.setP3(t.getP1().add(t.getP2()));
        return t;
    }
    
    private static TRIXDRA dra_trixdra(TDRA dra) {
        TRIXDRA t = new TRIXDRA();
        BigDecimal wynik = dra.getIV().getP37().add(dra.getVI().getP7()).add(dra.getVII().getP3()).subtract(dra.getV().getP5());
        if (wynik.compareTo(BigDecimal.ZERO)== 1) {
            t.setP2(wynik);
        } else {
            t.setP1(wynik);
        }
        return t;
    }
    
    private static TOPLS dra_topls() {
        TOPLS t = new TOPLS();
        t.setP1(Data.databiezaca());
        return t;
    }
    
    

    public static Object[] walidujkedu() {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        File schemaFile = null;
        try {
            schemaFile = new File("d:\\schematkedu.xsd");
        } catch (Exception ex) {
            // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
        }
            String data = null;
            error.E.s("start walidacji");
            try {
                FileInputStream fis = new FileInputStream("d:\\james.xml");
                data = IOUtils.toString(fis, "UTF-8");
            Source xmlFile = new StreamSource(new ByteArrayInputStream(data.getBytes(org.apache.commons.codec.CharEncoding.UTF_8)));
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                javax.xml.validation.Schema schema = schemaFactory.newSchema(schemaFile);
                javax.xml.validation.Validator validator = schema.newValidator();
                validator.validate(xmlFile);
                zwrot[0] = Boolean.TRUE;
                zwrot[1] = "Plik prawidłowy";
                error.E.s("Plik jest prawidłowy");
                error.E.s("Koniec walidacji bezbledna");
            } catch (SAXException e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = obsluzblad(e);
                error.E.s(obsluzblad(e));
            } catch (Exception e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = "Błąd walidacji pliku. Sprawdzanie przerwane";
            }
        } catch (Exception ex) {
            E.e(ex);
            error.E.s("Błąd ładowania plików do walidacji. Sprawdzanie przerwane");
        }
        return zwrot;
    }
    
     private static String obsluzblad(SAXException e) {
        String kolumna = "";
        String wiersz = "";
        String ostrzezenie ="Bląd walidacji: ";
        String wiadomosc = e.getMessage();
        String zwrot = ostrzezenie+wiadomosc;
        if (e.getClass().getName().equals("org.xml.sax.SAXParseException")) {
            kolumna = "kol. "+((SAXParseException)e).getColumnNumber()+";";
            wiersz = "wiersz "+((SAXParseException)e).getLineNumber()+";";
            zwrot = kolumna+wiersz+wiadomosc;
            
        }
        return zwrot;
    }


    

    

    

    

    

   
    
}
