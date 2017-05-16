/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Osito
 */
public class LazyKontoDataModel extends LazyDataModel<Konto>{
    private final List<Konto> wykazkont;

    public LazyKontoDataModel(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }
    
    
    @Override
    public List<Konto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Konto> lista = new ArrayList<>();
        if (pageSize > wykazkont.size()) {
            lista = wykazkont.subList(first, first + (wykazkont.size()-first));
        } else {
            lista = wykazkont.subList(first, first + pageSize);
        }
        this.setRowCount(wykazkont.size());
        return lista;
    };
    
    @Override
    public Konto getRowData(String rowKey) {
        Konto zwrot = null;
        for (Konto k : wykazkont) {
            if (k.getId().equals(Integer.parseInt(rowKey))) {
                zwrot = k;
                break;
            }
        }
        return zwrot;
    }
   
    @Override
    public Object getRowKey(Konto konto) {
        return konto.getId();
    }
}
