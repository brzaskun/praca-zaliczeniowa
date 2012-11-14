/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.faces.taglib.html_basic.OutputTextTag;
import dao.EVDAO;
import embeddable.EVLista;
import embeddable.EVatViewPola;
import embeddable.EVatwpis;
import embeddable.EVidencja;
import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class VatView implements Serializable{
    @ManagedProperty(value="#{DokTabView}")
    private DokTabView dokTabView;
    private List<Dok> listadokvat;
    private List<EVatViewPola> listadokvatprzetworzona;
    private HashMap<String,ArrayList> listaewidencji;
    private HashMap<String,EVatwpis> sumaewidencji;

    @Inject
    private Dok selected;
    @Inject
    private EVLista eVLista;
    
    //elementy niezbedne do generowania ewidencji vat
    private AccordionPanel akordeon;
    

    public VatView() {
        listadokvat = new ArrayList<>();
        listadokvatprzetworzona = new ArrayList<>();
        listaewidencji = new HashMap<>();
        sumaewidencji = new HashMap<>();
    }

    
    @PostConstruct
    private void init() throws Exception{
        listadokvat.addAll(dokTabView.getDokvatmc());
        Iterator it;
        it = listadokvat.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            if(tmp.getEwidencjaVAT()!=null){
                List<EVatwpis> ewidencja = new ArrayList<>();
                ewidencja.addAll(tmp.getEwidencjaVAT());
                    Iterator itx;
                    itx = ewidencja.iterator();
                    while(itx.hasNext()){
                        EVatwpis ewidwiersz = (EVatwpis) itx.next();
                        if(ewidwiersz.getNetto()>0){
                        EVatViewPola wiersz = new EVatViewPola();
                        wiersz.setId(tmp.getNrWpkpir());
                        wiersz.setDataSprz(tmp.getDataSprz());
                        wiersz.setDataWyst(tmp.getDataWyst());
                        wiersz.setKontr(tmp.getKontr());
                        wiersz.setNrWlDk(tmp.getNrWlDk());
                        wiersz.setOpis(tmp.getOpis());
                        wiersz.setNazwaewidencji(ewidwiersz.getEwidencja().getNazwaEwidencji());
                        wiersz.setNetto(ewidwiersz.getNetto());
                        wiersz.setVat(ewidwiersz.getVat());
                        wiersz.setOpizw(ewidwiersz.getEstawka());
                        listadokvatprzetworzona.add(wiersz);
                        }
            }
        }
    }
        //rozdziela zapisy na poszczególne ewidencje
        Set<String> zbiorewidencji = new HashSet<>();
        Iterator ity;
        ity = listadokvatprzetworzona.iterator();
        
       while(ity.hasNext()){
           ArrayList<EVatViewPola> listatmp = new ArrayList<>();
            EVatViewPola wierszogolny = (EVatViewPola) ity.next();
            //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
           String nazwaewidencji = wierszogolny.getNazwaewidencji();
           
           try {
                 Collection c = listaewidencji.get(nazwaewidencji);
                 listatmp.addAll(c);
           } catch (Exception e){
                listaewidencji.put(nazwaewidencji,new ArrayList<EVatViewPola>());
                EVidencja nowaEv = EVDAO.znajdzponazwie(nazwaewidencji);
                sumaewidencji.put(nazwaewidencji, new EVatwpis(nowaEv, 0.0,0.0,wierszogolny.getOpizw()));
           }
           listatmp.add(wierszogolny);
           
           EVatwpis ew = new EVatwpis();
           ew = sumaewidencji.get(nazwaewidencji);
           ew.setNetto(ew.getNetto()+wierszogolny.getNetto());
           ew.setVat(ew.getVat()+wierszogolny.getVat());
           sumaewidencji.put(nazwaewidencji, ew);
           listaewidencji.put(nazwaewidencji, listatmp);
        }

        wygeneruj(listaewidencji);
        System.out.println("lolo");
    }
    
    
      public void wygeneruj(HashMap lista) {
          FacesContext facesCtx = FacesContext.getCurrentInstance();
          ELContext elContext = facesCtx.getELContext();
          ExpressionFactory ef = ExpressionFactory.newInstance();
          
          akordeon = new AccordionPanel();
          //robienie glownej oprawy
          Set nazwyew = lista.keySet();
          Iterator it;
          it = nazwyew.iterator();
          int i = 0;
          while(it.hasNext()){
          String nazwapj = (String) it.next();
          Tab tab = new Tab();
          tab.setId("tabek"+i);
          tab.setTitle("ewidencja: "+nazwapj);
          
          DataTable dataTable = new DataTable();
          dataTable.setId("tablica"+i);
          dataTable.setResizableColumns(true);
          dataTable.setVar("var");
          dataTable.setValue(lista.get(nazwapj));
          //tak trzeba opisac kazda kolumne :)
          ArrayList<String> opisykolumn = new ArrayList<>();
          opisykolumn.addAll(EVatViewPola.getOpispol());
          
          Iterator itx;
          itx = opisykolumn.iterator();
          while(itx.hasNext()){
          String wstawka = (String) itx.next();    
          Column column = new Column();
          column.setHeaderText(wstawka);
          final String binding = "#{var."+wstawka+"}";
          ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
          HtmlOutputText ot = new HtmlOutputText();
          ot.setValueExpression("value", ve);
          column.getChildren().add(ot);
          dataTable.getChildren().add(column);
          }
          
          tab.getChildren().add(dataTable);
          
          akordeon.getChildren().add(tab);
          i++;
          }
         
          RequestContext.getCurrentInstance().update("form:akordeon");
          
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        FacesContext facesCtx = FacesContext.getCurrentInstance();
//        ELContext elContext = facesCtx.getELContext();
//        grid3 = getGrid3();
//        grid3.getChildren().clear();
//        RequestContext.getCurrentInstance().update("dodWiad:grid3");
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//            HtmlOutputText ot = new HtmlOutputText();
//            ot.setValue("nazwa Srodka");
//            grid3.getChildren().add(ot);
//            HtmlInputText ew = new HtmlInputText();
//            final String binding = "#{DokumentView.nazwaSTR}";
//            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
//            ew.setValueExpression("value", ve2);
//            grid3.getChildren().add(ew);
//            
//            HtmlOutputText ot1 = new HtmlOutputText();
//            ot1.setValue("data przyjecia");
//            grid3.getChildren().add(ot1);
//            HtmlInputText ew1 = new HtmlInputText();
//            final String binding1 = "#{DokumentView.dataPrzSTR}";
//            ValueExpression ve1 = ef.createValueExpression(elContext, binding1, String.class);
//            ew1.setValueExpression("value", ve1);
//            grid3.getChildren().add(ew1);
//            
//            HtmlOutputText ot3 = new HtmlOutputText();
//            ot3.setValue("symbol KST");
//            grid3.getChildren().add(ot3);
//            HtmlInputText ew3 = new HtmlInputText();
//            final String binding3 = "#{DokumentView.symbolKST}";
//            ValueExpression ve3 = ef.createValueExpression(elContext, binding3, String.class);
//            ew3.setValueExpression("value", ve3);
//            grid3.getChildren().add(ew3);
//            
//            HtmlOutputText ot4 = new HtmlOutputText();
//            ot4.setValue("stawka amort");
//            grid3.getChildren().add(ot4);
//            HtmlInputText ew4 = new HtmlInputText();
//            final String binding4 = "#{DokumentView.stawkaKST}";
//            ValueExpression ve4 = ef.createValueExpression(elContext, binding4, String.class);
//            ew4.setValueExpression("value", ve4);
//            grid3.getChildren().add(ew4);
//            
//            UISelectItems ulista = new UISelectItems();
//            List valueList = new ArrayList();
//            SelectItem selectItem = new SelectItem("srodek trw.", "srodek trw.");
//            valueList.add(selectItem);
//            selectItem = new SelectItem("wyposazenie", "wyposazenie");
//            valueList.add(selectItem);
//            ulista.setValue(valueList);
//            final String bindingX = "#{DokumentView.typKST}";
//            ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
//            HtmlSelectOneMenu htmlSelectOneMenu = new HtmlSelectOneMenu();
//            htmlSelectOneMenu.setValueExpression("value", ve2X);
//            htmlSelectOneMenu.setStyle("min-width: 150px");
//            htmlSelectOneMenu.getChildren().add(ulista);
//            grid3.getChildren().add(htmlSelectOneMenu);
//            
//        RequestContext.getCurrentInstance().update("dodWiad:grid3");
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

    public EVLista geteVLista() {
        return eVLista;
    }

    public void seteVLista(EVLista eVLista) {
        this.eVLista = eVLista;
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

    public AccordionPanel getAkordeon() {
        return akordeon;
    }

    public void setAkordeon(AccordionPanel akordeon) {
        this.akordeon = akordeon;
    }
 
}
