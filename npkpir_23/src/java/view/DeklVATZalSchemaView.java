/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.DeklaracjaVatSchemaBean;
import beansDok.DeklaracjaVatZZBean;
import comparator.DeklaracjaVatSchemacomparator;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatZZDAO;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatZZ;
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
public class DeklVATZalSchemaView  implements Serializable {
    @Inject
    private DeklaracjaVatZZDAO deklaracjaVatZZDAO;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private List<DeklaracjaVatSchema> schemyDeklaracjiVat;
    private List<DeklaracjaVatZZ> schemyDeklaracjiVatZZ;
    @Inject
    private DeklaracjaVatZZ wybranaschema;
    @Inject
    private DeklaracjaVatZZ nowaschema;
    
    @PostConstruct
    private void init() {
        schemyDeklaracjiVatZZ = deklaracjaVatZZDAO.findAll();
        schemyDeklaracjiVat = deklaracjaVatSchemaDAO.findAll();
        Collections.sort(schemyDeklaracjiVat, new DeklaracjaVatSchemacomparator());
    }
    
    public void skopiujschema() {
        nowaschema = wybranaschema;
        Msg.msg("Wybrano schemę "+wybranaschema.getNazwaschemy());
    }
    
    public int dodajscheme() {
        int zwrot = 0;
        int czyschemaistnieje = DeklaracjaVatZZBean.sprawdzScheme(nowaschema, schemyDeklaracjiVatZZ);
        if (czyschemaistnieje == 1) {
            Msg.msg("e", "Nie można dodać, taka schema o takiej nazwie już istnieje.");
        } else if (czyschemaistnieje == 3) {
            Msg.msg("e", "Nie można dodać, brak nazwy nowej schemy.");
        } else if (czyschemaistnieje == 4) {
            Msg.msg("e", "Nie można dodać. Nazwa schemy nie rozpoczyna się od M- lub K-");
        } else {
            deklaracjaVatZZDAO.dodaj(nowaschema);
            schemyDeklaracjiVatZZ.add(nowaschema);
            DeklaracjaVatSchema d = nowaschema.getDeklaracjaVatSchema();
            d.setDeklaracjaVatZZ(nowaschema);
            deklaracjaVatSchemaDAO.edit(d);
            Msg.msg("Dodano schemę");
            zwrot = 1;
            nowaschema = new DeklaracjaVatZZ();
        }
        return zwrot;
    }
    
    public void edytujscheme() {
        deklaracjaVatZZDAO.edit(nowaschema);
        Msg.msg("Udana edycja schemy");
    }
    
    public void usun(DeklaracjaVatZZ s) {
        deklaracjaVatZZDAO.destroy(s);
        DeklaracjaVatSchema d = s.getDeklaracjaVatSchema();
        d.setDeklaracjaVatZZ(null);
        deklaracjaVatSchemaDAO.edit(d);
        schemyDeklaracjiVatZZ.remove(s);
        if (schemyDeklaracjiVatZZ.size() > 0) {
            wybranaschema = schemyDeklaracjiVatZZ.get(schemyDeklaracjiVatZZ.size()-1);
        }
        Msg.msg("Usunieto schemę");
    }
    
    public DeklaracjaVatZZDAO getDeklaracjaVatZZDAO() {
        return deklaracjaVatZZDAO;
    }

    public void setDeklaracjaVatZZDAO(DeklaracjaVatZZDAO deklaracjaVatZZDAO) {
        this.deklaracjaVatZZDAO = deklaracjaVatZZDAO;
    }

    public List<DeklaracjaVatZZ> getSchemyDeklaracjiVatZZ() {
        return schemyDeklaracjiVatZZ;
    }

    public void setSchemyDeklaracjiVatZZ(List<DeklaracjaVatZZ> schemyDeklaracjiVatZZ) {
        this.schemyDeklaracjiVatZZ = schemyDeklaracjiVatZZ;
    }

    public DeklaracjaVatZZ getWybranaschema() {
        return wybranaschema;
    }

    public void setWybranaschema(DeklaracjaVatZZ wybranaschema) {
        this.wybranaschema = wybranaschema;
    }

    public DeklaracjaVatZZ getNowaschema() {
        return nowaschema;
    }

    public void setNowaschema(DeklaracjaVatZZ nowaschema) {
        this.nowaschema = nowaschema;
    }

    public List<DeklaracjaVatSchema> getSchemyDeklaracjiVat() {
        return schemyDeklaracjiVat;
    }

    public void setSchemyDeklaracjiVat(List<DeklaracjaVatSchema> schemyDeklaracjiVat) {
        this.schemyDeklaracjiVat = schemyDeklaracjiVat;
    }
    
    
}
