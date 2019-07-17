package sap.usb.crd.cetelem;

import java.util.*;
import org.apache.log4j.*;
import com.sap.mw.jco.*;
import sap.usb.tech.*;

public class crdGetDictionary
    extends sap.usb.crd.abstractLogicalCartridge {
  final String NAME_FOR_LOGGING = this.getClass().getName();

  Logger log = org.apache.log4j.Logger.getLogger(this.NAME_FOR_LOGGING);
  /** Creates a new instance of crdGetDictionary */
  public crdGetDictionary() {
  }

  public Vector startProcessing(Vector dataIncoming) {
    log.info("Begin GetDictionary ");
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
      dataOut = getDictionary(dataIn);
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
    log.info("End GetDictionary ");
    return dataReturn;
  }

  /**
   * createBP - creation of Business Partner in SAP
   *
   * @param data Vector
   * @return Vector
   * @throws Exception
   */
  private Vector<Vector<Hashtable<String, String>>>
      getDictionary(Vector<Hashtable<String, String>> data) throws Exception {
    JCO.Client connection = null;
    Vector<Hashtable<String, String>> returnDictionary = null;
    Vector<Hashtable<String, String>> errorDictionary = null;
    Vector<Vector<Hashtable<String, String>>> response = new Vector(2);
    try {
      GatewaySAPConnector connector = new GatewaySAPConnector(
          GatewaySAPConnector.
          CONNECT_TO_R3_SYSTEM);
      connection = connector.getConnection();
      JCO.Function function = connector.getFunction("ZCML_GET_ROWS_OF_TABLE");
      JCO.ParameterList input = function.getImportParameterList();
      String incTableName = null;
      String incDomenName = null;
      for (int i = 0; i < data.size(); i++) {
        Hashtable<String, String> inht = data.get(i);
        String fieldName = inht.get("FIELD_NAME");
        String fieldValue = inht.get("FIELD_VALUE");
        if (fieldName == null) {
          throw new Exception("Incorrect datastructure from Rata@Net");
        }
        if (fieldValue != null && fieldName.equalsIgnoreCase("I_TABNAME")) {
          incTableName = fieldValue;
        }
        if (fieldValue != null && fieldName.equalsIgnoreCase("I_DOMNAME")) {
          incDomenName = fieldValue;
        }
      }
      if (incTableName == null || incDomenName == null) {
        throw new Exception("Incorrect datastructure from Rata@Net");
      }
      input.setValue(incTableName, "I_TABNAME");
      input.setValue(incDomenName, "I_DOMNAME");
      connection.execute(function);
      JCO.Table itReturnDictionary = function.getExportParameterList().getTable(
          "E_DATA");
      if (!itReturnDictionary.isEmpty()) {
        returnDictionary = new Vector();
        for (int i = 0; i < itReturnDictionary.getNumRows(); i++) {
          itReturnDictionary.setRow(i);
          Hashtable<String, String> htResponse = new Hashtable(4);
          String tableName = itReturnDictionary.getString("TABLE_NAME");
          String numStr = itReturnDictionary.getString("NUM_STR");
          String fieldName = itReturnDictionary.getString("FIELD_NAME");
          String fieldValue = itReturnDictionary.getString("FIELD_VALUE");
          if (fieldName == null || tableName == null || numStr == null) {
            throw new Exception("Incorrect datastructure from SAP");
          }
          if (fieldValue == null) {
            fieldValue = "";
          }
          htResponse.put("TABLE_NAME", tableName);
          htResponse.put("NUM_STR", numStr);
          htResponse.put("FIELD_NAME", fieldName);
          htResponse.put("FIELD_VALUE", fieldValue);
          returnDictionary.add(htResponse);
        }
      }

/*
      JCO.Table itErrorDictionary = function.getExportParameterList().getTable(
          "ET_ERROR");
      if (!itErrorDictionary.isEmpty()) {
        errorDictionary = new Vector();
        for (int i = 0; i < itErrorDictionary.getNumRows(); i++) {
          itErrorDictionary.setRow(i);
          Hashtable<String, String> htErrResponse = new Hashtable(2);
          String errID = itErrorDictionary.getString("ERROR_ID");
          String errDescription = itErrorDictionary.getString("ERROR_TXT");
          if (errID == null) {
            throw new Exception("Incorrect error datastructure from SAP");
          }
          if (errDescription == null) {
            errDescription = "There is error from SAP but it's null";
          }
          htErrResponse.put("ERROR_ID", errID);
          htErrResponse.put("ERROR_TXT", errDescription);
          errorDictionary.add(htErrResponse);
        }
        log.error("SAP Errors Vector: " + errorDictionary.toString());
      }
  */

    }
    catch (Exception e) {
      throw new Exception(e);
    }
    finally {
      if (connection != null) {
        JCO.releaseClient(connection); //release the SAP connection back to the pool
      }
    }
    response.add(0, errorDictionary);
    response.add(1, returnDictionary);
    return response;
  }

//------------------------------------------------------------------------------
  public static void main(String[] args) {
//fill input params
    Vector vecIn = new Vector(2);
    Hashtable<String, String> ht = new Hashtable(4);
    Vector<Hashtable> v = new Vector();

    ht.put("FIELD_NAME", "I_TABNAME");
    ht.put("FIELD_VALUE", "TF AC_S");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("FIELD_NAME", "I_DOMNAME");
    ht.put("FIELD_VALUE", "");
    v.add(ht);
    ht = new Hashtable(4);

    vecIn.add(0, ""); //errMsg
    vecIn.add(1, v);

//run crd
    crdGetDictionary crd = new crdGetDictionary();
    Vector vecRes = crd.startProcessing(vecIn);
    System.out.println(vecRes.toString());

  }
//------------------------------------------------------------------------------



}
