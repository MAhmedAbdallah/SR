/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.util;

import com.vodafone.sr.model.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

//import oracle.net.nt.ConnOption;
import org.apache.log4j.Logger;

/**
 *
 * @author V19MFoda
 */
public class DataBase {

    static Logger logger = Logger.getLogger(DataBase.class.getName());

    public static String getCoId(String msi) {

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
        Connection con = null;
        String CoId = "";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                    //                                        "jdbc:oracle:thin:@bscs-scan:1528/BSCSPRD2","V19IAutomation1", "AutoSquad@12345_6789");
                    "jdbc:oracle:thin:@10.230.91.144:1528/BSCSPRD2", "V19IAutomation1", "AutoSquad@12345_6789");
//                    "jdbc:oracle:thin:@10.230.91.144:1528/BSCSPRD2", "V19MFoda", "159357MOhame!@");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT  --b.co_id \"Contract Id\" , f.tmcodeBilling\n"
                    + "\n"
                    + "               b.co_id \"Contract Id\",\n"
                    + "               d.sm_puk \"VFCC PUCK1\",\n"
                    + "               d.sm_puk2\"VFCC PUCK2\", NULL , NULL ,\n"
                    + "               ch.ch_reason \"Activation Reason\" , \n"
                    + "               SUBSTR(a.dn_num,3,10), d.sm_serialnum \"SIM\",\n"
                    + "               ch.ch_status \"Accnt Status\", \n"
                    + "               f.tmcode \"Rate Plan Code\", NULL , \n"
                    + "               DECODE (NVL(e.CHECK02,\'f\'),\'X\',\'Y\',\'N\') \"Itimized Bill\" ,\n"
                    + "               TO_CHAR(b.cs_activ_date,\'MM/DD/YYYY\')  AS Activation_Date,\n"
                    + "               \'A00000000\',--COMBO09 \"Sales Force ID\" ,\n"
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
                    + "                 AND a.dn_num in (\'" + msi + "\')");

            while (rs.next()) {
                //  System.out.println(rs.getInt("Contract Id"));
                CoId = String.valueOf(rs.getInt("Contract Id"));
            }

//step5 close the connection object  
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        }
        return CoId;
    }

    public static boolean checkPendingRequest(String coId) {
        boolean flag = false;

        Connection con = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@10.230.91.144:1528/BSCSPRD2", "V19IAutomation1", "AutoSquad@12345_6789");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from  mdsrrtab where co_id =\'" + coId + "\'");
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {

                String columnValue = rs.getString("STATUS");
                if (columnValue == "O") {
                    flag = true;
                }
                break;
            }

//            }
//step5 close the connection object  
            con.close();

        } catch (Exception e) {
            logger.error("Error Happend " + e.getMessage());
            try {
                con.close();
            } catch (Exception ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        }

        return flag;

    }

    public static List<Service> getAllServices(String coId) {
        List<Service> servicesList = new ArrayList<>();
        Connection con = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@10.230.91.144:1528/BSCSPRD2", "V19IAutomation1", "AutoSquad@12345_6789");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select B.DN_NUM,X.CO_ID,X.SNCODE,Y.STATUS,Z.DES, Y.VALID_FROM_DATE from profile_service X ,PR_SERV_STATUS_HIST Y ,mpusntab Z , DIRECTORY_NUMBER B , CONTR_SERVICES_CAP A where \n"
                    + "X.co_id=Y.CO_ID\n"
                    + "and X.co_id=\'" + coId + "\' \n"
                    + "--and  B.DN_NUM='201030660444'\n"
                    + "and A.CS_DEACTIV_DATE is null\n"
                    + "and A.DN_ID=B.DN_ID\n"
                    + "and X.CO_ID=A.CO_ID\n"
                    + "and X.SNCODE=Z.SNCODE\n"
                    + "and Y.SNCODE=Z.SNCODE\n"
                    + "and X.STATUS_HISTNO=Y.HISTNO \n"
                    + "order by X.SNCODE ");
            while (rs.next()) {
                Service s = new Service(rs.getString("SNCODE"), rs.getString("STATUS"));
                servicesList.add(s);
            }

//step5 close the connection object  
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        }

        return servicesList;
    }
//_______________________________________________________________________________________________________

    public static String getMsisdn(String msi) {

        Connection con = null;
        String msisdn = "";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                    //                                        "jdbc:oracle:thin:@bscs-scan:1528/BSCSPRD2","V19IAutomation1", "AutoSquad@12345_6789");
                   "jdbc:oracle:thin:@10.230.91.144:1528/BSCSPRD2", "V19IAutomation1", "AutoSquad@12345_6789");
//                    "jdbc:oracle:thin:@10.230.91.144:1528/BSCSPRD2", "V19MFoda", "159357MOhame!@");
            
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select\n"
                    + " a.dn_num\n"
                    + "from directory_number a,\n"
                    + "  contr_services_cap b,\n"
                    + "  contract_all c,\n"
                    + "  rateplan d,\n"
                    + "  customer_all e,\n"
                    + "  port p,\n"
                    + "  contr_devices cd,\n"
                    + "  billcycle_assignment_history bah\n"
                    + "where a.dn_id          = b.dn_id\n"
                    + "and b.cs_deactiv_date is null\n"
                    + "and b.co_id            = c.co_id\n"
                    + "and d.tmcode           = c.tmcode\n"
                    + "and c.customer_id      = e.customer_id\n"
                    + "--AND a.dn_num IN ( '201006818120')\n"
                    + "and   p.port_num='"+msi+"'\n"
                    + "and p.port_id       =cd.port_id\n"
                    + "and b.co_id         =cd.co_id\n"
                    + "and bah.customer_id = e.customer_id\n"
                    + "and bah.seqno       =\n"
                    + "  (select max(seqno)\n"
                    + "  from billcycle_assignment_history\n"
                    + "  where customer_id =e.customer_id\n"
                    + "  )\n"
                    + "and cd.cd_deactiv_date is null "
            );

            while (rs.next()) {
                //  System.out.println(rs.getInt("Contract Id"));
                msisdn = String.valueOf(rs.getString("DN_NUM"));
            }

//step5 close the connection object  
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Error Happend " + ex.getMessage());
            }
        }
        return msisdn;
    }

//    __________________________________________________________________________________________________________
    public static void main(String[] args) {

        System.out.println(getMsisdn("602022042807201"));
        
        }
    

}
