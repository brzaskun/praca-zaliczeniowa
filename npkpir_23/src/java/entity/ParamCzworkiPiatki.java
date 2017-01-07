/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Parametr;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Osito
 */
@Entity
public class ParamCzworkiPiatki extends ParamSuper implements Serializable{

    public ParamCzworkiPiatki() {
    }

        
    public ParamCzworkiPiatki(Parametr r) {
        this.mcOd = r.getMcOd();
        this.rokOd = r.getMcOd();
        this.mcDo = r.getMcDo();
        this.rokDo = r.getRokDo();
        this.parametr = r.getParametr();
    }
    
 
}
