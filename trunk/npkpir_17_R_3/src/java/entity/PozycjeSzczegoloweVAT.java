/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Osito
 */
@Entity
public class PozycjeSzczegoloweVAT implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String pole20;
    String pole21;
    String pole22;
    String pole23;
    String pole24;
    String pole25;
    String pole26;
    String pole27;
    String pole28;
    String pole29;
    String pole30;
    String pole31;
    String pole32;
    String pole33;
    String pole34;
    String pole35;
    String pole36;
    String pole37;
    String pole38;
    String pole39;
    String pole40;
    String pole41;
    String pole42;
    String pole43;
    String pole44;
    String pole45;
    String pole46;
    String pole47;
    String pole48;
    String pole49;
    String pole50;
    String pole51;
    String pole52;
    String pole53;
    String pole54;
    String pole55;
    String pole56;
    String pole57;
    String pole58;
    String pole59;
    String pole60;
    String pole61;
    String pole62;
    String pole66;
    String pole64;
    String pole65;
    
    List<String> lista;

    public PozycjeSzczegoloweVAT() {
        lista = new ArrayList<>();
        lista.add(pole20);
        lista.add(pole21);
        lista.add(pole22);
        lista.add(pole23);
        lista.add(pole24);
        lista.add(pole25);
        lista.add(pole26);
        lista.add(pole27);
        lista.add(pole28);
        lista.add(pole29);
        lista.add(pole30);
        lista.add(pole31);
        lista.add(pole32);
        lista.add(pole33);
        lista.add(pole34);
        lista.add(pole35);
        lista.add(pole36);
        lista.add(pole37);
        lista.add(pole38);
        lista.add(pole39);
        lista.add(pole40);
        lista.add(pole41);
        lista.add(pole42);
        lista.add(pole43);
        lista.add(pole44);
        lista.add(pole45);
        lista.add(pole46);
        lista.add(pole47);
        lista.add(pole48);
        lista.add(pole49);
        lista.add(pole50);
        lista.add(pole51);
        lista.add(pole52);
        lista.add(pole53);
        lista.add(pole54);
        lista.add(pole55);
        lista.add(pole56);
        lista.add(pole57);
        lista.add(pole58);
        lista.add(pole59);
        lista.add(pole60);
        lista.add(pole61);
        lista.add(pole62);
        lista.add(pole66);
        lista.add(pole64);
        lista.add(pole65);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PozycjeSzczegoloweVAT)) {
            return false;
        }
        PozycjeSzczegoloweVAT other = (PozycjeSzczegoloweVAT) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.NewEntity[ id=" + id + " ]";
    }

    public String getPole20() {
        return pole20;
    }

    public void setPole20(String pole20) {
        this.pole20 = pole20;
    }

    public String getPole21() {
        return pole21;
    }

    public void setPole21(String pole21) {
        this.pole21 = pole21;
    }

    public String getPole22() {
        return pole22;
    }

    public void setPole22(String pole22) {
        this.pole22 = pole22;
    }

    public String getPole23() {
        return pole23;
    }

    public void setPole23(String pole23) {
        this.pole23 = pole23;
    }

    public String getPole24() {
        return pole24;
    }

    public void setPole24(String pole24) {
        this.pole24 = pole24;
    }

    public String getPole25() {
        return pole25;
    }

    public void setPole25(String pole25) {
        this.pole25 = pole25;
    }

    public String getPole26() {
        return pole26;
    }

    public void setPole26(String pole26) {
        this.pole26 = pole26;
    }

    public String getPole27() {
        return pole27;
    }

    public void setPole27(String pole27) {
        this.pole27 = pole27;
    }

    public String getPole28() {
        return pole28;
    }

    public void setPole28(String pole28) {
        this.pole28 = pole28;
    }

    public String getPole29() {
        return pole29;
    }

    public void setPole29(String pole29) {
        this.pole29 = pole29;
    }

    public String getPole30() {
        return pole30;
    }

    public void setPole30(String pole30) {
        this.pole30 = pole30;
    }

    public String getPole31() {
        return pole31;
    }

    public void setPole31(String pole31) {
        this.pole31 = pole31;
    }

    public String getPole32() {
        return pole32;
    }

    public void setPole32(String pole32) {
        this.pole32 = pole32;
    }

    public String getPole33() {
        return pole33;
    }

    public void setPole33(String pole33) {
        this.pole33 = pole33;
    }

    public String getPole34() {
        return pole34;
    }

    public void setPole34(String pole34) {
        this.pole34 = pole34;
    }

    public String getPole35() {
        return pole35;
    }

    public void setPole35(String pole35) {
        this.pole35 = pole35;
    }

    public String getPole36() {
        return pole36;
    }

    public void setPole36(String pole36) {
        this.pole36 = pole36;
    }

    public String getPole37() {
        return pole37;
    }

    public void setPole37(String pole37) {
        this.pole37 = pole37;
    }

    public String getPole38() {
        return pole38;
    }

    public void setPole38(String pole38) {
        this.pole38 = pole38;
    }

    public String getPole39() {
        return pole39;
    }

    public void setPole39(String pole39) {
        this.pole39 = pole39;
    }

    public String getPole40() {
        return pole40;
    }

    public void setPole40(String pole40) {
        this.pole40 = pole40;
    }

    public String getPole41() {
        return pole41;
    }

    public void setPole41(String pole41) {
        this.pole41 = pole41;
    }

    public String getPole42() {
        return pole42;
    }

    public void setPole42(String pole42) {
        this.pole42 = pole42;
    }

    public String getPole43() {
        return pole43;
    }

    public void setPole43(String pole43) {
        this.pole43 = pole43;
    }

    public String getPole44() {
        return pole44;
    }

    public void setPole44(String pole44) {
        this.pole44 = pole44;
    }

    public String getPole45() {
        return pole45;
    }

    public void setPole45(String pole45) {
        this.pole45 = pole45;
    }

    public String getPole46() {
        return pole46;
    }

    public void setPole46(String pole46) {
        this.pole46 = pole46;
    }

    public String getPole47() {
        return pole47;
    }

    public void setPole47(String pole47) {
        this.pole47 = pole47;
    }

    public String getPole48() {
        return pole48;
    }

    public void setPole48(String pole48) {
        this.pole48 = pole48;
    }

    public String getPole49() {
        return pole49;
    }

    public void setPole49(String pole49) {
        this.pole49 = pole49;
    }

    public String getPole50() {
        return pole50;
    }

    public void setPole50(String pole50) {
        this.pole50 = pole50;
    }

    public String getPole51() {
        return pole51;
    }

    public void setPole51(String pole51) {
        this.pole51 = pole51;
    }

    public String getPole52() {
        return pole52;
    }

    public void setPole52(String pole52) {
        this.pole52 = pole52;
    }

    public String getPole53() {
        return pole53;
    }

    public void setPole53(String pole53) {
        this.pole53 = pole53;
    }

    public String getPole54() {
        return pole54;
    }

    public void setPole54(String pole54) {
        this.pole54 = pole54;
    }

    public String getPole55() {
        return pole55;
    }

    public void setPole55(String pole55) {
        this.pole55 = pole55;
    }

    public String getPole56() {
        return pole56;
    }

    public void setPole56(String pole56) {
        this.pole56 = pole56;
    }

    public String getPole57() {
        return pole57;
    }

    public void setPole57(String pole57) {
        this.pole57 = pole57;
    }

    public String getPole58() {
        return pole58;
    }

    public void setPole58(String pole58) {
        this.pole58 = pole58;
    }

    public String getPole59() {
        return pole59;
    }

    public void setPole59(String pole59) {
        this.pole59 = pole59;
    }

    public String getPole60() {
        return pole60;
    }

    public void setPole60(String pole60) {
        this.pole60 = pole60;
    }

    public String getPole61() {
        return pole61;
    }

    public void setPole61(String pole61) {
        this.pole61 = pole61;
    }

    public String getPole62() {
        return pole62;
    }

    public void setPole62(String pole62) {
        this.pole62 = pole62;
    }

    public String getPole66() {
        return pole66;
    }

    public void setPole66(String pole66) {
        this.pole66 = pole66;
    }

    public String getPole64() {
        return pole64;
    }

    public void setPole64(String pole64) {
        this.pole64 = pole64;
    }

    public String getPole65() {
        return pole65;
    }

    public void setPole65(String pole65) {
        this.pole65 = pole65;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }
    
    
}
