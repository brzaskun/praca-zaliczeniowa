/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.SchemaEwidencja;
import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
@Named
public class SchemaEwidencjaSuma  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private SchemaEwidencja schemaEwidencja;
    private EVatwpisSuma eVatwpisSuma;

    public SchemaEwidencja getSchemaEwidencja() {
        return schemaEwidencja;
    }

    public void setSchemaEwidencja(SchemaEwidencja schemaEwidencja) {
        this.schemaEwidencja = schemaEwidencja;
    }

    public EVatwpisSuma getEVatwpisSuma() {
        return eVatwpisSuma;
    }

    public void setEVatwpisSuma(EVatwpisSuma eVatwpisSuma) {
        this.eVatwpisSuma = eVatwpisSuma;
    }

    @Override
    public String toString() {
        return "SchemaEwidencjaSuma{" + "schemaEwidencja=" + schemaEwidencja.getDeklaracjaVatSchema().getNazwaschemy() + ", eVatwpisSuma=" + eVatwpisSuma.getEwidencja().getNazwa() + '}';
    }
    
    
    
}
