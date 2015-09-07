/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.DeklaracjaVatSchemaBean;
import entity.DeklaracjaVatSchema;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjaVatWierszSumarycznyDAO;
import dao.EvewidencjaDAO;
import dao.SchemaEwidencjaDAO;
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
    private DeklaracjaVatSchemaWierszSumDAO deklaracjaVatSchemaWierszSumDAO;
    private List<DeklaracjaVatSchemaWierszSum> schemyWierszSum;
    @Inject
    private DeklaracjaVatSchema deklaracjaVatSchema;
    private DeklaracjaVatSchema wybranaschema;
    @Inject
    private DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO;
    private List<DeklaracjaVatWierszSumaryczny> deklaracjaVatWierszSumaryczny;
    
    @PostConstruct
    private void init() {
        schemyWierszSum = deklaracjaVatSchemaWierszSumDAO.findAll();
        deklaracjaVatWierszSumaryczny = deklaracjaVatWierszSumarycznyDAO.findAll();
    }

       
//    public void pobierzschemaewidencja() {
//        deklaracjaVatWierszSumaryczny = deklaracjaVatWierszSumarycznyDAO.findEwidencjeSchemy(wybranaschema);
//        List<Evewidencja> uzupelnioneewidencje = new ArrayList<>();
//        for (SchemaEwidencja p : schemyWierszSum) {
//            uzupelnioneewidencje.add(p.getEvewidencja());
//        }
//        List<Evewidencja> ewidencjedododania = new ArrayList<>();
//        for (Evewidencja r : deklaracjaVatWierszSumaryczny) {
//            if (uzupelnioneewidencje.contains(r)) {
//                uzupelnioneewidencje.remove(r);
//            } else {
//                ewidencjedododania.add(r);
//            }
//        }
//        if (!ewidencjedododania.isEmpty()) {
//            for (Evewidencja s : ewidencjedododania) {
//                SchemaEwidencja nowaschemaewidencja = new SchemaEwidencja(wybranaschema, s, null, null);
//                schemaewidencjalista.add(nowaschemaewidencja);
//            }
//        }
//    }
//
//    public void zachowaj() {
//        deklaracjaVatSchemaWierszSumDAO.editList(schemyWierszSum);
//        Msg.msg("Zachowano schemat wierszy");
//    }
//    
   
   
    
    
    
}
