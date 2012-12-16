/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import embeddable.Mce;
import entity.Dok;
import entity.StornoDok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class StornoDokView implements Serializable {
    @Inject private StornoDok stornoDok;
    private ArrayList<StornoDok> lista;
    private ArrayList<Dok> pobraneDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;

    public StornoDokView() {
        lista = new ArrayList<>();
        pobraneDok = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        ArrayList<Dok> tmplist = new ArrayList<>();
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                tmplist.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu()));
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            Integer r = wpisView.getRokWpisu();
            Iterator itx;
            itx = tmplist.iterator();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                if (tmpx.getPkpirR().equals(r.toString())) {
                    tmplist.add(tmpx);
                     if (tmpx.getRozliczony() == false) {
                        pobraneDok.add(tmpx);
                    }
                }
            }
        }
    }
    
}
        
