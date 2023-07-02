/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UzFacade;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class HasloView implements Serializable {
      private static final long serialVersionUID = 1L;
      @Inject
      private WpisView wpisView;
      private String nowehaslo;
      private String nowehaslo2;
      @Inject
      private UzFacade uzFacade;
      
    
     public void zmianaHaslaUz() {
        if (nowehaslo2.equals(nowehaslo)) {
            if (!"".equals(nowehaslo) && nowehaslo.length()<6) {
                Msg.msg("e", "Minimalna długość hasła to 6 znaków. Krótkie hasło nie może zostać zaakceptowane. Dane nie zostały zmienione");
            } else {
                try {
                    Uz uzer = wpisView.getUzer();
                    uzer.setHaslo(haszuj(nowehaslo));
                    uzFacade.edit(uzer);
                    wpisView.setUzer(uzer);
                    Msg.msg("Udana zmiana hasła/adresu email");
                    nowehaslo = null;
                    nowehaslo2 = null;
                } catch (Exception e) { 
                    E.e(e); 
                    Msg.msg("e", "Wystąpił błąd. Nastąpiła nieudana zmiana hasła/adresu email.");
                }
            }
        } else {
            Msg.msg("Dane nie zostały zmienione.");
        }
    }
     public String haszuj(String password)  {
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
              Logger.getLogger(HasloView.class.getName()).log(Level.SEVERE, null, ex);
          }
          return zwrot;
     }
     
     
}
