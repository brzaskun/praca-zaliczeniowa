/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zbiorczy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class binek implements Serializable{
    private String form1;
    private List<Sprawa> sprawy;
    private List<Spoldzielnia> spoldzielnie;
    private List<Dluznik> dluznicy;
    private List<Sprawa> sprawyfilter;
    private List<Raport> raporty;
    
    private Object selSprawa;

    @PostConstruct
    private void init(){
        dluznicy = new ArrayList<>();
        dluznicy.add(new Dluznik(1,"Jan Kownacki","72-010 Police ul. Zbędna 13"));
        dluznicy.add(new Dluznik(2,"Marian Kownacki","72-010 Police ul. Zbędna 13"));
        dluznicy.add(new Dluznik(3,"Hubert Kownacki","72-010 Police ul. Zbędna 15"));
        dluznicy.add(new Dluznik(4,"Genowef Kownacki","72-010 Police ul. Zbędna 17"));
        dluznicy.add(new Dluznik(5,"Zmysław Kownacki","72-010 Police ul. Zbędna 19"));
        spoldzielnie = new ArrayList<>();
        spoldzielnie.add(new Spoldzielnia(1,"Wielki Krzak","12-112 Szczecin ul. Mala 13",4));
        spoldzielnie.add(new Spoldzielnia(2,"Wielki Poniedziałek","12-112 Szczecin ul. Duża 13",6));
        spoldzielnie.add(new Spoldzielnia(3,"Mały Krzak","12-122 Szczecin ul. Krzywa 13",12));
        spoldzielnie.add(new Spoldzielnia(4,"Ganimedes","12-114 Szczecin ul. Genialna 124",48));
        spoldzielnie.add(new Spoldzielnia(5,"Humorzasta","12-123 Szczecin ul. Wiwinji 13",14));
        sprawy = new ArrayList<>();
        sprawy.add(new Sprawa(1,spoldzielnie.get(0),dluznicy.get(0),"2013-01-01","2013-05-06",1585.2));
        sprawy.add(new Sprawa(2,spoldzielnie.get(1),dluznicy.get(1),"2013-01-01","2013-05-06",5485.2));
        sprawy.add(new Sprawa(3,spoldzielnie.get(2),dluznicy.get(2),"2013-01-01","2013-05-06",98485.2));
        sprawy.add(new Sprawa(4,spoldzielnie.get(3),dluznicy.get(2),"2013-01-01","2013-05-06",13545.2));
        sprawy.add(new Sprawa(5,spoldzielnie.get(4),dluznicy.get(4),"2013-01-01","2013-05-06",6985.2));
        raporty = new ArrayList<>();
        raporty.add(new Raport(1,sprawy.get(0),"2013-05-03","Hakuna Matata", false));
        raporty.add(new Raport(2,sprawy.get(1),"2013-06-03","Hakuna Matata", false));
        raporty.add(new Raport(3,sprawy.get(2),"2013-06-13","Hakuna Matata", false));
        raporty.add(new Raport(4,sprawy.get(3),"2013-04-18","Hakuna Matata", true));
    }

    public binek() {
        sprawyfilter = new ArrayList<>();
    }
    
    
    public String getForm1() {
        return form1;
    }

    public void setForm1(String form1) {
        this.form1 = form1;
    }

    public List<Sprawa> getSprawy() {
        return sprawy;
    }

    public void setSprawy(List<Sprawa> sprawy) {
        this.sprawy = sprawy;
    }

    public List<Spoldzielnia> getSpoldzielnie() {
        return spoldzielnie;
    }

    public void setSpoldzielnie(List<Spoldzielnia> spoldzielnie) {
        this.spoldzielnie = spoldzielnie;
    }

    public List<Dluznik> getDluznicy() {
        return dluznicy;
    }

    public void setDluznicy(List<Dluznik> dluznicy) {
        this.dluznicy = dluznicy;
    }

   

    public List<Sprawa> getSprawyfilter() {
        return sprawyfilter;
    }

    public void setSprawyfilter(List<Sprawa> sprawyfilter) {
        this.sprawyfilter = sprawyfilter;
    }

    public Object getSelSprawa() {
        return selSprawa;
    }

    public void setSelSprawa(Object selSprawa) {
        this.selSprawa = selSprawa;
    }

   

    public List<Raport> getRaporty() {
        return raporty;
    }

    public void setRaporty(List<Raport> raporty) {
        this.raporty = raporty;
    }
 
    
    
}
