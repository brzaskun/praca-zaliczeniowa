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
import javax.inject.Named;
import javax.inject.Inject;
import mail.Mail;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
public class ResetHasla implements Serializable {

    private static final long serialVersionUID = 1L;
    private String login;
    @Inject
    private Uz user;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;

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
            user.setHaslo("abe31fe1a2113e7e8bf174164515802806d388cf4f394cceace7341a182271ab");//haslo :)
            uzDAO.edit(user);
            Mail.resetowaniehasla(user.getEmail(), user.getLogin(), null, sMTPSettingsDAO.findSprawaByDef());
            Msg.msg("i", "Hasło zresetowane. Nowe haslo przeslane mailem", "formlog1:logowanie");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
