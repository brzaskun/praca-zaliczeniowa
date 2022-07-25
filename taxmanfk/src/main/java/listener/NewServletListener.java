/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import dao.SesjaDAO;
import entity.Sesja;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author Osito
 */
@WebListener()
public class NewServletListener implements HttpSessionListener {

    @Inject
    private Sesja sesja;
    @Inject
    private SesjaDAO sesjaDAO;


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String nrsesji = se.getSession().getId();
        try {
            sesja = sesjaDAO.find(nrsesji);
            Calendar calendar = Calendar.getInstance();
            sesja.setWylogowanie(new Timestamp(calendar.getTime().getTime()));
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
        }

    }
}
