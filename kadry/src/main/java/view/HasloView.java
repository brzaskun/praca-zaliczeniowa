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
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
      private Uz uzer;
      
     @PostConstruct
     private void init() {
         uzer = wpisView.getUzer();
     }
    
     
    
     public void zmianaHaslaUz() {
        if (nowehaslo2.equals(nowehaslo)) {
            if (!"".equals(nowehaslo) && nowehaslo.length()<8) {
                Msg.msg("e", "Minimalna długość hasła to 8 znaków. Krótkie hasło nie może zostać zaakceptowane. Hasło nie zostało zmienione");
            } else {
                try {
                    Uz uzer = wpisView.getUzer();
                    uzer.setHaslo(haszuj(nowehaslo));
                    uzFacade.edit(uzer);
                    wpisView.setUzer(uzer);
                    Msg.msg("Udana zmiana hasła");
                    nowehaslo = null;
                    nowehaslo2 = null;
                } catch (Exception e) { 
                    E.e(e); 
                    Msg.msg("e", "Wystąpił błąd. Nastąpiła nieudana zmiana hasła.");
                }
            }
        } else {
            Msg.msg("e","Podane hasła nie są identyczne");
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
     
     public void hasloEmailUz() {
        if (uzer.getHaslo()!=null) {
            try {
                uzFacade.edit(uzer);
                wpisView.setUzer(uzer);
                Msg.msg("Zachowano hasło do maila");
            } catch (Exception e) { 
                E.e(e); 
                Msg.msg("e", "Wystąpił błąd. Nastąpiła nieudana zmiana hasła do maila");
            }
        } else {
            Msg.msg("e","Nie wpisano hasła do maila");
        }
    }

    public String getNowehaslo() {
        return nowehaslo;
    }

    public void setNowehaslo(String nowehaslo) {
        this.nowehaslo = nowehaslo;
    }

    public String getNowehaslo2() {
        return nowehaslo2;
    }

    public void setNowehaslo2(String nowehaslo2) {
        this.nowehaslo2 = nowehaslo2;
    }

    public Uz getUzer() {
        return uzer;
    }

    public void setUzer(Uz uzer) {
        this.uzer = uzer;
    }
     
    
     
}
