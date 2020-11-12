/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.sr.controller;

import com.vodafone.sr.dao.UserRepository;
import com.vodafone.sr.model.ActivateVolte;
import com.vodafone.sr.model.DeactivateServiceModal;
import com.vodafone.sr.model.PortModel;
import com.vodafone.sr.model.Service;
import com.vodafone.sr.model.User;
import com.vodafone.sr.model.cancelContractModal;
import com.vodafone.sr.util.DataBase;
import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author V19MFoda
 */
@Controller
public class MainController {

    static Logger logger = Logger.getLogger(MainController.class.getName());
    @Autowired
    private UserRepository repo;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }

    @RequestMapping("home")
    public String getHome() {
        if (request.getSession().getAttribute("userName") != null) {
            return "home";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("siebel")
    public String getSiebel() {
        if (request.getSession().getAttribute("userName") != null) {
            return "siebel";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("samah")
    public String getSamah() {
        if (request.getSession().getAttribute("userName") != null) {
            return "samah";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("activateVolte")
    public String getactivateVolte() {
        if (request.getSession().getAttribute("userName") != null) {
            return "activatevolte";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("console")
    public String getConsole() {
        if (request.getSession().getAttribute("userName") != null) {
            return "console";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("consoleContract")
    public String getConsoleContract() {
        if (request.getSession().getAttribute("userName") != null) {
            return "cancelcontractbulkconsole";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("consoleRequest")
    public String getconsoleRequest() {
        if (request.getSession().getAttribute("userName") != null) {
            return "cancelrequestbulkconsole";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping("consoleDeactivateService")
    public String getconsoleDeactivateService() {
        if (request.getSession().getAttribute("userName") != null) {
            return "deactivateservicebulkconsole";
        } else {
            request.setAttribute("message", "Please Login ");
            return "index";
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public String authenticate(@RequestParam String userName, @RequestParam String passWord) {
        User user = null;
        try {
            user = repo.findById(userName).get();
        } catch (Exception e) {
            request.setAttribute("message", "Please enter correct user name and password");
            return "index";
        }
        if (user != null) {

//        _________________________________________________________________________________________________
//        if (userName.equals("FrontLine") && passWord.equals("BillingFront")) {
            if (user.getTeam().equals("Billing") && passWord.equals(user.getPassWord())) {

                request.getSession().setAttribute("userName", user.getUserName());
                logger.info("Login Attempt By :" + request.getSession().getAttribute("userName"));
                return "forward:/home";
            }
//        if (userName.equals("Billing") && passWord.equals("SupportBilling")) {

            if (user.getTeam().equals("FrontLine") && passWord.equals(user.getPassWord())) {
                request.getSession().setAttribute("userName", user.getUserName());
                logger.info("Login Attempt By :" + request.getSession().getAttribute("userName"));
                return "forward:/home";
            }
            if (user.getTeam().equals("Fatma") && passWord.equals(user.getPassWord())) {
                request.getSession().setAttribute("userName", user.getUserName());
                logger.info("Login Attempt By :" + request.getSession().getAttribute("userName"));
                return "forward:/siebel";
            }
            if (user.getTeam().equals("Siebel") && passWord.equals(user.getPassWord())) {
                request.getSession().setAttribute("userName", user.getUserName());
                logger.info("Login Attempt By :" + request.getSession().getAttribute("userName"));
                return "forward:/siebel";
            }
            if (user.getTeam().equals("samah") && passWord.equals(user.getPassWord())) {
                request.getSession().setAttribute("userName", user.getUserName());
                logger.info("Login Attempt By :" + request.getSession().getAttribute("userName"));
                return "forward:/samah";
            }

            if (user.getTeam().equals("Volte") && passWord.equals(user.getPassWord())) {
                request.getSession().setAttribute("userName", user.getUserName());
                logger.info("Login Attempt By :" + request.getSession().getAttribute("userName"));
                return "forward:/activateVolte";
            }

        }
//                 else {
//            request.setAttribute("message", "Please enter correct user name and password");
//            return "index";
//        }
        return "index";
    }

    @RequestMapping(value = "portout", method = RequestMethod.POST)
    public String portOutBulk(@RequestParam MultipartFile csv) {
        synchronized (this) {

            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                //   String msi = "";
                List<PortModel> consoleList = new ArrayList<PortModel>();
                //  try {
                //      System.out.println(csv.getName());
                // File f = new File("C:\\Users\\V19MFoda\\Desktop\\yara.xlsx");

                try {
                    List<String> list = new ArrayList<String>();
                    FileInputStream fis = (FileInputStream) csv.getInputStream();//new FileInputStream(csv);   //obtaining bytes from the file  
//creating Workbook instance that refers to .xlsx file  
//                XSSFWorkbook wb = new XSSFWorkbook(fis);
//                XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
//                Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
//                while (itr.hasNext()) {
//                    Row row = itr.next();
//                    Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
//                    while (cellIterator.hasNext()) {
//                        Cell cell = cellIterator.next();
//                        switch (cell.getCellType()) {
//                            case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
//                                //  System.out.print(cell.getStringCellValue() + "\t\t\t");
//                                list.add(cell.getStringCellValue());
//                                break;
//                            case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
//
//                                Double doubleValue = cell.getNumericCellValue();
//                                BigDecimal bd = new BigDecimal(doubleValue.toString());
//                                long lonVal = bd.longValue();
//                                msi = Long.toString(lonVal).trim();
//                                if (!msi.startsWith("20")) {
//                                    msi = "20" + msi;
//                                }
//                                list.add(msi);
//
//                                //  System.out.print(msi + "\t\t\t");
//                                break;
//                            default:
//                        }
//                    }
//                }
                    list = readExcel(fis);
                    for (int i = 0; i < list.size(); i = i + 2) {
                        //       System.err.println(list.get(i) + "         " + list.get(i + 1));
                        String status = "";
                        String pLCode = "";
                        if (list.get(i).startsWith("2010")) {
                            status = "o";
                        } else {
                            status = "s";
                        }
                        switch (list.get(i + 1).toLowerCase()) {
                            case "orange":
                                pLCode = "1464";
                                break;
                            case "etisalat":
                                pLCode = "1452";
                                break;
                            case "we":
                                pLCode = "1845";
                                break;
                            default:
                            //        System.out.println("no match");
                        }

                        //    System.err.println(pLCode + "  Plcode      " + status + "   Status     " + list.get(i));
                        //  consoleList = new ArrayList<>();
                        String url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=WriteBillingResource&MNP_FLAG=x&"
                                + "PLCODE=" + pLCode
                                + "&"
                                + "STATUS=" + status
                                + "&"
                                + "DN_NUM=" + list.get(i);

                        String response = invokeWebService(url); // callWFX(pLCode, status, list.get(i));
                        consoleList.add(new PortModel(list.get(i), list.get(i + 1), response));

//                    System.out.println(callWFX(pLCode, status, msi));
//                    System.out.println("_______________________________________________________________________________________________");
                        logger.info("Port Out Bulk By : " + request.getSession().getAttribute("userName") + " For Msisdn :  " + list.get(i));

                    }
                    // logger.info("Port Out Bulk By :" + request.getSession().getAttribute("userName"));

                } catch (Exception e) {
                    //  e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }
                request.setAttribute("consoleContent", consoleList);

                return "forward:/console";
            }
        }
    }

    @RequestMapping(value = "cancelContract", method = RequestMethod.POST)
    public String cancelContract(@RequestParam String coID) {
        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                String response = "";

                String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=CancelContract&CO_ID=" + coID;
                try {
                    response = invokeWebService(Url);
                    request.setAttribute("responseMessage", response);
                    logger.info("Cancel Contract By :" + request.getSession().getAttribute("userName") + ": " + coID);

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }

                return "home";
            }

        }
    }

    @RequestMapping(value = "cancelContractBulk", method = RequestMethod.POST)
    public String cancelContractBulk(@RequestParam MultipartFile csv) {

        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                //   String msi = "";
                List<cancelContractModal> consoleList = new ArrayList<cancelContractModal>();
                FileInputStream fis = null;
                //  try {
                //      System.out.println(csv.getName());
                // File f = new File("C:\\Users\\V19MFoda\\Desktop\\yara.xlsx");

                try {
                    List<String> list = new ArrayList<String>();
                    fis = (FileInputStream) csv.getInputStream();//new FileInputStream(csv);   //obtaining bytes from the file  

                    list = readExcel(fis);
                    for (int i = 0; i < list.size(); i++) {
                        //    System.err.println(list.get(i));

                        String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=CancelContract&CO_ID=" + list.get(i);
//        try {
                        String response = invokeWebService(Url); //"Contract Canceled";
                        //request.setAttribute("responseMessage", response);
                        cancelContractModal cCM = new cancelContractModal(list.get(i), response);
                        consoleList.add(cCM);
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);         
                        logger.info("Cancel Contract Bulk By :" + request.getSession().getAttribute("userName") + " for CoId : " + list.get(i));

                    }
                    request.setAttribute("consoleContentCancelContract", consoleList);
                    // logger.info("Cancel Contract Bulk By :" + request.getSession().getAttribute("userName"));

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }
                //    request.setAttribute("consoleContent", consoleList);

                return "forward:/consoleContract";
            }
        }

    }

    @RequestMapping(value = "cancelRequest", method = RequestMethod.POST)
    public String cancelRequest(@RequestParam String reqId) {
        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                String response = "";

                String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO%20%20%20%20%20&password=SY&workflowName=RequestsCancel&RE_REQUEST_LIST=" + reqId;
                try {
                    response = invokeWebService(Url); //"Request Canceled";
                    request.setAttribute("responseMessage", response);
                    logger.info("Cancel Request By :" + request.getSession().getAttribute("userName") + " For Request ID " + reqId);

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }

                return "home";
            }

        }
    }

    @RequestMapping(value = "cancelRequestBulk", method = RequestMethod.POST)
    public String cancelRequestBulk(@RequestParam MultipartFile csv) {

        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                //   String msi = "";
                List<cancelContractModal> consoleList = new ArrayList<cancelContractModal>();
                FileInputStream fis = null;
                //  try {
                //      System.out.println(csv.getName());
                // File f = new File("C:\\Users\\V19MFoda\\Desktop\\yara.xlsx");

                try {
                    List<String> list = new ArrayList<String>();
                    fis = (FileInputStream) csv.getInputStream();//new FileInputStream(csv);   //obtaining bytes from the file  

                    list = readExcel(fis);
                    for (int i = 0; i < list.size(); i++) {
                        //       System.err.println(list.get(i));

                        String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO%20%20%20%20%20&password=SY&workflowName=RequestsCancel&RE_REQUEST_LIST=" + list.get(i);
                        //     try {
                        String response = invokeWebService(Url); //"Contract Canceled";
                        //request.setAttribute("responseMessage", response);
                        cancelContractModal cCM = new cancelContractModal(list.get(i), response);
                        consoleList.add(cCM);
//       } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);         
                        logger.info("Cancel Request Bulk By :" + request.getSession().getAttribute("userName") + "For Request ID : " + list.get(i));
                    }
                    request.setAttribute("consoleContentCancelRequest", consoleList);

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }
                //  request.setAttribute("consoleContent", consoleList);

                return "forward:/consoleRequest";
            }
        }
    }

    @RequestMapping(value = "deactiveService", method = RequestMethod.POST)
    public String deactiveService(@RequestParam String coId, @RequestParam String snCode) {

        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                String response = "";

                String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=WriteCustomerContractServicesSimpleDN&CO_ID=" + coId + "&COS_PENDING_STATUS=4&SNCODE=" + snCode;
                try {
                    response = invokeWebService(Url); //  "Service Deactivated";
                    request.setAttribute("responseMessage", response);
                    logger.info("Deactivate Service By :" + request.getSession().getAttribute("userName") + " For CoId: " + coId);

                } catch (Exception e) {
                    logger.error("Error Happend " + e.getMessage());
                }

                return "home";
            }

        }
    }

    @RequestMapping(value = "deactivateServiceBulk", method = RequestMethod.POST)
    public String deactivateServiceBulk(@RequestParam MultipartFile csv) {
        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {
                //   String msi = "";
                List<DeactivateServiceModal> consoleList = new ArrayList<DeactivateServiceModal>();
                //  try {
                //      System.out.println(csv.getName());
                // File f = new File("C:\\Users\\V19MFoda\\Desktop\\yara.xlsx");

                try {
                    List<String> list = new ArrayList<String>();
                    FileInputStream fis = (FileInputStream) csv.getInputStream();//new FileInputStream(csv);   //obtaining bytes from the file  

                    list = readExcel(fis);
                    for (int i = 0; i < list.size(); i = i + 2) {
                        //    System.err.println(list.get(i) + "         " + list.get(i + 1));
                        //  System.err.println(list.get(i));

                        String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=WriteCustomerContractServicesSimpleDN&CO_ID="
                                + list.get(i) + "&COS_PENDING_STATUS=4&SNCODE=" + list.get(i + 1);
//       try {
                        String response = invokeWebService(Url); //"Contract Canceled"; //
                        //request.setAttribute("responseMessage", response);
                        DeactivateServiceModal dsM = new DeactivateServiceModal(list.get(i), list.get(i + 1), response);
                        consoleList.add(dsM);
                        logger.info("Deactivate Service Bulk By :" + request.getSession().getAttribute("userName") + " for CoId " + list.get(i) + " SnCode : " + list.get(i + 1));

                    }
//       catch (IOException ex) {
//            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);         
                    //  logger.info("Deactivate Service Bulk By :" + request.getSession().getAttribute("userName"));

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }
                request.setAttribute("consoleContentDeactivateService", consoleList);

                return "forward:/consoleDeactivateService";
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "serviceOnHold")
    public String serviceOnHold(@RequestParam String msi) {
        synchronized (this) {
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {
                List<DeactivateServiceModal> consoleList = new ArrayList<>();

                String response = "";
                String coId = DataBase.getCoId(msi);
                if (!DataBase.checkPendingRequest(coId)) {
                    List<Service> serviceList = DataBase.getAllServices(coId);
                    for (Service s : serviceList) {
                        if (s.getStatus().charAt(0) == 'O' || s.getStatus().charAt(0) == 'O') {
                            System.out.println(s.getSNCode());
                            try {
                                String url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?"
                                        + "username=TEBCO&password=SY&workflowName=WriteCustomerContractServicesSimpleDN&CO_ID=" + coId + "&COS_PENDING_STATUS=4&SNCODE=" + s.getSNCode();
                                response = invokeWebService(url);
                                DeactivateServiceModal dsm = new DeactivateServiceModal(coId, s.getSNCode(), response);
                                consoleList.add(dsm);
                                logger.info("Service OnHold By :" + request.getSession().getAttribute("userName") + " For CoId : " + coId + " SNCode :" + s.getSNCode());

                            } catch (Exception e) {
                                logger.error("Error Happend " + e.getMessage());
                            }

                        }
                    }
                    request.setAttribute("consoleContentDeactivateService", consoleList);

                    return "forward:/consoleDeactivateService";
                } else {
                    request.setAttribute("responseMessage", " The number you entered has no onhold services");
                    logger.info("Service OnHold By :" + request.getSession().getAttribute("userName") + " For CoId : " + coId);

                    return "forward:/home";
                }

            }
        }
    }

    @RequestMapping(value = "activeService", method = RequestMethod.POST)
    public String activeService(@RequestParam String coId,
            @RequestParam String snCode
    ) {

        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                String response = "";

                String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=WriteCustomerContractServicesSimpleDN&CO_ID=" + coId + "&COS_PENDING_STATUS=2&SNCODE=" + snCode;
                try {
                    response = invokeWebService(Url); //  "Service Deactivated";
                    request.setAttribute("responseMessage", response);
                    logger.info("Activate Service  By :" + request.getSession().getAttribute("userName") + " For CoId : " + coId + " SNCode : " + snCode);

                } catch (Exception e) {
                    logger.error("Error Happend " + e.getMessage());
                }

                return "home";
            }

        }
    }

    @RequestMapping(value = "activateServiceBulk", method = RequestMethod.POST)
    public String activateServiceBulk(@RequestParam MultipartFile csv
    ) {
        synchronized (this) {
            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {
                //   String msi = "";
                List<DeactivateServiceModal> consoleList = new ArrayList<DeactivateServiceModal>();
                //  try {
                //      System.out.println(csv.getName());
                // File f = new File("C:\\Users\\V19MFoda\\Desktop\\yara.xlsx");

                try {
                    List<String> list = new ArrayList<String>();
                    FileInputStream fis = (FileInputStream) csv.getInputStream();//new FileInputStream(csv);   //obtaining bytes from the file  

                    list = readExcel(fis);
                    for (int i = 0; i < list.size(); i = i + 2) {
                        //    System.err.println(list.get(i) + "         " + list.get(i + 1));
                        //  System.err.println(list.get(i));

                        String Url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?username=TEBCO&password=SY&workflowName=WriteCustomerContractServicesSimpleDN&CO_ID="
                                + list.get(i) + "&COS_PENDING_STATUS=2&SNCODE=" + list.get(i + 1);
//       try {
                        String response = invokeWebService(Url); //"Contract Canceled"; //
                        //request.setAttribute("responseMessage", response);
                        DeactivateServiceModal dsM = new DeactivateServiceModal(list.get(i), list.get(i + 1), response);
                        consoleList.add(dsM);
                        logger.info("Activate Service Bulk By :" + request.getSession().getAttribute("userName") + " For CoId : " + list.get(i) + " SNCode : " + list.get(i + 1));

                    }
//       catch (IOException ex) {
//            java.util.logging.Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);         
                    //     logger.info("Activate Service Bulk By :" + request.getSession().getAttribute("userName"));

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }
                request.setAttribute("consoleContentDeactivateService", consoleList);

                return "forward:/consoleDeactivateService";
            }
        }
    }

//    _________________________________Testing____________________________________________________________________________
    @RequestMapping(method = RequestMethod.POST, value = "serviceOnHoldBulk")
    public String serviceOnHoldBulk(@RequestParam MultipartFile csv
    ) {
        synchronized (this) {
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {
                List<DeactivateServiceModal> consoleList = new ArrayList<>();
                List<String> numbersList = new ArrayList<String>();
                try {

                    FileInputStream fis = (FileInputStream) csv.getInputStream();//new FileInputStream(csv);   //obtaining bytes from the file

                    numbersList = readExcel(fis);
                    List<List<DeactivateServiceModal>> bigConsoleList = new ArrayList<>();

                    String response = "";
                    for (String number : numbersList) {
                        String coId = DataBase.getCoId(number);
                        if (!DataBase.checkPendingRequest(coId)) {
                            List<Service> serviceList = DataBase.getAllServices(coId);
                            for (Service s : serviceList) {
                                if (s.getStatus().charAt(0) == 'O' || s.getStatus().charAt(0) == 'O') {
                                    //            System.out.println(s.getSNCode());
                                    try {
                                        String url = "http://10.230.91.39:7001/CMS_HTTP_TEST/DoSimpleRequest?"
                                                + "username=TEBCO&password=SY&workflowName=WriteCustomerContractServicesSimpleDN&CO_ID=" + coId + "&COS_PENDING_STATUS=4&SNCODE=" + s.getSNCode();
                                        response = invokeWebService(url);
                                        DeactivateServiceModal dsm = new DeactivateServiceModal(coId, s.getSNCode(), response);
                                        consoleList.add(dsm);
                                        bigConsoleList.add(consoleList);
                                        logger.info("Service OnHold Bulk By :" + request.getSession().getAttribute("userName") + " for Msisdn " + number + " CoId : " + coId + " SNCode : " + s.getSNCode());

                                    } catch (Exception e) {
                                        logger.error("Error Happend " + e.getMessage());
                                    }

                                }
                            }
                        }
                    }

                    request.setAttribute("consoleContentDeactivateServicebulk", bigConsoleList);
                } catch (Exception ex) {
                    logger.error("Error Happened " + ex);
                }
                return "forward:/consoleDeactivateService";

            }
        }
    }
    //    _________________________________Helping Methods____________________________________________________________________________

    private List readExcel(FileInputStream fis) throws IOException {
        synchronized (this) {
            String msi = "";
            List<String> list = new ArrayList<String>();
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
                            //  System.out.print(cell.getStringCellValue() + "\t\t\t");
                            list.add(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  

                            Double doubleValue = cell.getNumericCellValue();
                            BigDecimal bd = new BigDecimal(doubleValue.toString());
                            long lonVal = bd.longValue();
                            msi = Long.toString(lonVal).trim();
//                            _________________________________________________________

//                             boolean doNext = false;
//                             StackTraceElement e[] = Thread.currentThread().getStackTrace();
//   for (StackTraceElement s : e) {
//       if (doNext) {
//          System.out.println(s.getMethodName());
//          if (!msi.startsWith("20")) {
//                                msi = "20" + msi;
//                            }
//       }
//       doNext = s.getMethodName().equals("getStackTrace");
//   }
//                            
//// 
//                            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
//                            _____________________________________________________________
                            if (Thread.currentThread().getStackTrace()[2].getMethodName() == "portOutBulk"
                                    || Thread.currentThread().getStackTrace()[2].getMethodName() == "activateVolte") {
                                if (!msi.startsWith("20")) {
                                    msi = "20" + msi;
                                }

                            }

                            list.add(msi);

                            //  System.out.print(msi + "\t\t\t");
                            break;
                        default:
                    }
                }
            }
            return list;
        }
    }

    private String invokeWebService(String serviceUrl) throws MalformedURLException, IOException {
        StringBuilder response = new StringBuilder();
        synchronized (this) {
            try {
                // Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
                };

                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            } catch (NoSuchAlgorithmException e) {
                //   e.printStackTrace();
                logger.error("Error Happend in invoking service " + e.getMessage());
            } catch (KeyManagementException e) {
                // e.printStackTrace();
                logger.error("Error Happend in invoking service " + e.getMessage());

            }

            URL url = new URL(serviceUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            con.setDoOutput(true);

            //JSON String need to be constructed for the specific resource. 
            //We may construct complex JSON using any third-party JSON libraries such as jackson or org.json
            int code = con.getResponseCode();
            //     System.out.println(code);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                // System.out.println(response.toString());
            }

            return response.toString();
        }
    }

    //__________________________________________________________________________________________________________________
    @RequestMapping(value = "ActivateVolte", method = RequestMethod.POST)
    public String activateVolte(@RequestParam MultipartFile csv) {
        synchronized (this) {

            // System.out.println(coID);
            if (request.getSession().getAttribute("userName") == null) {
                request.setAttribute("message", "Please Login ");
                return "index";
            } else {

                //   String msi = "";
                List<PortModel> consoleList = new ArrayList<PortModel>();

                try {
                    List<String> list = new ArrayList<String>();
                    List<ActivateVolte> volteList = new ArrayList<ActivateVolte>();
                    HashSet<String> callfilterationSet = new HashSet<String>();
                    HashSet<String> rbtSet = new HashSet<String>();
//                    __________________________________________________________________________
                    BufferedReader callFilterBufReader = new BufferedReader(new FileReader("D:\\Volte Activation\\Call Filtration.txt"));

                    String line = callFilterBufReader.readLine();
                    while (line != null) {
                        callfilterationSet.add(line);
                        line = callFilterBufReader.readLine();
                    }
                    callFilterBufReader.close();

//                    _________________________________________________________________________
                    BufferedReader rbtBufReader = new BufferedReader(new FileReader("D:\\Volte Activation\\RBT.txt"));

                    line = rbtBufReader.readLine();
                    while (line != null) {
                        rbtSet.add(line);
                        line = rbtBufReader.readLine();
                    }
                    rbtBufReader.close();

//                    _________________________________________________________________________
                    FileInputStream fis = (FileInputStream) csv.getInputStream();
                    list = readExcel(fis);
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                        volteList.add(new ActivateVolte(list.get(i), ""));

                        //  System.out.println(callfilterationSet.size());
                        //System.out.println(rbtSet.size());
                    }

                    for (int i = 0; i < volteList.size(); i++) {
                        //System.out.println(list.get(i));
                        if (callfilterationSet.contains(volteList.get(i).getMsisdn())) {
                            volteList.get(i).setMessage("This MSISDN Has Call Filteration");
                        }
                        continue;
                        

                    }

//                      ___________________For Loop fo Testing_________________________
                    for (int i = 0; i < volteList.size(); i++) {
                        System.out.println("MSI = " + volteList.get(i).getMsisdn() + " Message = " + volteList.get(i).getMessage());

                    }

                    // logger.info("Port Out Bulk By :" + request.getSession().getAttribute("userName"));
                } catch (Exception e) {
                    //  e.printStackTrace();
                    logger.error("Error Happend " + e.getMessage());
                }
                request.setAttribute("consoleContent", consoleList);

                return "forward:/console";
            }
        }
    }

}
