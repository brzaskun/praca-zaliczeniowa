/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import entityfk.Dokfk;
import javax.inject.Named;
import static pdffk.PdfMain.*;


/**
 *
 * @author Osito
 */
@Named

public class PdfDokfk {
    
    public static void drukujtrescpojedynczegodok(String nazwa, Dokfk selected, Uz uz) {
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        informacjaoZaksiegowaniu(document, String.valueOf(selected.getNrdziennika()));
        dodajDate(document, selected.getDatawplywu());
        dodajOpisWstepny(document, selected);
        infooFirmie(document, selected);
        saldopoczatkowe(document, selected);
        if (selected.getRodzajedok().getKategoriadokumentu() != 0) {
            dodajTabele(document, testobjects.testobjects.getTabelaKonta(selected.getListawierszy()),100,0);
        } else {
            dodajTabele(document, testobjects.testobjects.getTabelaKonta1(selected.getListawierszy()),100,1);
        }
        saldokoncowe(document, selected);
        dodajpodpis(document, uz.getImie(), uz.getNazw());
        finalizacjaDokumentuQR(document,nazwa);
    }
    
}
