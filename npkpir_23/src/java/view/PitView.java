/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.PodatnikDAO;
import entity.Pitpoz;
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

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PitView implements Serializable {
    private static List<Pitpoz> lista;
    @Inject private PitDAO pitDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    private static Pitpoz biezacyPit;
   

    public PitView() {
        lista = new ArrayList<>();
        biezacyPit = new Pitpoz();
    }
    
    public void wybranopit() {
        Msg.msg("i", String.format("Wybrano PIT za %s/%s", biezacyPit.getPkpirR(), biezacyPit.getPkpirM()));
    }
    
    
    @PostConstruct
    private void init(){
        lista = pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
       
    }
    
     public void usun() {
        int index = lista.size() - 1;
        Pitpoz selected = lista.get(index);
        pitDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("formpi:tablicapit");
        Msg.msg("i", "Usunieto ostatni PIT dla podatnika "+selected.getUdzialowiec()+" za m-c: "+selected.getPkpirM(),"formpi:messages");
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
    
    public static Pitpoz getBiezacyPitS() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

   
    
}

