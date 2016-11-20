/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
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
public class PodatnicyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Podatnik> podatnicy;
    @Inject
    private PodatnikDAO podatnikDAO;
    
    @PostConstruct
    private void init() {
        podatnicy = podatnikDAO.findAll();
    }

    public List<Podatnik> getPodatnicy() {
        return podatnicy;
    }

    public void setPodatnicy(List<Podatnik> podatnicy) {
        this.podatnicy = podatnicy;
    }
    
}
