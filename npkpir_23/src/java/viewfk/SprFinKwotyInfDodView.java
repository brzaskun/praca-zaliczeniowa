/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.SprFinKwotyInfDodDAO;
import entityfk.SprFinKwotyInfDod;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprFinKwotyInfDodView  implements Serializable{
    private static final long serialVersionUID = 1L;
    private SprFinKwotyInfDod sprFinKwotyInfDod;
    @Inject
    private SprFinKwotyInfDodDAO sprFinKwotyInfDodDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (sprFinKwotyInfDod==null) {
            sprFinKwotyInfDod = new SprFinKwotyInfDod(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
    }
    
    public void zapisz() {
        try {
            sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
            init();
            Msg.msg("Zachowano zmiany");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd, niezachowano zmian");
        }
    }

    public SprFinKwotyInfDod getSprFinKwotyInfDod() {
        return sprFinKwotyInfDod;
    }

    public void setSprFinKwotyInfDod(SprFinKwotyInfDod sprFinKwotyInfDod) {
        this.sprFinKwotyInfDod = sprFinKwotyInfDod;
    }
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
