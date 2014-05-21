/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WierszeDAO;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class WierszeView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private WierszeDAO wierszeDAO;
    private List<Wiersze> wiersze;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
     @PostConstruct
        private void init(){
            wiersze = wierszeDAO.findWierszePodatnik(wpisView.getPodatnikWpisu());
        }

    public List<Wiersze> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<Wiersze> wiersze) {
        this.wiersze = wiersze;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
     
    
}
