/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.RodzajCzlonkostwaDAO;
import entityfk.RodzajCzlonkostwa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RodzajeCzlonkostwaView implements Serializable {
    @Inject
    private RodzajCzlonkostwa rodzajCzlonkostwa;
    private List<RodzajCzlonkostwa> rodzajCzlonkostwaLista;
    @Inject
    private RodzajCzlonkostwaDAO rodzajCzlonkostwaDAO;
    
    @PostConstruct
    private void init() {
        rodzajCzlonkostwaLista = rodzajCzlonkostwaDAO.findAll();
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public RodzajCzlonkostwa getRodzajCzlonkostwa() {
        return rodzajCzlonkostwa;
    }
    
    public void setRodzajCzlonkostwa(RodzajCzlonkostwa rodzajCzlonkostwa) {
        this.rodzajCzlonkostwa = rodzajCzlonkostwa;
    }
    
    public List<RodzajCzlonkostwa> getRodzajCzlonkostwaLista() {
        return rodzajCzlonkostwaLista;
    }
    
    public void setRodzajCzlonkostwaLista(List<RodzajCzlonkostwa> rodzajCzlonkostwaLista) {
        this.rodzajCzlonkostwaLista = rodzajCzlonkostwaLista;
    }
    
//</editor-fold>
    
}
