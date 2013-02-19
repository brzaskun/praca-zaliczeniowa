/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import entity.Deklaracjevat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class DeklaracjevatView implements Serializable {
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslane;
    private List<Deklaracjevat> oczekujace;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    public DeklaracjevatView() {
        wyslane = new ArrayList<>();
        oczekujace = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init(){
        try{
            Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDowyslania(wpisView.getPodatnikWpisu());
            oczekujace.add(temp);
        } catch (Exception e){}
         try{
            Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu());
            wyslane.add(temp);
        } catch (Exception e){}
    }

    public List<Deklaracjevat> getWyslane() {
        return wyslane;
    }

    public void setWyslane(List<Deklaracjevat> wyslane) {
        this.wyslane = wyslane;
    }

    public List<Deklaracjevat> getOczekujace() {
        return oczekujace;
    }

    public void setOczekujace(List<Deklaracjevat> oczekujace) {
        this.oczekujace = oczekujace;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
