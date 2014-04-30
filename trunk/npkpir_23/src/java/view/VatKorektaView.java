/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import comparator.Vatcomparator;
import dao.DeklaracjevatDAO;
import entity.Deklaracjevat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VatKorektaView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> deklaracjeWyslane;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    public VatKorektaView() {
        deklaracjeWyslane = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init(){
        try{
            deklaracjeWyslane =  deklaracjevatDAO.findDeklaracjeWyslane200(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        } catch (Exception e){
        }
         Collections.sort(deklaracjeWyslane, new Vatcomparator());
    }
    
   

    public List<Deklaracjevat> getDeklaracjeWyslane() {
        return deklaracjeWyslane;
    }

    public void setDeklaracjeWyslane(List<Deklaracjevat> deklaracjeWyslane) {
        this.deklaracjeWyslane = deklaracjeWyslane;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
    
}
