/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import entityfk.Wiersz;
import error.E;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Osito
 */
public class GetSelectedRowinDataTable {
    private void init() { //E.m(this);
        DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formwpisdokument:dataList");
        Object o = d.getLocalSelection();
        int wierszRKindex = d.getRowIndex();
        Wiersz wierszRK = (Wiersz) d.getRowData();
    }
}
