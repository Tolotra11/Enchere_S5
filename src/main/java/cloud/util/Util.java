/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author Tolotra
 */
public class Util {
    public static Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
       Class.forName("org.postgresql.Driver");
        Connection connectionSql = DriverManager.getConnection("jdbc:postgresql://localhost:5432/enchere", "postgres", "1234");   
        return connectionSql;
        
    }
    public static String[] vectorToArray(Vector<String> v){
        String [] array = new String[v.size()];
        array = v.toArray(array);
        return array;
    }
    ///Check si la connection est null
    public static boolean connectionNull(Connection con){
        boolean check = true;
        if(con != null){
            check = false;
        }
        return check;
    }
    
    public static double differenceHourTwoDate(Date date1,Date date2){
        double val = 0.0;
        double d1 = date1.getTime();
        double d2 = date2.getTime();
        double difference = d1 - d2;
        val = difference/3600000.0;
        return val;
    }
    public static double differenceDaysTwoDate(Date date1,Date date2){
        return Util.differenceHourTwoDate(date1, date2)/24.0;
    }
    public static double differenceHourTwoDate(Timestamp date1,Timestamp date2){
        double val = 0.0;
        double d1 = date1.getTime();
        double d2 = date2.getTime();
        double difference = d1 - d2;
        val = difference/3600000.0;
        return val;
    }
    public static double differenceDaysTwoDate(Timestamp date1,Timestamp date2){
        return Util.differenceHourTwoDate(date1, date2)/24.0;
    }
    public static int jourOuvrable(Date date1,Date date2){
        int intervalle = 0;
        Calendar first = Calendar.getInstance();
        first.setTime(date1);
        Calendar fin = Calendar.getInstance();
        fin.setTime(date2);
        while(first.before(fin)){
            if(first.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || first.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                first.add(Calendar.DATE, 1);
            }
            else{
                first.add(Calendar.DATE, 1);                
                intervalle++;
            }
        }
        return intervalle;
    }
    public static int jourOuvrable1(Date date1,Date date2){
        int intervalle = 1;
        Calendar first = Calendar.getInstance();
        first.setTime(date1);
        Calendar fin = Calendar.getInstance();
        fin.setTime(date2);
        while(first.before(fin)){
            if(first.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || first.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                first.add(Calendar.DATE, 1);
            }
            else{
                first.add(Calendar.DATE, 1);                
                intervalle++;
            }
        }
        return intervalle;
    }
}

