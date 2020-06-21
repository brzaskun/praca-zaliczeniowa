/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.DeklaracjaVatSchemaBean;
import dao.DeklaracjaVatPozycjeKoncoweDAO;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatSchemaPozKoncoweDAO;
import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjaVatWierszSumarycznyDAO;
import dao.EvewidencjaDAO;
import dao.SchemaEwidencjaDAO;
import entity.DeklaracjaVatPozycjeKoncowe;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaPozKoncowe;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.DeklaracjaVatWierszSumaryczny;
import entity.Evewidencja;
import entity.SchemaEwidencja;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjaVatSchemaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private List<DeklaracjaVatSchema> schemyDeklaracjiVat;
    @Inject
    private DeklaracjaVatSchema deklaracjaVatSchema;
    private DeklaracjaVatSchema wybranaschema;
    @Inject
    private DeklaracjaVatSchema kopiowanaschema;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    private List<Evewidencja> ewidencjevat;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    private List<SchemaEwidencja> schemaewidencjalista;
    @Inject
    private DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO;
    @Inject
    private DeklaracjaVatSchemaWierszSumDAO deklaracjaVatSchemaWierszSumDAO;
    @Inject
    private DeklaracjaVatPozycjeKoncoweDAO deklaracjaVatPozycjeKoncoweDAO;
    @Inject
    private DeklaracjaVatSchemaPozKoncoweDAO deklaracjaVatSchemaPozKoncoweDAO;
    private List<DeklaracjaVatSchemaWierszSum> schemawierszlista;
    private List<DeklaracjaVatSchemaPozKoncowe> schemapozycjekoncowe;
    
    @PostConstruct
    private void init() { //E.m(this);
        schemyDeklaracjiVat = deklaracjaVatSchemaDAO.findAll();
        wybranaschema = schemyDeklaracjiVat.get(schemyDeklaracjiVat.size()-1);
        ewidencjevat = evewidencjaDAO.findAll();
        pobierzschemawiersz();
        pobierzschemaewidencja();
        
    }

    public void skopiujschema() {
        deklaracjaVatSchema = wybranaschema;
        Msg.msg("Wybrano schemę "+wybranaschema.getNazwaschemy());
    }
    
    public int dodajscheme() {
        int zwrot = 0;
        int czyschemaistnieje = DeklaracjaVatSchemaBean.sprawdzScheme(deklaracjaVatSchema, schemyDeklaracjiVat);
        if (czyschemaistnieje == 1) {
            Msg.msg("e", "Nie można dodać, taka schema o takiej nazwie już istnieje.");
        } else if (czyschemaistnieje == 2) {
            Msg.msg("e", "Nie można dodać, niedopasowany okres schemy. Istnieją wcześniejsze.");
        } else if (czyschemaistnieje == 3) {
            Msg.msg("e", "Nie można dodać, brak nazwy nowej schemy.");
        } else if (czyschemaistnieje == 4) {
            Msg.msg("e", "Nie można dodać. Nazwa schemy nie rozpoczyna się od M- lub K-");
        } else {
            deklaracjaVatSchemaDAO.dodaj(deklaracjaVatSchema);
            schemyDeklaracjiVat.add(deklaracjaVatSchema);
            deklaracjaVatSchema = new DeklaracjaVatSchema();
            Msg.msg("Dodano schemę");
            zwrot = 1;
        }
        return zwrot;
    }
    
    public void edytujscheme() {
        deklaracjaVatSchemaDAO.edit(deklaracjaVatSchema);
        Msg.msg("Udana edycja schemy");
    }
    
    public void usun(DeklaracjaVatSchema s) {
        deklaracjaVatSchemaDAO.destroy(s);
        schemyDeklaracjiVat.remove(s);
        if (schemyDeklaracjiVat.size() > 0) {
            wybranaschema = schemyDeklaracjiVat.get(schemyDeklaracjiVat.size()-1);
        }
        Msg.msg("Usunieto schemę");
    }
    
    public void pobierzschemaewidencja() {
        ewidencjevat = evewidencjaDAO.findAll();
        schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(wybranaschema);
        List<Evewidencja> uzupelnioneewidencje = Collections.synchronizedList(new ArrayList<>());
        for (SchemaEwidencja p : schemaewidencjalista) {
            uzupelnioneewidencje.add(p.getEvewidencja());
        }
        List<Evewidencja> ewidencjedododania = Collections.synchronizedList(new ArrayList<>());
        for (Evewidencja r : ewidencjevat) {
            if (uzupelnioneewidencje.contains(r)) {
                uzupelnioneewidencje.remove(r);
            } else {
                ewidencjedododania.add(r);
            }
        }
        if (!ewidencjedododania.isEmpty()) {
            for (Evewidencja s : ewidencjedododania) {
                SchemaEwidencja nowaschemaewidencja = new SchemaEwidencja(wybranaschema, s, null, null);
                schemaewidencjalista.add(nowaschemaewidencja);
            }
        }
    }

    public void zachowaj() {
        schemaEwidencjaDAO.editList(schemaewidencjalista);
        Msg.msg("Zachowano schemat ewidencji");
    }
    
    public void duplikuj(SchemaEwidencja s) {
        try {
            SchemaEwidencja c = serialclone.SerialClone.clone(s);
            c.setId(null);
            c.setPolenetto(null);
            c.setPolevat(null);
            schemaewidencjalista.add(c);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void usun(SchemaEwidencja s) {
        try {
            schemaEwidencjaDAO.destroy(s);
            schemaewidencjalista.remove(s);
            Msg.uP();
        } catch (Exception e) {
            Msg.uPe();
        }
    }
    
    public void pobierzschemawiersz() {
        List<DeklaracjaVatWierszSumaryczny> listawierszy = deklaracjaVatWierszSumarycznyDAO.findAll();
        schemawierszlista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(wybranaschema);
        List<DeklaracjaVatWierszSumaryczny> uzupelnionewiersze = Collections.synchronizedList(new ArrayList<>());
        for (DeklaracjaVatSchemaWierszSum p : schemawierszlista) {
            uzupelnionewiersze.add(p.getDeklaracjaVatWierszSumaryczny());
        }
        List<DeklaracjaVatWierszSumaryczny> wierszedododania = Collections.synchronizedList(new ArrayList<>());
        for (DeklaracjaVatWierszSumaryczny r : listawierszy) {
            if (uzupelnionewiersze.contains(r)) {
                uzupelnionewiersze.remove(r);
            } else {
                wierszedododania.add(r);
            }
        }
        if (!wierszedododania.isEmpty()) {
            for (DeklaracjaVatWierszSumaryczny s : wierszedododania) {
                DeklaracjaVatSchemaWierszSum nowyschemawiersz = new DeklaracjaVatSchemaWierszSum(wybranaschema, s, null, null);
                schemawierszlista.add(nowyschemawiersz);
            }
        }
    }
    
     public void zachowajsumaryczne() {
        deklaracjaVatSchemaWierszSumDAO.editList(schemawierszlista);
        Msg.msg("Zachowano scheme-wiersz");
    }
     
     
     public void kopiujschemaprzyporzadkowania() {
         if (wybranaschema != null && kopiowanaschema.getNazwaschemy() != null) {
             if (!wybranaschema.equals(kopiowanaschema)) {
                schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(wybranaschema);
                schemawierszlista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(wybranaschema);
                List<SchemaEwidencja> nowaschemaewidencja = Collections.synchronizedList(new ArrayList<>());
                for (SchemaEwidencja p : schemaewidencjalista) {
                    p.setDeklaracjaVatSchema(kopiowanaschema);
                    p.setId(null);
                    nowaschemaewidencja.add(p);
                }
                if (!nowaschemaewidencja.isEmpty()) {
                    schemaEwidencjaDAO.dodaj(nowaschemaewidencja);
                }
                List<DeklaracjaVatSchemaWierszSum> nowesumwiersze = Collections.synchronizedList(new ArrayList<>());
                for (DeklaracjaVatSchemaWierszSum p : schemawierszlista) {
                    p.setDeklaracjaVatSchema(kopiowanaschema);
                    p.setId(null);
                    nowesumwiersze.add(p);
                }
                if (!nowesumwiersze.isEmpty()) {
                    deklaracjaVatSchemaWierszSumDAO.dodaj(nowesumwiersze);
                }
                wybranaschema = kopiowanaschema;
                schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(wybranaschema);
                schemawierszlista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(wybranaschema);
                Msg.msg("Zakończono kopiowanie przyporządkowań");
             } else {
                 Msg.msg("e", "Schema źródłowa i docelowa jest identyczna");
             }
         } else {
             Msg.msg("e", "Nie wybrano schemy wzorcowej. Nie określono nazwy schemy docelowej");
         }
     }
     
     public void pobierzschemapozycjekoncowe() {
        List<DeklaracjaVatPozycjeKoncowe> listapozycjikoncowych = deklaracjaVatPozycjeKoncoweDAO.findAll();
        schemapozycjekoncowe = deklaracjaVatSchemaPozKoncoweDAO.findWierszeSchemy(wybranaschema);
        List<DeklaracjaVatPozycjeKoncowe> uzupelnionewiersze = Collections.synchronizedList(new ArrayList<>());
        if (schemapozycjekoncowe != null) {
            for (DeklaracjaVatSchemaPozKoncowe p : schemapozycjekoncowe) {
                uzupelnionewiersze.add(p.getDeklaracjaVatPozycjeKoncowe());
            }
        } else {
            schemapozycjekoncowe = Collections.synchronizedList(new ArrayList<>());
        }
        List<DeklaracjaVatPozycjeKoncowe> wierszedododania = Collections.synchronizedList(new ArrayList<>());
        for (DeklaracjaVatPozycjeKoncowe r : listapozycjikoncowych) {
            if (uzupelnionewiersze.contains(r)) {
                uzupelnionewiersze.remove(r);
            } else {
                wierszedododania.add(r);
            }
        }
        if (!wierszedododania.isEmpty()) {
            for (DeklaracjaVatPozycjeKoncowe s : wierszedododania) {
                DeklaracjaVatSchemaPozKoncowe nowyschemawiersz = new DeklaracjaVatSchemaPozKoncowe(wybranaschema, s, null);
                schemapozycjekoncowe.add(nowyschemawiersz);
            }
        }
    }
    
     public void zachowajpozycjekoncowe() {
        deklaracjaVatSchemaPozKoncoweDAO.editList(schemapozycjekoncowe);
        Msg.msg("Zachowano scheme-pozycje końcowe");
    }
     
     public void sprawdzwybormacierzystej(SchemaEwidencja item) {
         if (item.equals(item.getSchemamacierzysta())) {
             item.setSchemamacierzysta(null);
             Msg.msg("e", "Wskazałeś na tę samą ewidencję. Popraw to");
         }
     }
     
     //<editor-fold defaultstate="collapsed" desc="comment">
     public List<DeklaracjaVatSchema> getSchemyDeklaracjiVat() {
         return schemyDeklaracjiVat;
     }
     
     public void setSchemyDeklaracjiVat(List<DeklaracjaVatSchema> schemyDeklaracjiVat) {
         this.schemyDeklaracjiVat = schemyDeklaracjiVat;
     }
     
     public DeklaracjaVatSchema getDeklaracjaVatSchema() {
         return deklaracjaVatSchema;
     }
     
     public void setDeklaracjaVatSchema(DeklaracjaVatSchema deklaracjaVatSchema) {
         this.deklaracjaVatSchema = deklaracjaVatSchema;
     }

    public List<DeklaracjaVatSchemaPozKoncowe> getSchemapozycjekoncowe() {
        return schemapozycjekoncowe;
    }

    public void setSchemapozycjekoncowe(List<DeklaracjaVatSchemaPozKoncowe> schemapozycjekoncowe) {
        this.schemapozycjekoncowe = schemapozycjekoncowe;
    }

     public DeklaracjaVatSchema getWybranaschema() {
         return wybranaschema;
     }
     
     public void setWybranaschema(DeklaracjaVatSchema wybranaschema) {
         this.wybranaschema = wybranaschema;
     }
     
     public EvewidencjaDAO getEvewidencjaDAO() {
         return evewidencjaDAO;
     }
     
     public void setEvewidencjaDAO(EvewidencjaDAO evewidencjaDAO) {
         this.evewidencjaDAO = evewidencjaDAO;
     }
     
     public List<Evewidencja> getEwidencjevat() {
         return ewidencjevat;
     }
     
     public void setEwidencjevat(List<Evewidencja> ewidencjevat) {
         this.ewidencjevat = ewidencjevat;
     }
     
     public List<SchemaEwidencja> getSchemaewidencjalista() {
         return schemaewidencjalista;
     }
     
     public void setSchemaewidencjalista(List<SchemaEwidencja> schemaewidencjalista) {
         this.schemaewidencjalista = schemaewidencjalista;
     }

    public DeklaracjaVatSchema getKopiowanaschema() {
        return kopiowanaschema;
    }

    public void setKopiowanaschema(DeklaracjaVatSchema kopiowanaschema) {
        this.kopiowanaschema = kopiowanaschema;
    }
     
     public List<DeklaracjaVatSchemaWierszSum> getSchemawierszlista() {
         return schemawierszlista;
     }
     
     public void setSchemawierszlista(List<DeklaracjaVatSchemaWierszSum> schemawierszlista) {
         this.schemawierszlista = schemawierszlista;
     }
     
//</editor-fold>
    
    
}
