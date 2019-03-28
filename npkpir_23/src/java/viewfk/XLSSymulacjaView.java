/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.SaldoKonto;
import entityfk.StronaWiersza;
import enumy.FormaPrawna;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import msg.Msg;
import org.apache.poi.ss.usermodel.Workbook;
import view.WpisView;
import waluty.Z;
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
    @ManagedProperty(value = "#{symulacjaWynikuNarastajacoView}")
    private SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    public void zachowajSymulacjewXLS(int modulator) {
         //E.m(this);
        try {
            List wynikpopmc = transferToPozycjaPrzychodKosztOdPoczRok();
            List przychody = null;
            if (modulator == 1) {
                przychody = transferToPozycjaPrzychod(symulacjaWynikuView.getListakontaprzychody(),"p");
            } else {
                przychody = transferToPozycjaPrzychodKoszt(symulacjaWynikuView.getListakontaprzychody(),"p");
            }
            List koszty = transferToPozycjaPrzychodKoszt(symulacjaWynikuView.getListakontakoszty(),"k");
            List wynik = transferToPozycjaObliczeniaWynik(symulacjaWynikuNarastajacoView.getPozycjePodsumowaniaWyniku());
            List podatek = transferToPozycjaObliczeniaPodatek(symulacjaWynikuNarastajacoView.getPozycjeObliczeniaPodatku());
            List dywidenda = transferToPozycjaObliczeniaDywidenda(symulacjaWynikuNarastajacoView.getPozycjeDoWyplaty());
            if (przychody.size()==0 || koszty.size()==0 || wynik.size() == 0) {
                Msg.msg("e","Wygeneruj najpierw zestawienie");
                return;
            }
            Map<String, List> listy = new ConcurrentHashMap<>();
            listy.put("b", wynikpopmc);
            listy.put("p", przychody);
            listy.put("k", koszty);
            listy.put("w", wynik);
            listy.put("o", podatek);
            listy.put("d", dywidenda);
            Workbook workbook = WriteXLSFile.zachowajXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "wynikfin"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(XLSSymulacjaView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void zachowajSymulacjewXLS_old(int modulator) {
        try {
            List przychody = null;
            if (modulator == 1) {
                przychody = transferToPozycjaPrzychod(symulacjaWynikuView.getListakontaprzychody(),"p");
            } else {
                przychody = transferToPozycjaPrzychodKoszt(symulacjaWynikuView.getListakontaprzychody(),"p");
            }
            List koszty = transferToPozycjaPrzychodKoszt(symulacjaWynikuView.getListakontakoszty(),"k");
            List wynik = transferToPozycjaObliczeniaWynik(symulacjaWynikuView.getPozycjePodsumowaniaWyniku());
            List podatek = transferToPozycjaObliczeniaPodatek(symulacjaWynikuView.getPozycjeObliczeniaPodatku());
            List dywidenda = transferToPozycjaObliczeniaDywidenda(symulacjaWynikuView.getPozycjeDoWyplaty());
            if (przychody.size()==0 || koszty.size()==0 || wynik.size() == 0) {
                Msg.msg("e","Wygeneruj najpierw zestawienie");
                return;
            }
            Map<String, List> listy = new ConcurrentHashMap<>();
            listy.put("p", przychody);
            listy.put("k", koszty);
            listy.put("w", wynik);
            listy.put("o", podatek);
            listy.put("d", dywidenda);
            Workbook workbook = WriteXLSFile.zachowajXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "wynikfin"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(XLSSymulacjaView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    
    private List transferToPozycjaPrzychodKosztOdPoczRok () {
        double przychodypopmc = symulacjaWynikuNarastajacoView.getSumapoprzednichmiesiecy().getPrzychody();
        double kosztypopmc = symulacjaWynikuNarastajacoView.getSumapoprzednichmiesiecy().getKoszty();
        double wynik = Z.z(przychodypopmc-kosztypopmc);
        List l = new ArrayList();
        int i =1;
        l.add(new PozycjaPrzychodKoszt(i++, "", "przychody poprzednie miesiące", "", przychodypopmc));
        l.add(new PozycjaPrzychodKoszt(i++, "", "koszty poprzednie miesiące", "", -kosztypopmc));
        return l;
    }

    private List transferToPozycjaPrzychod (List<SaldoKonto> lista, String p_k) {
        List l = new ArrayList();
        int i = 1;
        for (SaldoKonto p : lista) {
            for (StronaWiersza r : p.getZapisy()) {
                double kwota = 0.0;
                if (r.getWnma().equals("Wn")) {
                    kwota = -r.getKwotaPLN();
                } else {
                    kwota = r.getKwotaPLN();
                }
                String konto = p.getKonto().getPelnynumer()+" "+p.getKonto().getNazwapelna();
                String kontr = r.getDokfk().getKontr().getNpelna().length() > 40 ? r.getDokfk().getKontr().getNpelna().substring(0,39) : r.getDokfk().getKontr().getNpelna();
                String fa = kontr+" f:"+r.getDokfk().getNumerwlasnydokfk();
                PozycjaPrzychodKoszt pozycjaPrzychodKoszt = new PozycjaPrzychodKoszt(i++, konto, fa, r.getDokfk().getOpisdokfk(), kwota);
                l.add(pozycjaPrzychodKoszt);
            }
        }
        l.add(new PozycjaPrzychodKoszt(i++, "", "pozycja dla symulacji", "", 0.0));
        l.add(new PozycjaPrzychodKoszt(i++, "", "pozycja dla symulacji", "", 0.0));
        l.add(new PozycjaPrzychodKoszt(i++, "", "pozycja dla symulacji", "", 0.0));
        return l;
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
        l.add(new PozycjaObliczenia(1,"przychody za mc", "+przychody"));
        l.add(new PozycjaObliczenia(2,"koszty za mc", "+koszty"));
        l.add(new PozycjaObliczenia(3,"wynik za mc", "przychody-koszty"));
        l.add(new PozycjaObliczenia(4,"wynik pop mce", "+wynikmcepop"));
        l.add(new PozycjaObliczenia(5,"wynik finansowy", "+wynikmcepop+przychody-koszty"));
        l.add(new PozycjaObliczenia(6,"npup", lista.get(3).getWartosc()));
        l.add(new PozycjaObliczenia(7,"nkup", lista.get(4).getWartosc()));
        l.add(new PozycjaObliczenia(8,"wynik podatkowy", "wynikfinansowy-npup-nkup"));
        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
            l.add(new PozycjaObliczenia(9,"pdop", "round(wynikpodatkowy*0.19,0)"));
            l.add(new PozycjaObliczenia(10,"pdop_zapłacono", lista.get(7).getWartosc()));
            l.add(new PozycjaObliczenia(11,"pdop_do_zapłaty", "pdop-pdop_zapłacono"));
            l.add(new PozycjaObliczenia(12,"wynik finansowy netto", "wynikfinansowy-pdop_do_zapłaty"));
        }
        return l;
    }
    
    private List transferToPozycjaObliczeniaPodatek (List<SymulacjaWynikuView.PozycjeSymulacji> lista) {
        List l = new ArrayList();
        int j = 1;
        int k = 1;
        for (int i = 0; i < lista.size(); i = i+8) {
            SymulacjaWynikuView.PozycjeSymulacji p = lista.get(i);
            String nazwaudzialowca = p.getNazwa().replaceAll("\\s+","");
            l.add(new PozycjaObliczenia(j++,p.getNazwa(),p.getWartosc()));
            if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
                l.add(new PozycjaObliczenia(j++,"podstawa opodatkowania "+k, "round(wynikfinansowynetto*"+nazwaudzialowca+",0)"));
            } else {
                l.add(new PozycjaObliczenia(j++,"podstawa opodatkowania "+k, "round(wynikpodatkowy*"+nazwaudzialowca+",0)"));
            }
            l.add(new PozycjaObliczenia(j++,"podatek udziałowiec "+k, "round(podstawaopodatkowania"+k+"*0.19,0)"));
            p = lista.get(i+5);
            l.add(new PozycjaObliczenia(j++,"zapłacono "+k, p.getWartosc()));
            p = lista.get(i+6);
            l.add(new PozycjaObliczenia(j++,"do wpłaty "+k, "round(podatekudziałowiec"+k+"+zapłacono"+k+",0)"));
            k++;
        }
        return l;
    }
    
    private List transferToPozycjaObliczeniaDywidenda (List<SymulacjaWynikuView.PozycjeSymulacji> lista) {
        List l = new ArrayList();
        int j = 1;
        int k = 1;
        for (int i = 0; i < lista.size(); i = i+4) {
            SymulacjaWynikuView.PozycjeSymulacji p = lista.get(i);
            String nazwaudzialowca = p.getNazwa().replaceAll("\\s+","");
            l.add(new PozycjaObliczenia(j++, nazwaudzialowca+k,p.getWartosc()));
            String nazleznazamc;
            if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
                nazleznazamc = "round(wynikfinansowynetto*"+nazwaudzialowca+",2)-podatekudziałowiec"+k;
            } else {
                nazleznazamc = "round(wynikfinansowy*"+nazwaudzialowca+",2)-podatekudziałowiec"+k;
            }
            l.add(new PozycjaObliczenia(j++,"należna od pocz.rok. "+k, nazleznazamc));
            SymulacjaWynikuView.PozycjeSymulacji p1 = lista.get(i+2);
            double wyplaconopopmce = p1.getWartosc();
            l.add(new PozycjaObliczenia(j++,"wypłacono pop mce "+k, wyplaconopopmce));
            String dowyplaty = "round(należnaodpocz.rok."+k+"-wypłaconopopmce"+k+",2)";
            l.add(new PozycjaObliczenia(j++,"do wypłaty za mc"+k, dowyplaty));
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public SymulacjaWynikuNarastajacoView getSymulacjaWynikuNarastajacoView() {
        return symulacjaWynikuNarastajacoView;
    }

    public void setSymulacjaWynikuNarastajacoView(SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView) {
        this.symulacjaWynikuNarastajacoView = symulacjaWynikuNarastajacoView;
    }
    
    
}
