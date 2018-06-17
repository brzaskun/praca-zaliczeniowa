/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import dao.SMTPSettingsDAO;
import dao.UPODAO;
import entity.UPO;
import entity.Uz;
import error.E;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class JPKCheck {
    
    @Inject
    private UPODAO uPODAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    
    @Schedule(dayOfWeek="1-5", hour = "10", persistent = false)
    public void test() {
        List<UPO> listaupo = uPODAO.findUPOBez200();
        List<UPO> zbledem = new ArrayList<>();
        List<UPO> bezupo = new ArrayList<>();
        Set<Uz> wporowadzil = new HashSet<>();
        if (listaupo!=null) {
            for (UPO p : listaupo) {
                wporowadzil.add(p.getWprowadzil());
                if (p.getCode().toString().startsWith("1")) {
                    bezupo.add(p);
                } else if (p.getCode().toString().startsWith("4")) {
                    zbledem.add(p);
                }
            }
            for (Uz p : new ArrayList<>(wporowadzil)) {
                mail.Mail.brakiwJPK(p, "Wykaz jpk bez potwierdzeń", bezupo, null, sMTPSettingsDAO.findSprawaByDef());
                mail.Mail.brakiwJPK(p, "Wykaz jpk z błędami", zbledem, null, sMTPSettingsDAO.findSprawaByDef());
            }
            System.out.println("wywolanie check jpk");
        }
    }
    
    
}
