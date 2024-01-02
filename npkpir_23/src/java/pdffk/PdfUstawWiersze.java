/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import static pdffk.PdfMain.dodajSubTabele;
import static pdffk.PdfMain.getCurrencyFormater;
import static pdffk.PdfMain.getNumberFormater;
import static pdffk.PdfMain.getPercentFormater;

/**
 *
 * @author Osito
 */
public class PdfUstawWiersze extends PdfMain {

    public static void ustawwiersze(PdfPTable table, List wiersze, String nazwaklasy, int modyfikator, String rok, String mc) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String l = locale.getLanguage();
        NumberFormat currency = getCurrencyFormater();
        NumberFormat number = getNumberFormater();
        NumberFormat percent = getPercentFormater();
        int i = 1;
        int maxlevel = 0;
        for (Iterator it = wiersze.iterator(); it.hasNext();) {
            if (nazwaklasy.equals("entityfk.StronaWiersza")) {
                if (modyfikator == 0 || modyfikator == 1) {
                    StronaWiersza p = (StronaWiersza) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                    String nw = p.getNazwaWaluty().equals("PLN") ? p.getNazwaWaluty() : p.getNazwaWaluty() + " " + p.getKursWalutyBOSW();
                    table.addCell(ustawfrazeAlign(nw, "center", 7));
                    table.addCell(ustawfrazeAlign(p.getDokfk().getDataoperacji(), "left", 7));
                    table.addCell(ustawfrazeAlign(p.getDokfkS() + " " + p.getDokfk().getNumerwlasnydokfk(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getWiersz().getOpisWiersza(), "left", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getRozliczono())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPozostalo())), "right", 8));
                    if (modyfikator == 1) {
                        PdfPTable subtable = dodajSubTabele(testobjects.testobjects.getTabelaTransakcje(p.getPlatnosci()), 95, 1, 8);
                        if (subtable != null) {
                            PdfPCell r = new PdfPCell(subtable);
                            //r.setRightIndent(30f);
                            r.setColspan(8);
                            table.addCell(r);
                        }
                    }
                } else if (modyfikator == 3) {
                    //PdfKontoZapisy drukujzapisyKompakt
                    StronaWiersza p = (StronaWiersza) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                    try {
                        if (p.getDokfk() != null && p.getDokfk().getRodzajedok() != null && p.getDokfk().getRodzajedok().getKategoriadokumentu() == 0) {
                            table.addCell(ustawfrazeAlign(p.getDataWierszaPelna(), "center", 7));
                        } else {
                            table.addCell(ustawfrazeAlign(p.getDataOperacji(), "center", 7));
                        }
                    } catch (Exception e) {
                        table.addCell(ustawfrazeAlign("", "center", 7));
                    }
                    table.addCell(ustawfrazeAlign(p.getDokfkS(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getIdporzadkowy() > 0 ? p.getIdporzadkowy() : "", "center", 7));
                    table.addCell(ustawfrazeAlign(p.getNumerwlasnydokfk(), "left", 7));
                    table.addCell(ustawfrazeAlign(p.getOpisWiersza(34), "left", 7));
                    if (p.getOpisWiersza(34).contains("suma") || p.getOpisWiersza(34).contains("saldo ")) {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                    }
                    if (p.isWn()) {
                        if (p.getOpisWiersza(34).contains("suma ") || p.getOpisWiersza(34).contains("saldo ")) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                        } else {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPozostaloZapisynakoncie(rok, mc))), "right", 7));
                        }
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    }
                    if (p.isWn()) {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    } else {
                        if (p.getOpisWiersza(34).contains("suma ") || p.getOpisWiersza(34).contains("saldo ")) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                        } else {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPozostaloZapisynakoncie(rok, mc))), "right", 7));
                        }
                    }
                    table.addCell(ustawfrazeAlign(p.getSymbolWalutPrint(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getKontoPrzeciwstawneNumer(), "left", 7));
                } else {
                    //PdfKontoZapisy drukujzapisyKompakt
                    StronaWiersza p = (StronaWiersza) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                    try {
                        if (p.getDokfk() != null && p.getDokfk().getRodzajedok() != null && p.getDokfk().getRodzajedok().getKategoriadokumentu() == 0) {
                            table.addCell(ustawfrazeAlign(p.getDataWierszaPelna(), "center", 7));
                        } else {
                            table.addCell(ustawfrazeAlign(p.getDataOperacji(), "center", 7));
                        }
                    } catch (Exception e) {
                        table.addCell(ustawfrazeAlign("", "center", 7));
                    }
                    table.addCell(ustawfrazeAlign(p.getDokfkS(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getIdporzadkowy() > 0 ? p.getIdporzadkowy() : "", "center", 7));
                    table.addCell(ustawfrazeAlign(p.getNumerwlasnydokfk(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getOpisWiersza(34), "left", 7));
                    if (p.isWn()) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    }
                    if (p.isWn()) {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                    }
                    table.addCell(ustawfrazeAlign(p.getSymbolWalutPrint(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getKontoPrzeciwstawneNumer(), "left", 7));
                }
            }
        }
    }

}
