/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.util.List;

/**
 *
 * @author Osito
 */
public class ZestawienieRyczalt {
    private int id;
    private String okres;
    private double s170;
    private double s150;
    private double s125;
    private double s100;
    private double s85;
    private double s55;
    private double s30;

    public ZestawienieRyczalt(int id, String okres, List<Double> mapa) {
        this.id = id;
        this.okres = okres;
        this.s170 = mapa.get(0);
        this.s150 = mapa.get(1);
        this.s125 = mapa.get(2);
        this.s100 = mapa.get(3);
        this.s85 = mapa.get(4);
        this.s55 = mapa.get(5);
        this.s30 = mapa.get(6);
    }

    public ZestawienieRyczalt(int id, String okres, double s170, double s150, double s125, double s100, double s85, double s55, double s30) {
        this.id = id;
        this.okres = okres;
        this.s170 = s170;
        this.s150 = s150;
        this.s125 = s125;
        this.s100 = s100;
        this.s85 = s85;
        this.s55 = s55;
        this.s30 = s30;
    }
    
     

    public ZestawienieRyczalt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOkres() {
        return okres;
    }

    public void setOkres(String okres) {
        this.okres = okres;
    }

    public double getS170() {
        return s170;
    }

    public void setS170(double s170) {
        this.s170 = s170;
    }

    public double getS150() {
        return s150;
    }

    public void setS150(double s150) {
        this.s150 = s150;
    }

    public double getS125() {
        return s125;
    }

    public void setS125(double s125) {
        this.s125 = s125;
    }

    public double getS100() {
        return s100;
    }

    public void setS100(double s100) {
        this.s100 = s100;
    }

    public double getS85() {
        return s85;
    }

    public void setS85(double s85) {
        this.s85 = s85;
    }

    public double getS55() {
        return s55;
    }

    public void setS55(double s55) {
        this.s55 = s55;
    }

    public double getS30() {
        return s30;
    }

    public void setS30(double s30) {
        this.s30 = s30;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
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
        final ZestawienieRyczalt other = (ZestawienieRyczalt) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZestawienieRyczalt{" + "id=" + id + ", okres=" + okres + ", s170=" + s170 + ", s85=" + s85 + ", s55=" + s55 + ", s30=" + s30 + '}';
    }
    
    
}
