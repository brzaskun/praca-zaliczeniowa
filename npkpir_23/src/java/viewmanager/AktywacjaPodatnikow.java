/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewmanager;

import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class AktywacjaPodatnikow implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<Podatnik> listapodatnikow;
    @Inject private PodatnikDAO podatnikDAO;

    public AktywacjaPodatnikow() {
        listapodatnikow = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() {
        listapodatnikow = podatnikDAO.findAllManager();
    }
    
    public void aktywacjaDeaktywacja(Podatnik p) {
        boolean aktualnystan = p.isPodmiotaktywny();
        p.setPodmiotaktywny(!aktualnystan);
        podatnikDAO.edit(p);
        listapodatnikow = podatnikDAO.findAllManager();
        Msg.msg("Zmieniono status podatnika");
    }

    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }
    
    
    
    
}
