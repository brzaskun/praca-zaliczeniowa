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
import viewfk.CechyzapisuPrzegladView;
import viewfk.CechyzapisuPrzegladView.CechaStronaWiersza;

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
   
   public static List[] getTabelaKonta(List<Wiersz> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("opis");
       n.add("kwota Wn");
       n.add("konto Wn");
       n.add("kwota Ma");
       n.add("konto Ma");
       List t = getWierszeKonta(wiersze);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaCechyZapisow(List<CechaStronaWiersza> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("nazwa cechy");
       n.add("rodzaj cechy");
       n.add("id dokumentu");
       n.add("data dok");
       n.add("data operacji");
       n.add("opis");
       n.add("konto");
       n.add("kwota");
       List t = getWierszeCechyZapisow(wiersze);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaZaksiegowane(List<Dokfk> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("data dok");
       n.add("data operacji");
       n.add("id dok");
       n.add("kontrahent");
       n.add("numer wlasny");
       n.add("opis");  
       n.add("wartość");
       n.add("waluta");
       List t = getWierszeDokfk(wiersze);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaWDTWNT(List<Wiersz> wiersze) {
       List n = new ArrayList();
       n.add("id");
       n.add("data dok.");
       n.add("id dok");
       n.add("nr własny");
       n.add("lp wiersza");
       n.add("opis");
       n.add("kg");
       n.add("szt");
       n.add("kwota Wn");
       n.add("konto Wn");
       n.add("kwota Ma");
       n.add("konto Ma");
       List t = getWierszeWNTWDTKonta(wiersze);
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
               r.setKwotaWn(p.getStronaWn().getKwota());
               r.setOpiskontaWn(p.getStronaWn().getKonto().getPelnynumer()+ " " + p.getStronaWn().getKonto().getNazwapelna());
           }
           if (p.getStronaMa() != null) {
               r.setKwotaMa(p.getStronaMa().getKwota());
               r.setOpiskontaMa(p.getStronaMa().getKonto().getPelnynumer()+ " " + p.getStronaMa().getKonto().getNazwapelna());
           }
           w.add(r);
       }
       return w;
   }
   
   public static List<WierszWNTWDT> getWierszeWNTWDTKonta(List<Wiersz> wiersze) {
       List<WierszWNTWDT> w = new ArrayList<WierszWNTWDT>();
       for (Wiersz p : wiersze) {
           WierszWNTWDT l = new WierszWNTWDT(p.getIdwiersza(), p.getDataksiegowania() , p.getDokfkS(), p.getDokfk().getNumerwlasnydokfk(), p.getIdporzadkowy(),  p.getOpisWiersza(), p.getIlosc_kg(), p.getIlosc_szt());
           if (p.getStronaWn() != null) {
               l.setKwotaWn(p.getStronaWn().getKwota());
               l.setOpiskontaWn(p.getStronaWn().getKonto().getPelnynumer()+ " " + p.getStronaWn().getKonto().getNazwapelna());
           }
           if (p.getStronaMa() != null) {
               l.setKwotaMa(p.getStronaMa().getKwota());
               l.setOpiskontaMa(p.getStronaMa().getKonto().getPelnynumer()+ " " + p.getStronaMa().getKonto().getNazwapelna());
           }
           w.add(l);
       }
       return w;
   }
   
   public static List<WierszCecha> getWierszeCechyZapisow(List<CechaStronaWiersza> wiersze) {
       List<WierszCecha> w = new ArrayList<WierszCecha>();
       for (CechaStronaWiersza p : wiersze) {
           String opiskonta = p.getStronaWiersza().getKonto().getPelnynumer()+ " " + p.getStronaWiersza().getKonto().getNazwapelna();
           WierszCecha r = new WierszCecha(p.getId(), p.getCechazapisu().getCechazapisuPK().getNazwacechy(), p.getCechazapisu().getCechazapisuPK().getRodzajcechy(), p.getStronaWiersza().getDokfkS(), p.getStronaWiersza().getDokfk().getDatawystawienia(), p.getStronaWiersza().getDokfk().getDataoperacji(), p.getStronaWiersza().getWiersz().getOpisWiersza(), opiskonta, p.getStronaWiersza().getKwotaPLN());
           w.add(r);
       }
       return w;
   }
   
   public static List<WierszDokfk> getWierszeDokfk(List<Dokfk> wiersze) {
       List<WierszDokfk> w = new ArrayList<WierszDokfk>();
       for (Dokfk p : wiersze) {
           String kontrahent = p.getKontr().getNpelna()+" "+p.getKontr().getNip();
           WierszDokfk r = new WierszDokfk(p.getLp(), p.getDatadokumentu(), p.getDataoperacji(), p.getDokfkPK().toString2(), kontrahent, p.getNumerwlasnydokfk(), p.getOpisdokfk(), p.getWartoscdokumentu(), p.getWalutadokumentu().getSymbolwaluty());
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
