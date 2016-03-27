/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokumentFKBean;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import entityfk.Dokfk;
import entityfk.Transakcja;
import error.E;
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
import pdffk.PdfRRK;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RozniceKursoweFKView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Transakcja> pobranetransakcje;
    @Inject
    private TransakcjaDAO transakcjaDAO;
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
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private double sumatransakcji;

    public RozniceKursoweFKView() {
         E.m(this);
        pobranetransakcje = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        pobranetransakcje = transakcjaDAO.findPodatnikRokRozniceKursowe(wpisView);
        sumatransakcji = 0.0;
        for (Transakcja p : pobranetransakcje) {
            sumatransakcji += p.getRoznicekursowe();
        }
        RequestContext.getCurrentInstance().update("transakcje");
    }

    public void generowanieDokumentuRRK() {
        int nrkolejny = oblicznumerkolejny();
        if (nrkolejny > 1) {
            usundokumentztegosamegomiesiaca(nrkolejny);
        }
        Dokfk dokumentRKK = DokumentFKBean.generujdokument(wpisView, klienciDAO, "RRK", "zaksięgowanie różnicC kursowych", rodzajedokDAO, tabelanbpDAO, kontoDAOfk, pobranetransakcje, dokDAOfk);
        try {
            dokDAOfk.dodaj(dokumentRKK);
            Msg.msg("Zaksięgowano dokument RRK");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu RRK");
        }
    }

    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "RRK", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
    }

    private void usundokumentztegosamegomiesiaca(int numerkolejny) {
        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), "RRK", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            dokDAOfk.destroy(popDokfk);
        }
    }

     
    public void przepiszTransakcjeNowePole() {
        List<Transakcja> lista = transakcjaDAO.findAll();
        for (Transakcja p : lista) {
            p.setKwotawwalucierachunku(p.getKwotatransakcji());
            transakcjaDAO.edit(p);
        }
    }
    
    public void drukowanieRRK() {
        if (!pobranetransakcje.isEmpty()) {
            PdfRRK.drukujRKK(pobranetransakcje, wpisView);
        }
    }

    public List<Transakcja> getPobranetransakcje() {
        return pobranetransakcje;
    }

    public void setPobranetransakcje(List<Transakcja> pobranetransakcje) {
        this.pobranetransakcje = pobranetransakcje;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getSumatransakcji() {
        return sumatransakcji;
    }

    public void setSumatransakcji(double sumatransakcji) {
        this.sumatransakcji = sumatransakcji;
    }

    
}
