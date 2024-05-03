/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SMTPSettingsDAO;
import dao.UzDAO;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.Mail;
import msg.Msg;
import org.apache.commons.lang3.RandomStringUtils;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ResetHasla implements Serializable {

    private static final long serialVersionUID = 1L;
    private String login;
    @Inject
    private Uz user;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private boolean pokazreset;
    
    @PostConstruct
    private void init() {
        pokazreset = true;
    }

    public void reset() {
        try {
            user = uzDAO.findUzByLogin(login);
        } catch (Exception e) {
            E.e(e);
            
            return;
        }
        if (user==null) {
            Msg.msg("e", "Podany login: '" + login + "' nie istnieje", "formlog1:logowanie");
            login = null;
        } else {
            String generatedString = RandomStringUtils.random(7, false, true);
            user.setHaslo(haszuj(generatedString));//haslo :)
            uzDAO.edit(user);
            Mail.resetowaniehasla(user.getEmail(), user.getLogin(), null, sMTPSettingsDAO.findSprawaByDef(), generatedString);
            pokazreset = false;
            Msg.msg("w", "Hasło zresetowane. Nowe haslo przeslane mailem", "formlog1:logowanie");
        }
    }
    
    public String haszuj(String password) {
        String zwrot = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            zwrot = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ResetHasla.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zwrot;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isPokazreset() {
        return pokazreset;
    }

    public void setPokazreset(boolean pokazreset) {
        this.pokazreset = pokazreset;
    }

    
}
