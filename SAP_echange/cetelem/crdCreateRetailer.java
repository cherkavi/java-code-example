package sap.usb.crd.cetelem;

import java.util.*;
import org.apache.log4j.*;
import com.sap.mw.jco.*;
import sap.usb.tech.*;

public class crdCreateRetailer
    extends sap.usb.crd.abstractLogicalCartridge {
  final String NAME_FOR_LOGGING = this.getClass().getName();

  Logger log = org.apache.log4j.Logger.getLogger(this.NAME_FOR_LOGGING);
  /** Creates a new instance of crdGetBusinessPartnerData */
  public crdCreateRetailer() {
  }

  public Vector startProcessing(Vector dataIncoming) {
    log.info("Begin Create Retailer ");
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
      dataIn= (Vector) dataIncoming.get(1);
      dataOut = createRetailer(dataIn);
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
    log.info("End Create Business Partner ");
    return dataReturn;
  }

  /**
   * createRetailer - creation of Retailer in SAP
   *
   * @param data Vector
   * @return Vector
   * @throws Exception
   */
  private Vector<Vector<Hashtable<String, String>>>
      createRetailer(Vector<Hashtable<String, String>> data) throws Exception {
    JCO.Client connection = null;
    Vector<Hashtable<String, String>> returnRetailer = null;
    Vector<Hashtable<String, String>> errorRetailer = null;
    Vector<Vector<Hashtable<String, String>>> response = new Vector(2);
    try {
      GatewaySAPConnector connector = new GatewaySAPConnector(
          GatewaySAPConnector.
          CONNECT_TO_R3_SYSTEM);
      connection = connector.getConnection();
      JCO.Function function = connector.getFunction("ZRTN_IMPORT_RET_RFC");
      JCO.Table itDataRetailer = function.getImportParameterList().getTable(
          "IT_DATA_OF_RET");
      for (int i = 0; i < data.size(); i++) {
        Hashtable<String, String> inht = data.get(i);
        itDataRetailer.appendRow();
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
        itDataRetailer.setValue(tableName, "TABLE_NAME");
        itDataRetailer.setValue(numStr, "NUM_STR");
        itDataRetailer.setValue(fieldName, "FIELD_NAME");
        itDataRetailer.setValue(fieldValue, "FIELD_VALUE");
      }
      connection.execute(function);
      JCO.Table itReturnRetailer = function.getExportParameterList().getTable(
          "ET_RETURN");
      if (!itReturnRetailer.isEmpty()) {
        returnRetailer = new Vector();
        for (int i = 0; i < itReturnRetailer.getNumRows(); i++) {
          itReturnRetailer.setRow(i);
          Hashtable<String, String> htResponse = new Hashtable(4);
//          String tableName = itReturnBP.getString("TABLE_NAME");
//          String numStr = itReturnBP.getString("NUM_STR");
          String fieldName = itReturnRetailer.getString("FIELD_NAME");
          String fieldValue = itReturnRetailer.getString("FIELD_VALUE");
          if (fieldName == null
//              || tableName == null || numStr == null
              ) {
            throw new Exception("Incorrect datastructure from SAP");
          }
          if (fieldValue == null) {
            fieldValue = "";
          }
//          htResponse.put("TABLE_NAME", tableName);
//          htResponse.put("NUM_STR", numStr);
          htResponse.put("FIELD_NAME", fieldName);
          htResponse.put("FIELD_VALUE", fieldValue);
          returnRetailer.add(htResponse);
        }
      }
      JCO.Table itErrorRetailer = function.getExportParameterList().getTable(
          "ET_ERROR");
      if (!itErrorRetailer.isEmpty()) {
        errorRetailer = new Vector();
        for (int i = 0; i < itErrorRetailer.getNumRows(); i++) {
          itErrorRetailer.setRow(i);
          Hashtable<String, String> htErrResponse = new Hashtable(2);
          String errID = itErrorRetailer.getString("ERROR_ID");
          String errDescription = itErrorRetailer.getString("ERROR_TXT");
          if (errID == null) {
            throw new Exception("Incorrect error datastructure from SAP");
          }
          if (errDescription == null) {
            errDescription = "There is error from SAP but it's null";
          }
          htErrResponse.put("ERROR_ID", errID);
          htErrResponse.put("ERROR_TXT", errDescription);
          errorRetailer.add(htErrResponse);
        }
        log.error("SAP Errors Vector: " + errorRetailer.toString());
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
    response.add(0, errorRetailer);
    response.add(1, returnRetailer);
    return response;
  }

//------------------------------------------------------------------------------
  public static void main(String[] args) {
//fill input params
    Vector vecIn = new Vector(2);
    Hashtable<String, String> ht = new Hashtable(4);
    Vector<Hashtable> v = new Vector();

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RETAILORS");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

////////// fin

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

////////// kom

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ZRTN_RET_ACCOUNT");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "BUKRS");
    ht.put("FIELD_VALUE", "1000");
    v.add(ht);
    ht = new Hashtable(4);

///////////


    vecIn.add(0, ""); //errMsg
    vecIn.add(1, v);

//run crd
    crdCreateRetailer crd = new crdCreateRetailer();
    Vector vecRes = crd.startProcessing(vecIn);
    System.out.println(vecRes.toString());

  }
//------------------------------------------------------------------------------



}
