/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class DRASumy {
    private Integer id;
    private Zusdra zusdra;
    private Zusrca zusrca;
    private List<UbezpZusrca> ubezpZusrca;
    private String rok;
    private String mc;

    public DRASumy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Zusdra getZusdra() {
        return zusdra;
    }

    public void setZusdra(Zusdra zusdra) {
        this.zusdra = zusdra;
    }

    public Zusrca getZusrca() {
        return zusrca;
    }

    public void setZusrca(Zusrca zusrca) {
        this.zusrca = zusrca;
    }

    
    public List<UbezpZusrca> getUbezpZusrca() {
        return ubezpZusrca;
    }

    public void setUbezpZusrca(List<UbezpZusrca> ubezpZusrca) {
        this.ubezpZusrca = ubezpZusrca;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.zusdra);
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
        final DRASumy other = (DRASumy) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.zusdra, other.zusdra)) {
            return false;
        }
        return true;
    }
    
    public String getNazwa() {
        String zwrot = this.zusdra.getIi6Nazwaskr();
        if (zwrot == null) {
            zwrot = this.zusdra.getIi7Nazwisko()+" "+this.zusdra.getIi8Imiepierw();
        }
        return zwrot;
    }
    
    public int getUbezpieczeni() {
        int suma = 0;
        if (this.zusrca!=null) {
            suma = this.zusrca.getLPozycji();
        } else if (this.zusdra!=null && this.zusdra.getIii1Lubezp()!=null) {
            suma = this.zusdra.getIii1Lubezp();
        }
        return suma;
    }
    
    public int getPrzedsiebiorcy() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    suma = suma+1;
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getIii1Lubezp()!=null) {
            suma = this.zusdra.getIii1Lubezp();
        }
        return suma;
    }
    
     public int getInnetytuly() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (!p.getIiiB11kodtytub().startsWith("05")&&!p.getIiiB11kodtytub().startsWith("04")&&!p.getIiiB11kodtytub().startsWith("01")) {
                    suma = suma+1;
                }
            }
        }
        return suma;
    }
    
    
    public int getZleceniobiorcy() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("04")) {
                    suma = suma+1;
                }
            }
        }
        return suma;
    }
    public int getPracownicy() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("01")) {
                    suma = suma+1;
                }
            }
        }
        return suma;
    }
}
