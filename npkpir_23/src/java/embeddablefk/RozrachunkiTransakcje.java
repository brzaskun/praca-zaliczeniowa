/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Transakcja;
import entityfk.Rozrachunekfk;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Osito
 */
public class RozrachunkiTransakcje  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Rozrachunekfk rozrachunekfk;
    private List<Transakcja> transakcje;

    public Rozrachunekfk getRozrachunekfk() {
        return rozrachunekfk;
    }

    public void setRozrachunekfk(Rozrachunekfk rozrachunekfk) {
        this.rozrachunekfk = rozrachunekfk;
    }

    public RozrachunkiTransakcje(Rozrachunekfk rozrachunekfk, List<Transakcja> transakcje) {
        this.rozrachunekfk = rozrachunekfk;
        this.transakcje = transakcje;
    }
    

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }
    
    
    
}
