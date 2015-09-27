/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beansVAT.VATDeklaracja;
import dao.DeklaracjaVatSchemaDAO;
import dao.PodatnikDAO;
import dao.SchemaEwidencjaDAO;
import embeddable.EVatwpisSuma;
import embeddable.SchemaEwidencjaSuma;
import entity.DeklaracjaVatSchema;
import entity.Deklaracjevat;
import entity.SchemaEwidencja;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfVAT7new {
    

    public static void drukujwys(PodatnikDAO podatnikDAO, Deklaracjevat d, DeklaracjaVatSchema pasujacaSchema, SchemaEwidencjaDAO schemaEwidencjaDAO) {
        List<SchemaEwidencja> schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(pasujacaSchema);
        ArrayList<EVatwpisSuma> sumaewidencji = new ArrayList<>();
        sumaewidencji.addAll(d.getPodsumowanieewidencji().values());
        List<SchemaEwidencjaSuma> sumaschemewidencjilista = VATDeklaracja.uzupelnijSchemyoKwoty(schemaewidencjalista, sumaewidencji);
        System.out.println("test");
    }
    
}
