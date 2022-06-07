/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import daoplatnik.ZusdraDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PlatnikDRAView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private ZusdraDAO zusdraDAO;
    
    public void opipi() {
        List<entityplatnik.ZUSDRA> miasto = zusdraDAO.findByNip("8511005008");
        for (entityplatnik.ZUSDRA p : miasto) {
            System.out.println(p.toString());
        }
        zusdraDAO.find();
    }
}
