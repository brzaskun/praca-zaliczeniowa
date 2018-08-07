/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.CechaBean;
import comparator.Dokcomparator;
import comparator.Rodzajedokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import dao.WpisDAO;
import entity.Dok;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.Wpis;
import entityfk.Cechazapisu;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import pdf.PdfDok;
import pdf.PdfPK;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokTabGuestView")
@ViewScoped
public class DokTabGuestView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> selected;
    private List<Dok> pobranedokumenty;
    private List<Dok> pobranedokumentyFiltered;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private AmoDokDAO amoDokDAO;
    private double przychody;
     private double koszty;
     private double roznica;
     //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> dokumentyFiltered;
    private List<String> cechydokzlisty;
    private String wybranacechadok;
    private List<Rodzajedok> dokumentypodatnika;
    

    @PostConstruct
    public void init() {
        selected = new ArrayList<>();
        pobranedokumenty = new ArrayList<>();
        dokumentypodatnika = new ArrayList<>();
        pobranedokumentyFiltered = null;
        List<Dok> dokumentypobrane = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        try {
            dokumentypobrane.addAll(dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok.toString(), mc));
            //sortowanie dokumentów
            Collections.sort(dokumentypobrane, new Dokcomparator());
        } catch (Exception e) {
            E.e(e);
        }
         if (dokumentypobrane != null) {
            cechydokzlisty = CechaBean.znajdzcechy(dokumentypobrane);
        }
        int numerkolejny = dokDAO.liczdokumenty(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikObiekt()) + 1;
        Set<Rodzajedok> dokumentyl = new HashSet<>();
        for (Dok tmpx : dokumentypobrane) {
            boolean dodaj = false;
            if (tmpx.getPkpirM().equals(mc)) {
                dokumentyl.add(tmpx.getRodzajedok());
                if (wybranacechadok == null) {
                    dodaj = true;
                } else if (!tmpx.getCechadokumentuLista().isEmpty() && !wybranacechadok.equals("bezcechy")) {
                    for (Cechazapisu cz : tmpx.getCechadokumentuLista()) {
                        if (cz.getNazwacechy().equals(wybranacechadok)) {
                            dodaj = true;
                            break;
                        }
                    }
                } else if (wybranacechadok.equals("bezcechy") && (tmpx.getCechadokumentuLista() == null || tmpx.getCechadokumentuLista().isEmpty())){
                    dodaj = true;
                }
                if (dodaj) {
                    pobranedokumenty.add(tmpx);
                }
            }
        }
        dokumentypodatnika.addAll(dokumentyl);
        Collections.sort(dokumentypodatnika, new Rodzajedokcomparator());
        sumujwybrane();
        for (Dok tmpxa : pobranedokumenty) {
            tmpxa.setNrWpkpir(numerkolejny++);
        }
    }
    
     private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }
     private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }
     
     public void drukPIT5Pdf() {
         try {
             PdfPK.drukujPK(selected, podatnikDAO, wpisView, uzDAO, amoDokDAO);
         } catch (Exception e) { E.e(e); 
             
         }
     }
     
     public void sumujwybrane() {
        przychody = 0.0;
        koszty = 0.0;
        roznica = 0.0;
        if (pobranedokumentyFiltered != null) {
            for (Dok p : pobranedokumentyFiltered) {
                sumujdokumentydodane(p);
            }
        } else {
            for (Dok p : pobranedokumenty) {
                sumujdokumentydodane(p);
            }
        }
    }
    private void sumujdokumentydodane(Dok tmpx) {
        if (tmpx.getRodzajedok().getKategoriadokumentu() == 2 ||tmpx.getRodzajedok().getKategoriadokumentu() == 4) {
            przychody = przychody + tmpx.getNetto();
        } else {
            koszty = koszty + tmpx.getNetto();
        }
    }
    
    public void drukujdokumentyuproszczona() {
        if (dokumentyFiltered != null && dokumentyFiltered.size()>0) {
            PdfDok.drukujDokGuest(pobranedokumentyFiltered, wpisView,0, wybranacechadok);
        } else {
            PdfDok.drukujDokGuest(pobranedokumenty, wpisView,0, wybranacechadok);
        }
    }

    public List<Dok> getPobranedokumenty() {
        return pobranedokumenty;
    }

    public void setPobranedokumenty(List<Dok> pobranedokumenty) {
        this.pobranedokumenty = pobranedokumenty;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dok> getSelected() {
        return selected;
    }

    public void setSelected(List<Dok> selected) {
        this.selected = selected;
    }

    public double getPrzychody() {
        return przychody;
    }

    public void setPrzychody(double przychody) {
        this.przychody = przychody;
    }

    public double getKoszty() {
        return koszty;
    }

    public void setKoszty(double koszty) {
        this.koszty = koszty;
    }

    public double getRoznica() {
        return Z.z(this.przychody-this.koszty);
    }

    public void setRoznica(double roznica) {
        this.roznica = roznica;
    }

    public List<Dok> getDokumentyFiltered() {
        return dokumentyFiltered;
    }

    public void setDokumentyFiltered(List<Dok> dokumentyFiltered) {
        this.dokumentyFiltered = dokumentyFiltered;
    }

   
    public List<Dok> getPobranedokumentyFiltered() {
        return pobranedokumentyFiltered;
    }

    public void setPobranedokumentyFiltered(List<Dok> pobranedokumentyFiltered) {
        this.pobranedokumentyFiltered = pobranedokumentyFiltered;
    }

    public List<String> getCechydokzlisty() {
        return cechydokzlisty;
    }

    public void setCechydokzlisty(List<String> cechydokzlisty) {
        this.cechydokzlisty = cechydokzlisty;
    }

    public String getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(String wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public List<Rodzajedok> getDokumentypodatnika() {
        return dokumentypodatnika;
    }

    public void setDokumentypodatnika(List<Rodzajedok> dokumentypodatnika) {
        this.dokumentypodatnika = dokumentypodatnika;
    }
    
    

}
