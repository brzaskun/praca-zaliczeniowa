/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.UPODAO;
import embeddable.Parametr;
import entity.Podatnik;
import entity.UPO;
import entity.Uz;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import view.ParametrView;

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
    @Inject
    private PodatnikDAO podatnikDAO;
    
    @Schedule(dayOfWeek="1", hour = "10", persistent = false)
    public void test() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            if (ip.getHostAddress().equals("192.168.1.13")) {
                List<UPO> listaupo = uPODAO.findUPOBez200();
                List<UPO> zbledem = Collections.synchronizedList(new ArrayList<>());
                List<UPO> bezupo = Collections.synchronizedList(new ArrayList<>());
                List<UPO> brakjpk = Collections.synchronizedList(new ArrayList<>());
                Set<Uz> wporowadzil = new HashSet<>();
                for (UPO p : listaupo) {
                    wporowadzil.add(p.getWprowadzil());
                    if (p.getCode().toString().startsWith("1")) {
                        bezupo.add(p);
                    } else if (p.getCode().toString().startsWith("4")) {
                        zbledem.add(p);
                    }
                }
                List<Podatnik> podatnicy = podatnikDAO.findAll();
                String rok = data.Data.aktualnyRok();
                String mc = data.Data.aktualnyMc();
                String[] poprzedni = data.Data.poprzedniOkres(mc, rok);
                String rokpop = poprzedni[1];
                String mcpop = poprzedni[0];
                for (Iterator<Podatnik> it = podatnicy.iterator();it.hasNext();) {
                    Podatnik p = it.next();
                    boolean niema = true;
                    if (!sprawdzjakiokresvat(p, rokpop,mcpop).equals("blad")) {
                        for (UPO r : listaupo) {
                            if (r.getPodatnik().equals(p)) {
                                if (r.getRok().equals(rokpop) && r.getMiesiac().equals(mcpop)) {
                                    niema = false;
                                }
                            }
                        }
                    }
                    if(niema) {
                        brakjpk.add(new UPO(p, mcpop, rokpop));
                    }
                }
        for (Uz p : new ArrayList<>(wporowadzil)) {
            mail.Mail.brakiwJPK(p, "Wykaz jpk bez potwierdzeń", bezupo, null, sMTPSettingsDAO.findSprawaByDef());
            mail.Mail.brakiwJPK(p, "Wykaz jpk z błędami", zbledem, null, sMTPSettingsDAO.findSprawaByDef());
            mail.Mail.brakiwJPK(p, "Nie wysłano jpk dla firm", zbledem, null, sMTPSettingsDAO.findSprawaByDef());
        }
                error.E.s("wywolanie check jpk");
            }
        } catch (UnknownHostException ex) {
            // Logger.getLogger(JPKCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String sprawdzjakiokresvat(Podatnik p, String rokpop, String mcpop) {
        Integer rok = Integer.parseInt(rokpop);
        Integer mc = Integer.parseInt(mcpop);
        List<Parametr> parametry = p.getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    
   
}
