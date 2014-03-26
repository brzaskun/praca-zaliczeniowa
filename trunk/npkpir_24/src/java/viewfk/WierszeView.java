/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WierszeDAO;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class WierszeView implements Serializable {
    @Inject private WierszeDAO wierszeDAO;
    private List<Wiersze> wiersze;
    
     @PostConstruct
        private void init(){
            wiersze = wierszeDAO.findAll();
        }

    public List<Wiersze> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<Wiersze> wiersze) {
        this.wiersze = wiersze;
    }
    
     
    
}
