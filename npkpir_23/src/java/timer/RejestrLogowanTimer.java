/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timer;

import dao.RejestrlogowanDAO;
import entity.Rejestrlogowan;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Schedule;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@Named

public class RejestrLogowanTimer implements Serializable{
    @Inject
    private RejestrlogowanDAO rejestrlogowanDAO;
    
    //usuwa blokade ip po 24 godzinach
    
    @Schedule(hour="14", persistent=false)
    public void zmienstatuswiadomosci() {
        List<Rejestrlogowan> lista = rejestrlogowanDAO.findAll();
        for (Rejestrlogowan p : lista) {
            DateTime datalogowania = new DateTime(p.getDatalogowania());
            if (datalogowania instanceof DateTime) {
                datalogowania = datalogowania.plusDays(1);
                DateTime dzisiaj = new DateTime();
                if (datalogowania.compareTo(dzisiaj) < 0) {
                    p.setIlosclogowan(5);
                }
            }
        }
        if (lista != null) {
            rejestrlogowanDAO.editList(lista);
        }
    }
}
