/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import dao.EvewidencjaDAO;
import entity.Evewidencja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class EVatwpisFKConverterView implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<Evewidencja> listaEwidencji;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    
    @PostConstruct
    private void init() { //E.m(this);
        listaEwidencji= evewidencjaDAO.findAll();
    }

    public List<Evewidencja> getListaEwidencji() {
        return listaEwidencji;
    }

    public void setListaEwidencji(List<Evewidencja> listaEwidencji) {
        this.listaEwidencji = listaEwidencji;
    }
    
    
        
     
     
}
