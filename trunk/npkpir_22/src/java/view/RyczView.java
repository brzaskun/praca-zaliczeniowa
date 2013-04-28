/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.PodatnikDAO;
import dao.RyczDAO;
import entity.Ryczpoz;
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
public class RyczView implements Serializable {
    private List<Ryczpoz> lista;
    @Inject private RyczDAO ryczDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private Ryczpoz biezacyPit;
   

    public RyczView() {
        lista = new ArrayList<>();
       
    }
    
    
    
    @PostConstruct
    private void init(){
        lista = (List<Ryczpoz>) ryczDAO.findRyczPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
       
    }
    
     public void usun() {
        int index = lista.size() - 1;
        Ryczpoz selected = lista.get(index);
        ryczDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("formpi:");
        Msg.msg("i", "Usunieto parametr RyczDAO do podatnika "+selected.getUdzialowiec()+" za m-c: "+selected.getPkpirM(),"formpi:messages");
    }


    public List<Ryczpoz> getLista() {
        return lista;
    }

    public void setLista(List<Ryczpoz> lista) {
        this.lista = lista;
    }

    public RyczDAO getRyczDAO() {
        return ryczDAO;
    }

    public void setRyczDAO(RyczDAO ryczDAO) {
        this.ryczDAO = ryczDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Ryczpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Ryczpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

   
    
}

