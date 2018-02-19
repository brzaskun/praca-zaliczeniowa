/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.ParamVatUE;
import java.io.Serializable;
import javax.inject.Named;

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
