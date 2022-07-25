/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import error.E;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Osito
 */
public class ConnectionJDBC {
    //to nie jest niestety idealne rozwiazanie testowania bazy danych bo tu dostajemy
    //jedynie dostep do pol a nie ma transofrmacji rzedu na object
     public static Connection getConnection() throws NamingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "pkpir?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "brzaskun";
        String password = "Pufikun7005*";
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url + dbName, userName, password);
        return conn;
    }
    
    
    public static void main(String[] args)  {
        try {
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(
			   "select * from schemaewidencja"); 
		
		//get customer data from database
            ResultSet result =  ps.executeQuery();
            List<String> list = Collections.synchronizedList(new ArrayList<>());
            while(result.next()){
                list.add(result.getString("evewidencja"));
                error.E.s(result.getString("evewidencja"));
            }
        } catch (Exception ex) {
             error.E.s(E.e(ex));
        }
    }
}
