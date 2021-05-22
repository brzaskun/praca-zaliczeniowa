/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author Osito
 */
public class X {
    
    
    public static Object x(Cell cell) {
        Object zwrot = null;
        CellType celltype = cell.getCellType();
        switch (celltype) {
            case NUMERIC:
                zwrot = cell.getNumericCellValue();
                break;
            case STRING:
                zwrot = cell.getStringCellValue();
                break;
            default:
                zwrot = cell.getDateCellValue();
                break;
        }

        return zwrot;
    }
}
