/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.EwidencjeVatDAO;
import embeddable.EVatViewPola;
import embeddable.EVatwpis;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.Parametr;
import entity.Dok;
import entity.Evewidencja;
import entity.Ewidencjevat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VatView implements Serializable {

 
    private List<Dok> listadokvat;
    private List<EVatViewPola> listadokvatprzetworzona;
    private HashMap<String, ArrayList> listaewidencji;
    private HashMap<String, EVatwpisSuma> sumaewidencji;
    @Inject
    private Dok selected;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    //elementy niezbedne do generowania ewidencji vat
    private TabView akordeon;
    @Inject EwidencjeVatDAO ewidencjeVatDAO;
    @Inject private Ewidencjevat zrzucane;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    private List<EVatViewPola> goscwybral;
    private static List<EVatwpisSuma> goscwybralsuma;
    private List<String> listanowa;
    private List<EVatwpisSuma> sumydowyswietlenia;
    private Double suma1;
    private Double suma2;
    private Double suma3;
     //tablica obiektw danego klienta
    @Inject DokDAO dokDAO;
  

    public VatView() {
        listadokvat = new ArrayList<>();
        listadokvatprzetworzona = new ArrayList<>();
        listaewidencji = new HashMap<>();
        sumaewidencji = new HashMap<>();
        goscwybral = new ArrayList<>();
        listanowa = new ArrayList<>();
        sumydowyswietlenia = new ArrayList<>();
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
    }

    @PostConstruct
    private void init() {
          try {
            String vatokres = sprawdzjakiokresvat();
            try {
                List<Dok> listatmp = dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu());
                //sortowanie dokumentów
                Collections.sort(listatmp, new Dokcomparator());
                //
                Integer r = wpisView.getRokWpisu();
                int numerk = 1;
                for (Dok tmpx : listatmp) {
                    if (tmpx.getPkpirR().equals(r.toString())) {
                        tmpx.setNrWpkpir(numerk++);
                        listadokvat.add(tmpx);
                    }
                }
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }

            listadokvat = zmodyfikujliste(listadokvat, vatokres);
            for (Dok zaksiegowanafaktura : listadokvat) {
                if (zaksiegowanafaktura.getEwidencjaVAT() != null) {
                    List<EVatwpis> ewidencja = new ArrayList<>();
                    ewidencja.addAll(zaksiegowanafaktura.getEwidencjaVAT());
                    for (EVatwpis ewidwiersz : ewidencja) {
                        if (ewidwiersz.getNetto() != 0) {
                            EVatViewPola wiersz = new EVatViewPola();
                            wiersz.setId(zaksiegowanafaktura.getNrWpkpir());
                            wiersz.setDataSprz(zaksiegowanafaktura.getDataSprz());
                            wiersz.setDataWyst(zaksiegowanafaktura.getDataWyst());
                            wiersz.setKontr(zaksiegowanafaktura.getKontr());
                            wiersz.setNrWlDk(zaksiegowanafaktura.getNrWlDk());
                            wiersz.setOpis(zaksiegowanafaktura.getOpis());
                            wiersz.setNazwaewidencji(ewidwiersz.getEwidencja().getNazwa());
                            wiersz.setNrpolanetto(ewidwiersz.getEwidencja().getNrpolanetto());
                            wiersz.setNrpolavat(ewidwiersz.getEwidencja().getNrpolavat());
                            wiersz.setNetto(ewidwiersz.getNetto());
                            wiersz.setVat(ewidwiersz.getVat());
                            wiersz.setOpizw(ewidwiersz.getEstawka());
                            listadokvatprzetworzona.add(wiersz);
                        }
                    }
                }
            }
            //rozdziela zapisy na poszczególne ewidencje
            for (EVatViewPola wierszogolny : listadokvatprzetworzona) {
                ArrayList<EVatViewPola> listatmp = new ArrayList<>();
                //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
                String nazwaewidencji = wierszogolny.getNazwaewidencji();
                try {
                    Collection c = listaewidencji.get(nazwaewidencji);
                    listatmp.addAll(c);
                } catch (Exception e) {
                    listaewidencji.put(nazwaewidencji, new ArrayList<EVatViewPola>());
                    Evewidencja nowaEv = evewidencjaDAO.znajdzponazwie(nazwaewidencji);
                    sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, wierszogolny.getOpizw()));
                }
                listatmp.add(wierszogolny);
                EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
                BigDecimal sumanetto = ew.getNetto().add(BigDecimal.valueOf(wierszogolny.getNetto()).setScale(0, RoundingMode.HALF_EVEN));
                ew.setNetto(sumanetto);
                BigDecimal sumavat = ew.getVat().add(BigDecimal.valueOf(wierszogolny.getVat()).setScale(0, RoundingMode.HALF_EVEN));
                ew.setVat(sumavat);
                sumaewidencji.put(nazwaewidencji, ew);
                listaewidencji.put(nazwaewidencji, listatmp);
            }
            sumydowyswietlenia.addAll(sumaewidencji.values());
            /**
             * Dodaj sumy do ewidencji dla wydruku
             */
            Set<String> klucze = sumaewidencji.keySet();
            for (String p : klucze) {
                EVatViewPola wiersz = new EVatViewPola();
                wiersz.setId(9999);
                wiersz.setDataSprz("");
                wiersz.setDataWyst("");
                wiersz.setKontr(null);
                wiersz.setNrWlDk("");
                wiersz.setOpis("podsumowanie");
                wiersz.setNazwaewidencji("");
                wiersz.setNrpolanetto("");
                wiersz.setNrpolavat("");
                wiersz.setNetto(sumaewidencji.get(p).getNetto().doubleValue());
                wiersz.setVat(sumaewidencji.get(p).getVat().doubleValue());
                wiersz.setOpizw("");
                listaewidencji.get(p).add(wiersz);
            }

            /**
             *
             */
            //wygeneruj(listaewidencji); nie potrzeben juz :))
            String rok = wpisView.getRokWpisu().toString();
            String mc = wpisView.getMiesiacWpisu();
            String pod = wpisView.getPodatnikWpisu();
            //zachowaj wygenerowane ewidencje do bazy danych
            try {
                /**
                 * edycja nie dziala ale nie ma problemu, zawsze sa usuwane
                 * stare i dodawane nowe :)
                 */
                Ewidencjevat pobrane = ewidencjeVatDAO.find(rok, mc, pod);
                pobrane.setEwidencje(listaewidencji);
                pobrane.setSumaewidencji(sumaewidencji);
                ewidencjeVatDAO.edit(pobrane);
            } catch (Exception e) {
                zrzucane.setPodatnik(pod);
                zrzucane.setRok(rok);
                /**
                 * tyty
                 */
                if (!vatokres.equals("miesięczne")) {
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    zrzucane.setMiesiac(miesiacewkwartale.get(2));
                } else {
                    zrzucane.setMiesiac(mc);
                }
                zrzucane.setEwidencje(listaewidencji);
                zrzucane.setSumaewidencji(sumaewidencji);
                ewidencjeVatDAO.dodajewidencje(zrzucane);
            }
        } catch (Exception e) {
            System.out.println("Firma nie vat");
        }
    }

    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Integer sumaszukana = rok+mc;
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
        for(Parametr p : parametry){
            if(p.getRokDo()!=null&&!"".equals(p.getRokDo())){
            Integer dolnagranica = Integer.parseInt(p.getRokOd()) + Integer.parseInt(p.getMcOd());
            Integer gornagranica = Integer.parseInt(p.getRokDo()) + Integer.parseInt(p.getMcDo());
            if(sumaszukana>=dolnagranica&&sumaszukana<=gornagranica){
                return p.getParametr();
            }
            } else {
            Integer dolnagranica = Integer.parseInt(p.getRokOd()) + Integer.parseInt(p.getMcOd());
            if(sumaszukana>=dolnagranica){
                return p.getParametr();
            }
            }
        }
        return "blad";
    }
      
     private List<Dok> zmodyfikujliste(List<Dok> listadokvat, String vatokres) throws Exception {
         if(vatokres.equals("blad")){
            throw new Exception("Nie ma ustawionego parametru vat za dany okres");
         } else if (vatokres.equals("miesięczne")){
             List<Dok> listatymczasowa = new ArrayList<>();
             for(Dok p : listadokvat){
                 if(p.getVatM().equals(wpisView.getMiesiacWpisu())&&p.getUsunpozornie()==false){
                     listatymczasowa.add(p);
                 }
             }
             return listatymczasowa;
         } else {
             List<Dok> listatymczasowa = new ArrayList<>();
             Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
             List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
             for(Dok p : listadokvat){
                 if(p.getVatM().equals(miesiacewkwartale.get(0))||p.getVatM().equals(miesiacewkwartale.get(1))||p.getVatM().equals(miesiacewkwartale.get(2))){
                     if(p.getUsunpozornie()==false){
                        listatymczasowa.add(p);
                     }
                 }
             }
             return listatymczasowa;
         }
    }

     public void sumujwybrane(){
         suma1 = 0.0;
         suma2 = 0.0;
         suma3 = 0.0;
         for(EVatwpisSuma p : goscwybralsuma){
         suma1 += p.getNetto().doubleValue();
         suma2 += p.getVat().doubleValue();
         }
         suma3 = suma1+suma2;
         Msg.msg("i","Sumuję ewidencje vat");
     }
     
     public void sumujwybrane1(){
         suma1 = 0.0;
         suma2 = 0.0;
         suma3 = 0.0;
         for(EVatViewPola p : goscwybral){
         suma1 += p.getNetto();
         suma2 += p.getVat();
         }
         suma3 = suma1+suma2;
     }
     
      
      public void odsumujwybrane1(UnselectEvent event){
         EVatViewPola p = (EVatViewPola) event.getObject();
         suma1 -= p.getNetto();
         suma2 -= p.getVat();
         suma3 -= suma1+suma2;
     }
      
   
    //generuje poszczegolen ewidencje
