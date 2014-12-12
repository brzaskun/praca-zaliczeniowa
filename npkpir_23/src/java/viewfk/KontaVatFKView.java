/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.BOFKBean;
import beansFK.KontaFKBean;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import data.Data;
import embeddablefk.SaldoKonto;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontaVatFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> kontavat;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    boolean dodajBO;
    
    @PostConstruct
    private void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaVAT(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
       kontavat = przygotowanalistasald(kontaklienta);
    }
    
    public void dodajBOdoKont() {
        dodajBO = true;
        init();
        RequestContext.getCurrentInstance().update("form:akorderonbis:saldokontvat");
        Msg.msg("Dodano zapisy z BO");
    }
    
    private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        int licznik = 0;
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setId(licznik++);
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        return przygotowanalista;
    }

     //<editor-fold defaultstate="collapsed" desc="comment">
    public List<SaldoKonto> getKontavat() {
         return kontavat;
     }

    public void setKontavat(List<SaldoKonto> kontavat) {
        this.kontavat = kontavat;
    }

     
    public WpisView getWpisView() {
        return wpisView;
    }
     
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
//</editor-fold>

    private void naniesBOnaKonto(SaldoKonto saldoKonto, Konto p) {
        if (dodajBO) {
            List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
            for (StronaWiersza r : zapisyBO) {
                if (r.getWnma().equals("Wn")) {
                    saldoKonto.setBoWn(saldoKonto.getBoWn() + r.getKwotaPLN());
                } else {
                    saldoKonto.setBoMa(saldoKonto.getBoMa() + r.getKwotaPLN());
                }
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisyRok = KontaFKBean.pobierzZapisyRokMc(p, wpisView, stronaWierszaDAO);
        for (StronaWiersza r : zapisyRok) {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setObrotyWn(saldoKonto.getObrotyWn() + r.getKwotaPLN());
            } else {
                saldoKonto.setObrotyMa(saldoKonto.getObrotyMa() + r.getKwotaPLN());
            }
        }
    }

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() > 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() > 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    public void generowanieDokumentuVAT() {
        int nrkolejny = oblicznumerkolejny();
        if (nrkolejny > 1 ) {
            usundokumentztegosamegomiesiaca(nrkolejny);
        }
        Dokfk dokumentvat = stworznowydokument(nrkolejny);
        try {
            dokDAOfk.dodaj(dokumentvat);
            Msg.msg("Zaksięgowano dokument VAT");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu VAT");
        }
    }
    
    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikWpisu(), "VAT", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii()+1;
    }

    private void usundokumentztegosamegomiesiaca(int numerkolejny) {
        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), "VAT", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            dokDAOfk.destroy(popDokfk);
        }
    }
    
    private Dokfk stworznowydokument(int nrkolejny) {
        Dokfk nd = new Dokfk("VAT", nrkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd);
        nd.setOpisdokfk("przeksięgowanie VAT za: "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd);
        ustawwiersze(nd);
        return nd;
    }

    private void ustawdaty(Dokfk nd) {
        String datadokumentu = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
    }

    private void ustawkontrahenta(Dokfk nd) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {
            
        }
    }

    private void ustawnumerwlasny(Dokfk nd) {
        String numer = "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/VAT";
        nd.setNumerwlasnydokfk(numer);
    }

    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("VAT", wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        }
    }

    private void ustawtabelenbp(Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }

    private void ustawwiersze(Dokfk nd) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        dodajzaokraglenia(kontavat);
        for (SaldoKonto p : kontavat) {
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwiersz(w, nd);
            String opiswiersza = "przeksięg. konto: "+p.getKonto().getPelnynumer();
            w.setOpisWiersza(opiswiersza);
            Konto kontoRozrachunkizUS = kontoDAOfk.findKonto("222", wpisView.getPodatnikWpisu());
            if (p.getSaldoWn() != 0.0) {
                StronaWiersza wn = new StronaWiersza(w, "Wn", p.getSaldoWn(), kontoRozrachunkizUS);
                StronaWiersza ma = new StronaWiersza(w, "Ma", p.getSaldoWn(), p.getKonto());
                w.setStronaWn(wn);
                w.setStronaMa(ma);
            } else {
                StronaWiersza wn = new StronaWiersza(w, "Wn", p.getSaldoMa(), p.getKonto());
                StronaWiersza ma = new StronaWiersza(w, "Ma", p.getSaldoMa(), kontoRozrachunkizUS);
                w.setStronaWn(wn);
                w.setStronaMa(ma);
            }
            nd.getListawierszy().add(w);
        }
    }

    private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }

    private void dodajzaokraglenia(List<SaldoKonto> kontavat) {
        double nalezny = 0.0;
        double naliczony = 0.0;
        for (SaldoKonto p : kontavat) {
            if (p.getKonto().getNazwapelna().contains("należny")) {
                nalezny += p.getSaldoMa() > 0 ? p.getSaldoMa() : 0.0;
                nalezny -= p.getSaldoWn() > 0 ? p.getSaldoWn() : 0.0;
            } else {
                naliczony -= p.getSaldoMa() > 0 ? p.getSaldoMa() : 0.0;
                naliczony += p.getSaldoWn() > 0 ? p.getSaldoWn() : 0.0;
            }
        }
        double zaokraglenianalezny = nalezny % 1;
        zaokraglenianalezny = Math.round(zaokraglenianalezny * 100);
        zaokraglenianalezny /= 100;
        double zaokraglenianaliczony = naliczony % 1;
        zaokraglenianaliczony = Math.round(zaokraglenianaliczony * 100);
        zaokraglenianaliczony /= 100;
        SaldoKonto saldokontoNalezny = zrobSaldoKontoNalezny(zaokraglenianalezny);
        if (saldokontoNalezny != null) {
            kontavat.add(saldokontoNalezny);
        }
        SaldoKonto saldokontoNaliczony = zrobSaldoKontoNaliczony(zaokraglenianaliczony);
        if (saldokontoNaliczony != null) {
            kontavat.add(saldokontoNaliczony);
        }
    }
   
    private SaldoKonto zrobSaldoKontoNalezny(double zaokraglenia) {
        Konto pozostaleprzychodyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe przych. operac.", wpisView.getPodatnikWpisu());
        Konto pozostalekosztyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe koszty operac.", wpisView.getPodatnikWpisu());
        if (zaokraglenia >= 0.5) {
            return new SaldoKonto(pozostalekosztyoperacyjne, 0.0, 1-zaokraglenia);
        } 
        if (zaokraglenia > 0)  {
            return new SaldoKonto(pozostaleprzychodyoperacyjne, zaokraglenia, 0.0);
        } 
        if (zaokraglenia == 0) {
            return null;
        }
        return null;
    }
    
    private SaldoKonto zrobSaldoKontoNaliczony(double zaokraglenia) {
        Konto pozostaleprzychodyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe przych. operac.", wpisView.getPodatnikWpisu());
        Konto pozostalekosztyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe koszty operac.", wpisView.getPodatnikWpisu());
        if (zaokraglenia >= 0.5) {
            return new SaldoKonto(pozostaleprzychodyoperacyjne, 1- zaokraglenia, 0.0);
        }
        if (zaokraglenia > 0) {
            return new SaldoKonto(pozostalekosztyoperacyjne, 0.0, zaokraglenia);
        }
        if (zaokraglenia == 0) {
            return null;
        }
        return null;
    }
    
     public static void main(String[] args) {
        double r = 4.235646;
        double zaokr = r % 1;
        zaokr = Math.round(zaokr*100);
        zaokr /= 100;
        System.out.println(zaokr);
    }

   

}
