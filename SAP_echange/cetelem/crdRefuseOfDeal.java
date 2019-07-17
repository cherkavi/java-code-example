package sap.usb.crd.cetelem;

import java.util.*;
import org.apache.log4j.*;
import com.sap.mw.jco.*;
import sap.usb.tech.*;

public class crdRefuseOfDeal
    extends sap.usb.crd.abstractLogicalCartridge {
  final String NAME_FOR_LOGGING = this.getClass().getName();

  Logger log = org.apache.log4j.Logger.getLogger(this.NAME_FOR_LOGGING);
  /** Creates a new instance of crdGetBusinessPartnerData */
  public crdRefuseOfDeal() {
  }

  public Vector startProcessing(Vector dataIncoming) {
    log.info("Begin RefuseOfDeal ");
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
      dataOut = refuseOfDeal(dataIn);
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
    log.info("End RefuseOfDeal ");
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
      refuseOfDeal(Vector<Hashtable<String, String>> data) throws Exception {
    JCO.Client connection = null;
    Vector<Hashtable<String, String>> returnDeal = null;
    Vector<Hashtable<String, String>> errorDeal = null;
    Vector<Vector<Hashtable<String, String>>> response = new Vector(2);
    try {
      GatewaySAPConnector connector = new GatewaySAPConnector(
          GatewaySAPConnector.
          CONNECT_TO_R3_SYSTEM);
      connection = connector.getConnection();
      JCO.Function function = connector.getFunction("ZCML_REFUSE_RFC");
      JCO.Table itDataDeal = function.getImportParameterList().getTable(
          "IT_DATA_OF_DEAL");
      for (int i = 0; i < data.size(); i++) {
        Hashtable<String, String> inht = data.get(i);
        itDataDeal.appendRow();
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
        itDataDeal.setValue(tableName, "TABLE_NAME");
        itDataDeal.setValue(numStr, "NUM_STR");
        itDataDeal.setValue(fieldName, "FIELD_NAME");
        itDataDeal.setValue(fieldValue, "FIELD_VALUE");
      }
      connection.execute(function);
      JCO.Table itReturnDeal = function.getExportParameterList().getTable(
          "ET_RETURN");
      if (!itReturnDeal.isEmpty()) {
        returnDeal = new Vector();
        for (int i = 0; i < itReturnDeal.getNumRows(); i++) {
          itReturnDeal.setRow(i);
          Hashtable<String, String> htResponse = new Hashtable(2);
          String fieldName = itReturnDeal.getString("FIELD_NAME");
          String fieldValue = itReturnDeal.getString("FIELD_VALUE");
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
      JCO.Table itErrorDeal = function.getExportParameterList().getTable(
          "ET_ERROR");
      if (!itErrorDeal.isEmpty()) {
        errorDeal = new Vector();
        for (int i = 0; i < itErrorDeal.getNumRows(); i++) {
          itErrorDeal.setRow(i);
          Hashtable<String, String> htErrResponse = new Hashtable(2);
          String errID = itErrorDeal.getString("ERROR_ID");
          String errDescription = itErrorDeal.getString("ERROR_TXT");
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

    ht.put("TABLE_NAME", "VDARL");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "RANL");
    ht.put("FIELD_VALUE", "0090000003000");
    v.add(ht);
    ht = new Hashtable(4);

    vecIn.add(0, ""); //errMsg
    vecIn.add(1, v);

//run crd
    crdRefuseOfDeal crd = new crdRefuseOfDeal();
    Vector vecRes = crd.startProcessing(vecIn);
    System.out.println(vecRes.toString());

  }
//------------------------------------------------------------------------------



}
