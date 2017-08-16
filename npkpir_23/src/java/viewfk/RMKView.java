/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.RMKDAO;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import embeddable.Mce;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.RMK;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RMKView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RMK rmk;
    private List<Konto> listakontkosztowych;
    private List<RMK> listarmk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{rMKDokView}")
    private RMKDokView rMKDokView;
    @Inject
    private KontoDAOfk kontoDAO;
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
    @Inject
    private Dokfk dokfk;
    @Inject
    private RMKDAO rmkdao;
    private double sumarmk;

    public RMKView() {
        this.listakontkosztowych = new ArrayList<>();
        this.listarmk = new ArrayList<>();
    }
    
    
    public void init() {
        listakontkosztowych = kontoDAO.findKontaGrupa4(wpisView);
        for (Iterator<Konto> p = listakontkosztowych.iterator(); p.hasNext();) {
            if (p.next().isMapotomkow() == true) {
                p.remove();
            }
        }
        listarmk = rmkdao.findRMKByPodatnikRok(wpisView);
        this.sumarmk = podsumujrmk(listarmk);
        RequestContext.getCurrentInstance().update("formrmk");
    }
    
    public void dodajNoweRMKDokfk(List<Dokfk> wybranydok) {
        try {
            listakontkosztowych = kontoDAO.findKontaGrupa4(wpisView);
            for (Iterator<Konto> p = listakontkosztowych.iterator(); p.hasNext();) {
                if (p.next().isMapotomkow() == true) {
                    p.remove();
                }
            }
            dokfk = wybranydok.get(0);
            if (dokfk != null) {
                rmk.setDataksiegowania(dokfk.getDatawystawienia());
            }
            
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    public void dodajRMK() {
        rmk.setDokfk(dokfk);
        double kwotamiesieczna = Z.z(rmk.getKwotacalkowita()/rmk.getLiczbamiesiecy());
        rmk.setKwotamiesieczna(kwotamiesieczna);
        rmk.setDataksiegowania(dokfk.getDataoperacji());
        rmk.setMckosztu(dokfk.getMiesiac());
        rmk.setRokkosztu(dokfk.getRok());
        double kwotamax = rmk.getKwotacalkowita();
        Double narastajaco = 0.0;
        while (kwotamax - narastajaco > 0) {
            double odpisbiezacy = (kwotamax - narastajaco) > rmk.getKwotamiesieczna() ? rmk.getKwotamiesieczna() : kwotamax - narastajaco;
            if((kwotamax - narastajaco) < rmk.getKwotamiesieczna()){
                rmk.getPlanowane().add(Z.z(kwotamax - narastajaco));
                break;
            } else {
                rmk.getPlanowane().add(odpisbiezacy);
            }
            narastajaco = narastajaco + odpisbiezacy;
        }
        rmkdao.dodaj(rmk);
        System.out.println("rmk dodaje");
        Msg.msg("Dodano rozliczenie międzyokresowe");
    }
    
    private double podsumujrmk(List<RMK> listarmk) {
        double sumaWn = 0.0;
        for (RMK p : listarmk) {
            sumaWn += p.getKwotacalkowita();
        }
        return sumaWn;
    }
   
    public void destroy(RMK rmk) {
        try {
            if (rmk.getUjetewksiegach() != null && rmk.getUjetewksiegach().size() > 0) {
                Msg.msg("e", "Zapisy zostały zaksięgowane. Nie można usunąć pozycji");
            } else {
                rmkdao.destroy(rmk);
                listarmk.remove(rmk);
                init();
                rMKDokView.init();
                Msg.msg("Usunięto pozycję RMK");
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    public void generowanieDokumentuRMK() {
        usundokumentztegosamegomiesiaca();
        Dokfk dokumentRMK = stworznowydokument(oblicznumerkolejny());
        try {
            dokDAOfk.dodaj(dokumentRMK);
            Msg.msg("Zaksięgowano dokument RMK");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu RMK");
        }
    }

    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "RMK", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }

    private void usundokumentztegosamegomiesiaca() {
        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), "RMK", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            dokDAOfk.destroy(popDokfk);
        }
    }

    private Dokfk stworznowydokument(int nrkolejny) {
        Dokfk nd = new Dokfk(nrkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd);
        nd.setOpisdokfk("rozliczenia rmk za: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt());
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
        nd.setDataujecia(new Date());
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
    }

    private void ustawkontrahenta(Dokfk nd) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {  E.e(e);
            
        }
    }

    private void ustawnumerwlasny(Dokfk nd) {
        String numer = "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/RMK";
        nd.setNumerwlasnydokfk(numer);
    }
    
    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("RMK", wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu RMK");
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
        for (RMK p : listarmk) {
            if (p.isRozliczony() == false) {
                Wiersz w = new Wiersz(idporzadkowy++, 0);
                uzupelnijwiersz(w, nd);
                String opiswiersza = "odpis amortyzacyjny dla: "+p.getOpiskosztu(); 
                w.setOpisWiersza(opiswiersza);
                Konto kontoRMK = kontoDAO.findKonto("641", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
                double kwota = wyliczkwotedopobrania(p);
                StronaWiersza kosztrmk = new StronaWiersza(w, "Wn", kwota, p.getKontokosztowe());
                StronaWiersza emk = new StronaWiersza(w, "Ma", kwota, kontoRMK);
                w.setStronaWn(kosztrmk);
                w.setStronaMa(emk);
                nd.getListawierszy().add(w);
            }
        }
    }
    
     private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
     
    private double wyliczkwotedopobrania(RMK p) {
        String rok;
        int odelgloscwmcach = Mce.odlegloscMcy(p.getMckosztu(), p.getRokkosztu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        double kwota = 0.0;
        try {
           kwota = p.getPlanowane().get(odelgloscwmcach);
           p.setUjetewksiegach(new ArrayList<Double>());
           for (int i = 0;i < odelgloscwmcach+1;i++){
               p.getUjetewksiegach().add(p.getPlanowane().get(i));
           }
        } catch (Exception e) {  
            E.e(e);
            p.setRozliczony(true);
        }
        return kwota;
    }
     
     
    public RMK getRmk() {
        return rmk;
    }

    public void setRmk(RMK rmk) {
        this.rmk = rmk;
    }

    public List<Konto> getListakontkosztowych() {
        return listakontkosztowych;
    }

    public void setListakontkosztowych(List<Konto> listakontkosztowych) {
        this.listakontkosztowych = listakontkosztowych;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public KontoDAOfk getKontoDAO() {
        return kontoDAO;
    }

    public void setKontoDAO(KontoDAOfk kontoDAO) {
        this.kontoDAO = kontoDAO;
    }

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    public List<RMK> getListarmk() {
        return listarmk;
    }

    public void setListarmk(List<RMK> listarmk) {
        this.listarmk = listarmk;
    }

    public double getSumarmk() {
        return sumarmk;
    }

    public void setSumarmk(double sumarmk) {
        this.sumarmk = sumarmk;
    }

    public RMKDokView getrMKDokView() {
        return rMKDokView;
    }

    public void setrMKDokView(RMKDokView rMKDokView) {
        this.rMKDokView = rMKDokView;
    }

    
    
   
    
}
