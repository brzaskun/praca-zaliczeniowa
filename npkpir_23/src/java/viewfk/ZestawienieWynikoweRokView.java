/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import embeddable.Mce;
import embeddablefk.KontoSumyRok;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZestawienieWynikoweRokView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> listakontakoszty;
    private List<SaldoKonto> listakontaprzychody;
    private List<KontoSumyRok> kontosumyrokPrzychody;
    private List<KontoSumyRok> kontosumyrokKoszty;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;

 

    public ZestawienieWynikoweRokView() {
         E.m(this);
    }

    public void init() {
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlitykaWynikowe(wpisView);
        List<Konto> kontaklientaprzychody = new ArrayList<>();
        List<Konto> kontaklientakoszty = new ArrayList<>();
        for (Konto p : kontaklienta) {
            if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
                kontaklientakoszty.add(p);
                kontaklientaprzychody.add(p);
            } else if (p.isPrzychod0koszt1()) {
                kontaklientakoszty.add(p);
            } else {
                kontaklientaprzychody.add(p);
            }
        }
        kontosumyrokPrzychody = pobierzkontadolisty(kontaklientaprzychody);
        kontosumyrokKoszty = pobierzkontadolisty(kontaklientakoszty);
        List<StronaWiersza> zapisyRok = pobierzzapisyRok();
        for (String mc : Mce.getMceListS()) {
            listakontaprzychody = przygotowanalistasaldR(kontaklientaprzychody, 0, zapisyRok, mc);
            for (SaldoKonto p : listakontaprzychody) {
                double kwota = p.getSaldoMa() > 0 ? p.getSaldoMa() : -p.getSaldoWn();
                for (KontoSumyRok r : kontosumyrokPrzychody) {
                    if (r.getKonto().equals(p.getKonto()) && kwota != 0.0) {
                        dodajdomiesiaca(r,mc,kwota);
                    }
                }
            }
            listakontakoszty = przygotowanalistasaldR(kontaklientakoszty, 1, zapisyRok, mc);
            for (SaldoKonto p : listakontakoszty) {
                double kwota = p.getSaldoWn() > 0 ? p.getSaldoWn() : -p.getSaldoMa();
                for (KontoSumyRok r : kontosumyrokKoszty) {
                    if (r.getKonto().equals(p.getKonto()) && kwota != 0.0) {
                        dodajdomiesiaca(r,mc,kwota);
                    }
                }
            }
        }
        usunzerowekonta();
        obliczsrednia();
        obliczprocenty();
    }

    public void odswiezsymulacjewynikuanalityczne() {
        wpisView.wpisAktualizuj();
        init();
    }

    
     private List<SaldoKonto> przygotowanalistasaldR(List<Konto> kontaklienta, int przychod0koszt1, List<StronaWiersza> zapisyRok, String mc) {
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesZapisyNaKontoR(saldoKonto, p, zapisyRok, mc);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista, przychod0koszt1);
        }
        for (int i = 1; i < przygotowanalista.size() + 1; i++) {
            przygotowanalista.get(i - 1).setId(i);
        }
        return przygotowanalista;
    }
    

    private void naniesZapisyNaKontoR(SaldoKonto saldoKonto, Konto p, List<StronaWiersza> zapisyRok, String mc) {
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        for (StronaWiersza r : zapisyRok) {
            if (r.getDokfk().getMiesiac().equals(mc)) {
                if (r.getKonto().equals(p)) {
                    if (r.getWnma().equals("Wn")) {
                        sumaWn += r.getKwotaPLN();
                    } else {
                        sumaMa += r.getKwotaPLN();
                    }
                    saldoKonto.getZapisy().add(r);
                }
            }
        }
        saldoKonto.setObrotyWn(sumaWn);
        saldoKonto.setObrotyMa(sumaMa);
    }
    
    

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista, int przychod0koszt1) {
        boolean kontoszczegolne = saldoKonto.getKonto().getZwyklerozrachszczegolne().equals("szczególne");
        if (kontoszczegolne) {
            if (saldoKonto.getSaldoWn() > 0.0 && przychod0koszt1 == 1) {
                przygotowanalista.add(saldoKonto);
                return;
            }
            if (saldoKonto.getSaldoMa() > 0.0 && przychod0koszt1 == 0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
        } else {
            if (saldoKonto.getObrotyBoWn() > 0.0 || saldoKonto.getBoWn() != 0.0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
            if (saldoKonto.getObrotyBoMa() > 0.0 || saldoKonto.getBoMa() != 0.0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
        }
    }

   
    private List<StronaWiersza> pobierzzapisyR() {
        List<StronaWiersza> zapisywynikrokmc = stronaWierszaDAO.findStronaByPodatnikRokMcWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        return zapisywynikrokmc;
    }

    private List<StronaWiersza> pobierzzapisyRok() {
        List<StronaWiersza> zapisywynikrokmc = stronaWierszaDAO.findStronaByPodatnikRokWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        return zapisywynikrokmc;
    }
   
    
  
    public static void main(String[] args) {
        double wynikfinansowy = 49963.29;
        double udzial = Double.valueOf("1")/100;
        System.out.println(udzial);
        double podstawaopodatkowania = Z.z(udzial*wynikfinansowy);
        System.out.println(podstawaopodatkowania);
    }

    private List<KontoSumyRok> pobierzkontadolisty(List<Konto> kontaklienta) {
        List l = new ArrayList();
        for (Konto p : kontaklienta) {
            KontoSumyRok k = new KontoSumyRok(p);
            l.add(k);
        }
        return l;
    }

    public List<KontoSumyRok> getKontosumyrokPrzychody() {
        return kontosumyrokPrzychody;
    }

    public void setKontosumyrokPrzychody(List<KontoSumyRok> kontosumyrokPrzychody) {
        this.kontosumyrokPrzychody = kontosumyrokPrzychody;
    }

    public List<KontoSumyRok> getKontosumyrokKoszty() {
        return kontosumyrokKoszty;
    }

    public void setKontosumyrokKoszty(List<KontoSumyRok> kontosumyrokKoszty) {
        this.kontosumyrokKoszty = kontosumyrokKoszty;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    private void usunzerowekonta() {
        for (Iterator<KontoSumyRok> it = kontosumyrokPrzychody.iterator(); it.hasNext();) {
            KontoSumyRok p = (KontoSumyRok) it.next();
            if (p.isEmpty()) {
                it.remove();
            }
        }
        for (Iterator<KontoSumyRok> it = kontosumyrokKoszty.iterator(); it.hasNext();) {
            KontoSumyRok p = (KontoSumyRok) it.next();
            if (p.isEmpty()) {
                it.remove();
            }
        }
    }

    private void dodajdomiesiaca(KontoSumyRok r, String mc, double kwota) {
        switch (mc) {
            case "01":
                r.setStyczen(r.getStyczen()+kwota);
                break;
            case "02":
                r.setLuty(r.getLuty()+kwota);
                break;
            case "03":
                r.setMarzec(r.getMarzec()+kwota);
                break;
            case "04":
                r.setKwiecien(r.getKwiecien()+kwota);
                break;
            case "05":
                r.setMaj(r.getMaj()+kwota);
                break;
            case "06":
                r.setCzerwiec(r.getCzerwiec()+kwota);
                break;
            case "07":
                r.setLipiec(r.getLipiec()+kwota);
                break;
            case "08":
                r.setSierpien(r.getSierpien()+kwota);
                break;
            case "09":
                r.setWrzesien(r.getWrzesien()+kwota);
                break;
            case "10":
                r.setPazdziernik(r.getPazdziernik()+kwota);
                break;
            case "11":
                r.setListopad(r.getListopad()+kwota);
                break;
            case "12":
                r.setGrudzien(r.getGrudzien()+kwota);
                break;
        }
    }

    private void obliczsrednia() {
        int dzielnik = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        int max = Mce.getMiesiacToNumber().get(wpisView.getMiesiacNastepny());
        for (KontoSumyRok p : kontosumyrokPrzychody) {
            double suma = pobierzkwoty(p, max);
            double srednia = Z.z(suma/dzielnik);
            p.setSrednia(srednia);
        }
        for (KontoSumyRok p : kontosumyrokKoszty) {
            double suma = pobierzkwoty(p, max);
            double srednia = Z.z(suma/dzielnik);
            p.setSrednia(srednia);
        }
    }
  private double pobierzkwoty(KontoSumyRok p, int max) {
        double suma = 0.0;
        if (max == 1) {
            return suma;
        }
        if (max == 2) {
            suma += p.getStyczen();
        }
        if (max == 3) {
            suma += p.getStyczen();
            suma += p.getLuty();
        }
        if (max == 4) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
        }
        if (max == 5) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
        }
        if (max == 6) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
        }
        if (max == 7) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
        }
        if (max == 8) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
            suma += p.getLipiec();
        }
        if (max == 9) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
            suma += p.getLipiec();
            suma += p.getSierpien();
        }
        if (max == 10) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
            suma += p.getLipiec();
            suma += p.getSierpien();
            suma += p.getWrzesien();
        }
        if (max == 11) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
            suma += p.getLipiec();
            suma += p.getSierpien();
            suma += p.getWrzesien();
            suma += p.getPazdziernik();
        }
        if (max == 12) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
            suma += p.getLipiec();
            suma += p.getSierpien();
            suma += p.getWrzesien();
            suma += p.getPazdziernik();
            suma += p.getListopad();
        }
        if (max == 13) {
            suma += p.getStyczen();
            suma += p.getLuty();
            suma += p.getMarzec();
            suma += p.getKwiecien();
            suma += p.getMaj();
            suma += p.getCzerwiec();
            suma += p.getLipiec();
            suma += p.getSierpien();
            suma += p.getWrzesien();
            suma += p.getPazdziernik();
            suma += p.getListopad();
            suma += p.getGrudzien();
        }
        return suma;
    }

    private void obliczprocenty() {
        int max = Mce.getMiesiacToNumber().get(wpisView.getMiesiacNastepny());
        for (KontoSumyRok p : kontosumyrokPrzychody) {
            naniesprocenty(p, max);
        }
        for (KontoSumyRok p : kontosumyrokKoszty) {
            naniesprocenty(p, max);
        }
    }

    private void naniesprocenty(KontoSumyRok p, int max) {
        if (max == 1) {
            return ;
        }
        if (max == 2) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
        }
        if (max == 3) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
        }
        if (max == 4) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
        }
        if (max == 5) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
        }
        if (max == 6) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
        }
        if (max == 7) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
        }
        if (max == 8) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
            procent = p.getLipiec()/p.getSrednia();
            p.setLipiecP(procent);
        }
        if (max == 9) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
            procent = p.getLipiec()/p.getSrednia();
            p.setLipiecP(procent);
            procent = p.getSierpien()/p.getSrednia();
            p.setSierpienP(procent);
        }
        if (max == 10) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
            procent = p.getLipiec()/p.getSrednia();
            p.setLipiecP(procent);
            procent = p.getSierpien()/p.getSrednia();
            p.setSierpienP(procent);
            procent = p.getWrzesien()/p.getSrednia();
            p.setWrzesienP(procent);
        }
        if (max == 11) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
            procent = p.getLipiec()/p.getSrednia();
            p.setLipiecP(procent);
            procent = p.getSierpien()/p.getSrednia();
            p.setSierpienP(procent);
            procent = p.getWrzesien()/p.getSrednia();
            p.setWrzesienP(procent);
            procent = p.getPazdziernik()/p.getSrednia();
            p.setPazdziernikP(procent);
        }
        if (max == 12) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
            procent = p.getLipiec()/p.getSrednia();
            p.setLipiecP(procent);
            procent = p.getSierpien()/p.getSrednia();
            p.setSierpienP(procent);
            procent = p.getWrzesien()/p.getSrednia();
            p.setWrzesienP(procent);
            procent = p.getPazdziernik()/p.getSrednia();
            p.setPazdziernikP(procent);
            procent = p.getListopad()/p.getSrednia();
            p.setListopadP(procent);
        }
        if (max == 13) {
            double procent = p.getStyczen()/p.getSrednia();
            p.setStyczenP(procent);
            procent = p.getLuty()/p.getSrednia();
            p.setLutyP(procent);
            procent = p.getMarzec()/p.getSrednia();
            p.setMarzecP(procent);
            procent = p.getKwiecien()/p.getSrednia();
            p.setKwiecienP(procent);
            procent = p.getMaj()/p.getSrednia();
            p.setMajP(procent);
            procent = p.getCzerwiec()/p.getSrednia();
            p.setCzerwiecP(procent);
            procent = p.getLipiec()/p.getSrednia();
            p.setLipiecP(procent);
            procent = p.getSierpien()/p.getSrednia();
            p.setSierpienP(procent);
            procent = p.getWrzesien()/p.getSrednia();
            p.setWrzesienP(procent);
            procent = p.getPazdziernik()/p.getSrednia();
            p.setPazdziernikP(procent);
            procent = p.getListopad()/p.getSrednia();
            p.setListopadP(procent);
            procent = p.getGrudzien()/p.getSrednia();
            p.setGrudzienP(procent);
        }
    }
  
       
   
}
