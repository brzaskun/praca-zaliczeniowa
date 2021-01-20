/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Data;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import jaxb.Makexml;
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
        try {
            t.setP9(Data.dataoddo("1970-05-28"));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(KeduView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    
}
