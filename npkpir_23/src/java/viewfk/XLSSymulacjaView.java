/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.SaldoKonto;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import msg.Msg;
import org.apache.poi.ss.usermodel.Workbook;
import xls.PozycjaObliczenia;
import xls.PozycjaPrzychodKoszt;
import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class XLSSymulacjaView implements Serializable{
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{symulacjaWynikuView}")
    private SymulacjaWynikuView symulacjaWynikuView;
    
    public void zachowajSymulacjewXLS() {
        try {
            List przychody = transferToPozycjaPrzychodKoszt(symulacjaWynikuView.getListakontaprzychody(),"p");
            List koszty = transferToPozycjaPrzychodKoszt(symulacjaWynikuView.getListakontakoszty(),"k");
            List wynik = transferToPozycjaObliczeniaWynik(symulacjaWynikuView.getPozycjePodsumowaniaWyniku());
            List podatek = transferToPozycjaObliczeniaPodatek(symulacjaWynikuView.getPozycjeObliczeniaPodatku());
            if (przychody.size()==0 || koszty.size()==0 || wynik.size() == 0) {
                Msg.msg("e","Wygeneruj najpierw zestawienie");
                return;
            }
            Map<String, List> listy = new HashMap<>();
            listy.put("p", przychody);
            listy.put("k", koszty);
            listy.put("w", wynik);
            listy.put("o", podatek);
            Workbook workbook = WriteXLSFile.zachowajXLS(listy);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "xlsfile.xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(XLSSymulacjaView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }

    private List transferToPozycjaPrzychodKoszt (List<SaldoKonto> lista, String p_k) {
        List l = new ArrayList();
        int i = 1;
        for (SaldoKonto p : lista) {
            double kwota = 0.0;
            if (p_k.equals("k")) {
                kwota = p.getSaldoWn() > 0 ? p.getSaldoWn() : -p.getSaldoMa();
            } else {
                kwota = p.getSaldoWn() > 0 ? -p.getSaldoWn() : p.getSaldoMa();
            }
            PozycjaPrzychodKoszt pozycjaPrzychodKoszt = new PozycjaPrzychodKoszt(i++, p.getKonto().getPelnynumer(), p.getKonto().getNazwapelna(), p.getKonto().getNazwaskrocona(), kwota);
            l.add(pozycjaPrzychodKoszt);
        }
        l.add(new PozycjaPrzychodKoszt(i++, "", "pozycja dla symulacji", "", 0.0));
        l.add(new PozycjaPrzychodKoszt(i++, "", "pozycja dla symulacji", "", 0.0));
        l.add(new PozycjaPrzychodKoszt(i++, "", "pozycja dla symulacji", "", 0.0));
        return l;
    }
    
    private List transferToPozycjaObliczeniaWynik (List<SymulacjaWynikuView.PozycjeSymulacji> lista) {
        List l = new ArrayList();
        int i = 1;
        l.add(new PozycjaObliczenia(1,"przychody razem", "+przychody"));
        l.add(new PozycjaObliczenia(2,"koszty razem", "+koszty"));
        l.add(new PozycjaObliczenia(3,"wynik finansowy", "przychody-koszty"));
        l.add(new PozycjaObliczenia(4,"npup", lista.get(3).getWartosc()));
        l.add(new PozycjaObliczenia(5,"nkup", lista.get(4).getWartosc()));
        l.add(new PozycjaObliczenia(6,"wynik podatkowy", "wynikfinansowy-npup-nkup"));
        return l;
    }
    
    private List transferToPozycjaObliczeniaPodatek (List<SymulacjaWynikuView.PozycjeSymulacji> lista) {
        List l = new ArrayList();
        int j = 1;
        int k = 1;
        for (int i = 0; i < lista.size(); i = i+3) {
            SymulacjaWynikuView.PozycjeSymulacji p = lista.get(i);
            String nazwaudzialowca = p.getNazwa().replaceAll("\\s+","");
            l.add(new PozycjaObliczenia(j++,p.getNazwa(),p.getWartosc()));
            l.add(new PozycjaObliczenia(j++,"podstawa opodatkowania "+k, "round(wynikpodatkowy*"+nazwaudzialowca+",0)"));
            l.add(new PozycjaObliczenia(j++,"podatek udziałowiec "+k, "round(podstawaopodatkowania"+k+"*0.19,0)"));
            k++;
        }
        return l;
    }
    
    public SymulacjaWynikuView getSymulacjaWynikuView() {
        return symulacjaWynikuView;
    }

    public void setSymulacjaWynikuView(SymulacjaWynikuView symulacjaWynikuView) {
        this.symulacjaWynikuView = symulacjaWynikuView;
    }
    
    
}
