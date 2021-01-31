/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.DeklaracjaVatZTBean;
import beansDok.DeklaracjaVatZZBean;
import comparator.DeklaracjaVatSchemacomparator;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatZTDAO;
import dao.DeklaracjaVatZZDAO;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatZT;
import entity.DeklaracjaVatZZ;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DeklVATZalSchemaView  implements Serializable {
    @Inject
    private DeklaracjaVatZZDAO deklaracjaVatZZDAO;
    @Inject
    private DeklaracjaVatZTDAO deklaracjaVatZTDAO;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private List<DeklaracjaVatSchema> schemyDeklaracjiVat;
    private List<DeklaracjaVatZZ> schemyDeklaracjiVatZZ;
    private List<DeklaracjaVatZT> schemyDeklaracjiVatZT;
    @Inject
    private DeklaracjaVatZZ wybranaschemaZZ;
    @Inject
    private DeklaracjaVatZZ nowaschemaZZ;
    @Inject
    private DeklaracjaVatZT wybranaschemaZT;
    @Inject
    private DeklaracjaVatZT nowaschemaZT;
    
    @PostConstruct
    private void init() { //E.m(this);
        schemyDeklaracjiVatZZ = deklaracjaVatZZDAO.findAll();
        schemyDeklaracjiVatZT = deklaracjaVatZTDAO.findAll();
        schemyDeklaracjiVat = deklaracjaVatSchemaDAO.findAll();
        Collections.sort(schemyDeklaracjiVat, new DeklaracjaVatSchemacomparator());
    }
    
    public void skopiujschemaZZ() {
        nowaschemaZZ = wybranaschemaZZ;
        Msg.msg("Wybrano schemę ZZ "+wybranaschemaZZ.getNazwaschemy());
    }
    
    public void skopiujschemaZT() {
        nowaschemaZT = wybranaschemaZT;
        Msg.msg("Wybrano schemę ZT "+wybranaschemaZZ.getNazwaschemy());
    }
    
    public int dodajschemeZZ() {
        int zwrot = 0;
        int czyschemaistnieje = DeklaracjaVatZZBean.sprawdzScheme(nowaschemaZZ, schemyDeklaracjiVatZZ);
        if (czyschemaistnieje == 1) {
            Msg.msg("e", "Nie można dodać, taka schema ZZ o takiej nazwie już istnieje.");
        } else if (czyschemaistnieje == 3) {
            Msg.msg("e", "Nie można dodać, brak nazwy nowej schemy ZZ.");
        } else if (czyschemaistnieje == 4) {
            Msg.msg("e", "Nie można dodać. Nazwa schemy nie rozpoczyna się od ZZ");
        } else {
            deklaracjaVatZZDAO.create(nowaschemaZZ);
            schemyDeklaracjiVatZZ.add(nowaschemaZZ);
            DeklaracjaVatSchema d = nowaschemaZZ.getDeklaracjaVatSchema();
            d.setDeklaracjaVatZZ(nowaschemaZZ);
            deklaracjaVatSchemaDAO.edit(d);
            Msg.msg("Dodano schemę ZZ");
            zwrot = 1;
            nowaschemaZZ = new DeklaracjaVatZZ();
        }
        return zwrot;
    }
    
    public int dodajschemeZT() {
        int zwrot = 0;
        int czyschemaistnieje = DeklaracjaVatZTBean.sprawdzScheme(nowaschemaZT, schemyDeklaracjiVatZT);
        if (czyschemaistnieje == 1) {
            Msg.msg("e", "Nie można dodać, taka schema ZT o takiej nazwie już istnieje.");
        } else if (czyschemaistnieje == 3) {
            Msg.msg("e", "Nie można dodać, brak nazwy nowej schemy ZT.");
        } else if (czyschemaistnieje == 4) {
            Msg.msg("e", "Nie można dodać. Nazwa schemy nie rozpoczyna się od ZT");
        } else {
            deklaracjaVatZTDAO.create(nowaschemaZT);
            schemyDeklaracjiVatZT.add(nowaschemaZT);
            DeklaracjaVatSchema d = nowaschemaZT.getDeklaracjaVatSchema();
            d.setDeklaracjaVatZT(nowaschemaZT);
            deklaracjaVatSchemaDAO.edit(d);
            Msg.msg("Dodano schemę ZT");
            zwrot = 1;
            nowaschemaZT = new DeklaracjaVatZT();
        }
        return zwrot;
    }
    
    public void edytujschemeZZ() {
        DeklaracjaVatSchema d = nowaschemaZZ.getDeklaracjaVatSchema();
        d.setDeklaracjaVatZZ(nowaschemaZZ);
        deklaracjaVatSchemaDAO.edit(d);
        deklaracjaVatZZDAO.edit(nowaschemaZZ);
        Msg.msg("Udana edycja schemy ZZ");
    }
    
    public void edytujschemeZT() {
        DeklaracjaVatSchema d = nowaschemaZT.getDeklaracjaVatSchema();
        d.setDeklaracjaVatZT(nowaschemaZT);
        deklaracjaVatSchemaDAO.edit(d);
        deklaracjaVatZTDAO.edit(nowaschemaZT);
        Msg.msg("Udana edycja schemy ZT");
    }
    
    public void usunschemeZZ(DeklaracjaVatZZ s) {
        try {
            DeklaracjaVatSchema d = s.getDeklaracjaVatSchema();
            d.setDeklaracjaVatZZ(null);
            deklaracjaVatSchemaDAO.edit(d);
            deklaracjaVatZZDAO.remove(s);
            schemyDeklaracjiVatZZ.remove(s);
            if (schemyDeklaracjiVatZZ.size() > 0) {
                wybranaschemaZZ = schemyDeklaracjiVatZZ.get(schemyDeklaracjiVatZZ.size()-1);
            }
            Msg.msg("Usunieto schemę ZZ");
        } catch(Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void usunschemeZT(DeklaracjaVatZT s) {
        try {
            DeklaracjaVatSchema d = s.getDeklaracjaVatSchema();
            d.setDeklaracjaVatZT(null);
            deklaracjaVatSchemaDAO.edit(d);
            deklaracjaVatZTDAO.remove(s);
            schemyDeklaracjiVatZT.remove(s);
            if (schemyDeklaracjiVatZT.size() > 0) {
                wybranaschemaZT = schemyDeklaracjiVatZT.get(schemyDeklaracjiVatZT.size() - 1);
            }
            Msg.msg("Usunieto schemę ZT");
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
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

    public DeklaracjaVatZZ getWybranaschemaZZ() {
        return wybranaschemaZZ;
    }

    public void setWybranaschemaZZ(DeklaracjaVatZZ wybranaschemaZZ) {
        this.wybranaschemaZZ = wybranaschemaZZ;
    }

    public DeklaracjaVatZZ getNowaschemaZZ() {
        return nowaschemaZZ;
    }

    public void setNowaschemaZZ(DeklaracjaVatZZ nowaschemaZZ) {
        this.nowaschemaZZ = nowaschemaZZ;
    }

    public List<DeklaracjaVatSchema> getSchemyDeklaracjiVat() {
        return schemyDeklaracjiVat;
    }

    public void setSchemyDeklaracjiVat(List<DeklaracjaVatSchema> schemyDeklaracjiVat) {
        this.schemyDeklaracjiVat = schemyDeklaracjiVat;
    }

    public List<DeklaracjaVatZT> getSchemyDeklaracjiVatZT() {
        return schemyDeklaracjiVatZT;
    }

    public void setSchemyDeklaracjiVatZT(List<DeklaracjaVatZT> schemyDeklaracjiVatZT) {
        this.schemyDeklaracjiVatZT = schemyDeklaracjiVatZT;
    }

    public DeklaracjaVatZT getWybranaschemaZT() {
        return wybranaschemaZT;
    }

    public void setWybranaschemaZT(DeklaracjaVatZT wybranaschemaZT) {
        this.wybranaschemaZT = wybranaschemaZT;
    }

    public DeklaracjaVatZT getNowaschemaZT() {
        return nowaschemaZT;
    }

    public void setNowaschemaZT(DeklaracjaVatZT nowaschemaZT) {
        this.nowaschemaZT = nowaschemaZT;
    }
    
    
}
