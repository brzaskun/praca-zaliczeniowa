/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.DeklaracjaVatSchemaBean;
import entity.DeklaracjaVatSchema;
import dao.DeklaracjaVatSchemaDAO;
import dao.EvewidencjaDAO;
import dao.SchemaEwidencjaDAO;
import entity.Evewidencja;
import entity.SchemaEwidencja;
import java.io.Serializable;
import java.util.ArrayList;
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
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private List<DeklaracjaVatSchema> schemyDeklaracjiVat;
    @Inject
    private DeklaracjaVatSchema deklaracjaVatSchema;
    private DeklaracjaVatSchema wybranaschema;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    private List<Evewidencja> ewidencjevat;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    private List<SchemaEwidencja> schemaewidencjalista;
    
    @PostConstruct
    private void init() {
        schemyDeklaracjiVat = deklaracjaVatSchemaDAO.findAll();
        ewidencjevat = evewidencjaDAO.findAll();
    }

    public void skopiujschema() {
        deklaracjaVatSchema = wybranaschema;
        Msg.msg("Wybrano schemę "+wybranaschema.getNazwaschemy());
    }
    
    public void dodajscheme() {
        int czyschemaistnieje = DeklaracjaVatSchemaBean.sprawdzScheme(deklaracjaVatSchema, schemyDeklaracjiVat);
        if (czyschemaistnieje == 1) {
            Msg.msg("e", "Nie można dodać, taka schema o takiej nazwie już istnieje");
        } else if (czyschemaistnieje == 2) {
            Msg.msg("e", "Nie można dodać, niedopasowany okres schemy. Istnieją wcześniejsze");
        } else {
            deklaracjaVatSchemaDAO.dodaj(deklaracjaVatSchema);
            schemyDeklaracjiVat.add(deklaracjaVatSchema);
            deklaracjaVatSchema = new DeklaracjaVatSchema();
            Msg.msg("Dodano schemę");
        }
    }
    
    public void edytujscheme() {
        deklaracjaVatSchemaDAO.edit(deklaracjaVatSchema);
        deklaracjaVatSchema = new DeklaracjaVatSchema();
        Msg.msg("Udana edycja schemy");
    }
    
    public void usun(DeklaracjaVatSchema s) {
        deklaracjaVatSchemaDAO.destroy(s);
        schemyDeklaracjiVat.remove(s);
        Msg.msg("Usunieto schemę");
    }
    
    public void pobierzschemaewidencja() {
        ewidencjevat = evewidencjaDAO.findAll();
        schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(wybranaschema);
        List<Evewidencja> uzupelnioneewidencje = new ArrayList<>();
        for (SchemaEwidencja p : schemaewidencjalista) {
            uzupelnioneewidencje.add(p.getEvewidencja());
        }
        List<Evewidencja> ewidencjedododania = new ArrayList<>();
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

   
    
    
    
}
