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
    private String okres;
    

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

    public String getOkres() {
        return okres;
    }

    public void setOkres(String okres) {
        this.okres = okres;
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
    
    public String getKod() {
        String suma = "n.d.";
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    suma = p.getIiiB11kodtytub()!=null?String.valueOf(p.getIiiB11kodtytub()):"brak";
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getXi11kodtytub()!=null) {
            suma = this.zusdra.getXi11kodtytub()!=null?String.valueOf(this.zusdra.getXi11kodtytub()):"brak";
        }
        return suma;
    }
    
    public double getSpoleczne() {
        double suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    double em = p.getIiiB7KwskleuRN()!= null ? p.getIiiB7KwskleuRN().doubleValue() :0.0;
                    double ren = p.getIiiB8KwsklruRN()!= null ? p.getIiiB8KwsklruRN().doubleValue() :0.0;
                    double cho = p.getIiiB9KwsklchuRN()!= null ? p.getIiiB9KwsklchuRN().doubleValue() :0.0;
                    double wyp = p.getIiiB10KwsklwyuR()!= null ? p.getIiiB10KwsklwyuR().doubleValue() :0.0;
                    suma = suma+em+ren+cho+wyp;
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getIv32Kwskspol()!=null) {
            suma = this.zusdra.getIv32Kwskspol().doubleValue();
        }
        return suma;
    }
    
     public double getZdrowotne() {
        double suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    double zdr = p.getIiiC4KwsklzuR()!= null ? p.getIiiC4KwsklzuR().doubleValue() :0.0;
                    suma = suma+zdr;
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getVii4Kwzap()!=null) {
            suma = this.zusdra.getVii4Kwzap().doubleValue();
        }
        return suma;
    }
}
