/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Ostatnidokument;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class OstatnidokumentDAO extends DAO implements Serializable {
    @Inject private SessionFacade ostatnidokumentFacade;

    public OstatnidokumentDAO() {
        super(Ostatnidokument.class);
    }
    
    public Dok pobierz(){
        List<Ostatnidokument> temp = ostatnidokumentFacade.findAll(Ostatnidokument.class);
        return temp.get(0).getDokument();
    }
    
    public void usun(){
        List<Ostatnidokument> temp = ostatnidokumentFacade.findAll(Ostatnidokument.class);
        ostatnidokumentFacade.remove(temp.get(0));
    }
    
}
