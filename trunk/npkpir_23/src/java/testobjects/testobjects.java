/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;

import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.RodzajedokPK;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class testobjects {
    
    public static Dokfk getDokfk(String rodzaj) {
        DokfkPK dp = new DokfkPK(rodzaj, 12, "WZORCOWY", "2015");
        Dokfk d = new Dokfk(dp);
        d.setDatadokumentu("2015-03-01");
        d.setDataoperacji("2015-03-02");
        d.setDatawplywu("2015-03-05");
        d.setDatawystawienia("2015-03-06");
        d.setRodzajedok(getRodzajedok(rodzaj));
        d.setPodatnikObj(getPodatnik());
        d.setKontr(getKlienci());
        d.setNumerwlasnydokfk("1/23/345/z");
        d.setMiesiac("02");
        return d;
    }
    
    public static Rodzajedok getRodzajedok(String rodzaj) {
        RodzajedokPK rp = new RodzajedokPK(rodzaj, "WZORCOWY");
        Rodzajedok r = new Rodzajedok(rp);
        r.setKategoriadokumentu(0);
        r.setSkrot(rodzaj);
        return r;
    }
    
    public static Podatnik getPodatnik() {
        Podatnik p = new Podatnik();
        p.setNip("8511005008");
        p.setImie("Imie");
        p.setNazwisko("Nazwisko");
        p.setMiejscowosc("Szczecin");
        p.setUlica("Ulica");
        p.setNrdomu("20");
        p.setNrlokalu("2");
        p.setPoczta("Szczecin");
        p.setKodpocztowy("70-100");
        p.setNazwapelna("Wzorcowy Janek Nazwa Pelna");
        return p;
    }
    
    public static Klienci getKlienci() {
        Klienci p = new Klienci();
        p.setNip("8511005008");
        p.setNskrocona("Nazwa skrocona Kontr");
        p.setMiejscowosc("Szczecin");
        p.setUlica("UlicaDluga");
        p.setDom("20");
        p.setLokal("2");
        p.setKodpocztowy("70-100");
        p.setNpelna("Wzorcowy Kontrahent Nazwa Pelna");
        return p;
    }
    
   public static List[] getTabela() {
       List n = new ArrayList();
       n.add("naglowek 1");
       n.add("naglowek 2");
       n.add("naglowek 3");
       List t = new ArrayList();
       t.add(new WierszTabeli(1, "opis1", 123.0));
       t.add(new WierszTabeli(2, "opis2", 4444.0));
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaKonta() {
       List n = new ArrayList();
       n.add("lp");
       n.add("opis");
       n.add("kwota Wn");
       n.add("konto Wn");
       n.add("kwota Ma");
       n.add("konto Ma");
       List t = getWierszeKonta(getWiersze());
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List<Wiersz> getWiersze() {
       List<Wiersz> l = new ArrayList<>();
       Wiersz w = new Wiersz(1,0);
//       (Integer id, String podatnik, String nrkonta, String syntetyczne, int analityka, String nazwapelna, String nazwaskrocona, 
//            String bilansowewynikowe, String zwyklerozrachszczegolne, String macierzyste, String pelnynumer, boolean rozwin, int rok,
//            String syntetycznenumer);
       w.setOpisWiersza("wiersz pierwszy testowy");
       w.setStronaWn(new StronaWiersza(w, "Wn", 1023.0, new Konto(1, "Wzorcowy", "100", "syntetyczne", 0, "kasa", "kasa", "bilansowe", "zwykle", "0", "100", true, 2015, "0")));
       w.setStronaMa(new StronaWiersza(w, "Ma", 23.0, new Konto(2, "Wzorcowy", "130", "syntetyczne", 0, "bank", "bank", "bilansowe", "zwykle", "0", "130", true, 2015, "0")));
       l.add(w);
       return l;
   }
   
   public static List<WierszKonta> getWierszeKonta(List<Wiersz> wiersze) {
       List<WierszKonta> w = new ArrayList<WierszKonta>();
       for (Wiersz p : wiersze) {
           WierszKonta r = new WierszKonta(p.getIdporzadkowy(), p.getOpisWiersza());
           if (p.getStronaWn() != null) {
               r.setKwotawn(p.getStronaWn().getKwota());
               r.setOpiskontawn(p.getStronaWn().getKonto().getPelnynumer()+ " " + p.getStronaWn().getKonto().getNazwapelna());
           }
           if (p.getStronaMa() != null) {
               r.setKwotama(p.getStronaMa().getKwota());
               r.setOpiskontama(p.getStronaMa().getKonto().getPelnynumer()+ " " + p.getStronaMa().getKonto().getNazwapelna());
           }
           w.add(r);
       }
       return w;
   }

   public static void main(String[] args) {
       Dokfk p = getDokfk("PK");
       List[] t = getTabela();
       List r = getWiersze();
       System.out.println(p.toString());
   }
   
    
}
