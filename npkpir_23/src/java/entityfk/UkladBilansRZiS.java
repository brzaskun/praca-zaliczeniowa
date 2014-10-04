/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class UkladBilansRZiS {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, name = "blokada")
    protected boolean blokada;

       
    
}
