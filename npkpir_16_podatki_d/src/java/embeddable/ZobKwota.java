/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
@Table(name = "zusstawki")
@XmlRootElement

public class ZobKwota implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private String rok;
    private String miesiac;
    private Double zus51;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date zus51zapl;
    private Double zus51ods;
    private Double zus51suma;
    private Double zus52;
    private Double zus52ods;
    private Double zus52suma;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date zus52zapl;
    private Double zus53;
    private Double zus53ods;
    private Double zus53suma;
    private String terminzuz;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date zus53zapl;
    private Double pit4;
    private String terminzpit4;
    private Double pit5;
    private String terminzpit5;
    private Double vat;
    private String terminzvat;

    public ZobKwota() {
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

   
    public Double getZus51() {
        return zus51;
    }

    public void setZus51(Double zus51) {
        this.zus51 = zus51;
    }

    public Date getZus51zapl() {
        return zus51zapl;
    }

    public void setZus51zapl(Date zus51zapl) {
        this.zus51zapl = zus51zapl;
    }

  

    public Double getZus52() {
        return zus52;
    }

    public void setZus52(Double zus52) {
        this.zus52 = zus52;
    }

    public Date getZus52zapl() {
        return zus52zapl;
    }

    public void setZus52zapl(Date zus52zapl) {
        this.zus52zapl = zus52zapl;
    }

   

    public Double getZus53() {
        return zus53;
    }

    public void setZus53(Double zus53) {
        this.zus53 = zus53;
    }

    public String getTerminzuz() {
        return terminzuz;
    }

    public void setTerminzuz(String terminzuz) {
        this.terminzuz = terminzuz;
    }

    public Double getPit4() {
        return pit4;
    }

    public void setPit4(Double pit4) {
        this.pit4 = pit4;
    }

    public String getTerminzpit4() {
        return terminzpit4;
    }

    public void setTerminzpit4(String terminzpit4) {
        this.terminzpit4 = terminzpit4;
    }

    public Double getPit5() {
        return pit5;
    }

    public void setPit5(Double pit5) {
        this.pit5 = pit5;
    }

    public String getTerminzpit5() {
        return terminzpit5;
    }

    public void setTerminzpit5(String terminzpit5) {
        this.terminzpit5 = terminzpit5;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public String getTerminzvat() {
        return terminzvat;
    }

    public void setTerminzvat(String terminzvat) {
        this.terminzvat = terminzvat;
    }

    public Date getZus53zapl() {
        return zus53zapl;
    }

    public void setZus53zapl(Date zus53zapl) {
        this.zus53zapl = zus53zapl;
    }

    
    
    public Double getZus51ods() {
        return zus51ods;
    }

    public void setZus51ods(Double zus51ods) {
        this.zus51ods = zus51ods;
    }

    public Double getZus52ods() {
        return zus52ods;
    }

    public void setZus52ods(Double zus52ods) {
        this.zus52ods = zus52ods;
    }

    public Double getZus53ods() {
        return zus53ods;
    }

    public void setZus53ods(Double zus53ods) {
        this.zus53ods = zus53ods;
    }

    public Double getZus51suma() {
        return zus51suma;
    }

    public void setZus51suma(Double zus51suma) {
        this.zus51suma = zus51suma;
    }

    public Double getZus52suma() {
        return zus52suma;
    }

    public void setZus52suma(Double zus52suma) {
        this.zus52suma = zus52suma;
    }

    public Double getZus53suma() {
        return zus53suma;
    }

    public void setZus53suma(Double zus53suma) {
        this.zus53suma = zus53suma;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.rok);
        hash = 89 * hash + Objects.hashCode(this.miesiac);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZobKwota other = (ZobKwota) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.miesiac, other.miesiac)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZobKwota{" + "rok=" + rok + ", miesiac=" + miesiac + ", zus51=" + zus51 + ", zus51zapl=" + zus51zapl + ", zus51ods=" + zus51ods + ", zus52=" + zus52 + ", zus52ods=" + zus52ods + ", zus52zapl=" + zus52zapl + ", zus53=" + zus53 + ", zus53ods=" + zus53ods + ", terminzuz=" + terminzuz + ", zus53zapl=" + zus53zapl + ", pit4=" + pit4 + ", terminzpit4=" + terminzpit4 + ", pit5=" + pit5 + ", terminzpit5=" + terminzpit5 + ", vat=" + vat + ", terminzvat=" + terminzvat + '}';
    }
    
    
    
   
}
