/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaVatSchemaDAO;
import entity.DeklaracjaVatSchema;
import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjaVatWierszSumarycznyDAO;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.DeklaracjaVatWierszSumaryczny;
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
public class DeklaracjaVatSchemaWierszSumView implements Serializable {
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private List<DeklaracjaVatSchema> schemyDeklaracjiVat;
    private DeklaracjaVatSchema wybranaschema;
    @Inject
    private DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO;
    private List<DeklaracjaVatWierszSumaryczny> listawierszy;
    @Inject
    private DeklaracjaVatSchemaWierszSumDAO deklaracjaVatSchemaWierszSumDAO;
    private List<DeklaracjaVatSchemaWierszSum> schemawierszlista;
   
    @PostConstruct
    private void init() {
        schemyDeklaracjiVat = deklaracjaVatSchemaDAO.findAll();
        listawierszy = deklaracjaVatWierszSumarycznyDAO.findAll();
       
    }

    public void pobierzschemawiersz() {
        listawierszy = deklaracjaVatWierszSumarycznyDAO.findAll();
        schemawierszlista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(wybranaschema);
        List<DeklaracjaVatWierszSumaryczny> uzupelnionewiersze = new ArrayList<>();
        for (DeklaracjaVatSchemaWierszSum p : schemawierszlista) {
            uzupelnionewiersze.add(p.getDeklaracjaVatWierszSumaryczny());
        }
        List<DeklaracjaVatWierszSumaryczny> wierszedododania = new ArrayList<>();
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
    
     public void zachowaj() {
        deklaracjaVatSchemaWierszSumDAO.editList(schemawierszlista);
        Msg.msg("Zachowano scheme-wiersz");
    }
    
    public List<DeklaracjaVatSchema> getSchemyDeklaracjiVat() {
        return schemyDeklaracjiVat;
    }

    public void setSchemyDeklaracjiVat(List<DeklaracjaVatSchema> schemyDeklaracjiVat) {
        this.schemyDeklaracjiVat = schemyDeklaracjiVat;
    }

    public DeklaracjaVatSchema getWybranaschema() {
        return wybranaschema;
    }

    public void setWybranaschema(DeklaracjaVatSchema wybranaschema) {
        this.wybranaschema = wybranaschema;
    }

    public List<DeklaracjaVatSchemaWierszSum> getSchemawierszlista() {
        return schemawierszlista;
    }

    public void setSchemawierszlista(List<DeklaracjaVatSchemaWierszSum> schemawierszlista) {
        this.schemawierszlista = schemawierszlista;
    }
   
    
    
    
    
}