//    public void wygeneruj(HashMap lista) throws Exception {
//        FacesContext facesCtx = FacesContext.getCurrentInstance();
//        ELContext elContext = facesCtx.getELContext();
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//
//        akordeon = new TabView();
//        //robienie glownej oprawy
//        List<String> nazwyew = new ArrayList<>();
//        nazwyew.addAll(lista.keySet());
//        Collections.sort(nazwyew);
//        Iterator it;
//        int i = 0;
//        for(String nazwapj : nazwyew){
//            Tab tab = new Tab();
//            tab.setId("tabek" + i);
//            tab.setTitle("ewidencja: " + nazwapj);
//
//            DataTable dataTable = new DataTable();
//            dataTable.setId("tablica" + i);
//            //dataTable.setResizableColumns(true);
//            dataTable.setVar("var");
//            dataTable.setValue(lista.get(nazwapj));
//            dataTable.setStyle("width: 1250px;");
//            //dodaj buton drukowania
//             CommandButton button = new CommandButton();
//            button.setValue("PobierzPdf");
//            button.setType("button");
//            button.setId("przyciskpdf"+i);
//            FacesContext context = FacesContext.getCurrentInstance();
//            MethodExpression actionListener = context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), "#{pdf.drukujewidencje('zakup')}", null, new Class[] {ActionEvent.class});
//            button.addActionListener(new MethodExpressionActionListener(actionListener));
//             
////            MethodExpression methodExpressionX = context.getApplication().getExpressionFactory().createMethodExpression(
////            context.getELContext(), "#{pdf.drukujewidencje('"+nazwapj+"')}", null, new Class[] {});
////            button.setActionExpression(methodExpressionX);
//            String nowanazwa;
//            if(nazwapj.contains("sprzedaż")){
//                nowanazwa = nazwapj.substring(0, nazwapj.length()-1);
//                } else{
//                nowanazwa = nazwapj;
//                }
//            String tablican = "PrimeFaces.ab({source:'form:przyciskpdf"+i+"',onsuccess:function(data,status,xhr){wydrukewidencje('"+wpisView.getPodatnikWpisu()+"','"+nowanazwa+"');;}});return false;";
//            button.setOnclick(tablican);
//            tab.getChildren().add(button);
//            Separator sep = new Separator();
//           tab.getChildren().add(sep);
//            //tak trzeba opisac kazda kolumne :)
//           //
//            ArrayList<String> opisykolumn = new ArrayList<>();
//            opisykolumn.addAll(EVatViewPola.getOpispol());
//            Iterator itx;
//            itx = opisykolumn.iterator();
//            while (itx.hasNext()) {
//                String wstawka = (String) itx.next();
//                Column column = new Column();
//                column.setHeaderText(wstawka);
//                final String binding = "#{var." + wstawka + "}";
//                ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
//                HtmlOutputText ot = new HtmlOutputText();
//                ot.setValueExpression("value", ve);
//                switch (wstawka) {
//                    case "kontr":
//                        column.setWidth("300");
//                        break;
//                    case "nrWlDk":
//                        column.setWidth("150");
//                        break;
//                    case "dataWyst":
//                        column.setWidth("80");
//                        break;
//                    case "dataSprz":
//                        column.setWidth("80");
//                        break;
//                    case "opis":
//                        column.setWidth("150");
//                        break;
//                    case "id": 
//                        column.setWidth("50");
//                        break;
//                    case "netto":
//                        ot.setStyle("float: right;");
//                        NumberConverter numx = new NumberConverter();
//                        numx.setMaxFractionDigits(2);
//                        numx.setMinFractionDigits(2);
//                        ot.setConverter(numx);
//                    case "vat":
//                        ot.setStyle("float: right;");
//                        NumberConverter numy = new NumberConverter();
//                        numy.setMaxFractionDigits(2);
//                        numy.setLocale(new Locale("PL"));
//                        numy.setGroupingUsed(true);
//                        ot.setConverter(numy);
//                }
//                column.getChildren().add(ot);
//                dataTable.getChildren().add(column);
//            }
//            dataTable.setSelectionMode("multiple");
//            dataTable.setSelection(goscwybral);
//            dataTable.setRowKey(new EVatViewPola().getId());
//            tab.getChildren().add(dataTable);
//            akordeon.getChildren().add(tab);
//
//            i++;
//        }
//
//        //generowanie podsumowania
//        List<EVatwpisSuma> suma2 = new ArrayList<>();
//        suma2.addAll(sumaewidencji.values());
//        Tab tab = new Tab();
//        tab.setId("tabekdsuma");
//        tab.setTitle("podsumowanie ewidencji");
//        DataTable dataTable = new DataTable();
//        dataTable.setId("tablicasuma");
//        dataTable.setResizableColumns(true);
//        dataTable.setVar("var");
//        dataTable.setValue(suma2);
//        dataTable.setStyle("width: 1000px;");
//        List<String> opisykolumny = new ArrayList<>();
//        opisykolumny.add("ewidencja");
//        opisykolumny.add("netto");
//        opisykolumny.add("vat");
//        Iterator ity = opisykolumny.iterator();
//        while (ity.hasNext()) {
//            String wstawka = (String) ity.next();
//            Column column = new Column();
//            column.setHeaderText(wstawka);
//            HtmlOutputText ot = new HtmlOutputText();
//            if(!wstawka.equals("ewidencja")){
//                ot.setStyle("float: right;");
//                NumberConverter numberconv = new NumberConverter();
//                numberconv.setLocale(new Locale("PL"));
//                numberconv.setMinFractionDigits(2);
//                numberconv.setMaxFractionDigits(2);
//                column.setWidth("200");
//                ot.setConverter(numberconv);
//            }
//            final String binding = "#{var." + wstawka + "}";
//            ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
//            ot.setValueExpression("value", ve);
//            column.getChildren().add(ot);
//            dataTable.getChildren().add(column);
//            
//        }
//        dataTable.setStyleClass("mytable");
//        tab.getChildren().add(dataTable);
//        akordeon.getChildren().add(tab);
//    }

   
    public List<Dok> getListadokvat() {
        return listadokvat;
    }

    public void setListadokvat(List<Dok> listadokvat) {
        this.listadokvat = listadokvat;
    }

    public Dok getSelected() {
        return selected;
    }

    public void setSelected(Dok selected) {
        this.selected = selected;
    }

    public List<EVatViewPola> getListadokvatprzetworzona() {
        return listadokvatprzetworzona;
    }

    public void setListadokvatprzetworzona(List<EVatViewPola> listadokvatprzetworzona) {
        this.listadokvatprzetworzona = listadokvatprzetworzona;
    }

    public HashMap<String, ArrayList> getListaewidencji() {
        return listaewidencji;
    }

    public void setListaewidencji(HashMap<String, ArrayList> listaewidencji) {
        this.listaewidencji = listaewidencji;
    }

    public TabView getAkordeon() {
        return akordeon;
    }

    public void setAkordeon(TabView akordeon) {
        this.akordeon = akordeon;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

   public static void main(String[] args){
       String wiersz = "35.23 zł";
        String prices = wiersz.replaceAll("\\s","");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
            System.out.println(Double.parseDouble(m.group()));
            }
   }

    public List<EVatViewPola> getGoscwybral() {
        return goscwybral;
    }

    public void setGoscwybral(List<EVatViewPola> goscwybral) {
        this.goscwybral = goscwybral;
    }

    public HashMap<String, EVatwpisSuma> getSumaewidencji() {
        return sumaewidencji;
    }

    public void setSumaewidencji(HashMap<String, EVatwpisSuma> sumaewidencji) {
        this.sumaewidencji = sumaewidencji;
    }

    public List<String> getListanowa() {
        return listanowa;
    }

    public void setListanowa(List<String> listanowa) {
        this.listanowa = listanowa;
    }

    public List<EVatwpisSuma> getSumydowyswietlenia() {
        return sumydowyswietlenia;
    }

    public void setSumydowyswietlenia(List<EVatwpisSuma> sumydowyswietlenia) {
        this.sumydowyswietlenia = sumydowyswietlenia;
    }

    public List<EVatwpisSuma> getGoscwybralsuma() {
        return goscwybralsuma;
    }

    public void setGoscwybralsuma(List<EVatwpisSuma> goscwybralsuma) {
        this.goscwybralsuma = goscwybralsuma;
    }

    public Double getSuma1() {
        return suma1;
    }
   
    public void setSuma1(Double suma1) {
        this.suma1 = suma1;
    }
   
    public Double getSuma2() {
        return suma2;
}

    public void setSuma2(Double suma2) {
        this.suma2 = suma2;
    }

    public Double getSuma3() {
        return suma3;
    }

    public void setSuma3(Double suma3) {
        this.suma3 = suma3;
    }
    
    
    
   
}
