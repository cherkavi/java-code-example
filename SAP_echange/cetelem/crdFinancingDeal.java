package sap.usb.crd.cetelem;

import java.util.*;
import org.apache.log4j.*;
import com.sap.mw.jco.*;
import sap.usb.tech.*;

public class crdFinancingDeal
    extends sap.usb.crd.abstractLogicalCartridge {
  final String NAME_FOR_LOGGING = this.getClass().getName();

  Logger log = org.apache.log4j.Logger.getLogger(this.NAME_FOR_LOGGING);
  /** Creates a new instance of crdFinancingDeal */
  public crdFinancingDeal() {
  }

  public Vector startProcessing(Vector dataIncoming) {
    log.info("Begin Financing Deal ");
    log.debug("Incoming Vector: " + dataIncoming.toString());
    String errMsg = "";
    Vector dataIn = new Vector(2);
    Vector dataReturn = new Vector(2);
    Vector<Vector<Hashtable<String, String>>> dataOut = new Vector(1);
    try {
      errMsg = (String) dataIncoming.get(0);
      if (errMsg.length() > 0) {
        log.error("Incoming data have error element: " + "'" + errMsg + "'");
        return dataIncoming; //nothing to do
      }
      dataIn = (Vector) dataIncoming.get(1);
      dataOut = financingDeal(dataIn);
    }
    catch (Exception ex) {
      setLastError(NAME_FOR_LOGGING + ": " + ex.toString());
    }
    errMsg = (errMsg + getLastError()).trim();
    if (errMsg.length() > 0) {
      log.error("SAP Error: " + "'" + errMsg + "'");
    }
    dataReturn.add(0, errMsg); //0 element have error status
    dataReturn.add(1, dataOut); //
    log.debug("Outgoing Vector: " + dataReturn.toString());
    log.info("End Financing Deal ");
    return dataReturn;
  }

  /**
   * financingDeal - financing of Deal in SAP
   *
   * @param data Vector
   * @return Vector
   * @throws Exception
   */
  private Vector<Vector<Hashtable<String, String>>>
      financingDeal(Vector<Hashtable<String, String>> data) throws Exception {
    JCO.Client connection = null;
    Vector<Hashtable<String, String>> returnDeal = null;
    Vector<Hashtable<String, String>> errorDeal = null;
    Vector<Vector<Hashtable<String, String>>> response = new Vector(2);
    try {
      GatewaySAPConnector connector = new GatewaySAPConnector(
          GatewaySAPConnector.
          CONNECT_TO_R3_SYSTEM);
      connection = connector.getConnection();
      JCO.Function function = connector.getFunction("ZCML_FINANCING_RFC");
      JCO.Table itDataFin = function.getImportParameterList().getTable(
          "IT_DATA_OF_FIN");
      for (int i = 0; i < data.size(); i++) {
        Hashtable<String, String> inht = data.get(i);
        itDataFin.appendRow();
        String tableName = inht.get("TABLE_NAME");
        String numStr = inht.get("NUM_STR");
        String fieldName = inht.get("FIELD_NAME");
        String fieldValue = inht.get("FIELD_VALUE");
        if (fieldName == null || tableName == null || numStr == null) {
          throw new Exception("Incorrect datastructure from Rata@Net");
        }
        if (fieldValue == null) {
          fieldValue = "";
        }
        itDataFin.setValue(tableName, "TABLE_NAME");
        itDataFin.setValue(numStr, "NUM_STR");
        itDataFin.setValue(fieldName, "FIELD_NAME");
        itDataFin.setValue(fieldValue, "FIELD_VALUE");
      }
      connection.execute(function);
      JCO.Table itReturnFinDeal = function.getExportParameterList().getTable(
          "ET_RETURN");
      if (!itReturnFinDeal.isEmpty()) {
        returnDeal = new Vector();
        for (int i = 0; i < itReturnFinDeal.getNumRows(); i++) {
          itReturnFinDeal.setRow(i);
          Hashtable<String, String> htResponse = new Hashtable(2);
          String fieldName = itReturnFinDeal.getString("FIELD_NAME");
          String fieldValue = itReturnFinDeal.getString("FIELD_VALUE");
          if (fieldName == null) {
            throw new Exception("Incorrect datastructure from SAP");
          }
          if (fieldValue == null) {
            fieldValue = "";
          }
          htResponse.put("FIELD_NAME", fieldName);
          htResponse.put("FIELD_VALUE", fieldValue);
          returnDeal.add(htResponse);
        }
      }
      JCO.Table itErrorFinDeal = function.getExportParameterList().getTable(
          "ET_ERROR");
      if (!itErrorFinDeal.isEmpty()) {
        errorDeal = new Vector();
        for (int i = 0; i < itErrorFinDeal.getNumRows(); i++) {
          itErrorFinDeal.setRow(i);
          Hashtable<String, String> htErrResponse = new Hashtable(2);
          String errID = itErrorFinDeal.getString("ERROR_ID");
          String errDescription = itErrorFinDeal.getString("ERROR_TXT");
          if (errID == null) {
            throw new Exception("Incorrect error datastructure from SAP");
          }
          if (errDescription == null) {
            errDescription = "There is error from SAP but it's null";
          }
          htErrResponse.put("ERROR_ID", errID);
          htErrResponse.put("ERROR_TXT", errDescription);
          errorDeal.add(htErrResponse);
        }
        log.error("SAP Errors Vector: " + errorDeal.toString());
      }
    }
    catch (Exception e) {
      throw new Exception(e);
    }
    finally {
      if (connection != null) {
        JCO.releaseClient(connection); //release the SAP connection back to the pool
      }
    }
    response.add(0, errorDeal);
    response.add(1, returnDeal);
    return response;
  }

//------------------------------------------------------------------------------
  public static void main(String[] args) {
//fill input params
    Vector vecIn = new Vector(2);
    Hashtable<String, String> ht = new Hashtable(4);
    Vector<Hashtable> v = new Vector();


    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "RANL");
    ht.put("FIELD_VALUE", "0090000002000");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "PARTNER");
    ht.put("FIELD_VALUE", "0000150340");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BANKK");
    ht.put("FIELD_VALUE", "");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BANKS");
    ht.put("FIELD_VALUE", "UA");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "ACCOUNT");
    ht.put("FIELD_VALUE", "26250000982762");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "AMOUNT");
    ht.put("FIELD_VALUE", "78000.0000000");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "WAERS");
    ht.put("FIELD_VALUE", "UAH");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "WITHDRAWAL");
    ht.put("FIELD_VALUE", "");
    v.add(ht);

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "TEST_RUN");
    ht.put("FIELD_VALUE", "");
    v.add(ht);

//////////////////////

    ht = new Hashtable();
    ht.put("TABLE_NAME", "FIN");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "EV_RESULT");
    ht.put("FIELD_VALUE", "");
    v.add(ht);


///////////////////////

    vecIn.add(0, ""); //errMsg
    vecIn.add(1, v);

//run crd
    crdFinancingDeal crd = new crdFinancingDeal();
    System.out.println(vecIn.toString());
    Vector vecRes = crd.startProcessing(vecIn);
    System.out.println(vecRes.toString());

  }
//------------------------------------------------------------------------------



}
