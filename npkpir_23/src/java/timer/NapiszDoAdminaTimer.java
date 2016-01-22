/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timer;

import dao.PismoadminDAO;
import entity.Pismoadmin;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@Named

public class NapiszDoAdminaTimer implements Serializable{
    @Inject
    private PismoadminDAO pismoadminDAO;
    
    //usuwa dawne zrobione statusy
    
    @Schedule(hour="14", persistent=false)
    public void zmienstatuswiadomosci() {
        List<Pismoadmin> lista = pismoadminDAO.findAll();
        for (Pismoadmin p : lista) {
            DateTime datastatusu = new DateTime(p.getDatastatus());
            if (datastatusu instanceof DateTime) {
                datastatusu = datastatusu.plusDays(14);
                DateTime dzisiaj = new DateTime();
                if (datastatusu.compareTo(dzisiaj) < 0 && !p.getStatus().equals("admin przeczytał")) {
                    p.setStatus("archiwalna");
                    pismoadminDAO.edit(p);
                }
            }
        }
    }
}
