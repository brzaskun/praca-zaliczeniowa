/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class PdfBean {
     public static List[] getTabelaWiersz(List naglowki, List wiersze) {
       List n = new ArrayList();
       n.add("lp");
       n.add("konto");
       n.add("opis");
       n.add("kwota Wn");
       n.add("kwota Wn PLN");
       n.add("kwota Ma");
       n.add("kwota Ma PLN");
       List[] tabela = new List[2];
       tabela[0] = n;
       tabela[1] = wiersze;
       return tabela;
   }
}
