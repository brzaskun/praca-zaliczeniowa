/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package embeddable;

import entity.Podatnik;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class PodatnikRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Podatnik podatnik;
    private int day1;
    private int day2;
    private int day3;
    private int day4;
    private int day5;
    private int day6;
    private int day7;
    private int day8;
    private int day9;
    private int day10;
    private int day11;
    private int day12;
    private int day13;
    private int day14;
    private int day15;
    private int day16;
    private int day17;
    private int day18;
    private int day19;
    private int day20;
    private int day21;
    private int day22;
    private int day23;
    private int day24;
    private int day25;
    private int day26;
    private int day27;
    private int day28;
    private int day29;
    private int day30;
    private int day31;
    private Date zamkniecie;

    public Podatnik getPodatnik() {
        return podatnik;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.podatnik);
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
        final PodatnikRecord other = (PodatnikRecord) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.podatnik, other.podatnik);
    }
    
    public int getTotalDays() {
        return day1 + day2 + day3 + day4 + day5 + day6 + day7 + day8 + day9 + day10 +
               day11 + day12 + day13 + day14 + day15 + day16 + day17 + day18 + day19 + day20 +
               day21 + day22 + day23 + day24 + day25 + day26 + day27 + day28 + day29 + day30 + day31;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public int getDay3() {
        return day3;
    }

    public void setDay3(int day3) {
        this.day3 = day3;
    }

    public int getDay4() {
        return day4;
    }

    public void setDay4(int day4) {
        this.day4 = day4;
    }

    public int getDay5() {
        return day5;
    }

    public void setDay5(int day5) {
        this.day5 = day5;
    }

    public int getDay6() {
        return day6;
    }

    public void setDay6(int day6) {
        this.day6 = day6;
    }

    public int getDay7() {
        return day7;
    }

    public void setDay7(int day7) {
        this.day7 = day7;
    }

    public int getDay8() {
        return day8;
    }

    public void setDay8(int day8) {
        this.day8 = day8;
    }

    public int getDay9() {
        return day9;
    }

    public void setDay9(int day9) {
        this.day9 = day9;
    }

    public int getDay10() {
        return day10;
    }

    public void setDay10(int day10) {
        this.day10 = day10;
    }

    public int getDay11() {
        return day11;
    }

    public void setDay11(int day11) {
        this.day11 = day11;
    }

    public int getDay12() {
        return day12;
    }

    public void setDay12(int day12) {
        this.day12 = day12;
    }

    public int getDay13() {
        return day13;
    }

    public void setDay13(int day13) {
        this.day13 = day13;
    }

    public int getDay14() {
        return day14;
    }

    public void setDay14(int day14) {
        this.day14 = day14;
    }

    public int getDay15() {
        return day15;
    }

    public void setDay15(int day15) {
        this.day15 = day15;
    }

    public int getDay16() {
        return day16;
    }

    public void setDay16(int day16) {
        this.day16 = day16;
    }

    public int getDay17() {
        return day17;
    }

    public void setDay17(int day17) {
        this.day17 = day17;
    }

    public int getDay18() {
        return day18;
    }

    public void setDay18(int day18) {
        this.day18 = day18;
    }

    public int getDay19() {
        return day19;
    }

    public void setDay19(int day19) {
        this.day19 = day19;
    }

    public int getDay20() {
        return day20;
    }

    public void setDay20(int day20) {
        this.day20 = day20;
    }

    public int getDay21() {
        return day21;
    }

    public void setDay21(int day21) {
        this.day21 = day21;
    }

    public int getDay22() {
        return day22;
    }

    public void setDay22(int day22) {
        this.day22 = day22;
    }

    public int getDay23() {
        return day23;
    }

    public void setDay23(int day23) {
        this.day23 = day23;
    }

    public int getDay24() {
        return day24;
    }

    public void setDay24(int day24) {
        this.day24 = day24;
    }

    public int getDay25() {
        return day25;
    }

    public void setDay25(int day25) {
        this.day25 = day25;
    }

    public int getDay26() {
        return day26;
    }

    public void setDay26(int day26) {
        this.day26 = day26;
    }

    public int getDay27() {
        return day27;
    }

    public void setDay27(int day27) {
        this.day27 = day27;
    }

    public int getDay28() {
        return day28;
    }

    public void setDay28(int day28) {
        this.day28 = day28;
    }

    public int getDay29() {
        return day29;
    }

    public void setDay29(int day29) {
        this.day29 = day29;
    }

    public int getDay30() {
        return day30;
    }

    public void setDay30(int day30) {
        this.day30 = day30;
    }

    public int getDay31() {
        return day31;
    }

    public void setDay31(int day31) {
        this.day31 = day31;
    }

    public Date getZamkniecie() {
        return zamkniecie;
    }

    public void setZamkniecie(Date zamkniecie) {
        this.zamkniecie = zamkniecie;
    }

}
