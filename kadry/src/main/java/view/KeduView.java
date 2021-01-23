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
import pl.zus._2020.kedu_5_2.TDADRA;
import pl.zus._2020.kedu_5_2.TDIPL;
import pl.zus._2020.kedu_5_2.TDRA;
import pl.zus._2020.kedu_5_2.TDanePLId;
import pl.zus._2020.kedu_5_2.TIdentyfikatorDeklRap8;
import pl.zus._2020.kedu_5_2.TKEDU;
import pl.zus._2020.kedu_5_2.TNaglowekKEDU;
import pl.zus._2020.kedu_5_2.TProgram;
import pl.zus._2020.kedu_5_2.TStatusIdentyfikacji;
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
        Makexml.marszal(new JAXBElement<TKEDU>(_KEDU_QNAME, TKEDU.class, null, kedu), pl.zus._2020.kedu_5_2.TKEDU.class);
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
        String zwrot ="Bląd walidacji: ";
        String wiadomosc = e.getMessage();
        return zwrot+wiadomosc;
    }
}
