/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.net.nt.ConnOption;

/**
 *
 * @author V19MFoda
 */
public class DataBase {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object  
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@bscs-scan:1528:BSCSPRD2", "V19MFoda", "159357MOhame!@");

        return con;
    }

    public static String getCoId(String msi) throws ClassNotFoundException, SQLException {
        // System.err.println(msi);
        if (!msi.startsWith("20")) {
            if (msi.length() == 10) {
                msi = "20" + msi;
                //     System.err.println(msi);

            } else {
                msi = "2" + msi;
                //     System.err.println(msi);

            }
        }

        String sql = "SELECT  --b.co_id \"Contract Id\" , f.tmcodeBilling\n"
                + "\n"
                + "               b.co_id \"Contract Id\",\n"
                + "               d.sm_puk \"VFCC PUCK1\",\n"
                + "               d.sm_puk2\"VFCC PUCK2\", NULL , NULL ,\n"
                + "               ch.ch_reason \"Activation Reason\" , \n"
                + "               SUBSTR(a.dn_num,3,10), d.sm_serialnum \"SIM\",\n"
                + "               ch.ch_status \"Accnt Status\", \n"
                + "               f.tmcode \"Rate Plan Code\", NULL , \n"
                + "               DECODE (NVL(e.CHECK02,'f'),'X','Y','N') \"Itimized Bill\" ,\n"
                + "               TO_CHAR(b.cs_activ_date,'MM/DD/YYYY')  AS Activation_Date,\n"
                + "               'A00000000',--COMBO09 \"Sales Force ID\" ,\n"
                + "               b.cs_deactiv_date , NULL , custcode \n"
                + "               FROM directory_number@bscsprd2 a , \n"
                + "               contr_services_cap@bscsprd2 b, \n"
                + "               contr_devices@bscsprd2  c, \n"
                + "               storage_medium@bscsprd2  d,\n"
                + "               info_contr_check@bscsprd2  e, \n"
                + "               contract_all@bscsprd2 f,\n"
                + "               customer_all@bscsprd2  g, \n"
                + "               contract_history@bscsprd2  ch,\n"
                + "               info_contr_combo@bscsprd2 T  \n"
                + "               WHERE a.dn_id=b.dn_id\n"
                + "               AND b.cs_deactiv_date IS NULL \n"
                + "               AND c.CD_DEACTIV_DATE IS NULL\n"
                + "               AND b.co_id=c.co_id\n"
                + "               AND f.co_id =b.co_id\n"
                + "               AND c.cd_sm_num=d.sm_serialnum\n"
                + "               AND b.co_id = e.co_id(+)\n"
                + "               AND t.co_id(+)= b.co_id \n"
                + "               AND g.customer_id=f.customer_id\n"
                + "               AND c.co_id = ch.co_id\n"
                + "               AND ch.CH_SEQNO = (SELECT MAX(ch_seqno) FROM contract_history@bscsprd2  WHERE co_id = ch.co_id)\n"
                + "                 AND a.dn_num in ('" + msi + "');";
        Connection con = getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        }
        closeConnection(con);
        return "";
    }

    public static void closeConnection(Connection con) throws SQLException {
        con.close();
    }

}
