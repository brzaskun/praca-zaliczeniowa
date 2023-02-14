/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beanstesty.DataBean;
import comparator.Dziencomparator;
import dao.RodzajnieobecnosciFacade;
import data.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kalendarzmiesiac", uniqueConstraints = {
    @UniqueConstraint(columnNames={"rok,mc,angaz"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendarzmiesiac.findAll", query = "SELECT k FROM Kalendarzmiesiac k"),
    @NamedQuery(name = "Kalendarzmiesiac.findById", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.id = :id"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRok", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kalendarzmiesiac.findByMc", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRokMcAngaz", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.angaz=:angaz"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRokAngaz", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.rok = :rok AND k.angaz=:angaz"),
    @NamedQuery(name = "Kalendarzmiesiac.findByAngaz", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.angaz=:angaz"),
    @NamedQuery(name = "Kalendarzmiesiac.findByFirmaRokMc", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.angaz.firma=:firma"),
    @NamedQuery(name = "Kalendarzmiesiac.findByFirmaRokMcNierezydent", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.angaz.firma=:firma AND k.angaz.pracownik.nierezydent = TRUE")
   })
@Cacheable(false)
public class Kalendarzmiesiac implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
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
//    @NotNull
//    @JoinColumn(name = "umowa", referencedColumnName = "id")
//    @ManyToOne
//    private Umowa umowa;
    @OneToMany(mappedBy = "kalendarzmiesiac", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pasekwynagrodzen> pasekwynagrodzenList;
    @OneToMany(mappedBy = "kalendarzmiesiac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dzien> dzienList;
    @Column(name="dnioddelegowania")
    private double dnioddelegowania;
    @Column(name="norma")
    private double norma;
    @Column(name="przepracowane")
    private double przepracowane;
    @Column(name="urlop")
    private double urlop;
    @Column(name="urlopbezplatny")
    private double urlopbezplatny;
    @Column(name="choroba")
    private double choroba;
    @Column(name="zasilek")
    private double zasilek;
    @Column(name="opiekadziecko")
    private double opiekadziecko;
    @Column(name="macierzynski")
    private double macierzynski;
    @Column(name="wychowawczy")
    private double wychowawczy;
    @Column(name="piecdziesiatka")
    private double piecdziesiatka;
    @Column(name="setka")
    private double setka;
    @Column(name="poranocna")
    private double poranocna;
    @Column(name="dnidelegowaniewymiar")
    private double dnidelegowaniewymiar;
    @Column(name="godzinydelegowaniewymiar")
    private double godzinydelegowaniewymiar;
    @Column(name="godzinydelegowanieprzepracowane")
    private double godzinydelegowanieprzepracowane;
    @Column(name="delegowanienadgodziny")
    private double delegowanienadgodziny;
    @Column(name="stawkazagodzine")
    private double stawkazagodzine;
    @Column(name="dodatekzanadgodziny")
    private double dodatekzanadgodziny;
    @Column(name="dodatekzanadgodzinymc")
    private double dodatekzanadgodzinymc;
    
    


    public Kalendarzmiesiac() {
        this.dzienList = new ArrayList<>();
        this.pasekwynagrodzenList = new ArrayList<>();
    }

    public Kalendarzmiesiac(int id) {
        this.id = id;
        this.dzienList = new ArrayList<>();
        this.pasekwynagrodzenList = new ArrayList<>();
    }

    public Kalendarzmiesiac(Angaz angaz, String rokWpisu, String miesiacWpisu) {
        this.angaz = angaz;
        this.rok = rokWpisu;
        this.mc = miesiacWpisu;
    }

    public Kalendarzmiesiac(Kalendarzmiesiac selected) {
        this.angaz = selected.angaz;
        this.rok = selected.rok;
        this.mc = selected.mc;
        this.dzienList = selected.dzienList;
    }

    public Kalendarzmiesiac(Kalendarzwzor findByFirmaRokMc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void edytujdnizglobalnego(Kalendarzwzor kalendarzwzor) {
        List<Dzien> dzienListwzor = kalendarzwzor.getDzienList();
        Collections.sort(dzienListwzor, new Dziencomparator());
        List<Dzien> dzienList = this.getDzienList();
        Collections.sort(dzienList, new Dziencomparator());
        for (int i = 0; i < dzienListwzor.size(); i++) {
            Dzien dzien = dzienList.get(i);
            Dzien dzienwzor = dzienListwzor.get(i);
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
    public Angaz getAngaz() {
        return angaz;
    }
    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
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
        return "Kalendarzmiesiac{" + "rok=" + rok + ", mc=" + mc + ", angaz=" + getNazwiskoImie() + '}';
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
    
    public double[] roboczeOddelegowaniePolska() {
        double[] zwrot = new double[2];
        double roboczenalicz = 0;
        double roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getTypdnia()==0&&(d.getKod()==null||(d.getKod()!=null&&!d.getKod().equals("Z")))) {
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
    
     public double[] roboczeOddelegowanieZagranica() {
        double[] zwrot = new double[2];
        double roboczenalicz = 0;
        double roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getTypdnia()==0&&d.getKod()!=null&&d.getKod().equals("Z")) {
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
                    String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(this);
                    String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(this);
                    dataod = Data.czyjestpo(pierwszydzienmiesiaca, d.getNieobecnosc().getDataod())?d.getNieobecnosc().getDataod():pierwszydzienmiesiaca;
                    datado = Data.czyjestprzed(ostatnidzienmiesiaca, d.getNieobecnosc().getDatado())?d.getNieobecnosc().getDatado():ostatnidzienmiesiaca;
                    chorobagodziny = chorobagodziny+d.getWynagrodzeniezachorobe();
                    zwrot[0] = Data.iletodniKalendarzowych(dataod, datado);
                }
            }
        }
        zwrot[1] = chorobagodziny;
        return zwrot;
    }
    
    public double[] macierzynskidnigodz() {
        double[] zwrot = new double[2];
        double chorobadni = 0;
        double chorobagodziny = 0;
        String dataod = null;
        String datado = null;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getMacierzynski()>0.0) {
                    String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(this);
                    String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(this);
                    dataod = Data.czyjestpo(pierwszydzienmiesiaca, d.getNieobecnosc().getDataod())?d.getNieobecnosc().getDataod():pierwszydzienmiesiaca;
                    datado = Data.czyjestprzed(ostatnidzienmiesiaca, d.getNieobecnosc().getDatado())?d.getNieobecnosc().getDatado():ostatnidzienmiesiaca;
                    chorobagodziny = chorobagodziny+d.getWynagrodzeniezachorobe();
                    zwrot[0] = Data.iletodniKalendarzowych(dataod, datado);
                }
            }
        }
        zwrot[1] = chorobagodziny;
        return zwrot;
    }
    
    public double[] wychowawczydnigodz() {
        double[] zwrot = new double[2];
        double chorobadni = 0;
        double chorobagodziny = 0;
        String dataod = null;
        String datado = null;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getWychowawczy()>0.0) {
                    String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(this);
                    String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(this);
                    dataod = Data.czyjestpo(pierwszydzienmiesiaca, d.getNieobecnosc().getDataod())?d.getNieobecnosc().getDataod():pierwszydzienmiesiaca;
                    datado = Data.czyjestprzed(ostatnidzienmiesiaca, d.getNieobecnosc().getDatado())?d.getNieobecnosc().getDatado():ostatnidzienmiesiaca;
                    chorobagodziny = chorobagodziny+d.getWynagrodzeniezachorobe();
                    zwrot[0] = Data.iletodniKalendarzowych(dataod, datado);
                }
            }
        }
        zwrot[1] = chorobagodziny;
        return zwrot;
    }
    
    public double[] chorobaczywaloryzacja() {
        double[] zwrot = new double[3];
        double godzinyobowiazku = 0;
        double chorobagodziny = 0;
        double przepracowane = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getPrzepracowano()==0) {
                    chorobagodziny = chorobagodziny+d.getWynagrodzeniezachorobe()+d.getZasilek()+d.getUrlopbezplatny();
                }
                if (d.getTypdnia()==0) {
                    godzinyobowiazku = godzinyobowiazku+d.getNormagodzin();
                }
            }
        }
        przepracowane = godzinyobowiazku-chorobagodziny;
        double polowagodzinyobowiazku = godzinyobowiazku/2;
        zwrot[0] = godzinyobowiazku;
        zwrot[1] = chorobagodziny;
        //jedynka to trzeba upgradowac
        if (przepracowane!=godzinyobowiazku) {
            zwrot[2] = przepracowane>polowagodzinyobowiazku?1:0;
        } else {
            zwrot[2] = 0;
        }
        return zwrot;
    }
    
    public boolean czyjestZarudnienieWtrakcieMca() {
        boolean zwrot = false;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getKod()!=null&&d.getKod().equals("D")&&d.getTypdnia()==0) {
                    zwrot = true;
                    break;
                }
                if (d.getKod()!=null&&d.getKod().equals("NN")&&d.getTypdnia()==0) {
                    zwrot = true;
                    break;
                }
                if (d.getKod()!=null&&d.getKod().equals("NP")&&d.getTypdnia()==0) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    
    public boolean czyjestchoroba() {
        boolean zwrot = false;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getWynagrodzeniezachorobe()>0.0) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }
    public boolean czyjestzasilek() {
        boolean zwrot = false;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getZasilek()>0.0) {
                    zwrot = true;
                    break;
                }
            }
        }
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
        if (this.dzienList!=null && kod!=null) {
            for (Dzien d : dzienList) {
                if (cosjest(d.getKod())) {
                    if (d.getKod().equals(kod)) {
                        roboczenalicz = roboczenalicz+d.getNormagodzin();
                        if (d.getPrzepracowano()>0) {
                            roboczenawyk = roboczenawyk+d.getPrzepracowano();
                        }
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }
    
    public List nieobecnoscipdf(RodzajnieobecnosciFacade rodzajnieobecnosciFacade) {
        List<Nieobecnosc> wykaz = new ArrayList<>();
        if (this.dzienList!=null) {
            Nieobecnosc biezaca = null;
            int licznik = 1;
            for (Dzien d : dzienList) {
                if (cosjest(d.getKod())&&biezaca==null) {
                    //nowykod
                    Rodzajnieobecnosci nieobecnosckodzus = rodzajnieobecnosciFacade.findByKod(d.getKod());
                    biezaca = new Nieobecnosc(this.getAngaz());
                    biezaca.setKodzwolnienia(d.getKod());
                    biezaca.setDataod(Data.pelnadata(this,d.getNrdnia()));
                    biezaca.setDatado(Data.pelnadata(this,d.getNrdnia()));
                    biezaca.setRodzajnieobecnosci(nieobecnosckodzus);
                } else if (cosjest(d.getKod())&&biezaca!=null) {
                    if (d.getKod().equals(biezaca.getKodzwolnienia())) {
                        biezaca.setDatado(Data.pelnadata(this,d.getNrdnia()));
                    } else {
                        wykaz.add(biezaca);
                        Rodzajnieobecnosci nieobecnosckodzus = rodzajnieobecnosciFacade.findByKod(d.getKod());
                        biezaca = new Nieobecnosc(this.getAngaz());
                        biezaca.setKodzwolnienia(d.getKod());
                        biezaca.setDataod(Data.pelnadata(this,d.getNrdnia()));
                        biezaca.setDatado(Data.pelnadata(this,d.getNrdnia()));
                        biezaca.setRodzajnieobecnosci(nieobecnosckodzus);
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


   public Pasekwynagrodzen getPasek() {
       Pasekwynagrodzen zwrot = new Pasekwynagrodzen();
         if (this.pasekwynagrodzenList!=null && this.pasekwynagrodzenList.size()==1) {
           for (Pasekwynagrodzen p : this.pasekwynagrodzenList) {
               zwrot = p;
           }
         }
        return zwrot;
   }
   
   public Pasekwynagrodzen getPasek(Definicjalistaplac definicjalistaplac) {
       Pasekwynagrodzen zwrot = new Pasekwynagrodzen();
         if (this.pasekwynagrodzenList!=null && this.pasekwynagrodzenList.size()>0&&definicjalistaplac!=null) {
           for (Pasekwynagrodzen p : this.pasekwynagrodzenList) {
               if (p.getDefinicjalistaplac().getRodzajlistyplac().equals(definicjalistaplac.getRodzajlistyplac())) {
                    zwrot = p;
                    break;
               }
           }
         }
        return zwrot;
   }
   
  
   

   
   public List<Naliczenienieobecnosc> skladnikinieobecnosclista(Definicjalistaplac wybranalistaplac2) {
       Set<Naliczenienieobecnosc> zwrot = new HashSet<>();
       if (this.pasekwynagrodzenList!=null && !this.pasekwynagrodzenList.isEmpty()) {
           for (Pasekwynagrodzen p : this.pasekwynagrodzenList) {
               if (p.getDefinicjalistaplac().getRodzajlistyplac().equals(wybranalistaplac2.getRodzajlistyplac())) {
                    if (p.getNaliczenienieobecnoscList()!=null) {
                        zwrot.addAll(p.getNaliczenienieobecnoscList());
                    }
               }
           }
       }
       return new ArrayList<>(zwrot);
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
    
    public Integer getRokI() {
        Integer zwrot = 0;
        if (this.rok!=null) {
            zwrot = Integer.parseInt(this.rok);
        }
        return zwrot;
    }

  
    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMc() {
        return mc;
    }
    
    public int getMcI() {
        Integer zwrot = 0;
        if (this.mc!=null) {
            zwrot = Integer.parseInt(this.mc);
        }
        return zwrot;
    }
    
    public String getRokMc() {
        return rok+mc;
    }

    

    public void nanies(Kalendarzwzor kalendarzwzor) {
        List<Dzien> dzienListWzor = kalendarzwzor.getDzienList();
        Collections.sort(dzienListWzor, new Dziencomparator());
        List<Dzien> dzienList = this.getDzienList();
        Collections.sort(dzienList, new Dziencomparator());
        int i = 0;
        for (Dzien d : dzienList) {
            Dzien dzienwzor = dzienListWzor.get(i++);
            d.nanies(dzienwzor);
        }
    }
    
    public void ganerujdnizwzrocowego(Kalendarzwzor kalendarzwzor, Integer dzienstart) {
        List<Dzien> dzienListWzor = kalendarzwzor.getDzienList();
        Collections.sort(dzienListWzor, new Dziencomparator());
        int start = dzienstart!=null? dzienstart:0;
        List<Dzien> nowedni = new ArrayList<>();
        for (int i = 0; i < dzienListWzor.size(); i++) {
            Dzien dzienwzor = dzienListWzor.get(i);
            Dzien dzien = new Dzien(dzienwzor, this);
            if (dzien.getNrdnia()<start) {
                dzien.setPrzepracowano(0);
            }
            nowedni.add(dzien);
        }
        this.dzienList = nowedni;
    }
    
    
    public void ganerujdnizwzrocowego(Kalendarzwzor kalendarzwzor, Integer dzienstart, List<EtatPrac> etaty) {
        List<Dzien> dzienListWzor = kalendarzwzor.getDzienList();
        Collections.sort(dzienListWzor, new Dziencomparator());
        int start = dzienstart!=null? dzienstart:0;
        List<Dzien> nowedni = new ArrayList<>();
        for (int i = 0; i < dzienListWzor.size(); i++) {
            Dzien dzienwzor = dzienListWzor.get(i);
            EtatPrac etat = pobierzetat(etaty, dzienwzor);
            Dzien dzien = new Dzien(dzienwzor, this, etat);
            if (dzien.getNrdnia()<start) {
                dzien.setPrzepracowano(0);
            }
            nowedni.add(dzien);
        }
        this.dzienList = nowedni;
    }
    
   
    
    private EtatPrac pobierzetat(List<EtatPrac> etaty, Dzien dzienwzor) {
        String datadnia = dzienwzor.getDatastring();
        EtatPrac zwrot = null;
        if (etaty!=null) {
            for (EtatPrac e : etaty) {
                boolean czysiemiesci = DataBean.czysiemiescidzien(datadnia, e.getDataod(), e.getDatado());
                if (czysiemiesci) {
                    zwrot = e;
                    break;
                }
            }
        }
        return zwrot;
    }

    public String getNazwiskoImie() {
        return this.getAngaz().getPracownik().getNazwiskoImie();
    }
    
    public String getPesel() {
        return this.getAngaz().getPracownik().getPesel();
    }
    
    public String getDataUrodzenia() {
        return this.getAngaz().getPracownik().getDataurodzenia();
    }
    
    public String getNazwisko() {
        return this.getAngaz().getPracownik().getNazwisko();
    }
    
    public String getImie() {
        return this.getAngaz().getPracownik().getImie();
    }

    public double getDnioddelegowania() {
        return dnioddelegowania;
    }

    public void setDnioddelegowania(double dnioddelegowania) {
        this.dnioddelegowania = dnioddelegowania;
    }
    
    public double getNorma() {
        return norma;
    }

    public void setNorma(double norma) {
        this.norma = norma;
    }

    public double getPrzepracowane() {
        return przepracowane;
    }

    public void setPrzepracowane(double przepracowane) {
        this.przepracowane = przepracowane;
    }

    public double getUrlop() {
        return urlop;
    }

    public void setUrlop(double urlop) {
        this.urlop = urlop;
    }

    public double getUrlopbezplatny() {
        return urlopbezplatny;
    }

    public void setUrlopbezplatny(double urlopbezplatny) {
        this.urlopbezplatny = urlopbezplatny;
    }

    public double getChoroba() {
        return choroba;
    }

    public void setChoroba(double choroba) {
        this.choroba = choroba;
    }

    public double getZasilek() {
        return zasilek;
    }

    public void setZasilek(double zasilek) {
        this.zasilek = zasilek;
    }

    public double getPiecdziesiatka() {
        return piecdziesiatka;
    }

    public void setPiecdziesiatka(double piecdziesiatka) {
        this.piecdziesiatka = piecdziesiatka;
    }

    public double getSetka() {
        return setka;
    }

    public void setSetka(double setka) {
        this.setka = setka;
    }

    public double getPoranocna() {
        return poranocna;
    }

    public void setPoranocna(double poranocna) {
        this.poranocna = poranocna;
    }

    public double getOpiekadziecko() {
        return opiekadziecko;
    }

    public void setOpiekadziecko(double opiekadziecko) {
        this.opiekadziecko = opiekadziecko;
    }

    public double getMacierzynski() {
        return macierzynski;
    }

    public void setMacierzynski(double macierzynski) {
        this.macierzynski = macierzynski;
    }

    public double getWychowawczy() {
        return wychowawczy;
    }

    public void setWychowawczy(double wychowawczy) {
        this.wychowawczy = wychowawczy;
    }

    
    
    public void naniesnieobecnosc(Nieobecnosc p, boolean pierwszymc, boolean ostatnimc) {
        int dzienod = Data.getDzienI(p.getDataod());
        int dziendo = Data.getDzienI(p.getDatado());
        String mcod = Data.getMc(p.getDataod());
        String mcdo = Data.getMc(p.getDatado());
        if (pierwszymc==true&&ostatnimc==false) {
            dziendo = 31;
        } else if (pierwszymc==false&&ostatnimc==true) {
            dzienod = 1;
        } else if (pierwszymc==false&&ostatnimc==false) {
            dzienod = 1;
            dziendo = 31;
        }
        int dnirobocze = 0;
        int godzinyrobocze = 0;
        if (p.getDzienList()==null) {
            p.setDzienList(new ArrayList<>());
        }
        for (int i = dzienod; i <= dziendo; i++) {
            final int j = i;
            Dzien dzienaktualny = this.dzienList.stream().filter(pa->pa.getNrdnia()==j).findFirst().get();
            String kod = p.getKod();
            String kodzbiorczy = p.getRodzajnieobecnosci().getKodzbiorczy();
            if (kod.equals("CH")) {
                dzienaktualny.setWynagrodzeniezachorobe(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
                
            } else if (kod.equals("ZC")) {
                dzienaktualny.setZasilek(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            } else if (kod.equals("U")||kod.equals("UD")) {
                dzienaktualny.setUrlopPlatny(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            } else if (kod.equals("X")||kod.equals("NP")||kod.equals("NN")) {
                dzienaktualny.setUrlopbezplatny(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            } else if (kod.equals("MD")) {
                dzienaktualny.setOpiekadziecko(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            } else if (kod.equals("UM")) {
                dzienaktualny.setMacierzynski(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            } else if (kod.equals("UR")) {
                dzienaktualny.setMacierzynski(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            } else if (kod.equals("WY")) {
                dzienaktualny.setWychowawczy(dzienaktualny.getNormagodzin());
                dzienaktualny.setPrzepracowano(0);
                dzienaktualny.setKod(kod);
                p.setNaniesiona(true);
            }  else if (kod.equals("Z")) {
                if (p.isPonpiatek()) {
                    if (dzienaktualny.getTypdnia()==0) {
                        dzienaktualny.setKod(kod);
                        p.setNaniesiona(true);
                    }
                } else {
                    dzienaktualny.setKod(kod);
                    p.setNaniesiona(true);
                }
            } else if (kod.equals("D")) {
                dzienaktualny.setKod(kod);
                dzienaktualny.setPrzepracowano(0);
                p.setNaniesiona(true);
            }
            dzienaktualny.setNieobecnosc(p);
            if (dzienaktualny.getTypdnia()==0) {
                dnirobocze = dnirobocze+1;
                godzinyrobocze = godzinyrobocze+((int)dzienaktualny.getNormagodzin());
            }
            p.getDzienList().add(dzienaktualny);
        }
        p.setDniroboczenieobecnosci(dnirobocze);
        p.setGodzinyroboczenieobecnosc(godzinyrobocze);

    }

//    private int modyfikujod(String mcod, int dzienod) {
//        String mckalendarza = this.getMc();
//        if (!mcod.equals(mckalendarza)) {
//            int mckalendarzaint = Integer.parseInt(mckalendarza);
//            int mcodint = Integer.parseInt(mcod);
//            if (mcodint<mckalendarzaint) {
//                dzienod = 1;
//            }
//        }
//        return dzienod;
//    }
//    
//    private int modyfikujdo(String mcoddo, int dziendo) {
//        String mckalendarza = this.getMc();
//        if (!mcoddo.equals(mckalendarza)) {
//            int mckalendarzaint = Integer.parseInt(mckalendarza);
//            int mcdoint = Integer.parseInt(mcoddo);
//            if (mcdoint>mckalendarzaint) {
//                dziendo = Integer.parseInt(Data.getDzien(Data.ostatniDzien(this.rok, this.mc)));
//            }
//        }
//        return dziendo;
//    }

    public boolean czysainnekody() {
        boolean zwrot = false;
        for (Dzien d : this.dzienList) {
            if (d.getKod()!=null&&!d.getKod().equals("Z")) {
                zwrot = true;
            }
        }
        return zwrot;
    }

    

    public double pobierzPodstaweNieobecnosc(Nieobecnosc nieobecnosc) {
        double kwota = 0.0;
        if (this.getPasek()!=null) {
            for (Naliczenienieobecnosc p : this.getPasek().getNaliczenienieobecnoscList()) {
                if (p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("CH")||p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("ZC")) {
                    kwota = p.getPodstawadochoroby();
                }
            }
        }
        return kwota;
    }
    
    public double pobierzSumeKwotNieobecnosc(Nieobecnosc nieobecnosc) {
        double kwota = 0.0;
        if (this.getPasek()!=null) {
            for (Naliczenienieobecnosc p : this.getPasek().getNaliczenienieobecnoscList()) {
                if (p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("CH")||p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("ZC")) {
                    kwota = p.getSumakwotdosredniej();
                }
            }
        }
        return kwota;
    }

    public boolean isNierezydent() {
        return this.getAngaz().getPracownik().isNierezydent();
    }

    public Pracownik getPracownik() {
        return this.getAngaz().getPracownik();
    }

    public Dzien getDzień(int nrdnia) {
        Dzien zwrot = null;
        for (Dzien p : this.dzienList) {
            if (p.getNrdnia()==nrdnia) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    public double getGodzinydelegowaniewymiar() {
        return godzinydelegowaniewymiar;
    }

    public void setGodzinydelegowaniewymiar(double godzinydelegowaniewymiar) {
        this.godzinydelegowaniewymiar = godzinydelegowaniewymiar;
    }

    public double getGodzinydelegowanieprzepracowane() {
        return godzinydelegowanieprzepracowane;
    }

    public void setGodzinydelegowanieprzepracowane(double godzinydelegowanieprzepracowane) {
        this.godzinydelegowanieprzepracowane = godzinydelegowanieprzepracowane;
    }

    public double getDelegowanienadgodziny() {
        return delegowanienadgodziny;
    }

    public void setDelegowanienadgodziny(double delegowanienadgodziny) {
        this.delegowanienadgodziny = delegowanienadgodziny;
    }

    public double getStawkazagodzine() {
        return stawkazagodzine;
    }

    public void setStawkazagodzine(double stawkazagodzine) {
        this.stawkazagodzine = stawkazagodzine;
    }

    public double getDodatekzanadgodziny() {
        return dodatekzanadgodziny;
    }

    public void setDodatekzanadgodziny(double dodatekzanadgodziny) {
        this.dodatekzanadgodziny = dodatekzanadgodziny;
    }

    public double getDodatekzanadgodzinymc() {
        return dodatekzanadgodzinymc;
    }

    public void setDodatekzanadgodzinymc(double dodatekzanadgodzinymc) {
        this.dodatekzanadgodzinymc = dodatekzanadgodzinymc;
    }

    public double getDnidelegowaniewymiar() {
        return dnidelegowaniewymiar;
    }

    public void setDnidelegowaniewymiar(double dnidelegowaniewymiar) {
        this.dnidelegowaniewymiar = dnidelegowaniewymiar;
    }

    
       
        
}

