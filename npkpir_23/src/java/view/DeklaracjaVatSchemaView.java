/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.DeklaracjaVatSchema;
import dao.DeklaracjaVatSchemaDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjaVatSchemaView implements Serializable {
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private List schemyDeklaracjiVat;
    @Inject
    private DeklaracjaVatSchema deklaracjaVatSchema;
    
    @PostConstruct
    private void init() {
        schemyDeklaracjiVat = deklaracjaVatSchemaDAO.findAll();
    }

    public List getSchemyDeklaracjiVat() {
        return schemyDeklaracjiVat;
    }

    public void setSchemyDeklaracjiVat(List schemyDeklaracjiVat) {
        this.schemyDeklaracjiVat = schemyDeklaracjiVat;
    }

    public DeklaracjaVatSchema getDeklaracjaVatSchema() {
        return deklaracjaVatSchema;
    }

    public void setDeklaracjaVatSchema(DeklaracjaVatSchema deklaracjaVatSchema) {
        this.deklaracjaVatSchema = deklaracjaVatSchema;
    }
    
    
    
}
