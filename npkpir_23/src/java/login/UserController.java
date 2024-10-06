/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import dao.SesjaDAO;
import entity.Sesja;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
public class UserController {

    @Inject
    private SesjaDAO sesjaDAO;

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String nrsesji = session.getId();
            try {
                Sesja sesja = sesjaDAO.find(nrsesji);
                if (sesja != null) {
                    // Używamy LocalDateTime do pobrania aktualnego czasu
                    LocalDateTime currentTime = LocalDateTime.now();
                    // Konwertujemy LocalDateTime na Timestamp
                    sesja.setWylogowanie(Timestamp.valueOf(currentTime));
                    sesjaDAO.edit(sesja);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.invalidate();
        }
    }

}
