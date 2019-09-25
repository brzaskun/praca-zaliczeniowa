/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

/**
 *
 * @author Osito
 */
public class sqlduplicates {
    
//    SET SQL_SAFE_UPDATES = 0;
//    DELETE e.* FROM pkpir.pitpoz e WHERE id IN (
//SELECT
//	id
//    
//FROM (
//	SELECT MIN(id) as id from pkpir.pitpoz e2
//	GROUP BY
//		podatnik,
//		udzialowiec,
//		pkpir_r,
//		pkpir_m,
//		cechazapisu
//	HAVING 
//		COUNT(*) > 1) x);
//
//SELECT
//	podatnik,
//    udzialowiec,
//    pkpir_r, 
//    pkpir_m,
//    cechazapisu,
//    COUNT(*) occurrences
//FROM pkpir.pitpoz
//GROUP BY
//	podatnik,
//    udzialowiec,
//    pkpir_r,
//    pkpir_m,
//    cechazapisu
//HAVING 
//    COUNT(*) > 1;
}
