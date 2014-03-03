/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.KlienciDAO;
import entity.Klienci;
import java.io.Serializable;
import java.util.ArrayList;
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
public class KliencifkView implements Serializable{
    @Inject private KlienciDAO klienciDAO;
    @Inject private Klienci wybranyklient;
    private List<Klienci> listawszystkichklientow;

    public KliencifkView() {
        listawszystkichklientow = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        listawszystkichklientow = klienciDAO.findAll();
    }
    

    public List<Klienci> getListawszystkichklientow() {
        return listawszystkichklientow;
    }

    public void setListawszystkichklientow(List<Klienci> listawszystkichklientow) {
        this.listawszystkichklientow = listawszystkichklientow;
    }

    public Klienci getWybranyklient() {
        return wybranyklient;
    }

    public void setWybranyklient(Klienci wybranyklient) {
        this.wybranyklient = wybranyklient;
    }

   
    
    
    
    
}
