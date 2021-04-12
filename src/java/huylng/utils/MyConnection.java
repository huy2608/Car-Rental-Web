/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Shi
 */
public class MyConnection {
    public static Connection getMyConnection() throws SQLException, NamingException{
        
        Context context = new InitialContext();
        Context tomcatcontext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatcontext.lookup("SE140029");
        Connection con = ds.getConnection();
        return con;
    }
}
