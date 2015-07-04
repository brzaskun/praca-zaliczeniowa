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
import javax.ejb.Stateless;
import javax.inject.Named;
import static pdffk.PdfMain.dodajDate;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajpodpis;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.infooFirmie;
import static pdffk.PdfMain.informacjaoZaksiegowaniu;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;

/**
 *
 * @author Osito
 */
@Named
@Stateless
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
        dodajTabele(document, testobjects.testobjects.getTabelaKonta(selected.getListawierszy()),100,0);
        dodajpodpis(document, uz.getImie(), uz.getNazw());
        finalizacjaDokumentu(document);
    }
    
}
