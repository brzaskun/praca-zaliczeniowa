/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import embeddable.VatKorektaDok;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "VATDeklaracjaKorektaDok")
public class VATDeklaracjaKorektaDok  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(min = 1, max = 255)
    @Column(name = "id")
    private int id;
    @OneToOne
    
    private Deklaracjevat deklaracjaPierwotna;
    @OneToOne
    
    private Deklaracjevat deklaracjaKorekta;
    private List<VatKorektaDok> listadokumentowDoKorekty;
    @Column(name = "nowaWartoscVatZPrzeniesienia")
    private Integer nowaWartoscVatZPrzeniesienia;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
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
        final VATDeklaracjaKorektaDok other = (VATDeklaracjaKorektaDok) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("VATDeklaracjaKorektaDok{id=%s, deklaracjaPierwotna=%s, listadokumentowDoKorekty=%s, nowaWartoscVatZPrzeniesienia=%s%s", id, deklaracjaPierwotna.getIdentyfikator(), listadokumentowDoKorekty.size(), nowaWartoscVatZPrzeniesienia, '}');
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Deklaracjevat getDeklaracjaPierwotna() {
        return deklaracjaPierwotna;
    }

    public void setDeklaracjaPierwotna(Deklaracjevat deklaracjaPierwotna) {
        this.deklaracjaPierwotna = deklaracjaPierwotna;
    }

    public Deklaracjevat getDeklaracjaKorekta() {
        return deklaracjaKorekta;
    }

    public void setDeklaracjaKorekta(Deklaracjevat deklaracjaKorekta) {
        this.deklaracjaKorekta = deklaracjaKorekta;
    }

    public List<VatKorektaDok> getListadokumentowDoKorekty() {
        return listadokumentowDoKorekty;
    }

    public void setListadokumentowDoKorekty(List<VatKorektaDok> listadokumentowDoKorekty) {
        this.listadokumentowDoKorekty = listadokumentowDoKorekty;
    }

    public Integer getNowaWartoscVatZPrzeniesienia() {
        return nowaWartoscVatZPrzeniesienia;
    }

    public void setNowaWartoscVatZPrzeniesienia(Integer nowaWartoscVatZPrzeniesienia) {
        this.nowaWartoscVatZPrzeniesienia = nowaWartoscVatZPrzeniesienia;
    }
    
    
    
}
