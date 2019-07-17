package sap.usb.crd.cetelem;

import java.util.*;
import org.apache.log4j.*;
import com.sap.mw.jco.*;
import sap.usb.tech.*;

public class crdCreateBusinessPartner
    extends sap.usb.crd.abstractLogicalCartridge {
  final String NAME_FOR_LOGGING = this.getClass().getName();

  Logger log = org.apache.log4j.Logger.getLogger(this.NAME_FOR_LOGGING);
  /** Creates a new instance of crdGetBusinessPartnerData */
  public crdCreateBusinessPartner() {
  }

  public Vector startProcessing(Vector dataIncoming) {
    log.info("Begin Create Business Partner ");
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
      dataOut = createBP(dataIn);
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
   * createBP - creation of Business Partner in SAP
   *
   * @param data Vector
   * @return Vector
   * @throws Exception
   */
  private Vector<Vector<Hashtable<String, String>>>
      createBP(Vector<Hashtable<String, String>> data) throws Exception {
    JCO.Client connection = null;
    Vector<Hashtable<String, String>> returnBP = null;
    Vector<Hashtable<String, String>> errorBP = null;
    Vector<Vector<Hashtable<String, String>>> response = new Vector(2);
    try {
      GatewaySAPConnector connector = new GatewaySAPConnector(
          GatewaySAPConnector.
          CONNECT_TO_R3_SYSTEM);
      connection = connector.getConnection();
      JCO.Function function = connector.getFunction("ZCML_IMPORT_BP_RFC");
      JCO.Table itDataBP = function.getImportParameterList().getTable(
          "IT_DATA_OF_BP");
      for (int i = 0; i < data.size(); i++) {
        Hashtable<String, String> inht = data.get(i);
        itDataBP.appendRow();
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
        itDataBP.setValue(tableName, "TABLE_NAME");
        itDataBP.setValue(numStr, "NUM_STR");
        itDataBP.setValue(fieldName, "FIELD_NAME");
        itDataBP.setValue(fieldValue, "FIELD_VALUE");
      }
      connection.execute(function);
      JCO.Table itReturnBP = function.getExportParameterList().getTable(
          "ET_RETURN");
      if (!itReturnBP.isEmpty()) {
        returnBP = new Vector();
        for (int i = 0; i < itReturnBP.getNumRows(); i++) {
          itReturnBP.setRow(i);
          Hashtable<String, String> htResponse = new Hashtable(4);
          String tableName = itReturnBP.getString("TABLE_NAME");
          String numStr = itReturnBP.getString("NUM_STR");
          String fieldName = itReturnBP.getString("FIELD_NAME");
          String fieldValue = itReturnBP.getString("FIELD_VALUE");
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
          returnBP.add(htResponse);
        }
      }
      JCO.Table itErrorBP = function.getExportParameterList().getTable(
          "ET_ERROR");
      if (!itErrorBP.isEmpty()) {
        errorBP = new Vector();
        for (int i = 0; i < itErrorBP.getNumRows(); i++) {
          itErrorBP.setRow(i);
          Hashtable<String, String> htErrResponse = new Hashtable(2);
          String errID = itErrorBP.getString("ERROR_ID");
          String errDescription = itErrorBP.getString("ERROR_TXT");
          if (errID == null) {
            throw new Exception("Incorrect error datastructure from SAP");
          }
          if (errDescription == null) {
            errDescription = "There is error from SAP but it's null";
          }
          htErrResponse.put("ERROR_ID", errID);
          htErrResponse.put("ERROR_TXT", errDescription);
          errorBP.add(htErrResponse);
        }
        log.error("SAP Errors Vector: " + errorBP.toString());
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
    response.add(0, errorBP);
    response.add(1, returnBP);
    return response;
  }

//------------------------------------------------------------------------------
  public static void main(String[] args) {
//fill input params
    Vector vecIn = new Vector(2);
    Hashtable<String, String> ht = new Hashtable(4);
    Vector<Hashtable> v = new Vector();

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BPKIND");
    ht.put("FIELD_VALUE", "0001");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "TYPE");
    ht.put("FIELD_VALUE", "1");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "NAME_FIRST");
    ht.put("FIELD_VALUE", "Testov");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "NAME_LAST");
    ht.put("FIELD_VALUE", "Test");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "NICKNAME");
    ht.put("FIELD_VALUE", "Testerovich");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BIRTHDT");
    ht.put("FIELD_VALUE", "19700101");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "XSEXM");
    ht.put("FIELD_VALUE", "X");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "BIRTHPL");
    ht.put("FIELD_VALUE", "Gdeto rodom");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT000");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "SOURCE");
    ht.put("FIELD_VALUE", "0008");
    v.add(ht);
    ht = new Hashtable(4);

//// doc

    ht.put("TABLE_NAME", "BUT0ID");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "TYPE");
    ht.put("FIELD_VALUE", "ZS0011");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT0ID");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "IDNUMBER");
    ht.put("FIELD_VALUE", "8888888888");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT0ID");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "INSTITUTE");
    ht.put("FIELD_VALUE", "Nalogovaya");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT0ID");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "TYPE");
    ht.put("FIELD_VALUE", "ZS0021");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT0ID");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "IDNUMBER");
    ht.put("FIELD_VALUE", "¿¿π123456");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "BUT0ID");
    ht.put("NUM_STR", "2");
    ht.put("FIELD_NAME", "INSTITUTE");
    ht.put("FIELD_VALUE", "Kievskoe ROUMVD");
    v.add(ht);
    ht = new Hashtable(4);

//// adrrrs

    ht.put("TABLE_NAME", "ADRC");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "HOUSE_NUM1");
    ht.put("FIELD_VALUE", "1");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ADRC");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "STREET");
    ht.put("FIELD_VALUE", "Lenina");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ADRC");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "CITY1");
    ht.put("FIELD_VALUE", "Kiiv");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ADRC");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "REGION");
    ht.put("FIELD_VALUE", "1");
    v.add(ht);
    ht = new Hashtable(4);

    ht.put("TABLE_NAME", "ADRC");
    ht.put("NUM_STR", "1");
    ht.put("FIELD_NAME", "POST_CODE1");
    ht.put("FIELD_VALUE", "07800");
    v.add(ht);
    ht = new Hashtable(4);

    vecIn.add(0, ""); //errMsg
    vecIn.add(1, v);

//run crd
    crdCreateBusinessPartner crd = new crdCreateBusinessPartner();
    Vector vecRes = crd.startProcessing(vecIn);
    System.out.println(vecRes.toString());

  }
//------------------------------------------------------------------------------



}
