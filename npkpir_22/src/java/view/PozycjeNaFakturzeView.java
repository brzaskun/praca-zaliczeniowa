/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PozycjenafakturzeDAO;
import entity.Pozycjenafakturze;
import entity.PozycjenafakturzePK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PozycjeNaFakturzeView implements Serializable{
    
    private int lewy;
    private int gora;
    private String co;
    private static String lewyTablica;
    private static String goraTablica;
    private static String coTablica;
    private static final List<Faktura> zestaw;
    
    static{
        zestaw = new ArrayList<>();
        zestaw.add(new Faktura(1, "serek topiony", "20.45", "kg", 12, 8.5, 125, "23%", 12, 147));
        zestaw.add(new Faktura(1, "koperek topiony", "20.45", "kg", 12, 8.5, 125, "23%", 12, 147));
        zestaw.add(new Faktura(1, "marchewka topiona", "20.45", "kg", 12, 8.5, 125, "23%", 12, 147));
    }
    
    public static class Faktura {
        int lp;
        String nazwa;
        String PKWiU;
        String jednostka;
        double ilosc;
        double cena;
        double netto;
        String podatek;
        double podatekkwota;
        double brutto;

        public Faktura(int lp, String nazwa, String PKWiU, String jednostka, double ilosc, double cena, double netto, String podatek, double podatekkwota, double brutto) {
            this.lp = lp;
            this.nazwa = nazwa;
            this.PKWiU = PKWiU;
            this.jednostka = jednostka;
            this.ilosc = ilosc;
            this.cena = cena;
            this.netto = netto;
            this.podatek = podatek;
            this.podatekkwota = podatekkwota;
            this.brutto = brutto;
        }

        public int getLp() {
            return lp;
        }

        public void setLp(int lp) {
            this.lp = lp;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public String getPKWiU() {
            return PKWiU;
        }

        public void setPKWiU(String PKWiU) {
            this.PKWiU = PKWiU;
        }

        public String getJednostka() {
            return jednostka;
        }

        public void setJednostka(String jednostka) {
            this.jednostka = jednostka;
        }

        public double getIlosc() {
            return ilosc;
        }

        public void setIlosc(double ilosc) {
            this.ilosc = ilosc;
        }

        public double getCena() {
            return cena;
        }

        public void setCena(double cena) {
            this.cena = cena;
        }

        public double getNetto() {
            return netto;
        }

        public void setNetto(double netto) {
            this.netto = netto;
        }

        public String getPodatek() {
            return podatek;
        }

        public void setPodatek(String podatek) {
            this.podatek = podatek;
        }

        public double getPodatekkwota() {
            return podatekkwota;
        }

        public void setPodatekkwota(double podatekkwota) {
            this.podatekkwota = podatekkwota;
        }

        public double getBrutto() {
            return brutto;
        }

        public void setBrutto(double brutto) {
            this.brutto = brutto;
        }
        
    }
    
    @Inject private PozycjenafakturzeDAO pozycjeDAO;
    
    public void zachowajpozycje(){
    System.out.println("lolo");
    PozycjenafakturzePK klucz = new PozycjenafakturzePK();
        klucz.setNazwa(co);
        klucz.setPodatnik("Test");
    Pozycjenafakturze pozycje = new Pozycjenafakturze(klucz, true, gora, lewy);
    try{
        pozycjeDAO.dodaj(pozycje);
    } catch (Exception e){
        pozycjeDAO.edit(pozycje);
    }
    Msg.msg("i", pozycje.toString(),"form:messages");
    }

   public void odchowaj(){
       List<Pozycjenafakturze> lista = pozycjeDAO.findAll();
       if(!lista.isEmpty()){
           lewyTablica = "";
           goraTablica = "";
           coTablica = "";
       for(Pozycjenafakturze p : lista){
        lewyTablica = lewyTablica + p.getLewy()+",";
        goraTablica = goraTablica + p.getGora()+",";
        coTablica = coTablica + p.getPozycjenafakturzePK().getNazwa()+",";
       }
       }
   }

    public int getLewy() {
        return lewy;
    }

    public void setLewy(int lewy) {
        this.lewy = lewy;
    }

    public int getGora() {
        return gora;
    }

    public void setGora(int gora) {
        this.gora = gora;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getLewyTablica() {
        return lewyTablica;
    }

    public void setLewyTablica(String lewyTablica) {
        PozycjeNaFakturzeView.lewyTablica = lewyTablica;
    }

    public String getGoraTablica() {
        return goraTablica;
    }

    public void setGoraTablica(String goraTablica) {
        PozycjeNaFakturzeView.goraTablica = goraTablica;
    }

    public String getCoTablica() {
        return coTablica;
    }

    public void setCoTablica(String coTablica) {
        PozycjeNaFakturzeView.coTablica = coTablica;
    }

    public List<Faktura> getZestaw() {
        return zestaw;
    }

    
   
}
