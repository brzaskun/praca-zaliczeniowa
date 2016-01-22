/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansLogowanie;

import dao.RejestrlogowanDAO;
import entity.Rejestrlogowan;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import mail.MailAdmin;

/**
 *
 * @author Osito
 */
@Named

public class Liczniklogowan {
    
    
    public static int pobierzIloscLogowan(String ip, RejestrlogowanDAO rejestrlogowanDAO) {
        try {
            Rejestrlogowan biezacelogowanie = rejestrlogowanDAO.findByIP(ip);
            return biezacelogowanie.getIlosclogowan();
        } catch (Exception e) {
            Rejestrlogowan rejestrlogowan = new Rejestrlogowan(ip,new Date(),5,false);
            rejestrlogowanDAO.dodaj(rejestrlogowan);
            return 5;
        }
    }
    
    public static int odejmijLogowanie (String ip, RejestrlogowanDAO rejestrlogowanDAO) {
        try {
            Rejestrlogowan biezacelogowanie = rejestrlogowanDAO.findByIP(ip);
            int ilosclogowan = biezacelogowanie.getIlosclogowan();
            if (ilosclogowan > 0) {
                biezacelogowanie.setIlosclogowan(--ilosclogowan);
                biezacelogowanie.setDatalogowania(new Date());
                rejestrlogowanDAO.edit(biezacelogowanie);
                return ilosclogowan;
            } else {
                MailAdmin.zablokowanoIPinfoDlaadmina(ip);
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            return 0;
        }
    }
    
    public static int resetujLogowanie (String ip, RejestrlogowanDAO rejestrlogowanDAO) {
        try {
            Rejestrlogowan biezacelogowanie = rejestrlogowanDAO.findByIP(ip);
            biezacelogowanie.setIlosclogowan(5);
            biezacelogowanie.setDatalogowania(new Date());
            rejestrlogowanDAO.edit(biezacelogowanie);
            return biezacelogowanie.getIlosclogowan();
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            return 0;
        }
    }
    
    public static void odblokujIPusera (Rejestrlogowan logowanie, RejestrlogowanDAO rejestrlogowanDAO) {
        logowanie.setIlosclogowan(5);
        logowanie.setDatalogowania(new Date());
        logowanie.setBlokada(false);
        rejestrlogowanDAO.edit(logowanie);
    }
}
    
    

