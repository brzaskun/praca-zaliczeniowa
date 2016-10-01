/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.ParamVatUE;
import entity.Platnosci;
import entity.PlatnosciPK;
import error.E;
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
public class ParamVatUEDAO extends DAO implements Serializable{
      
    public ParamVatUEDAO() {
        super(ParamVatUE.class);
    }

    
}
