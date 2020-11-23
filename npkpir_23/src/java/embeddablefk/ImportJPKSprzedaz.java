/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entity.Klienci;
import java.io.Serializable;
import java.util.Objects;
import jpkabstract.SprzedazWierszA;

/**
 *
 * @author Osito
 */
public class ImportJPKSprzedaz implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private SprzedazWierszA sprzedazWiersz;
    private Klienci klient;
    private boolean juzzaksiegowany;

    public ImportJPKSprzedaz() {
    }

    public ImportJPKSprzedaz(SprzedazWierszA p) {
        super();
        this.sprzedazWiersz = p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }

    public boolean isJuzzaksiegowany() {
        return juzzaksiegowany;
    }

    public void setJuzzaksiegowany(boolean juzzaksiegowany) {
        this.juzzaksiegowany = juzzaksiegowany;
    }

    public SprzedazWierszA getSprzedazWiersz() {
        return sprzedazWiersz;
    }

    public void setSprzedazWiersz(SprzedazWierszA sprzedazWiersz) {
        this.sprzedazWiersz = sprzedazWiersz;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.sprzedazWiersz);
        hash = 59 * hash + Objects.hashCode(this.klient);
        hash = 59 * hash + (this.juzzaksiegowany ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImportJPKSprzedaz other = (ImportJPKSprzedaz) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.juzzaksiegowany != other.juzzaksiegowany) {
            return false;
        }
        if (!Objects.equals(this.sprzedazWiersz, other.sprzedazWiersz)) {
            return false;
        }
        if (!Objects.equals(this.klient, other.klient)) {
            return false;
        }
        return true;
    }

    
    
}
