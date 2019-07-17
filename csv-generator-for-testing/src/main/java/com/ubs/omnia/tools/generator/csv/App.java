package com.ubs.omnia.tools.generator.csv;

import com.ubs.omnia.tools.generator.csv.domain.Column;
import com.ubs.omnia.tools.generator.csv.domain.Line;
import com.ubs.omnia.tools.generator.csv.persist.Saver;
import com.ubs.omnia.tools.generator.csv.persist.TextDelimiterSaver;
import com.ubs.omnia.tools.generator.csv.value.AppendGenerator;
import com.ubs.omnia.tools.generator.csv.value.ConstGenerator;
import com.ubs.omnia.tools.generator.csv.value.ConstSequenceGenerator;
import com.ubs.omnia.tools.generator.csv.value.IntegerGenerator;

/**
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println(" --- begin --- ");
        String absoluteDraftFile="/tmp/draft.csv";
        String absoluteAlertFile="/tmp/alert.csv";
        int size=4;
        int alertCount=5;
        
        IntegerGenerator clientGenerator=new IntegerGenerator(6, 900000);
        IntegerGenerator orderGenerator=new IntegerGenerator(9, 900000000);
        IntegerGenerator statementGenerator=new IntegerGenerator(9, 900000000);

        Column columnClientId=new Column("CLIENT_ID", new AppendGenerator(new ConstGenerator("CLIENT"), clientGenerator));
        Column columnOrderId=new Column("ORDER_ID", new AppendGenerator(new ConstGenerator("ORDER000"), orderGenerator));
        Column columnProductId=new Column("PRODUCT_ID",new ConstGenerator("SN01"));
        Column columnStatementId=new Column("STATEMENT_ID", new AppendGenerator(new ConstGenerator("STATEMENT"), statementGenerator));
        Column columnStatementCreatedDate=new Column("STATEMENT_CREATED_DATE", new ConstGenerator("20140516"));
        
        Line draft=new Line(columnClientId, columnOrderId, columnProductId, columnStatementId,  
        		columnStatementCreatedDate, 
        		new Column("STATEMENT_STATUS", new ConstGenerator("18")),
				new Column("SN_COMMENTS", new ConstGenerator("0")),
				new Column("DONT_CLEAN", new ConstGenerator("0")),
				new Column("BATCH_ORDER", new ConstGenerator("1")),
				new Column("REPORT_YEAR", new ConstGenerator("2014")),
				new Column("PERIOD_START", new ConstGenerator("20140101")),
				new Column("PERIOD_END", new ConstGenerator("20141231")),
				new Column("TAX_COUNTRY", new ConstGenerator("CH")),
				new Column("TAX_CANTON", new ConstGenerator("ZH"))
        );

        IntegerGenerator alertGenerator=new IntegerGenerator(7, 9000000);
        ConstSequenceGenerator alertTypeGenerator=new ConstSequenceGenerator("288","287","251","4004","288");
        ConstSequenceGenerator isinGenerator=new ConstSequenceGenerator("CH0181041325","CH0134707006","CH0134707006");
        ConstSequenceGenerator valorGenerator=new ConstSequenceGenerator("18104132","13470700","13470700","60903483");
        ConstSequenceGenerator valorTypeGenerator=new ConstSequenceGenerator("","V1");
        
        
        Line alert=new Line(
        		new Column("ALERT_ID", new AppendGenerator(new ConstGenerator("ALERT"), alertGenerator)),
        		new Column("ALERT_TYPE", alertTypeGenerator),
        		columnStatementId,
        		columnStatementCreatedDate,
        		new Column("ISIN", isinGenerator),
        		new Column("VALOR", valorGenerator),
        		new Column("VALOR_TYPE", valorTypeGenerator),
        		columnClientId,
        		columnOrderId,
        		columnProductId,
        		new Column("CLIENT_OBJECT_ID", new ConstGenerator("0206.73.AA559384.0"))
        );

        Saver draftFile=new TextDelimiterSaver(absoluteDraftFile, draft);
        draftFile.init();
        Saver alertFile=new TextDelimiterSaver(absoluteAlertFile, alert);
        alertFile.init();
        
        for( int index=0; index<size; index++ ){
            clientGenerator.next();
            orderGenerator.next();
            statementGenerator.next();
            draftFile.add(draft);
            
        	for( int indexAlert=0; indexAlert<alertCount; indexAlert++ ){
                alertGenerator.next();
                alertTypeGenerator.next();
                isinGenerator.next();
                valorGenerator.next();
                valorTypeGenerator.next();
        		alertFile.add(alert);
        	}
        	
        }
        
        
        draftFile.destroy();
        alertFile.destroy();
        System.out.println(" ---  end  --- ");
    }
}
