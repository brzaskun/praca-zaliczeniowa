/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Adminmail;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class AdminmailDAO  extends DAO implements Serializable {
    
    @Inject
    private SessionFacade adminmailFacade;

    public AdminmailDAO() {
        super(Adminmail.class);
    }
    
    public  List<Adminmail> findAll(){
        try {
            System.out.println("Pobieram AdminmailDAO");
            return adminmailFacade.findAll(Adminmail.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    
}
