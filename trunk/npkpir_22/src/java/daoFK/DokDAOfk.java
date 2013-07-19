/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Dokfk;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named(value="DokDAOfk")
public class DokDAOfk extends DAO implements Serializable {
    @Inject private SessionFacade dokFacade;
    
    public DokDAOfk() {
        super(Dokfk.class);
    }
    
    public List<Dokfk> findAll(){
        return (List<Dokfk>) dokFacade.findAll(Dokfk.class);
    }

    public void usun(Dokfk selected) {
        dokFacade.remove(selected);
    }

    public Dokfk findZZapisu(Kontozapisy kontozapisy) {
        return dokFacade.findZZapisu(kontozapisy.getDokument().getNumer());
    }
}
