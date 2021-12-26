/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import beanstesty.PasekwynagrodzenBean;
import entity.Angaz;
import entity.Kalendarzmiesiac;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Umowa;
import java.util.List;
import java.util.Objects;
import z.Z;

/**
 *
 * @author Osito
 */
public class Oddelegowanie {
    private int id;
    private Umowa umowa;
    private Kalendarzmiesiac kalendarz;
    private String rok;
    private String mc;
    private int liczbadni;
    private double brutto;
    private double polska;
    private double podatek;
    private double podatekpolska;
    private double różnica;
    private boolean chowaj;

    public Oddelegowanie(List<Kalendarzmiesiac> kalendarze, Angaz a, String rok, String mc, List<Podatki> stawkipodatkowe) {
        this.kalendarz = pobierzkalendarz(kalendarze, rok, mc);
        this.umowa = kalendarz.getUmowa();
        this.rok = rok;
        this.mc = mc;
        this.liczbadni = (int) kalendarz.getDnioddelegowania();
        this.brutto = Z.z(this.kalendarz.getPasek().getBrutto());
        double zagranica = pobierzzagranica(this.kalendarz.getPasek().getNaliczenieskladnikawynagrodzeniaList());
        this.polska = Z.z(this.brutto-zagranica);
        this.podatek = Z.z(this.kalendarz.getPasek().getPodatekdochodowy());
        this.podatekpolska = Z.z(symulacjaoblicz(kalendarz, polska, stawkipodatkowe));
        this.różnica = Z.z(this.podatek-this.podatekpolska);
    }
    
     public double symulacjaoblicz(Kalendarzmiesiac kalendarz, double polska, List<Podatki> stawkipodatkowe) {
        double zwrot = 0.0;
        int i = 1;
        if (polska>0.0) {
            if (stawkipodatkowe != null && !stawkipodatkowe.isEmpty()) {
                boolean zlecenie0praca1 = true;
                Pasekwynagrodzen pasek = PasekwynagrodzenBean.obliczWynagrodzeniesymulacja(kalendarz, stawkipodatkowe, zlecenie0praca1, polska);
                zwrot = pasek.getPodatekdochodowy()>0.0?pasek.getPodatekdochodowy():0.0;
            }
        }
        return Z.z(zwrot);
    }
    
    private double pobierzzagranica(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        double zwrot = 0.0;
        for (Naliczenieskladnikawynagrodzenia n : naliczenieskladnikawynagrodzeniaList) {
            if (n.getSkladnikwynagrodzenia()!=null&&n.getSkladnikwynagrodzenia().getKod().equals("13")) {
                zwrot = zwrot+n.getKwotadolistyplac();
            }
        }
        return zwrot;
    }
    
    private Kalendarzmiesiac pobierzkalendarz(List<Kalendarzmiesiac> kalendarze, String rok, String mc) {
        Kalendarzmiesiac zwrot = null;
        for (Kalendarzmiesiac k : kalendarze) {
            if (k.getRok().equals(rok)&&k.getMc().equals(mc)) {
                zwrot = k;
            }
        }
        return zwrot;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLiczbadni() {
        return liczbadni;
    }

    public void setLiczbadni(int liczbadni) {
        this.liczbadni = liczbadni;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    public Kalendarzmiesiac getKalendarz() {
        return kalendarz;
    }

    public void setKalendarz(Kalendarzmiesiac kalendarz) {
        this.kalendarz = kalendarz;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public double getPolska() {
        return polska;
    }

    public void setPolska(double polska) {
        this.polska = polska;
    }

    public double getPodatek() {
        return podatek;
    }

    public void setPodatek(double podatek) {
        this.podatek = podatek;
    }

    public double getPodatekpolska() {
        return podatekpolska;
    }

    public void setPodatekpolska(double podatekpolska) {
        this.podatekpolska = podatekpolska;
    }

    public double getRóżnica() {
        return różnica;
    }

    public void setRóżnica(double różnica) {
        this.różnica = różnica;
    }

    public boolean isChowaj() {
        return chowaj;
    }

    public void setChowaj(boolean chowaj) {
        this.chowaj = chowaj;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.umowa);
        hash = 29 * hash + Objects.hashCode(this.kalendarz);
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
        final Oddelegowanie other = (Oddelegowanie) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.umowa, other.umowa)) {
            return false;
        }
        if (!Objects.equals(this.kalendarz, other.kalendarz)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Oddelegowanie{" + "umowa=" + umowa.getNazwiskoImie() + ", kalendarz=" + kalendarz.getRokMc() + ", liczbadni=" + liczbadni + '}';
    }

    

   
    
    
    
}
