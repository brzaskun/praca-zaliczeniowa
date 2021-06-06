/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import entityfk.Konto;
import error.E;
import format.F;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import msg.Msg;
import org.primefaces.PrimeFaces;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfBilansPodgladKonta {
    
    public static void drukujBilansPodgladKonta(List<Konto> wykazkont, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"bokonta";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wykazkont != null && wykazkont.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = PdfMain.inicjacjaA4Portrait();
            PdfWriter writer = pdffk.PdfMain.inicjacjaWritera(document, nazwa);
            pdffk.PdfMain.naglowekStopkaP(writer);
            pdffk.PdfMain.otwarcieDokumentu(document, nazwa);
            pdffk.PdfMain.dodajOpisWstepny(document, "Salda BO firmy", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, getTabelaBOKonta(wykazkont),95,1);
            pdffk.PdfMain.finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }
    
    public static void dodajTabele(Document document, List[] tabela, int perc, int modyfikator) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumny(naglowki.size(), nazwaklasy, modyfikator);
                PdfPTable table = pdffk.PdfMain.przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                pdffk.PdfMain.ustawnaglowki(table, naglowki);
                ustawwiersze(table,wiersze, nazwaklasy, modyfikator);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static List[] getTabelaBOKonta(List<Konto> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("nr konta");
       n.add("nazwa pełna");
       n.add("typ konta");
       n.add("saldo wn");
       n.add("saldo wn rok pop.");
       n.add("saldo ma");
       n.add("saldo ma rok pop.");
       n.add("różnica wn");
       n.add("różnica ma");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
    private static int[] obliczKolumny(int size, String classname, int modyfikator) {
        int[] col102 = new int[size];
        col102[0] = 1;
        col102[1] = 2;
        col102[2] = 5;
        col102[3] = 3;
        col102[4] = 3;
        col102[5] = 3;
        col102[6] = 3;
        col102[7] = 3;
        col102[8] = 3;
        col102[9] = 3;
        return col102;
    }
    
    private static void ustawwiersze(PdfPTable table, List wiersze, String nazwaklasy, int modyfikator) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String l = locale.getLanguage();
        NumberFormat currency = pdffk.PdfMain.getCurrencyFormater();
        NumberFormat number = pdffk.PdfMain.getNumberFormater();
        NumberFormat percent = pdffk.PdfMain.getPercentFormater();
        int i = 1;
        int maxlevel = 0;
        for (Iterator it = wiersze.iterator(); it.hasNext();) {
            Konto p = (Konto) it.next();
            table.addCell(ustawfrazeAlign(i++, "left", 8, 22f));
            table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8));
            table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 8));
            table.addCell(ustawfrazeAlign(p.getZwyklerozrachszczegolne(), "center", 8));
            table.addCell(ustawfrazeAlign(F.numberS(p.getBoWn()), "right", 8));
            table.addCell(ustawfrazeAlign(F.numberS(p.getSaldorokpopWn()), "right", 8));
            table.addCell(ustawfrazeAlign(F.numberS(p.getBoMa()), "right", 8));
            table.addCell(ustawfrazeAlign(F.numberS(p.getSaldorokpopMa()), "right", 8));
            table.addCell(ustawfrazeAlign(F.numberS(p.getRoznicaWn()), "right", 8));
            table.addCell(ustawfrazeAlign(F.numberS(p.getRoznicaMa()), "right", 8));
        }
    }
}
