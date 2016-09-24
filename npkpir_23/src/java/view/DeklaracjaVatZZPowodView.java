/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaVatZZPowodDAO;
import entity.DeklaracjaVatZZPowod;
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
public class DeklaracjaVatZZPowodView  implements Serializable {
    @Inject
    private DeklaracjaVatZZPowodDAO deklaracjaVatZZPowodDAO;
    private List<DeklaracjaVatZZPowod> deklracjaVatZZpowody;
    
    @PostConstruct
    private void init() {
        deklracjaVatZZpowody = deklaracjaVatZZPowodDAO.findAll();
    }

    public List<DeklaracjaVatZZPowod> getDeklracjaVatZZpowody() {
        return deklracjaVatZZpowody;
    }

    public void setDeklracjaVatZZpowody(List<DeklaracjaVatZZPowod> deklracjaVatZZpowody) {
        this.deklracjaVatZZpowody = deklracjaVatZZpowody;
    }
    
    
}
