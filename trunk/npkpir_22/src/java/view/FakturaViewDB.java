/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaViewDB extends FakturaView implements Serializable{
    @Inject private FakturaDAO fakturaDAO;
    
    public void dodaj(){
        if(fakturaDAO.dodaj(selected).equals("ok")){
            Msg.msg("i", "Dodano fakturę.");
        } else {
            Msg.msg("e", "Wystąpił błąd. Nie dodano faktury.");
        }
        
    }
}
