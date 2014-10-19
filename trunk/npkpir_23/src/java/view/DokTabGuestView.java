/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.WpisDAO;
import entity.Dok;
import entity.Wpis;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokTabGuestView")
@ViewScoped
public class DokTabGuestView implements Serializable {
    private Dok selected;
    private List<Dok> pobranedokumenty;
    private List<Dok> pobranedokumentyFiltered;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private WpisDAO wpisDAO;

    @PostConstruct
    private void init() {
        pobranedokumenty = new ArrayList<>();
        pobranedokumentyFiltered = null;
        try {
            pobranedokumenty.addAll(dokDAO.zwrocBiezacegoKlientaRokMC(wpisView.getPodatnikWpisu(), String.valueOf(wpisView.getRokWpisu()), wpisView.getMiesiacWpisu()));
            Collections.sort(pobranedokumenty, new Dokcomparator());
        } catch (Exception e) {
        }
        //pobranie pierwszego numeru jezeli jest w trakcie roku
        int numerkolejny = 1;
        try {
            String wartosc = ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getNumerpkpir(), wpisView.getRokWpisu(), Integer.parseInt(wpisView.getMiesiacWpisu()));
            numerkolejny = Integer.parseInt(wartosc);
        } catch (Exception e) {
            System.out.println("Brak numeru pkpir wprowadzonego w trakcie roku");
        }
        numerkolejny = dokDAO.liczdokumenty(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu()) + 1;
        for (Dok tmpx : pobranedokumenty) {
            tmpx.setNrWpkpir(numerkolejny++);
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
        wpisView.findWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
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

    public Dok getSelected() {
        return selected;
    }

    public void setSelected(Dok selected) {
        this.selected = selected;
    }

    public List<Dok> getPobranedokumentyFiltered() {
        return pobranedokumentyFiltered;
    }

    public void setPobranedokumentyFiltered(List<Dok> pobranedokumentyFiltered) {
        this.pobranedokumentyFiltered = pobranedokumentyFiltered;
    }
    
    

}
