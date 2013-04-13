/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.inject.Inject;
import org.primefaces.component.column.Column;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.rowtoggler.RowToggler;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class VatView implements Serializable {

    @ManagedProperty(value = "#{DokTabView}")
    private DokTabView dokTabView;
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
     

    public VatView() {
        listadokvat = new ArrayList<>();
        listadokvatprzetworzona = new ArrayList<>();
        listaewidencji = new HashMap<>();
        sumaewidencji = new HashMap<>();
        goscwybral = new ArrayList<>();
    }

    @PostConstruct
    private void init() throws Exception {
        listadokvat.addAll(dokTabView.getObiektDOKjsfSelRok());
        String vatokres = sprawdzjakiokresvat();
        listadokvat = zmodyfikujliste(listadokvat,vatokres);
        for (Dok zaksiegowanafaktura : listadokvat){
            if (zaksiegowanafaktura.getEwidencjaVAT() != null) {
                List<EVatwpis> ewidencja = new ArrayList<>();
                ewidencja.addAll(zaksiegowanafaktura.getEwidencjaVAT());
                for (EVatwpis ewidwiersz : ewidencja){
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
        /**
         * Dodaj sumy do ewidencji dla wydruku
         */
        
        Set<String> klucze = sumaewidencji.keySet();
        for(String p : klucze){
            EVatViewPola wiersz = new EVatViewPola();
            wiersz.setId(0);
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
        wygeneruj(listaewidencji);
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String pod = wpisView.getPodatnikWpisu();
        //zachowaj wygenerowane ewidencje do bazy danych
        try {
            /**
             * edycja nie dziala ale nie ma problemu, zawsze sa usuwane stare i dodawane nowe :)
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
            if(!vatokres.equals("miesięczne")){
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
        System.out.println("lolo");
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
                 if(p.getVatM().equals(wpisView.getMiesiacWpisu())){
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
                     listatymczasowa.add(p);
                 }
             }
             return listatymczasowa;
         }
    }

   
    //generuje poszczegolen ewidencje
    public void wygeneruj(HashMap lista) throws Exception {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elContext = facesCtx.getELContext();
        ExpressionFactory ef = ExpressionFactory.newInstance();

        akordeon = new TabView();
        //robienie glownej oprawy
        List<String> nazwyew = new ArrayList<>();
        nazwyew.addAll(lista.keySet());
        Collections.sort(nazwyew);
        Iterator it;
        int i = 0;
        for(String nazwapj : nazwyew){
            Tab tab = new Tab();
            tab.setId("tabek" + i);
            tab.setTitle("ewidencja: " + nazwapj);

            DataTable dataTable = new DataTable();
            dataTable.setId("tablica" + i);
            //dataTable.setResizableColumns(true);
            dataTable.setVar("var");
            dataTable.setValue(lista.get(nazwapj));
            dataTable.setStyle("width: 1250px;");
            //dodaj buton drukowania
             CommandButton button = new CommandButton();
            button.setValue("PobierzPdf");
            button.setType("button");
            button.setId("przyciskpdf"+i);
            FacesContext context = FacesContext.getCurrentInstance();
            MethodExpression actionListener = context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), "#{pdf.drukujewidencje('zakup')}", null, new Class[] {ActionEvent.class});
            button.addActionListener(new MethodExpressionActionListener(actionListener));
             
//            MethodExpression methodExpressionX = context.getApplication().getExpressionFactory().createMethodExpression(
//            context.getELContext(), "#{pdf.drukujewidencje('"+nazwapj+"')}", null, new Class[] {});
//            button.setActionExpression(methodExpressionX);
            String nowanazwa;
            if(nazwapj.contains("sprzedaż")){
                nowanazwa = nazwapj.substring(0, nazwapj.length()-1);
                } else{
                nowanazwa = nazwapj;
                }
            String tablican = "PrimeFaces.ab({source:'form:przyciskpdf"+i+"',onsuccess:function(data,status,xhr){wydrukewidencje('"+wpisView.getPodatnikWpisu()+"','"+nowanazwa+"');;}});return false;";
            button.setOnclick(tablican);
            tab.getChildren().add(button);
            Separator sep = new Separator();
           tab.getChildren().add(sep);
            //tak trzeba opisac kazda kolumne :)
           //
            ArrayList<String> opisykolumn = new ArrayList<>();
            opisykolumn.addAll(EVatViewPola.getOpispol());
            Iterator itx;
            itx = opisykolumn.iterator();
            while (itx.hasNext()) {
                String wstawka = (String) itx.next();
                Column column = new Column();
                column.setHeaderText(wstawka);
                final String binding = "#{var." + wstawka + "}";
                ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
                HtmlOutputText ot = new HtmlOutputText();
                ot.setValueExpression("value", ve);
                switch (wstawka) {
                    case "kontr":
                        column.setWidth("300");
                        break;
                    case "nrWlDk":
                        column.setWidth("150");
                        break;
                    case "dataWyst":
                        column.setWidth("80");
                        break;
                    case "dataSprz":
                        column.setWidth("80");
                        break;
                    case "opis":
                        column.setWidth("150");
                        break;
                    case "id": 
                        column.setWidth("50");
                        break;
                    case "netto":
                        ot.setStyle("float: right;");
                        NumberConverter numx = new NumberConverter();
                        numx.setMaxFractionDigits(2);
                        numx.setMinFractionDigits(2);
                        ot.setConverter(numx);
                    case "vat":
                        ot.setStyle("float: right;");
                        NumberConverter numy = new NumberConverter();
                        numy.setMaxFractionDigits(2);
                        numy.setLocale(new Locale("PL"));
                        numy.setGroupingUsed(true);
                        ot.setConverter(numy);
                }
                column.getChildren().add(ot);
                dataTable.getChildren().add(column);
            }
            dataTable.setSelectionMode("multiple");
            dataTable.setSelection(goscwybral);
            dataTable.setRowKey(new EVatViewPola().getId());
            tab.getChildren().add(dataTable);
            akordeon.getChildren().add(tab);

            i++;
        }

        //generowanie podsumowania
        List<EVatwpisSuma> suma2 = new ArrayList<>();
        suma2.addAll(sumaewidencji.values());
        Tab tab = new Tab();
        tab.setId("tabekdsuma");
        tab.setTitle("podsumowanie ewidencji");
        DataTable dataTable = new DataTable();
        dataTable.setId("tablicasuma");
        dataTable.setResizableColumns(true);
        dataTable.setVar("var");
        dataTable.setValue(suma2);
        dataTable.setStyle("width: 1000px;");
        List<String> opisykolumny = new ArrayList<>();
        opisykolumny.add("ewidencja");
        opisykolumny.add("netto");
        opisykolumny.add("vat");
        Iterator ity = opisykolumny.iterator();
        while (ity.hasNext()) {
            String wstawka = (String) ity.next();
            Column column = new Column();
            column.setHeaderText(wstawka);
            HtmlOutputText ot = new HtmlOutputText();
            if(!wstawka.equals("ewidencja")){
                ot.setStyle("float: right;");
                NumberConverter numberconv = new NumberConverter();
                numberconv.setLocale(new Locale("PL"));
                numberconv.setMinFractionDigits(2);
                numberconv.setMaxFractionDigits(2);
                column.setWidth("200");
                ot.setConverter(numberconv);
            }
            final String binding = "#{var." + wstawka + "}";
            ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
            ot.setValueExpression("value", ve);
            column.getChildren().add(ot);
            dataTable.getChildren().add(column);
            
        }
        dataTable.setStyleClass("mytable");
        tab.getChildren().add(dataTable);
        akordeon.getChildren().add(tab);
    }

    public DokTabView getDokTabView() {
        return dokTabView;
    }

    public void setDokTabView(DokTabView dokTabView) {
        this.dokTabView = dokTabView;
    }

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
       Scanner in = new Scanner("Hello123%").useDelimiter("[^0-9]+");
       int integer = in.nextInt();
       String nazwa = "12";
       Integer.parseInt(nazwa);
       System.out.println(integer);
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
    
   
}
