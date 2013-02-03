/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import entity.Vatpoz;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class VatDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade vatpozFacade;

    public VatDAO() {
        super(Vatpoz.class);
    }
    
     public List<Vatpoz> findVatPod(String rok, String pod) {
        return vatpozFacade.findVatpodatnik(rok,pod);
    }
}
