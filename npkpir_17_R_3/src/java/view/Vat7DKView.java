/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import deklaracjaVAT7_13.VAT713;
import embeddable.Daneteleadresowe;
import embeddable.EVatwpis;
import embeddable.EVatwpisSuma;
import embeddable.Parametr;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.TKodUS;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito 
 */
@ManagedBean
@RequestScope
public class Vat7DKView implements Serializable {
    @Inject private Deklaracjevat poprzedniadeklaracja;
    @Inject private Deklaracjevat pobranadeklaracja;
    @Inject private Deklaracjevat nowadeklaracja;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private Vatpoz selected;
    static private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;
    @Inject private Daneteleadresowe adres;
    @Inject PodatnikDAO podatnikDAO;
    @Inject EwidencjeVatDAO ewidencjeVatDAO;
    @Inject private TKodUS tKodUS;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    String rok;
    String mc;
    String podatnik;
    
    
    

    public Vat7DKView() {
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
    }
    
    @PostConstruct
    private void init(){
        rok = wpisView.getRokWpisu().toString();
        mc = wpisView.getMiesiacWpisu();
        podatnik = wpisView.getPodatnikWpisu();
   }
    
    
    
    
    public void oblicz() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Podatnik pod = podatnikDAO.find(podatnik);
        HashMap<String, EVatwpisSuma> sumaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        Collection <EVatwpisSuma> wyciagnieteewidencje =  sumaewidencji.values();
        for (EVatwpisSuma ew : wyciagnieteewidencje){
            String nrpolanetto = ew.getEwidencja().getNrpolanetto();
            String nrpolavat = ew.getEwidencja().getNrpolavat();
            String netto = String.valueOf(ew.getNetto());
            int nettoI = Integer.parseInt(ew.getNetto().toString());
            String vat = String.valueOf(ew.getVat().toString());
            int vatI =  Integer.parseInt(ew.getVat().toString());
            Class[] paramString = new Class[1];	
            paramString[0] = String.class;
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole"+nrpolanetto, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new String(netto));
            paramString = new Class[1];	
            paramString[0] = Integer.class;
            try{
            met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI"+nrpolanetto, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new Integer(nettoI));
            } catch(Exception e){}
            if(nrpolavat!=null){
                paramString = new Class[1];	
                paramString[0] = String.class;
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole"+nrpolavat, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new String(vat));
                paramString = new Class[1];	
                paramString[0] = Integer.class;
                try{
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI"+nrpolavat, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new Integer(vatI));
                } catch(Exception e){}
            }
        }
        podsumujszczegolowe();
        selected.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        selected.setPodatnik(podatnik);
        selected.setRok(rok);
        String mcx = String.valueOf(Integer.parseInt(mc));
        selected.setMiesiac(mcx);
        selected.setKodurzedu(tKodUS.getLista().get(pod.getUrzadskarbowy()));
        selected.setNazwaurzedu(pod.getUrzadskarbowy());
        adres.setNIP(pod.getNip());
        adres.setImiePierwsze(pod.getImie().toUpperCase());
        adres.setNazwisko(pod.getNazwisko().toUpperCase());
        adres.setDataUrodzenia(pod.getDataurodzenia());
        adres.setWojewodztwo(pod.getWojewodztwo().toUpperCase());
        adres.setPowiat(pod.getPowiat().toUpperCase());
        adres.setGmina(pod.getGmina().toUpperCase());
        adres.setUlica(pod.getUlica().toUpperCase());
        adres.setNrDomu(pod.getNrdomu());
        adres.setNrLokalu(pod.getNrlokalu());
        adres.setMiejscowosc(pod.getMiejscowosc().toUpperCase());
        adres.setKodPocztowy(pod.getKodpocztowy());
        adres.setPoczta(pod.getPoczta().toUpperCase());
        selected.setAdres(adres);
        String kwotaautoryzujaca = null;
        List<Parametr> listakwotaautoryzujaca = pod.getKwotaautoryzujaca();
        for(Parametr par : listakwotaautoryzujaca){
            if(par.getRokOd().equals(rok)){
                kwotaautoryzujaca = par.getParametr();
                break;
            }
        }
        selected.setKwotaautoryzacja(kwotaautoryzujaca);
        stworzdeklaracje();
        nowadeklaracja.setEwidencje(ewidencjeVatDAO.find(rok, mc, podatnik).getEwidencje());
        nowadeklaracja.setPodsumowanieewidencji(ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji());
        nowadeklaracja.setRok(rok);
        nowadeklaracja.setMiesiac(mc);
        nowadeklaracja.setKodurzedu(selected.getKodurzedu());
        nowadeklaracja.setPodatnik(podatnik);
        nowadeklaracja.setSelected(selected);
        nowadeklaracja.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        if(nowadeklaracja.getNrkolejny().equals(pobranadeklaracja.getNrkolejny())){
            deklaracjevatDAO.destroy(pobranadeklaracja);
            deklaracjevatDAO.edit(nowadeklaracja);
        } else {
            deklaracjevatDAO.dodaj(nowadeklaracja);
        }
         //wysylanie
        //pobieranie potwierdzenia
        RequestContext.getCurrentInstance().update("vat7:");
               
        }

    private void podsumujszczegolowe(){
         try{
            try{
                List<Deklaracjevat> pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjewszystkie(rok.toString(),mc,podatnik);
                poprzedniadeklaracja = pobranalistadeklaracji.get(pobranalistadeklaracji.size()-1);
                selected.setCelzlozenia("2");
            } catch (Exception er){
                poprzedniadeklaracja =  deklaracjevatDAO.findDeklaracje(rok.toString(),mc,podatnik);
                selected.setCelzlozenia("2");
            }
            
            if(pobranadeklaracja.getIdentyfikator()==null){
                nowadeklaracja.setNrkolejny(pobranadeklaracja.getNrkolejny());
            }else {
                nowadeklaracja.setNrkolejny(pobranadeklaracja.getNrkolejny()+1);
            }
        } catch (Exception e){
            selected.setCelzlozenia("1");
            nowadeklaracja.setNrkolejny(1);
        }
        selected.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        wyszukajpoprzednia();
        PozycjeSzczegoloweVAT p = pozycjeSzczegoloweVAT;
        p.setPoleI45(p.getPoleI20()+p.getPoleI21()+p.getPoleI23()+p.getPoleI25()+p.getPoleI27()+p.getPoleI29()+p.getPoleI31()+p.getPoleI33()+p.getPoleI35()+p.getPoleI37()+p.getPoleI41());
        p.setPole45(String.valueOf(p.getPoleI45()));
        p.setPoleI46(p.getPoleI26()+p.getPoleI28()+p.getPoleI30()+p.getPoleI34()+p.getPoleI36()+p.getPoleI38()+p.getPoleI40()+p.getPoleI42()+p.getPoleI43()+p.getPoleI44());
        p.setPole46(String.valueOf(p.getPoleI46()));
        p.setPoleI55(p.getPoleI47()+p.getPoleI48()+p.getPoleI50()+p.getPoleI52()+p.getPoleI53()+p.getPoleI54());
        p.setPole55(String.valueOf(p.getPoleI55()));
        Integer dozaplaty = p.getPoleI46()-p.getPoleI55();
        if(dozaplaty>p.getPoleI56()){
            p.setPoleI56(dozaplaty);
            p.setPole56(dozaplaty.toString());
        } else {
            p.setPole56("0");
            p.setPoleI56(0);
        }
            p.setPole57("0");
            p.setPoleI57(0);
        Integer roznica = p.getPoleI46()-p.getPoleI55()-p.getPoleI56()-p.getPoleI57();
        if(roznica >0){
            p.setPoleI58(roznica);
            p.setPole58(roznica.toString());
        } else {
            p.setPole58("0");
            p.setPoleI58(0);
        }
        Integer dozwrotu = p.getPoleI55()-p.getPoleI46();
        roznica = p.getPoleI55()-p.getPoleI46()+p.getPoleI59();
        if(dozwrotu>0){
            p.setPoleI60(roznica);
            p.setPole60(roznica.toString());
        }
        p.setPole61("0");
        p.setPoleI61(0);
        roznica = p.getPoleI60()-p.getPoleI61();
        p.setPoleI65(roznica);
        p.setPole65(roznica.toString());
        pozycjeSzczegoloweVAT = p;
    }
    private void wyszukajpoprzednia(){
        Integer rokI = wpisView.getRokWpisu();
        if (mc.equals("01")) {
                mc = "12";
                rokI--;
            } else {
                Integer tmp = Integer.parseInt(mc);
                tmp--;
                mc = tmp.toString();
                if(!mc.equals("10")||!mc.equals("11")||!mc.equals("12")){
                    mc = "0".concat(mc);
                }
            }
        try {
            try{
                List<Deklaracjevat> pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjewszystkie(rok.toString(),mc,podatnik);
                poprzedniadeklaracja = pobranalistadeklaracji.get(pobranalistadeklaracji.size()-1);
            } catch (Exception er){
                poprzedniadeklaracja =  deklaracjevatDAO.findDeklaracje(rok.toString(),mc,podatnik);
            }
            pozycjeSzczegoloweVAT.setPole47(poprzedniadeklaracja.getPozycjeszczegolowe().getPole65());
            pozycjeSzczegoloweVAT.setPoleI47(poprzedniadeklaracja.getPozycjeszczegolowe().getPoleI65());
        } catch (Exception e){
            Podatnik pod = podatnikDAO.find(podatnik);
            String Pole47 = pod.getPole47();
            Integer PoleI47 = Integer.parseInt(Pole47);
            pozycjeSzczegoloweVAT.setPole47(Pole47);
            pozycjeSzczegoloweVAT.setPoleI47(PoleI47);
        }
    }
    
    private void stworzdeklaracje(){
        VAT713 vat713 = null;
        try {
            vat713 = new VAT713(selected);
        } catch (Exception ex) {
            Logger.getLogger(Vat7DKView.class.getName()).log(Level.SEVERE, null, ex);
        }
        String wiersz = vat713.getWiersz();
        System.out.println(wiersz);
        nowadeklaracja.setDeklaracja(wiersz);
    }
    
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vatpoz getSelected() {
        return selected;
    }

    public void setSelected(Vatpoz selected) {
        this.selected = selected;
    }

    public PozycjeSzczegoloweVAT getPozycjeSzczegoloweVAT() {
        return pozycjeSzczegoloweVAT;
    }

    public void setPozycjeSzczegoloweVAT(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT) {
        this.pozycjeSzczegoloweVAT = pozycjeSzczegoloweVAT;
    }
    
    
}

