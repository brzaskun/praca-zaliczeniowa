/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import entityfk.Wiersz;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Osito
 */
public class LazyWierszDataModel extends LazyDataModel<Wiersz>{
    private final List<Wiersz> listawierszy;

    public LazyWierszDataModel(List<Wiersz> listawierszy) {
        this.listawierszy = listawierszy;
    }
    
    
    @Override
    public List<Wiersz> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Wiersz> lista = new ArrayList<>();
        if (pageSize > listawierszy.size()) {
            lista = listawierszy.subList(first, first + (listawierszy.size()-first));
        } else {
            lista = listawierszy.subList(first, first + pageSize);
        }
        this.setPageSize(pageSize);
        this.setWrappedData(lista);
        this.setRowCount(listawierszy.size());
        return lista;
    };
    
   
}
