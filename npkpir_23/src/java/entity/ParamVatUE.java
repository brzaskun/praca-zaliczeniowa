/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Osito
 */
@Entity
public class ParamVatUE extends ParamSuper implements Serializable{
    
    
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getMcOd() {
        return mcOd;
    }
    
    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }
    
    public String getRokOd() {
        return rokOd;
    }
    
    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }
    
    public String getMcDo() {
        return mcDo;
    }
    
    public void setMcDo(String mcDo) {
        this.mcDo = mcDo;
    }
    
    public String getRokDo() {
        return rokDo;
    }
    
    public void setRokDo(String rokDo) {
        this.rokDo = rokDo;
    }
    
    public String getParametr() {
        return parametr;
    }
    
    public void setParametr(String parametr) {
        this.parametr = parametr;
    }
    
    
//</editor-fold>
    
}
