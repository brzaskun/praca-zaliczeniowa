/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import dao.SesjaDAO;
import entity.Sesja;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ApplicationScoped
public class SessionCleanup {

    @Inject
    private SesjaDAO sesjaDAO;

    @PreDestroy
    public void cleanup() {
        List<Sesja> activeSessions = sesjaDAO.findSesjaZalogowani();
        for (Sesja activeSesja : activeSessions) {
            Calendar calendar = Calendar.getInstance();
            activeSesja.setWylogowanie(new Timestamp(calendar.getTime().getTime()));
            sesjaDAO.edit(activeSesja);
        }
    }
}

