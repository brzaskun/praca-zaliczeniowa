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

    @Inject private SessionFacade dokFacade;
    
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
        return dokFacade.findDokPod(pod);
    }

     public List<Dok> zwrocBiezacegoKlientaRok(String pod, String rok) {
        return dokFacade.findDokBK(pod,rok);
    }
    
      
    public List<Dok> zwrocBiezacegoKlientaRokMC(String pod, String rok, String mc) {
        return dokFacade.findDokBK(pod, rok, mc);
    }
    
    
    
    private static final Logger LOG = Logger.getLogger(DokDAO.class.getName());
    
    public Dok find(String typdokumentu, String podatnik, Integer rok){
        List<Dok> lista = new ArrayList<>();
        Iterator it;
        it = zwrocBiezacegoKlientaRok(podatnik,rok.toString()).iterator();
        while (it.hasNext()) {
            Dok tmp = (Dok) it.next();
            if (tmp.getTypdokumentu().equals(typdokumentu)) {
                lista.add(tmp);
            }
        }
        Dok wynik = lista.get(lista.size()-1);
        return wynik;
    }
    
    public void destroyStornoDok(String rok, String mc, String podatnik) {
        Dok tmp = (Dok) dokFacade.findStornoDok(rok, mc, podatnik);
        dokFacade.remove(tmp);
    }
    
  
}
