/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import entityfk.Wiersz;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Osito
 */
public class LazyWierszDataModel extends LazyDataModel<Wiersz>{
    private final List<Wiersz> listawierszy;

    public LazyWierszDataModel(List<Wiersz> listawierszy) {
        this.listawierszy = listawierszy;
    }
    
    
//    @Override
//    public List<Wiersz> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//        List<Wiersz> lista = Collections.synchronizedList(new ArrayList<>());
//        if (pageSize > listawierszy.size()) {
//            lista = listawierszy.subList(first, first + (listawierszy.size()-first));
//        } else {
//            lista = listawierszy.subList(first, first + pageSize);
//        }
//        this.setPageSize(pageSize);
//        this.setWrappedData(lista);
//        this.setRowCount(listawierszy.size());
//        return lista;
//    };
//    

    @Override
    public List<Wiersz> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
