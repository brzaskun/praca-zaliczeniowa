/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named(value = "DokDAO")
public class DokDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade dokFacade;
    //tablica wciagnieta z bazy danych

    public DokDAO() {
        super(Dok.class);
    }


    public Dok znajdzDuplikat(Dok selD) throws Exception {
        Dok tmp = null;
        tmp = dokFacade.dokumentDuplicat(selD);
        return tmp;
    }

    
     public Dok znajdzPoprzednika(Integer rok, Integer mc) throws Exception{
        Dok tmp = null;
        tmp = dokFacade.poprzednik(rok,mc);
        return tmp;
        }

    public List<Dok> zwrocBiezacegoKlienta(String pod) {
        List<Dok> lista = new ArrayList<>();
        Iterator it;
        it = downloaded.iterator();
        while (it.hasNext()) {
            Dok tmp = (Dok) it.next();
            if (tmp.getPodatnik().equals(pod)) {
                lista.add(tmp);
            }
        }
        return lista;
    }

    /**
     *
     * @param pod
     * @param rok
     * @return
     */
    public List<Dok> zwrocBiezacegoKlientaRok(String pod, Integer rok) {
        List<Dok> lista = new ArrayList<>();
        Iterator it;
        it = downloaded.iterator();
        while (it.hasNext()) {
            Dok tmp = (Dok) it.next();
            if (tmp.getPodatnik().equals(pod) && tmp.getPkpirR().equals(rok.toString())) {
                lista.add(tmp);
            }
        }
        return lista;
    }
    private static final Logger LOG = Logger.getLogger(DokDAO.class.getName());
    
}
