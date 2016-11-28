/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZamkniecieRokuEtapDAO;
import entity.ZamkniecieRokuEtap;
import error.E;
import java.io.Serializable;
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
public class ZamkniecieRokuEtapView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ZamkniecieRokuEtap> lista;
    @Inject
    private ZamkniecieRokuEtapDAO zamkniecieRokuEtapDAO;
    @Inject
    private ZamkniecieRokuEtap nowy;
    
    @PostConstruct
    private void init() {
        lista = zamkniecieRokuEtapDAO.findAll();
    }
    
    public void dodaj() {
        try {
            zamkniecieRokuEtapDAO.dodaj(nowy);
            lista.add(nowy);
            nowy = new ZamkniecieRokuEtap();
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void usun(ZamkniecieRokuEtap item) {
        try {
            zamkniecieRokuEtapDAO.destroy(item);
            lista.remove(item);
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }

    public List<ZamkniecieRokuEtap> getLista() {
        return lista;
    }

    public void setLista(List<ZamkniecieRokuEtap> lista) {
        this.lista = lista;
    }

    public ZamkniecieRokuEtap getNowy() {
        return nowy;
    }

    public void setNowy(ZamkniecieRokuEtap nowy) {
        this.nowy = nowy;
    }
    
     
    
}
