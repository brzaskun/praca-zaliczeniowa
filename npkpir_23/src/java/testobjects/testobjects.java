/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;

import embeddable.SchemaEwidencjaSuma;
import embeddable.Umorzenie;
import embeddable.VatUe;
import embeddable.ZestawienieRyczalt;
import embeddablefk.KontoKwota;
import embeddablefk.TreeNodeExtended;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.RodzajedokPK;
import entity.Ryczpoz;
import entity.SrodekTrw;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.Wiersz;
import entityfk.WierszBO;
import java.util.ArrayList;
import java.util.List;
import msg.B;
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
   
   public static List[] getTabelaKonta1(List<Wiersz> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("opis");
       n.add("kwota Wn");
       n.add("konto Wn");
       n.add("kwota Ma");
       n.add("konto Ma");
       n.add("saldo");
       List t = getWierszeKonta(wiersze);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaBOKonta(List<Konto> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("nr konta");
       n.add("nazwa pełna");
       n.add("typ konta");
       n.add("strona wn");
       n.add("strona ma");
       n.add("saldo wn");
       n.add("saldo ma");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
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
       n.add(B.b("kwota"));
       List t = getWierszeCechyZapisow(wiersze);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaZaksiegowane(List<Dokfk> wiersze) {
       List n = new ArrayList();
       n.add(B.b("lp"));
       n.add(B.b("datadok"));
       n.add(B.b("data"));
       n.add(B.b("iddok"));
       n.add(B.b("kontrahent"));
       n.add(B.b("numerwlasny"));
       n.add(B.b("opis"));  
       n.add(B.b("wartość"));
       n.add(B.b("waluta"));
       List t = getWierszeDokfk(wiersze);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaRyczpoz(List<Ryczpoz>  wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("okres rozliczeniowy");
       n.add("udziałowiec");
       n.add("przychody");
       n.add("udział");
       n.add("przychody wg udziału");
       n.add("ZUS 51");  
       n.add("ZUS 52");
       n.add("podatek za m-c");
       List t = wiersze;
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
    public static List[] getTabelaPlanKont(List<Konto> wiersze) {
       List n = new ArrayList();
       n.add("numer konta");
       n.add("nazwa pełna");
       n.add("nazwa skrócona");
       n.add("typ konta");
       n.add("ma subkonta");
       n.add("pozycja Wn");
       n.add("pozycja Ma");  
       n.add("przychód/koszt");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
     public static List[] getTabelaTransakcje(List<Transakcja> wiersze) {
       List n = new ArrayList();
       n.add(B.b("kwotatrans"));
       n.add(B.b("datatrans"));
       n.add(B.b("rozliczono"));
       n.add(B.b("dokument"));
       n.add(B.b("wiersz"));
       n.add(B.b("nrdokrozl"));
       n.add(B.b("opis"));
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
     
     public static List[] getTabelaBilansKonta(List<KontoKwota> wiersze) {
       List n = new ArrayList();
       n.add(B.b("numerkonta"));
       n.add(B.b("nazwapełna"));
       n.add(B.b("kwota"));
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
    public static List[] getTabelaRozrachunki(List<StronaWiersza> wiersze) {
       List n = new ArrayList();
       n.add(B.b("lp"));
       n.add(B.b("waluta"));
       n.add(B.b("kurs"));
       n.add(B.b("datadok"));
       n.add(B.b("symbol"));
       n.add(B.b("dokument"));
       n.add(B.b("opis"));
       n.add(B.b("kwota"));
       n.add(B.b("zapłacono"));
       n.add(B.b("dozapłaty"));
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
    public static List[] getTabelaPlanKontTR(List<Konto> wiersze) {
       List n = new ArrayList();
       n.add(B.b("lp"));
       n.add(B.b("numerkonta"));
       n.add(B.b("nazwapełna"));
       n.add(B.b("nazwaskrócona"));
       n.add(B.b("tłumaczenie"));
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    public static List[] getEwidencjaVATUE(List<VatUe> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("transakcja");
       n.add("kod kraju");
       n.add("NIP");
       n.add("kontrahent");
       n.add("netto");
       n.add("ilość dok");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
     public static List[] getSchemaEwidencjaSuma(List<SchemaEwidencjaSuma> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("nazwa wiersza");
       n.add("nr pola netto");
       n.add("kwota netto");
       n.add("nr pola vat");
       n.add("kwota vat");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
     
     public static List[] getDeklaracjaVatSchemaWierszSum(List<DeklaracjaVatSchemaWierszSum> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("nazwa wiersza");
       n.add("nr pola netto");
       n.add("kwota netto");
       n.add("nr pola vat");
       n.add("kwota vat");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
     
     public static List[] getSrodekUmorzenie(List<Umorzenie> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("rok");
       n.add("mc");
       n.add("kwota");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
     public static List[] getSrodkiTRWlista(List<SrodekTrw> wiersze) {
       List n = new ArrayList();
       n.add("nr kol.");
       n.add("data zakupu środka");
       n.add("data przekazania");
       n.add("nazwa środka trwałego");
       n.add("KŚT");
       n.add("nr. faktury");
       n.add("cena zak.netto");
       n.add("kwota vat");
       n.add("umorzenie począt.");
       n.add("roczna staw. odpisu");
       n.add("odpis za rok");
       n.add("odpis miesięczny");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
    public static List[] getTabelaRRK(List<Transakcja> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("rachunek");
       n.add("kurs rachunku");
       n.add("płatność");
       n.add("kurs płatności");
       n.add("data zapłaty");
       n.add("różnica kursowa");  
       n.add("konto rozrachunkowe");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
    public static List[] getZestawienieRyczalt(List<ZestawienieRyczalt> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("okres");
       n.add("przych. wg 17%");
       n.add("przych. wg 8,5%");
       n.add("przych. wg 5,5%");
       n.add("przych. wg 3%");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
    
    public static List[] getTabelaWierszBO(List<WierszBO> wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("konto");
       n.add("opis");
       n.add("kurs");
       n.add("waluta");
       n.add("kwota Wn");
       n.add("kwota Wn PLN");
       n.add("kwota Ma");
       n.add("kwota Ma PLN");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
   
   public static List[] getTabelaRZiS(TreeNodeExtended rootProjektRZiS) {
       int level = rootProjektRZiS.ustaldepthDT();
       List n = new ArrayList();
       for (int i = 0; i < level; i++) {
        n.add("");
       }
       n.add(B.b("nazwapozycjiRZiS"));
       n.add(B.b(B.b("kwota")));
       List t = getWierszeRZiS(rootProjektRZiS, level);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaRZiSKonta(TreeNodeExtended rootProjektRZiS) {
       int level = rootProjektRZiS.ustaldepthDT();
       List n = new ArrayList();
       for (int i = 0; i < level; i++) {
        n.add("");
       }
       n.add(B.b("nazwapozycjiRZiS"));
       n.add(B.b(B.b("kwota")));
       n.add(B.b("konta"));
       List t = getWierszeRZiS(rootProjektRZiS, level);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaRZiSKontaPrzyporządkowane(TreeNodeExtended rootProjektRZiS) {
       int level = rootProjektRZiS.ustaldepthDT();
       List n = new ArrayList();
       for (int i = 0; i < level; i++) {
        n.add("");
       }
       n.add(B.b("nazwapozycjiRZiS"));
       n.add(B.b(B.b("kwota")));
       List t = getWierszeRZiS(rootProjektRZiS, level);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
   public static List[] getTabelaBilans(TreeNodeExtended rootProjekt) {
       int level = rootProjekt.ustaldepthDT();
       List n = new ArrayList();
       n.add("");
       n.add(B.b("nazwapozycjiBilansu"));
       n.add(B.b(B.b("kwota")));
       List t = getWierszeRZiS(rootProjekt, level);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
    public static List[] getTabelaBilansKonta(TreeNodeExtended rootProjekt) {
       int level = rootProjekt.ustaldepthDT();
       List n = new ArrayList();
       n.add("");
       n.add(B.b("nazwapozycjiBilansu"));
       n.add(B.b("kwota"));
       n.add(B.b("konta"));
       List t = getWierszeRZiS(rootProjekt, level);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
    public static List[] getTabelaBilansKontaPrzyporzadkowane(TreeNodeExtended rootProjekt) {
       int level = rootProjekt.ustaldepthDT();
       List n = new ArrayList();
       n.add("");
       n.add(B.b("nazwapozycjiBilansu"));
       n.add(B.b("kwota"));
       n.add(B.b("konta"));
       List t = getWierszeRZiS(rootProjekt, level);
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = t;
       return tabela;
   }
   
    public static List[] getTabelaFakturyPlatnosci(List<Faktura> wiersze, String zn) {
       List n = new ArrayList();
       n.add("lp");
       n.add("data wystawienia");
       n.add("nr kolejny");
       n.add("kontrahent");
       n.add("opis");  
       n.add("wartość");
       if (zn.equals("niezapłaconych")) {
           n.add("termin płatności");
       } else {
           n.add("zapłacono dnia");
       }
       List t = wiersze;
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
       n.add("opis");
       n.add("kg");
       n.add("szt");
       n.add("kwota Wn");
       n.add("kwota Wn PLN");
       n.add("konto Wn");
       n.add("kwota Ma");
       n.add("kwota Ma PLN");
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
           r.setSaldo(p.getSaldoWBRK());
           w.add(r);
       }
       return w;
   }
   
   public static List<WierszWNTWDT> getWierszeWNTWDTKonta(List<Wiersz> wiersze) {
       List<WierszWNTWDT> w = new ArrayList<WierszWNTWDT>();
       for (Wiersz p : wiersze) {
           WierszWNTWDT l = null;
           if (p.getOpisWiersza().equals("podsumowanie")) {
               l = new WierszWNTWDT(p.getIdwiersza(), "" , "", "", p.getIdporzadkowy(),  p.getOpisWiersza(), p.getIlosc_kg(), p.getIlosc_szt());
           } else {
               l = new WierszWNTWDT(p.getIdwiersza(), p.getDataksiegowania() , p.getDokfkS(), p.getDokfk().getNumerwlasnydokfk(), p.getIdporzadkowy(),  p.getOpisWiersza(), p.getIlosc_kg(), p.getIlosc_szt());
           }
           if (p.getStronaWn() != null) {
               l.setKwotaWn(p.getStronaWn().getKwota());
               l.setKwotaWnPLN(p.getStronaWn().getKwotaPLN());
               if (p.getOpisWiersza().equals("podsumowanie")) {
                   l.setOpiskontaWn("");
               } else {
                    l.setOpiskontaWn(p.getStronaWn().getKonto().getPelnynumer()+ " " + p.getStronaWn().getKonto().getNazwapelna());
               }
           }
           if (p.getStronaMa() != null) {
               l.setKwotaMa(p.getStronaMa().getKwota());
               l.setKwotaMaPLN(p.getStronaMa().getKwotaPLN());
               if (p.getOpisWiersza().equals("podsumowanie")) {
                   l.setOpiskontaMa("");
               } else {
                   l.setOpiskontaMa(p.getStronaMa().getKonto().getPelnynumer()+ " " + p.getStronaMa().getKonto().getNazwapelna());
               }
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
   
   public static List<PozycjaRZiSBilans> getWierszeRZiS(TreeNodeExtended rootProjektRZiS, int level) {
       List<PozycjaRZiSBilans> w = new ArrayList<PozycjaRZiSBilans>();
       rootProjektRZiS.getChildrenTree(new ArrayList<TreeNodeExtended>(), w);
       System.out.println("");
       return w;
   }
   
  
   public static void main(String[] args) {
       Dokfk p = getDokfk("PK");
       List[] t = getTabela();
       List r = getWiersze();
       System.out.println(p.toString());
   }
   
    
}
