/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansMail;

import dao.SMTPSettingsDAO;
import entity.SMTPSettings;
import entity.Uz;
import error.E;

/**
 *
 * @author Osito
 */
public class SMTPBean {
    
    public static SMTPSettings pobierzSMTP(SMTPSettingsDAO sMTPSettingsDAO, Uz uzytkownik) {
        SMTPSettings zwrot = null;
        try {
            zwrot = sMTPSettingsDAO.findSprawaByUzytkownik(uzytkownik);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public static SMTPSettings pobierzSMTPDef(SMTPSettingsDAO sMTPSettingsDAO) {
        SMTPSettings zwrot = null;
        try {
            zwrot = sMTPSettingsDAO.findSprawaByDef();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public static String adresFrom(SMTPSettings settings, SMTPSettings ogolne) {
        String zwrot = ogolne.getUseremail();
        if (settings != null) {
            zwrot = settings.getUseremail();
        }
        return zwrot;
    }
    
    public static String nazwaFirmyFrom(SMTPSettings settings, SMTPSettings ogolne) {
        String zwrot = ogolne.getNazwafirmy();
        if (settings != null) {
            zwrot = settings.getNazwafirmy();
        }
        return zwrot;
    }
    
}
