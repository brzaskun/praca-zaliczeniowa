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
        System.out.println("opipi start");
        List<entityplatnik.Zusdra> miasto = zusdraDAO.findByNip("8511005008");
        for (entityplatnik.Zusdra p : miasto) {
            System.out.println(p.toString());
        }
        System.out.println("opipi koniec");
        zusdraDAO.find();
        System.out.println("opipi koniec2");
    }
}
