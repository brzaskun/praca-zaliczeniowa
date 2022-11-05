/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Pracownik;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class OddelegowanieTabela {
    private Pracownik pracownik;
    private String rok;
    private Oddelegowanie o_01;
    private Oddelegowanie o_02;
    private Oddelegowanie o_03;
    private Oddelegowanie o_04;
    private Oddelegowanie o_05;
    private Oddelegowanie o_06;
    private Oddelegowanie o_07;
    private Oddelegowanie o_08;
    private Oddelegowanie o_09;
    private Oddelegowanie o_10;
    private Oddelegowanie o_11;
    private Oddelegowanie o_12;
    private double sumadni;
    private double przychodpolski;
    private double podatekpolski;
    private double przychodzagraniczny;
    private double podatekzagraniczny;
    private String rokmcprzekroczenia;

    public OddelegowanieTabela(Oddelegowanie p) {
        this.pracownik = p.getAngaz().getPracownik();
        this.rok = p.getRok();
        switch (p.getMc()) {
            case "01":
            this.o_01 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "02":
            this.o_02 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "03":
            this.o_03 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "04":
            this.o_04 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "05":
            this.o_05 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "06":
            this.o_06 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "07":
            this.o_07 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "08":
            this.o_08 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "09":
            this.o_09 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "10":
            this.o_10 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "11":
            this.o_11 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
            case "12":
            this.o_12 = p;
            this.sumadni = this.sumadni+p.getLiczbadni();
            this.podatekpolski = this.podatekpolski+p.getPodatekpolska();
            this.przychodzagraniczny = this.przychodzagraniczny+p.getPrzychodyzagranica();
            this.przychodpolski = this.przychodpolski+p.getPrzychodypolska();
            this.podatekzagraniczny = this.podatekzagraniczny+p.getPodatekzagranica();
            break;
        }
    }

    public void sumujskladniki() {
        
    }
    
    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Oddelegowanie getO_01() {
        return o_01;
    }

    public void setO_01(Oddelegowanie o_01) {
        this.o_01 = o_01;
    }

    public Oddelegowanie getO_02() {
        return o_02;
    }

    public void setO_02(Oddelegowanie o_02) {
        this.o_02 = o_02;
    }

    public Oddelegowanie getO_03() {
        return o_03;
    }

    public void setO_03(Oddelegowanie o_03) {
        this.o_03 = o_03;
    }

    public Oddelegowanie getO_04() {
        return o_04;
    }

    public void setO_04(Oddelegowanie o_04) {
        this.o_04 = o_04;
    }

    public Oddelegowanie getO_05() {
        return o_05;
    }

    public void setO_05(Oddelegowanie o_05) {
        this.o_05 = o_05;
    }

    public Oddelegowanie getO_06() {
        return o_06;
    }

    public void setO_06(Oddelegowanie o_06) {
        this.o_06 = o_06;
    }

    public Oddelegowanie getO_07() {
        return o_07;
    }

    public void setO_07(Oddelegowanie o_07) {
        this.o_07 = o_07;
    }

    public Oddelegowanie getO_08() {
        return o_08;
    }

    public void setO_08(Oddelegowanie o_08) {
        this.o_08 = o_08;
    }

    public Oddelegowanie getO_09() {
        return o_09;
    }

    public void setO_09(Oddelegowanie o_09) {
        this.o_09 = o_09;
    }

    public Oddelegowanie getO_10() {
        return o_10;
    }

    public void setO_10(Oddelegowanie o_10) {
        this.o_10 = o_10;
    }

    public Oddelegowanie getO_11() {
        return o_11;
    }

    public void setO_11(Oddelegowanie o_11) {
        this.o_11 = o_11;
    }

    public Oddelegowanie getO_12() {
        return o_12;
    }

    public void setO_12(Oddelegowanie o_12) {
        this.o_12 = o_12;
    }

    

    public double getSumadni() {
        return sumadni;
    }

    public void setSumadni(double sumadni) {
        this.sumadni = sumadni;
    }

    public String getRokmcprzekroczenia() {
        return rokmcprzekroczenia;
    }

    public void setRokmcprzekroczenia(String rokmcprzekroczenia) {
        this.rokmcprzekroczenia = rokmcprzekroczenia;
    }

    public double getPrzychodpolski() {
        return przychodpolski;
    }

    public void setPrzychodpolski(double przychodpolski) {
        this.przychodpolski = przychodpolski;
    }

    public double getPodatekpolski() {
        return podatekpolski;
    }

    public void setPodatekpolski(double podatekpolski) {
        this.podatekpolski = podatekpolski;
    }

    public double getPrzychodzagraniczny() {
        return przychodzagraniczny;
    }

    public void setPrzychodzagraniczny(double przychodzagraniczny) {
        this.przychodzagraniczny = przychodzagraniczny;
    }

    public double getPodatekzagraniczny() {
        return podatekzagraniczny;
    }

    public void setPodatekzagraniczny(double podatekzagraniczny) {
        this.podatekzagraniczny = podatekzagraniczny;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.pracownik);
        hash = 71 * hash + Objects.hashCode(this.rok);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.sumadni) ^ (Double.doubleToLongBits(this.sumadni) >>> 32));
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
        final OddelegowanieTabela other = (OddelegowanieTabela) obj;
        if (Double.doubleToLongBits(this.sumadni) != Double.doubleToLongBits(other.sumadni)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.pracownik, other.pracownik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OddelegowanieTabela{" + "pracownik=" + pracownik.getNazwiskoImie() + ", rok=" + rok + ", o_01=" + o_01 + ", o_02=" + o_02 + ", o_03=" + o_03 + ", o_04=" + o_04 + ", o_05=" + o_05 + ", o_06=" + o_06 + ", o_07=" + o_07 + ", o_08=" + o_08 + ", o_09=" + o_09 + ", o_10=" + o_10 + ", o_11=" + o_11 + ", o_12=" + o_12 + ", suma=" + sumadni + ", rokmcprzekroczenia=" + rokmcprzekroczenia + '}';
    }

  
    
    
    
}
