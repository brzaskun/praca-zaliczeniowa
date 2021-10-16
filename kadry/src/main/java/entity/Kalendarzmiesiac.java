/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import dao.NieobecnosckodzusFacade;
import data.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kalendarzmiesiac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendarzmiesiac.findAll", query = "SELECT k FROM Kalendarzmiesiac k"),
    @NamedQuery(name = "Kalendarzmiesiac.findById", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.id = :id"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRok", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kalendarzmiesiac.findByMc", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRokMcUmowa", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.umowa=:umowa"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRokUmowa", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.rok = :rok AND k.umowa=:umowa"),
    @NamedQuery(name = "Kalendarzmiesiac.findByFirmaRokMc", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.umowa.angaz.firma=:firma"),
    @NamedQuery(name = "Kalendarzmiesiac.findByFirmaRokMcPraca", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.umowa.angaz.firma=:firma AND k.umowa.umowakodzus.praca = TRUE"),
    @NamedQuery(name = "Kalendarzmiesiac.findByFirmaRokMcZlecenie", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.umowa.angaz.firma=:firma AND k.umowa.umowakodzus.zlecenie = TRUE")
   })
public class Kalendarzmiesiac implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "mc")
    private String mc;
    @NotNull
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;
    @OneToMany(mappedBy = "kalendarzmiesiac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pasekwynagrodzen> pasekwynagrodzenList;
    @OneToMany(mappedBy = "kalendarzmiesiac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dzien> dzienList;
    
    

    public Kalendarzmiesiac() {
        this.dzienList = new ArrayList<>();
        this.pasekwynagrodzenList = new ArrayList<>();
    }

    public Kalendarzmiesiac(int id) {
        this.id = id;
        this.dzienList = new ArrayList<>();
        this.pasekwynagrodzenList = new ArrayList<>();
    }

    public Kalendarzmiesiac(Umowa umowa, String rokWpisu, String miesiacWpisu) {
        this.umowa = umowa;
        this.rok = rokWpisu;
        this.mc = miesiacWpisu;
    }

    public Kalendarzmiesiac(Kalendarzmiesiac selected) {
        this.umowa = selected.umowa;
        this.rok = selected.rok;
        this.mc = selected.mc;
        this.dzienList = selected.dzienList;
    }

      public void edytujdnizglobalnego(Kalendarzwzor kalendarzwzor) {
        for (int i = 0; i < kalendarzwzor.getDzienList().size(); i++) {
            Dzien dzien = this.getDzienList().get(i);
            Dzien dzienwzor = kalendarzwzor.getDzienList().get(i);
            dzien.nanieswzor(dzienwzor);
        }
    }
    
    public String getPierwszyDzien() {
        String zwrot = null;
        if (this.dzienList!=null) {
            zwrot = this.dzienList.get(0).getDatastring();
        }
        return zwrot;
    }
    
    public String getOstatniDzien() {
        String zwrot = null;
        Dzien dzien = null;
        int i = 1;
        if (this.dzienList!=null) {
            do {
                dzien = this.dzienList.get(this.dzienList.size()-i);
                i++;
            } while(dzien.getTypdnia()==-1);
        }
        return dzien.getDatastring();
    }
      
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Umowa getUmowa() {
        return umowa;
    }
    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }
   
      
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kalendarzmiesiac)) {
            return false;
        }
        Kalendarzmiesiac other = (Kalendarzmiesiac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kalendarzmiesiac{" + "rok=" + rok + ", mc=" + mc + ", umowa=" + umowa.getImieNazwisko() + '}';
    }
  
   
    @XmlTransient
    public List<Dzien> getDzienList() {
        return dzienList;
    }

    public void setDzienList(List<Dzien> dzienList) {
        this.dzienList = dzienList;
    }

    public int[] robocze() {
        int[] zwrot = new int[2];
        int roboczenalicz = 0;
        int roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getTypdnia()==0) {
                    roboczenalicz++;
                    if (d.getPrzepracowano()>0) {
                        roboczenawyk++;
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }
    
    public double[] roboczegodz() {
        double[] zwrot = new double[2];
        double roboczenalicz = 0;
        double roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getTypdnia()==0) {
                    roboczenalicz = roboczenalicz+d.getNormagodzin();
                    if (d.getPrzepracowano()>0) {
                        roboczenawyk = roboczenawyk+d.getPrzepracowano();
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }
    
   
    
    public double[] urlopdnigodz() {
        double[] zwrot = new double[2];
        double urlopdni = 0;
        double urlopgodziny = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getUrlopPlatny()>0.0) {
                    urlopdni = urlopdni+1;
                    urlopgodziny = urlopgodziny+d.getUrlopPlatny();
                }
            }
        }
        zwrot[0] = urlopdni;
        zwrot[1] = urlopgodziny;
        return zwrot;
    }
    
    public double[] chorobadnigodz() {
        double[] zwrot = new double[2];
        double chorobadni = 0;
        double chorobagodziny = 0;
        String dataod = null;
        String datado = null;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getWynagrodzeniezachorobe()>0.0) {
                    dataod = d.getNieobecnosc().getDataod();
                    datado = d.getNieobecnosc().getDatado();
                    chorobagodziny = chorobagodziny+d.getWynagrodzeniezachorobe();
                }
            }
        }
        if (dataod!=null&&datado!=null) {
            zwrot[0] = Data.iletodniKalendarzowych(dataod, datado);
        } else {
            zwrot[0] = 0;
        }
        zwrot[1] = chorobagodziny;
        return zwrot;
    }
    
    public double[] zasilekdnigodz() {
        double[] zwrot = new double[2];
        double zasilekdni = 0;
        double zasilekgodziny = 0;
        String dataod = null;
        String datado = null;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getZasilek()>0.0) {
                    dataod = d.getNieobecnosc().getDataod();
                    datado = d.getNieobecnosc().getDatado();
                    zasilekgodziny = zasilekgodziny+d.getZasilek();
                }
            }
        }
        if (dataod!=null&&datado!=null) {
            zwrot[0] = Data.iletodniKalendarzowych(dataod, datado);
        } else {
            zwrot[0] = 0;
        }
        zwrot[1] = zasilekgodziny;
        return zwrot;
    }
    
     public double[] urlopbezplatnydnigodz() {
        double[] zwrot = new double[2];
        double urlopdni = 0;
        double urlopgodziny = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getUrlopbezplatny()>0.0) {
                    urlopdni = urlopdni+1;
                    urlopgodziny = urlopgodziny+d.getUrlopbezplatny();
                }
            }
        }
        zwrot[0] = urlopdni;
        zwrot[1] = urlopgodziny;
        return zwrot;
    }
    
    public double[] roboczenieob(String kod) {
        double[] zwrot = new double[3];
        double roboczenalicz = 0;
        double roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getKod().equals(kod)) {
                    roboczenalicz = roboczenalicz+d.getNormagodzin();
                    if (d.getPrzepracowano()>0) {
                        roboczenawyk = roboczenawyk+d.getPrzepracowano();
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }
    
    public List nieobecnoscipdf(NieobecnosckodzusFacade nieobecnosckodzusFacade) {
        List<Nieobecnosc> wykaz = new ArrayList<>();
        if (this.dzienList!=null) {
            Nieobecnosc biezaca = null;
            int licznik = 1;
            for (Dzien d : dzienList) {
                if (cosjest(d.getKod())&&biezaca==null) {
                    //nowykod
                    Nieobecnosckodzus nieobecnosckodzus = nieobecnosckodzusFacade.findByKod(d.getKod());
                    biezaca = new Nieobecnosc(this.getUmowa());
                    biezaca.setKodzwolnienia(d.getKod());
                    biezaca.setDataod(Data.pelnadata(this,d.getNrdnia()));
                    biezaca.setDatado(Data.pelnadata(this,d.getNrdnia()));
                    biezaca.setNieobecnosckodzus(nieobecnosckodzus);
                } else if (cosjest(d.getKod())&&biezaca!=null) {
                    if (d.getKod().equals(biezaca.getKodzwolnienia())) {
                        biezaca.setDatado(Data.pelnadata(this,d.getNrdnia()));
                    } else {
                        wykaz.add(biezaca);
                        Nieobecnosckodzus nieobecnosckodzus = nieobecnosckodzusFacade.findByKod(d.getKod());
                        biezaca = new Nieobecnosc(this.getUmowa());
                        biezaca.setKodzwolnienia(d.getKod());
                        biezaca.setDataod(Data.pelnadata(this,d.getNrdnia()));
                        biezaca.setDatado(Data.pelnadata(this,d.getNrdnia()));
                        biezaca.setNieobecnosckodzus(nieobecnosckodzus);
                    }
                } else if (!cosjest(d.getKod())&&biezaca!=null) {
                        wykaz.add(biezaca);
                        biezaca = null;
                }
                if (licznik==dzienList.size() && biezaca!=null) {
                    wykaz.add(biezaca);
                    biezaca = null;
                }
                licznik++;
            }
        }
        return wykaz;
    }

    private boolean cosjest(String kod) {
        return kod!=null&&!kod.equals("");
    }

   public boolean isPraca() {
       return this.getUmowa().getUmowakodzus().isPraca();
   }
   
   public Pasekwynagrodzen getPasek() {
       Pasekwynagrodzen zwrot = new Pasekwynagrodzen();
         if (this.pasekwynagrodzenList!=null && this.pasekwynagrodzenList.size()==1) {
           for (Pasekwynagrodzen p : this.pasekwynagrodzenList) {
               zwrot = p;
           }
         }
        return zwrot;
   }
   
   public List<Naliczenieskladnikawynagrodzenia> skladnikiwynagrodzenialista() {
       List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
       if (this.pasekwynagrodzenList!=null && !this.pasekwynagrodzenList.isEmpty()) {
           for (Pasekwynagrodzen p : this.pasekwynagrodzenList) {
               if (p.getNaliczenieskladnikawynagrodzeniaList()!=null) {
                   zwrot.addAll(p.getNaliczenieskladnikawynagrodzeniaList());
               }
           }
       }
       return zwrot;
   }
   
   public List<Naliczenienieobecnosc> skladnikinieobecnosclista() {
       List<Naliczenienieobecnosc> zwrot = new ArrayList<>();
       if (this.pasekwynagrodzenList!=null && !this.pasekwynagrodzenList.isEmpty()) {
           for (Pasekwynagrodzen p : this.pasekwynagrodzenList) {
               if (p.getNaliczenienieobecnoscList()!=null) {
                   zwrot.addAll(p.getNaliczenienieobecnoscList());
               }
           }
       }
       return zwrot;
   }

    public List<Pasekwynagrodzen> getPasekwynagrodzenList() {
        return pasekwynagrodzenList;
    }

    public void setPasekwynagrodzenList(List<Pasekwynagrodzen> pasekwynagrodzenList) {
        this.pasekwynagrodzenList = pasekwynagrodzenList;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

  
    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMc() {
        return mc;
    }
    
    public String getRokMc() {
        return rok+mc;
    }
    

    public void nanies(Kalendarzwzor kalendarzwzor) {
        int i = 0;
        for (Dzien d : this.dzienList) {
            Dzien dzienwzor = kalendarzwzor.getDzienList().get(i++);
            d.nanies(dzienwzor);
        }
    }
    
    public void ganerujdnizwzrocowego(Kalendarzwzor kalendarzwzor, Integer dzienstart) {
        int start = dzienstart!=null? dzienstart:0;
        List<Dzien> nowedni = new ArrayList<>();
        for (int i = 0; i < kalendarzwzor.getDzienList().size(); i++) {
            Dzien dzienwzor = kalendarzwzor.getDzienList().get(i);
            Dzien dzien = new Dzien(dzienwzor, this);
            if (dzien.getNrdnia()<start) {
                dzien.setPrzepracowano(0);
            }
            nowedni.add(dzien);
        }
        this.dzienList = nowedni;
    }

    public String getNazwiskoImie() {
        return this.getUmowa().getAngaz().getPracownik().getNazwiskoImie();
    }
    
    public String getPesel() {
        return this.getUmowa().getAngaz().getPracownik().getPesel();
    }
    
    public String getNazwisko() {
        return this.getUmowa().getAngaz().getPracownik().getNazwisko();
    }
    
    public String getImie() {
        return this.getUmowa().getAngaz().getPracownik().getImie();
    }

    public void naniesnieobecnosc(Nieobecnosc p) {
        int dzienod = Data.getDzienI(p.getDataod());
        int dziendo = Data.getDzienI(p.getDatado());
        String mcod = Data.getMc(p.getDataod());
        String mcdo = Data.getMc(p.getDatado());
        dzienod = modyfikujod(mcod, dzienod);
        dziendo = modyfikujdo(mcdo, dziendo);
        for (int i = dzienod; i <= dziendo; i++) {
            final int j = i;
            Dzien dzienaktualny = this.dzienList.stream().filter(pa->pa.getNrdnia()==j).findFirst().get();
            dzienaktualny.setKod(p.getNieobecnosckodzus().getKod());
            if (p.getNieobecnosckodzus().getKod().equals("331")) {
                dzienaktualny.setWynagrodzeniezachorobe(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
            } else if (p.getNieobecnosckodzus().getKod().equals("100")) {
                dzienaktualny.setUrlopPlatny(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
            } else if (p.getNieobecnosckodzus().getKod().equals("111")) {
                dzienaktualny.setUrlopbezplatny(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
            } else if (p.getNieobecnosckodzus().getKod().equals("777")) {
                
            } else if (p.getNieobecnosckodzus().getKod().equals("200")) {
                dzienaktualny.setNormagodzin(0);
                dzienaktualny.setPrzepracowano(0);
            }
            dzienaktualny.setNieobecnosc(p);
        }
    }

    private int modyfikujod(String mcod, int dzienod) {
        String mckalendarza = this.getMc();
        if (!mcod.equals(mckalendarza)) {
            int mckalendarzaint = Integer.parseInt(mckalendarza);
            int mcodint = Integer.parseInt(mcod);
            if (mcodint<mckalendarzaint) {
                dzienod = 0;
            }
        }
        return dzienod;
    }
    
    private int modyfikujdo(String mcoddo, int dziendo) {
        String mckalendarza = this.getMc();
        if (!mcoddo.equals(mckalendarza)) {
            int mckalendarzaint = Integer.parseInt(mckalendarza);
            int mcdoint = Integer.parseInt(mcoddo);
            if (mcdoint>mckalendarzaint) {
                dziendo = Integer.parseInt(Data.getDzien(Data.ostatniDzien(this.rok, this.mc)));
            }
        }
        return dziendo;
    }

    public boolean czysainnekody() {
        boolean zwrot = false;
        for (Dzien d : this.dzienList) {
            if (d.getKod()!=null&&!d.getKod().equals("777")) {
                zwrot = true;
            }
        }
        return zwrot;
    }

    public Naliczenieskladnikawynagrodzenia getNaliczonewynagrodzenie(Skladnikwynagrodzenia s) {
        Naliczenieskladnikawynagrodzenia zwrot = null;
        List<Naliczenieskladnikawynagrodzenia> lista = skladnikiwynagrodzenialista();
        if (lista!=null) {
            for (Naliczenieskladnikawynagrodzenia naliczenie : lista) {
                if (naliczenie.getSkladnikwynagrodzenia().equals(s)) {
                    zwrot = naliczenie;
                    break;
                }
            }
        }
        return zwrot;
    }
       
        
}

