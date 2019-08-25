/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.TabelanbpDAO;
import entityfk.Tabelanbp;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
public class TabelaNBPView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<Tabelanbp> listanpb;

    public TabelaNBPView() {
         ////E.m(this);
        listanpb = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        listanpb = tabelanbpDAO.findAll();
    }

    public List<Tabelanbp> getListanpb() {
        return listanpb;
    }

    public void setListanpb(List<Tabelanbp> listanpb) {
        this.listanpb = listanpb;
    }
    
    
    
}
