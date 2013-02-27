/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.PodatnikDAO;
import embeddable.Udzialy;
import entity.Pitpoz;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class PitView implements Serializable {
    private List<Pitpoz> lista;
    @Inject private PitDAO pitDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private Pitpoz biezacyPit;
   

    public PitView() {
        lista = new ArrayList<>();
       
    }
    
    
    
    @PostConstruct
    private void init(){
        lista = (List<Pitpoz>) pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
       
    }
    
     public void usun() {
        int index = lista.size() - 1;
        Pitpoz selected = lista.get(index);
        pitDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("formpi:");
        Msg.msg("i", "Usunieto parametr ZUS do podatnika "+selected.getUdzialowiec()+" za m-c: "+selected.getPkpirM(),"formpi:messages");
    }


    public List<Pitpoz> getLista() {
        return lista;
    }

    public void setLista(List<Pitpoz> lista) {
        this.lista = lista;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

   
    
}

