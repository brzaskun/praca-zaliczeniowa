/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Osito
 */
@MappedSuperclass
@DiscriminatorColumn(name="DTYPE")
public class Rozrachunek implements Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "DTYPE")
    protected String DTYPE;

    public String getDTYPE() {
        return DTYPE;
    }

    public void setDTYPE(String DTYPE) {
        this.DTYPE = DTYPE;
    }
    
    
    
}
